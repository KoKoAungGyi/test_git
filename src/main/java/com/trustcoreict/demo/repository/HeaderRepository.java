package com.trustcoreict.demo.repository;

import com.trustcoreict.demo.domain.Header;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Header entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeaderRepository extends JpaRepository<Header, Long> {

}
