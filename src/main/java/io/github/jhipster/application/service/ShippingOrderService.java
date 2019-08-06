package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ShippingOrderDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.jhipster.application.domain.ShippingOrder}.
 */
public interface ShippingOrderService {

    /**
     * Save a shippingOrder.
     *
     * @param shippingOrderDTO the entity to save.
     * @return the persisted entity.
     */
    ShippingOrderDTO save(ShippingOrderDTO shippingOrderDTO);

    /**
     * Get all the shippingOrders.
     *
     * @return the list of entities.
     */
    List<ShippingOrderDTO> findAll();


    /**
     * Get the "id" shippingOrder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShippingOrderDTO> findOne(Long id);

    /**
     * Delete the "id" shippingOrder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
