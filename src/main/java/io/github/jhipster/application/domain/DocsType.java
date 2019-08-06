package io.github.jhipster.application.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DocsType.
 */
@Entity
@Table(name = "docs_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DocsType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "docsType")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SoDocs> soDocs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public DocsType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SoDocs> getSoDocs() {
        return soDocs;
    }

    public DocsType soDocs(Set<SoDocs> soDocs) {
        this.soDocs = soDocs;
        return this;
    }

    public DocsType addSoDocs(SoDocs soDocs) {
        this.soDocs.add(soDocs);
        soDocs.setDocsType(this);
        return this;
    }

    public DocsType removeSoDocs(SoDocs soDocs) {
        this.soDocs.remove(soDocs);
        soDocs.setDocsType(null);
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
        if (!(o instanceof DocsType)) {
            return false;
        }
        return id != null && id.equals(((DocsType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocsType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
