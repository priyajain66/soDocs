package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.SoDocsApp;
import io.github.jhipster.application.domain.ShippingOrder;
import io.github.jhipster.application.repository.ShippingOrderRepository;
import io.github.jhipster.application.service.ShippingOrderService;
import io.github.jhipster.application.service.dto.ShippingOrderDTO;
import io.github.jhipster.application.service.mapper.ShippingOrderMapper;
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
 * Integration tests for the {@link ShippingOrderResource} REST controller.
 */
@SpringBootTest(classes = SoDocsApp.class)
public class ShippingOrderResourceIT {

    @Autowired
    private ShippingOrderRepository shippingOrderRepository;

    @Autowired
    private ShippingOrderMapper shippingOrderMapper;

    @Autowired
    private ShippingOrderService shippingOrderService;

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

    private MockMvc restShippingOrderMockMvc;

    private ShippingOrder shippingOrder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShippingOrderResource shippingOrderResource = new ShippingOrderResource(shippingOrderService);
        this.restShippingOrderMockMvc = MockMvcBuilders.standaloneSetup(shippingOrderResource)
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
    public static ShippingOrder createEntity(EntityManager em) {
        ShippingOrder shippingOrder = new ShippingOrder();
        return shippingOrder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShippingOrder createUpdatedEntity(EntityManager em) {
        ShippingOrder shippingOrder = new ShippingOrder();
        return shippingOrder;
    }

    @BeforeEach
    public void initTest() {
        shippingOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createShippingOrder() throws Exception {
        int databaseSizeBeforeCreate = shippingOrderRepository.findAll().size();

        // Create the ShippingOrder
        ShippingOrderDTO shippingOrderDTO = shippingOrderMapper.toDto(shippingOrder);
        restShippingOrderMockMvc.perform(post("/api/shipping-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the ShippingOrder in the database
        List<ShippingOrder> shippingOrderList = shippingOrderRepository.findAll();
        assertThat(shippingOrderList).hasSize(databaseSizeBeforeCreate + 1);
        ShippingOrder testShippingOrder = shippingOrderList.get(shippingOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void createShippingOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shippingOrderRepository.findAll().size();

        // Create the ShippingOrder with an existing ID
        shippingOrder.setId(1L);
        ShippingOrderDTO shippingOrderDTO = shippingOrderMapper.toDto(shippingOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShippingOrderMockMvc.perform(post("/api/shipping-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingOrder in the database
        List<ShippingOrder> shippingOrderList = shippingOrderRepository.findAll();
        assertThat(shippingOrderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShippingOrders() throws Exception {
        // Initialize the database
        shippingOrderRepository.saveAndFlush(shippingOrder);

        // Get all the shippingOrderList
        restShippingOrderMockMvc.perform(get("/api/shipping-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shippingOrder.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getShippingOrder() throws Exception {
        // Initialize the database
        shippingOrderRepository.saveAndFlush(shippingOrder);

        // Get the shippingOrder
        restShippingOrderMockMvc.perform(get("/api/shipping-orders/{id}", shippingOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shippingOrder.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShippingOrder() throws Exception {
        // Get the shippingOrder
        restShippingOrderMockMvc.perform(get("/api/shipping-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShippingOrder() throws Exception {
        // Initialize the database
        shippingOrderRepository.saveAndFlush(shippingOrder);

        int databaseSizeBeforeUpdate = shippingOrderRepository.findAll().size();

        // Update the shippingOrder
        ShippingOrder updatedShippingOrder = shippingOrderRepository.findById(shippingOrder.getId()).get();
        // Disconnect from session so that the updates on updatedShippingOrder are not directly saved in db
        em.detach(updatedShippingOrder);
        ShippingOrderDTO shippingOrderDTO = shippingOrderMapper.toDto(updatedShippingOrder);

        restShippingOrderMockMvc.perform(put("/api/shipping-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrderDTO)))
            .andExpect(status().isOk());

        // Validate the ShippingOrder in the database
        List<ShippingOrder> shippingOrderList = shippingOrderRepository.findAll();
        assertThat(shippingOrderList).hasSize(databaseSizeBeforeUpdate);
        ShippingOrder testShippingOrder = shippingOrderList.get(shippingOrderList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingShippingOrder() throws Exception {
        int databaseSizeBeforeUpdate = shippingOrderRepository.findAll().size();

        // Create the ShippingOrder
        ShippingOrderDTO shippingOrderDTO = shippingOrderMapper.toDto(shippingOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShippingOrderMockMvc.perform(put("/api/shipping-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shippingOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShippingOrder in the database
        List<ShippingOrder> shippingOrderList = shippingOrderRepository.findAll();
        assertThat(shippingOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShippingOrder() throws Exception {
        // Initialize the database
        shippingOrderRepository.saveAndFlush(shippingOrder);

        int databaseSizeBeforeDelete = shippingOrderRepository.findAll().size();

        // Delete the shippingOrder
        restShippingOrderMockMvc.perform(delete("/api/shipping-orders/{id}", shippingOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShippingOrder> shippingOrderList = shippingOrderRepository.findAll();
        assertThat(shippingOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingOrder.class);
        ShippingOrder shippingOrder1 = new ShippingOrder();
        shippingOrder1.setId(1L);
        ShippingOrder shippingOrder2 = new ShippingOrder();
        shippingOrder2.setId(shippingOrder1.getId());
        assertThat(shippingOrder1).isEqualTo(shippingOrder2);
        shippingOrder2.setId(2L);
        assertThat(shippingOrder1).isNotEqualTo(shippingOrder2);
        shippingOrder1.setId(null);
        assertThat(shippingOrder1).isNotEqualTo(shippingOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShippingOrderDTO.class);
        ShippingOrderDTO shippingOrderDTO1 = new ShippingOrderDTO();
        shippingOrderDTO1.setId(1L);
        ShippingOrderDTO shippingOrderDTO2 = new ShippingOrderDTO();
        assertThat(shippingOrderDTO1).isNotEqualTo(shippingOrderDTO2);
        shippingOrderDTO2.setId(shippingOrderDTO1.getId());
        assertThat(shippingOrderDTO1).isEqualTo(shippingOrderDTO2);
        shippingOrderDTO2.setId(2L);
        assertThat(shippingOrderDTO1).isNotEqualTo(shippingOrderDTO2);
        shippingOrderDTO1.setId(null);
        assertThat(shippingOrderDTO1).isNotEqualTo(shippingOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shippingOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shippingOrderMapper.fromId(null)).isNull();
    }
}
