package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.SoDocsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SoDocs} and its DTO {@link SoDocsDTO}.
 */
@Mapper(componentModel = "spring", uses = {ShippingOrderMapper.class, DocsTypeMapper.class})
public interface SoDocsMapper extends EntityMapper<SoDocsDTO, SoDocs> {

    @Mapping(source = "shippingOrder.id", target = "shippingOrderId")
    @Mapping(source = "docsType.id", target = "docsTypeId")
    SoDocsDTO toDto(SoDocs soDocs);

    @Mapping(source = "shippingOrderId", target = "shippingOrder")
    @Mapping(source = "docsTypeId", target = "docsType")
    SoDocs toEntity(SoDocsDTO soDocsDTO);

    default SoDocs fromId(Long id) {
        if (id == null) {
            return null;
        }
        SoDocs soDocs = new SoDocs();
        soDocs.setId(id);
        return soDocs;
    }
}
