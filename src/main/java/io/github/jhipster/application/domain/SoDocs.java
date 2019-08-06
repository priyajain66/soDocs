package io.github.jhipster.application.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SoDocs.
 */
@Entity
@Table(name = "so_docs")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SoDocs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Lob
    @Column(name = "upload_file")
    private byte[] uploadFile;

    @Column(name = "upload_file_content_type")
    private String uploadFileContentType;

    @Column(name = "upload_file_content_type")
    private String uploadFileContentType;

    @Column(name = "private_mode")
    private Boolean privateMode;

    @ManyToOne
    @JsonIgnoreProperties("soDocs")
    private ShippingOrder shippingOrder;

    @ManyToOne
    @JsonIgnoreProperties("soDocs")
    private DocsType docsType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public SoDocs fileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getUploadFile() {
        return uploadFile;
    }

    public SoDocs uploadFile(byte[] uploadFile) {
        this.uploadFile = uploadFile;
        return this;
    }

    public void setUploadFile(byte[] uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    public SoDocs uploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
        return this;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    public SoDocs uploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
        return this;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public Boolean isPrivateMode() {
        return privateMode;
    }

    public SoDocs privateMode(Boolean privateMode) {
        this.privateMode = privateMode;
        return this;
    }

    public void setPrivateMode(Boolean privateMode) {
        this.privateMode = privateMode;
    }

    public ShippingOrder getShippingOrder() {
        return shippingOrder;
    }

    public SoDocs shippingOrder(ShippingOrder shippingOrder) {
        this.shippingOrder = shippingOrder;
        return this;
    }

    public void setShippingOrder(ShippingOrder shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    public DocsType getDocsType() {
        return docsType;
    }

    public SoDocs docsType(DocsType docsType) {
        this.docsType = docsType;
        return this;
    }

    public void setDocsType(DocsType docsType) {
        this.docsType = docsType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoDocs)) {
            return false;
        }
        return id != null && id.equals(((SoDocs) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SoDocs{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", uploadFile='" + getUploadFile() + "'" +
            ", uploadFileContentType='" + getUploadFileContentType() + "'" +
            ", uploadFileContentType='" + getUploadFileContentType() + "'" +
            ", privateMode='" + isPrivateMode() + "'" +
            "}";
    }
}
