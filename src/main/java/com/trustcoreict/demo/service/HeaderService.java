package com.trustcoreict.demo.service;

import com.trustcoreict.demo.domain.Header;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Header}.
 */
public interface HeaderService {

    /**
     * Save a header.
     *
     * @param header the entity to save.
     * @return the persisted entity.
     */
    Header save(Header header);

    /**
     * Get all the headers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Header> findAll(Pageable pageable);


    /**
     * Get the "id" header.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Header> findOne(Long id);

    /**
     * Delete the "id" header.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the header corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Header> search(String query, Pageable pageable);
}
