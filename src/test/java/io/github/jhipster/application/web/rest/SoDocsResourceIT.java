package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SoDocsApp;
import io.github.jhipster.application.domain.SoDocs;
import io.github.jhipster.application.repository.SoDocsRepository;
import io.github.jhipster.application.service.SoDocsService;
import io.github.jhipster.application.service.dto.SoDocsDTO;
import io.github.jhipster.application.service.mapper.SoDocsMapper;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SoDocsResource} REST controller.
 */
@SpringBootTest(classes = SoDocsApp.class)
public class SoDocsResourceIT {

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_UPLOAD_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_UPLOAD_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_UPLOAD_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_UPLOAD_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_UPLOAD_FILE_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_FILE_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIVATE_MODE = false;
    private static final Boolean UPDATED_PRIVATE_MODE = true;

    @Autowired
    private SoDocsRepository soDocsRepository;

    @Autowired
    private SoDocsMapper soDocsMapper;

    @Autowired
    private SoDocsService soDocsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSoDocsMockMvc;

    private SoDocs soDocs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SoDocsResource soDocsResource = new SoDocsResource(soDocsService);
        this.restSoDocsMockMvc = MockMvcBuilders.standaloneSetup(soDocsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoDocs createEntity(EntityManager em) {
        SoDocs soDocs = new SoDocs()
            .fileName(DEFAULT_FILE_NAME)
            .uploadFile(DEFAULT_UPLOAD_FILE)
            .uploadFileContentType(DEFAULT_UPLOAD_FILE_CONTENT_TYPE)
            .uploadFileContentType(DEFAULT_UPLOAD_FILE_CONTENT_TYPE)
            .privateMode(DEFAULT_PRIVATE_MODE);
        return soDocs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoDocs createUpdatedEntity(EntityManager em) {
        SoDocs soDocs = new SoDocs()
            .fileName(UPDATED_FILE_NAME)
            .uploadFile(UPDATED_UPLOAD_FILE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE)
            .privateMode(UPDATED_PRIVATE_MODE);
        return soDocs;
    }

    @BeforeEach
    public void initTest() {
        soDocs = createEntity(em);
    }

    @Test
    @Transactional
    public void createSoDocs() throws Exception {
        int databaseSizeBeforeCreate = soDocsRepository.findAll().size();

        // Create the SoDocs
        SoDocsDTO soDocsDTO = soDocsMapper.toDto(soDocs);
        restSoDocsMockMvc.perform(post("/api/so-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soDocsDTO)))
            .andExpect(status().isCreated());

        // Validate the SoDocs in the database
        List<SoDocs> soDocsList = soDocsRepository.findAll();
        assertThat(soDocsList).hasSize(databaseSizeBeforeCreate + 1);
        SoDocs testSoDocs = soDocsList.get(soDocsList.size() - 1);
        assertThat(testSoDocs.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testSoDocs.getUploadFile()).isEqualTo(DEFAULT_UPLOAD_FILE);
        assertThat(testSoDocs.getUploadFileContentType()).isEqualTo(DEFAULT_UPLOAD_FILE_CONTENT_TYPE);
        assertThat(testSoDocs.getUploadFileContentType()).isEqualTo(DEFAULT_UPLOAD_FILE_CONTENT_TYPE);
        assertThat(testSoDocs.isPrivateMode()).isEqualTo(DEFAULT_PRIVATE_MODE);
    }

    @Test
    @Transactional
    public void createSoDocsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = soDocsRepository.findAll().size();

        // Create the SoDocs with an existing ID
        soDocs.setId(1L);
        SoDocsDTO soDocsDTO = soDocsMapper.toDto(soDocs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoDocsMockMvc.perform(post("/api/so-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soDocsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoDocs in the database
        List<SoDocs> soDocsList = soDocsRepository.findAll();
        assertThat(soDocsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSoDocs() throws Exception {
        // Initialize the database
        soDocsRepository.saveAndFlush(soDocs);

        // Get all the soDocsList
        restSoDocsMockMvc.perform(get("/api/so-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soDocs.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].uploadFileContentType").value(hasItem(DEFAULT_UPLOAD_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].uploadFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_UPLOAD_FILE))))
            .andExpect(jsonPath("$.[*].uploadFileContentType").value(hasItem(DEFAULT_UPLOAD_FILE_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].privateMode").value(hasItem(DEFAULT_PRIVATE_MODE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getSoDocs() throws Exception {
        // Initialize the database
        soDocsRepository.saveAndFlush(soDocs);

        // Get the soDocs
        restSoDocsMockMvc.perform(get("/api/so-docs/{id}", soDocs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(soDocs.getId().intValue()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.uploadFileContentType").value(DEFAULT_UPLOAD_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.uploadFile").value(Base64Utils.encodeToString(DEFAULT_UPLOAD_FILE)))
            .andExpect(jsonPath("$.uploadFileContentType").value(DEFAULT_UPLOAD_FILE_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.privateMode").value(DEFAULT_PRIVATE_MODE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSoDocs() throws Exception {
        // Get the soDocs
        restSoDocsMockMvc.perform(get("/api/so-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSoDocs() throws Exception {
        // Initialize the database
        soDocsRepository.saveAndFlush(soDocs);

        int databaseSizeBeforeUpdate = soDocsRepository.findAll().size();

        // Update the soDocs
        SoDocs updatedSoDocs = soDocsRepository.findById(soDocs.getId()).get();
        // Disconnect from session so that the updates on updatedSoDocs are not directly saved in db
        em.detach(updatedSoDocs);
        updatedSoDocs
            .fileName(UPDATED_FILE_NAME)
            .uploadFile(UPDATED_UPLOAD_FILE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE)
            .uploadFileContentType(UPDATED_UPLOAD_FILE_CONTENT_TYPE)
            .privateMode(UPDATED_PRIVATE_MODE);
        SoDocsDTO soDocsDTO = soDocsMapper.toDto(updatedSoDocs);

        restSoDocsMockMvc.perform(put("/api/so-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soDocsDTO)))
            .andExpect(status().isOk());

        // Validate the SoDocs in the database
        List<SoDocs> soDocsList = soDocsRepository.findAll();
        assertThat(soDocsList).hasSize(databaseSizeBeforeUpdate);
        SoDocs testSoDocs = soDocsList.get(soDocsList.size() - 1);
        assertThat(testSoDocs.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testSoDocs.getUploadFile()).isEqualTo(UPDATED_UPLOAD_FILE);
        assertThat(testSoDocs.getUploadFileContentType()).isEqualTo(UPDATED_UPLOAD_FILE_CONTENT_TYPE);
        assertThat(testSoDocs.getUploadFileContentType()).isEqualTo(UPDATED_UPLOAD_FILE_CONTENT_TYPE);
        assertThat(testSoDocs.isPrivateMode()).isEqualTo(UPDATED_PRIVATE_MODE);
    }

    @Test
    @Transactional
    public void updateNonExistingSoDocs() throws Exception {
        int databaseSizeBeforeUpdate = soDocsRepository.findAll().size();

        // Create the SoDocs
        SoDocsDTO soDocsDTO = soDocsMapper.toDto(soDocs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoDocsMockMvc.perform(put("/api/so-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(soDocsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SoDocs in the database
        List<SoDocs> soDocsList = soDocsRepository.findAll();
        assertThat(soDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSoDocs() throws Exception {
        // Initialize the database
        soDocsRepository.saveAndFlush(soDocs);

        int databaseSizeBeforeDelete = soDocsRepository.findAll().size();

        // Delete the soDocs
        restSoDocsMockMvc.perform(delete("/api/so-docs/{id}", soDocs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoDocs> soDocsList = soDocsRepository.findAll();
        assertThat(soDocsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoDocs.class);
        SoDocs soDocs1 = new SoDocs();
        soDocs1.setId(1L);
        SoDocs soDocs2 = new SoDocs();
        soDocs2.setId(soDocs1.getId());
        assertThat(soDocs1).isEqualTo(soDocs2);
        soDocs2.setId(2L);
        assertThat(soDocs1).isNotEqualTo(soDocs2);
        soDocs1.setId(null);
        assertThat(soDocs1).isNotEqualTo(soDocs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoDocsDTO.class);
        SoDocsDTO soDocsDTO1 = new SoDocsDTO();
        soDocsDTO1.setId(1L);
        SoDocsDTO soDocsDTO2 = new SoDocsDTO();
        assertThat(soDocsDTO1).isNotEqualTo(soDocsDTO2);
        soDocsDTO2.setId(soDocsDTO1.getId());
        assertThat(soDocsDTO1).isEqualTo(soDocsDTO2);
        soDocsDTO2.setId(2L);
        assertThat(soDocsDTO1).isNotEqualTo(soDocsDTO2);
        soDocsDTO1.setId(null);
        assertThat(soDocsDTO1).isNotEqualTo(soDocsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(soDocsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(soDocsMapper.fromId(null)).isNull();
    }
}
