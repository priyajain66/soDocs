package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ShippingOrderService;
import io.github.jhipster.application.domain.ShippingOrder;
import io.github.jhipster.application.repository.ShippingOrderRepository;
import io.github.jhipster.application.service.dto.ShippingOrderDTO;
import io.github.jhipster.application.service.mapper.ShippingOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ShippingOrder}.
 */
@Service
@Transactional
public class ShippingOrderServiceImpl implements ShippingOrderService {

    private final Logger log = LoggerFactory.getLogger(ShippingOrderServiceImpl.class);

    private final ShippingOrderRepository shippingOrderRepository;

    private final ShippingOrderMapper shippingOrderMapper;

    public ShippingOrderServiceImpl(ShippingOrderRepository shippingOrderRepository, ShippingOrderMapper shippingOrderMapper) {
        this.shippingOrderRepository = shippingOrderRepository;
        this.shippingOrderMapper = shippingOrderMapper;
    }

    /**
     * Save a shippingOrder.
     *
     * @param shippingOrderDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ShippingOrderDTO save(ShippingOrderDTO shippingOrderDTO) {
        log.debug("Request to save ShippingOrder : {}", shippingOrderDTO);
        ShippingOrder shippingOrder = shippingOrderMapper.toEntity(shippingOrderDTO);
        shippingOrder = shippingOrderRepository.save(shippingOrder);
        return shippingOrderMapper.toDto(shippingOrder);
    }

    /**
     * Get all the shippingOrders.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ShippingOrderDTO> findAll() {
        log.debug("Request to get all ShippingOrders");
        return shippingOrderRepository.findAll().stream()
            .map(shippingOrderMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one shippingOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ShippingOrderDTO> findOne(Long id) {
        log.debug("Request to get ShippingOrder : {}", id);
        return shippingOrderRepository.findById(id)
            .map(shippingOrderMapper::toDto);
    }

    /**
     * Delete the shippingOrder by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShippingOrder : {}", id);
        shippingOrderRepository.deleteById(id);
    }
}
