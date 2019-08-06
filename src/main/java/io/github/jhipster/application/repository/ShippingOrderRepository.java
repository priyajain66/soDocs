package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.ShippingOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ShippingOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {

}
