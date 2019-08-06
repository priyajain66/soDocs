package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ShippingOrder.
 */
@Entity
@Table(name = "shipping_order")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ShippingOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "shippingOrder")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SoDocs> soDocs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<SoDocs> getSoDocs() {
        return soDocs;
    }

    public ShippingOrder soDocs(Set<SoDocs> soDocs) {
        this.soDocs = soDocs;
        return this;
    }

    public ShippingOrder addSoDocs(SoDocs soDocs) {
        this.soDocs.add(soDocs);
        soDocs.setShippingOrder(this);
        return this;
    }

    public ShippingOrder removeSoDocs(SoDocs soDocs) {
        this.soDocs.remove(soDocs);
        soDocs.setShippingOrder(null);
        return this;
    }

    public void setSoDocs(Set<SoDocs> soDocs) {
        this.soDocs = soDocs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShippingOrder)) {
            return false;
        }
        return id != null && id.equals(((ShippingOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ShippingOrder{" +
            "id=" + getId() +
            "}";
    }
}
