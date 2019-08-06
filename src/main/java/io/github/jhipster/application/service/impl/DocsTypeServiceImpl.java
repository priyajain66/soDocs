package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.DocsTypeService;
import io.github.jhipster.application.domain.DocsType;
import io.github.jhipster.application.repository.DocsTypeRepository;
import io.github.jhipster.application.service.dto.DocsTypeDTO;
import io.github.jhipster.application.service.mapper.DocsTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DocsType}.
 */
@Service
@Transactional
public class DocsTypeServiceImpl implements DocsTypeService {

    private final Logger log = LoggerFactory.getLogger(DocsTypeServiceImpl.class);

    private final DocsTypeRepository docsTypeRepository;

    private final DocsTypeMapper docsTypeMapper;

    public DocsTypeServiceImpl(DocsTypeRepository docsTypeRepository, DocsTypeMapper docsTypeMapper) {
        this.docsTypeRepository = docsTypeRepository;
        this.docsTypeMapper = docsTypeMapper;
    }

    /**
     * Save a docsType.
     *
     * @param docsTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DocsTypeDTO save(DocsTypeDTO docsTypeDTO) {
        log.debug("Request to save DocsType : {}", docsTypeDTO);
        DocsType docsType = docsTypeMapper.toEntity(docsTypeDTO);
        docsType = docsTypeRepository.save(docsType);
        return docsTypeMapper.toDto(docsType);
    }

    /**
     * Get all the docsTypes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<DocsTypeDTO> findAll() {
        log.debug("Request to get all DocsTypes");
        return docsTypeRepository.findAll().stream()
            .map(docsTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one docsType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DocsTypeDTO> findOne(Long id) {
        log.debug("Request to get DocsType : {}", id);
        return docsTypeRepository.findById(id)
            .map(docsTypeMapper::toDto);
    }

    /**
     * Delete the docsType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocsType : {}", id);
        docsTypeRepository.deleteById(id);
    }
}
