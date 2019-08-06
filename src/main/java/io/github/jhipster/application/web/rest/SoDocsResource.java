package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.SoDocsService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.SoDocsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link io.github.jhipster.application.domain.SoDocs}.
 */
@RestController
@RequestMapping("/api")
public class SoDocsResource {

    private final Logger log = LoggerFactory.getLogger(SoDocsResource.class);

    private static final String ENTITY_NAME = "soDocs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoDocsService soDocsService;

    public SoDocsResource(SoDocsService soDocsService) {
        this.soDocsService = soDocsService;
    }

    /**
     * {@code POST  /so-docs} : Create a new soDocs.
     *
     * @param soDocsDTO the soDocsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soDocsDTO, or with status {@code 400 (Bad Request)} if the soDocs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/so-docs")
    public ResponseEntity<SoDocsDTO> createSoDocs(@RequestBody SoDocsDTO soDocsDTO) throws URISyntaxException {
        log.debug("REST request to save SoDocs : {}", soDocsDTO);
        if (soDocsDTO.getId() != null) {
            throw new BadRequestAlertException("A new soDocs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoDocsDTO result = soDocsService.save(soDocsDTO);
        return ResponseEntity.created(new URI("/api/so-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /so-docs} : Updates an existing soDocs.
     *
     * @param soDocsDTO the soDocsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soDocsDTO,
     * or with status {@code 400 (Bad Request)} if the soDocsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soDocsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/so-docs")
    public ResponseEntity<SoDocsDTO> updateSoDocs(@RequestBody SoDocsDTO soDocsDTO) throws URISyntaxException {
        log.debug("REST request to update SoDocs : {}", soDocsDTO);
        if (soDocsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SoDocsDTO result = soDocsService.save(soDocsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, soDocsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /so-docs} : get all the soDocs.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soDocs in body.
     */
    @GetMapping("/so-docs")
    public List<SoDocsDTO> getAllSoDocs() {
        log.debug("REST request to get all SoDocs");
        return soDocsService.findAll();
    }

    /**
     * {@code GET  /so-docs/:id} : get the "id" soDocs.
     *
     * @param id the id of the soDocsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soDocsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/so-docs/{id}")
    public ResponseEntity<SoDocsDTO> getSoDocs(@PathVariable Long id) {
        log.debug("REST request to get SoDocs : {}", id);
        Optional<SoDocsDTO> soDocsDTO = soDocsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(soDocsDTO);
    }

    /**
     * {@code DELETE  /so-docs/:id} : delete the "id" soDocs.
     *
     * @param id the id of the soDocsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/so-docs/{id}")
    public ResponseEntity<Void> deleteSoDocs(@PathVariable Long id) {
        log.debug("REST request to delete SoDocs : {}", id);
        soDocsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
