package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.jhipster.application.domain.ShippingOrder} entity.
 */
public class ShippingOrderDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShippingOrderDTO shippingOrderDTO = (ShippingOrderDTO) o;
        if (shippingOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shippingOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShippingOrderDTO{" +
            "id=" + getId() +
            "}";
    }
}
