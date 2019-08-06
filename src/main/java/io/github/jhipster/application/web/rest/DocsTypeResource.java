package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.service.DocsTypeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.service.dto.DocsTypeDTO;

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
 * REST controller for managing {@link io.github.jhipster.application.domain.DocsType}.
 */
@RestController
@RequestMapping("/api")
public class DocsTypeResource {

    private final Logger log = LoggerFactory.getLogger(DocsTypeResource.class);

    private static final String ENTITY_NAME = "docsType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocsTypeService docsTypeService;

    public DocsTypeResource(DocsTypeService docsTypeService) {
        this.docsTypeService = docsTypeService;
    }

    /**
     * {@code POST  /docs-types} : Create a new docsType.
     *
     * @param docsTypeDTO the docsTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docsTypeDTO, or with status {@code 400 (Bad Request)} if the docsType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/docs-types")
    public ResponseEntity<DocsTypeDTO> createDocsType(@RequestBody DocsTypeDTO docsTypeDTO) throws URISyntaxException {
        log.debug("REST request to save DocsType : {}", docsTypeDTO);
        if (docsTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new docsType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocsTypeDTO result = docsTypeService.save(docsTypeDTO);
        return ResponseEntity.created(new URI("/api/docs-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /docs-types} : Updates an existing docsType.
     *
     * @param docsTypeDTO the docsTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docsTypeDTO,
     * or with status {@code 400 (Bad Request)} if the docsTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docsTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/docs-types")
    public ResponseEntity<DocsTypeDTO> updateDocsType(@RequestBody DocsTypeDTO docsTypeDTO) throws URISyntaxException {
        log.debug("REST request to update DocsType : {}", docsTypeDTO);
        if (docsTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocsTypeDTO result = docsTypeService.save(docsTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, docsTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /docs-types} : get all the docsTypes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docsTypes in body.
     */
    @GetMapping("/docs-types")
    public List<DocsTypeDTO> getAllDocsTypes() {
        log.debug("REST request to get all DocsTypes");
        return docsTypeService.findAll();
    }

    /**
     * {@code GET  /docs-types/:id} : get the "id" docsType.
     *
     * @param id the id of the docsTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docsTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/docs-types/{id}")
    public ResponseEntity<DocsTypeDTO> getDocsType(@PathVariable Long id) {
        log.debug("REST request to get DocsType : {}", id);
        Optional<DocsTypeDTO> docsTypeDTO = docsTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docsTypeDTO);
    }

    /**
     * {@code DELETE  /docs-types/:id} : delete the "id" docsType.
     *
     * @param id the id of the docsTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/docs-types/{id}")
    public ResponseEntity<Void> deleteDocsType(@PathVariable Long id) {
        log.debug("REST request to delete DocsType : {}", id);
        docsTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
