package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SoDocsApp;
import io.github.jhipster.application.domain.DocsType;
import io.github.jhipster.application.repository.DocsTypeRepository;
import io.github.jhipster.application.service.DocsTypeService;
import io.github.jhipster.application.service.dto.DocsTypeDTO;
import io.github.jhipster.application.service.mapper.DocsTypeMapper;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DocsTypeResource} REST controller.
 */
@SpringBootTest(classes = SoDocsApp.class)
public class DocsTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private DocsTypeRepository docsTypeRepository;

    @Autowired
    private DocsTypeMapper docsTypeMapper;

    @Autowired
    private DocsTypeService docsTypeService;

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

    private MockMvc restDocsTypeMockMvc;

    private DocsType docsType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocsTypeResource docsTypeResource = new DocsTypeResource(docsTypeService);
        this.restDocsTypeMockMvc = MockMvcBuilders.standaloneSetup(docsTypeResource)
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
    public static DocsType createEntity(EntityManager em) {
        DocsType docsType = new DocsType()
            .name(DEFAULT_NAME);
        return docsType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocsType createUpdatedEntity(EntityManager em) {
        DocsType docsType = new DocsType()
            .name(UPDATED_NAME);
        return docsType;
    }

    @BeforeEach
    public void initTest() {
        docsType = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocsType() throws Exception {
        int databaseSizeBeforeCreate = docsTypeRepository.findAll().size();

        // Create the DocsType
        DocsTypeDTO docsTypeDTO = docsTypeMapper.toDto(docsType);
        restDocsTypeMockMvc.perform(post("/api/docs-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docsTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DocsType in the database
        List<DocsType> docsTypeList = docsTypeRepository.findAll();
        assertThat(docsTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DocsType testDocsType = docsTypeList.get(docsTypeList.size() - 1);
        assertThat(testDocsType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createDocsTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docsTypeRepository.findAll().size();

        // Create the DocsType with an existing ID
        docsType.setId(1L);
        DocsTypeDTO docsTypeDTO = docsTypeMapper.toDto(docsType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocsTypeMockMvc.perform(post("/api/docs-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docsTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocsType in the database
        List<DocsType> docsTypeList = docsTypeRepository.findAll();
        assertThat(docsTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocsTypes() throws Exception {
        // Initialize the database
        docsTypeRepository.saveAndFlush(docsType);

        // Get all the docsTypeList
        restDocsTypeMockMvc.perform(get("/api/docs-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docsType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getDocsType() throws Exception {
        // Initialize the database
        docsTypeRepository.saveAndFlush(docsType);

        // Get the docsType
        restDocsTypeMockMvc.perform(get("/api/docs-types/{id}", docsType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docsType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocsType() throws Exception {
        // Get the docsType
        restDocsTypeMockMvc.perform(get("/api/docs-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocsType() throws Exception {
        // Initialize the database
        docsTypeRepository.saveAndFlush(docsType);

        int databaseSizeBeforeUpdate = docsTypeRepository.findAll().size();

        // Update the docsType
        DocsType updatedDocsType = docsTypeRepository.findById(docsType.getId()).get();
        // Disconnect from session so that the updates on updatedDocsType are not directly saved in db
        em.detach(updatedDocsType);
        updatedDocsType
            .name(UPDATED_NAME);
        DocsTypeDTO docsTypeDTO = docsTypeMapper.toDto(updatedDocsType);

        restDocsTypeMockMvc.perform(put("/api/docs-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docsTypeDTO)))
            .andExpect(status().isOk());

        // Validate the DocsType in the database
        List<DocsType> docsTypeList = docsTypeRepository.findAll();
        assertThat(docsTypeList).hasSize(databaseSizeBeforeUpdate);
        DocsType testDocsType = docsTypeList.get(docsTypeList.size() - 1);
        assertThat(testDocsType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingDocsType() throws Exception {
        int databaseSizeBeforeUpdate = docsTypeRepository.findAll().size();

        // Create the DocsType
        DocsTypeDTO docsTypeDTO = docsTypeMapper.toDto(docsType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocsTypeMockMvc.perform(put("/api/docs-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docsTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DocsType in the database
        List<DocsType> docsTypeList = docsTypeRepository.findAll();
        assertThat(docsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocsType() throws Exception {
        // Initialize the database
        docsTypeRepository.saveAndFlush(docsType);

        int databaseSizeBeforeDelete = docsTypeRepository.findAll().size();

        // Delete the docsType
        restDocsTypeMockMvc.perform(delete("/api/docs-types/{id}", docsType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocsType> docsTypeList = docsTypeRepository.findAll();
        assertThat(docsTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocsType.class);
        DocsType docsType1 = new DocsType();
        docsType1.setId(1L);
        DocsType docsType2 = new DocsType();
        docsType2.setId(docsType1.getId());
        assertThat(docsType1).isEqualTo(docsType2);
        docsType2.setId(2L);
        assertThat(docsType1).isNotEqualTo(docsType2);
        docsType1.setId(null);
        assertThat(docsType1).isNotEqualTo(docsType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocsTypeDTO.class);
        DocsTypeDTO docsTypeDTO1 = new DocsTypeDTO();
        docsTypeDTO1.setId(1L);
        DocsTypeDTO docsTypeDTO2 = new DocsTypeDTO();
        assertThat(docsTypeDTO1).isNotEqualTo(docsTypeDTO2);
        docsTypeDTO2.setId(docsTypeDTO1.getId());
        assertThat(docsTypeDTO1).isEqualTo(docsTypeDTO2);
        docsTypeDTO2.setId(2L);
        assertThat(docsTypeDTO1).isNotEqualTo(docsTypeDTO2);
        docsTypeDTO1.setId(null);
        assertThat(docsTypeDTO1).isNotEqualTo(docsTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(docsTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(docsTypeMapper.fromId(null)).isNull();
    }
}
