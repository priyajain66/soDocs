package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.SoDocsDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.jhipster.application.domain.SoDocs}.
 */
public interface SoDocsService {

    /**
     * Save a soDocs.
     *
     * @param soDocsDTO the entity to save.
     * @return the persisted entity.
     */
    SoDocsDTO save(SoDocsDTO soDocsDTO);

    /**
     * Get all the soDocs.
     *
     * @return the list of entities.
     */
    List<SoDocsDTO> findAll();


    /**
     * Get the "id" soDocs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoDocsDTO> findOne(Long id);

    /**
     * Delete the "id" soDocs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
