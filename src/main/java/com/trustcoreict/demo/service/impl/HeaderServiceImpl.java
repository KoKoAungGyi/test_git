package com.trustcoreict.demo.service.impl;

import com.trustcoreict.demo.service.HeaderService;
import com.trustcoreict.demo.domain.Header;
import com.trustcoreict.demo.repository.HeaderRepository;
import com.trustcoreict.demo.repository.search.HeaderSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Header}.
 */
@Service
@Transactional
public class HeaderServiceImpl implements HeaderService {

    private final Logger log = LoggerFactory.getLogger(HeaderServiceImpl.class);

    private final HeaderRepository headerRepository;

    private final HeaderSearchRepository headerSearchRepository;

    public HeaderServiceImpl(HeaderRepository headerRepository, HeaderSearchRepository headerSearchRepository) {
        this.headerRepository = headerRepository;
        this.headerSearchRepository = headerSearchRepository;
    }

    /**
     * Save a header.
     *
     * @param header the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Header save(Header header) {
        log.debug("Request to save Header : {}", header);
        Header result = headerRepository.save(header);
        headerSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the headers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Header> findAll(Pageable pageable) {
        log.debug("Request to get all Headers");
        return headerRepository.findAll(pageable);
    }


    /**
     * Get one header by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Header> findOne(Long id) {
        log.debug("Request to get Header : {}", id);
        return headerRepository.findById(id);
    }

    /**
     * Delete the header by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Header : {}", id);
        headerRepository.deleteById(id);
        headerSearchRepository.deleteById(id);
    }

    /**
     * Search for the header corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Header> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Headers for query {}", query);
        return headerSearchRepository.search(queryStringQuery(query), pageable);    }
}
