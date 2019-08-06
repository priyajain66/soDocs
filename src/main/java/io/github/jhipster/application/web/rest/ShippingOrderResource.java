package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.ShippingOrderService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.ShippingOrderDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.ShippingOrder}.
 */
@RestController
@RequestMapping("/api")
public class ShippingOrderResource {

    private final Logger log = LoggerFactory.getLogger(ShippingOrderResource.class);

    private static final String ENTITY_NAME = "shippingOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShippingOrderService shippingOrderService;

    public ShippingOrderResource(ShippingOrderService shippingOrderService) {
        this.shippingOrderService = shippingOrderService;
    }

    /**
     * {@code POST  /shipping-orders} : Create a new shippingOrder.
     *
     * @param shippingOrderDTO the shippingOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shippingOrderDTO, or with status {@code 400 (Bad Request)} if the shippingOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipping-orders")
    public ResponseEntity<ShippingOrderDTO> createShippingOrder(@RequestBody ShippingOrderDTO shippingOrderDTO) throws URISyntaxException {
        log.debug("REST request to save ShippingOrder : {}", shippingOrderDTO);
        if (shippingOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new shippingOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShippingOrderDTO result = shippingOrderService.save(shippingOrderDTO);
        return ResponseEntity.created(new URI("/api/shipping-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipping-orders} : Updates an existing shippingOrder.
     *
     * @param shippingOrderDTO the shippingOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shippingOrderDTO,
     * or with status {@code 400 (Bad Request)} if the shippingOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shippingOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipping-orders")
    public ResponseEntity<ShippingOrderDTO> updateShippingOrder(@RequestBody ShippingOrderDTO shippingOrderDTO) throws URISyntaxException {
        log.debug("REST request to update ShippingOrder : {}", shippingOrderDTO);
        if (shippingOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShippingOrderDTO result = shippingOrderService.save(shippingOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shippingOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shipping-orders} : get all the shippingOrders.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shippingOrders in body.
     */
    @GetMapping("/shipping-orders")
    public List<ShippingOrderDTO> getAllShippingOrders() {
        log.debug("REST request to get all ShippingOrders");
        return shippingOrderService.findAll();
    }

    /**
     * {@code GET  /shipping-orders/:id} : get the "id" shippingOrder.
     *
     * @param id the id of the shippingOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shippingOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipping-orders/{id}")
    public ResponseEntity<ShippingOrderDTO> getShippingOrder(@PathVariable Long id) {
        log.debug("REST request to get ShippingOrder : {}", id);
        Optional<ShippingOrderDTO> shippingOrderDTO = shippingOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shippingOrderDTO);
    }

    /**
     * {@code DELETE  /shipping-orders/:id} : delete the "id" shippingOrder.
     *
     * @param id the id of the shippingOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipping-orders/{id}")
    public ResponseEntity<Void> deleteShippingOrder(@PathVariable Long id) {
        log.debug("REST request to delete ShippingOrder : {}", id);
        shippingOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
