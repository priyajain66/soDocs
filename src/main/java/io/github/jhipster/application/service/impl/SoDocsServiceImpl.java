package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.SoDocsService;
import io.github.jhipster.application.domain.SoDocs;
import io.github.jhipster.application.repository.SoDocsRepository;
import io.github.jhipster.application.service.dto.SoDocsDTO;
import io.github.jhipster.application.service.mapper.SoDocsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SoDocs}.
 */
@Service
@Transactional
public class SoDocsServiceImpl implements SoDocsService {

    private final Logger log = LoggerFactory.getLogger(SoDocsServiceImpl.class);

    private final SoDocsRepository soDocsRepository;

    private final SoDocsMapper soDocsMapper;

    public SoDocsServiceImpl(SoDocsRepository soDocsRepository, SoDocsMapper soDocsMapper) {
        this.soDocsRepository = soDocsRepository;
        this.soDocsMapper = soDocsMapper;
    }

    /**
     * Save a soDocs.
     *
     * @param soDocsDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public SoDocsDTO save(SoDocsDTO soDocsDTO) {
        log.debug("Request to save SoDocs : {}", soDocsDTO);
        SoDocs soDocs = soDocsMapper.toEntity(soDocsDTO);
        soDocs = soDocsRepository.save(soDocs);
        return soDocsMapper.toDto(soDocs);
    }

    /**
     * Get all the soDocs.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SoDocsDTO> findAll() {
        log.debug("Request to get all SoDocs");
        return soDocsRepository.findAll().stream()
            .map(soDocsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one soDocs by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SoDocsDTO> findOne(Long id) {
        log.debug("Request to get SoDocs : {}", id);
        return soDocsRepository.findById(id)
            .map(soDocsMapper::toDto);
    }

    /**
     * Delete the soDocs by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoDocs : {}", id);
        soDocsRepository.deleteById(id);
    }
}
