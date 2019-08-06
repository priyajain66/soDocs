package io.github.jhipster.application.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.jhipster.application.domain.SoDocs} entity.
 */
public class SoDocsDTO implements Serializable {

    private Long id;

    private String fileName;

    @Lob
    private byte[] uploadFile;

    private String uploadFileContentType;
    private String uploadFileContentType;

    private Boolean privateMode;


    private Long shippingOrderId;

    private Long docsTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(byte[] uploadFile) {
        this.uploadFile = uploadFile;
    }

    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public String getUploadFileContentType() {
        return uploadFileContentType;
    }

    public void setUploadFileContentType(String uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public Boolean isPrivateMode() {
        return privateMode;
    }

    public void setPrivateMode(Boolean privateMode) {
        this.privateMode = privateMode;
    }

    public Long getShippingOrderId() {
        return shippingOrderId;
    }

    public void setShippingOrderId(Long shippingOrderId) {
        this.shippingOrderId = shippingOrderId;
    }

    public Long getDocsTypeId() {
        return docsTypeId;
    }

    public void setDocsTypeId(Long docsTypeId) {
        this.docsTypeId = docsTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SoDocsDTO soDocsDTO = (SoDocsDTO) o;
        if (soDocsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), soDocsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SoDocsDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", uploadFile='" + getUploadFile() + "'" +
            ", uploadFileContentType='" + getUploadFileContentType() + "'" +
            ", privateMode='" + isPrivateMode() + "'" +
            ", shippingOrder=" + getShippingOrderId() +
            ", docsType=" + getDocsTypeId() +
            "}";
    }
}
