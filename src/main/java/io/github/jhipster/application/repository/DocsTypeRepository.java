package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DocsType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DocsType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocsTypeRepository extends JpaRepository<DocsType, Long> {

}
