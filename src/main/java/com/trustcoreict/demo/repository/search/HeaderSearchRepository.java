package com.trustcoreict.demo.repository.search;

import com.trustcoreict.demo.domain.Header;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Header} entity.
 */
public interface HeaderSearchRepository extends ElasticsearchRepository<Header, Long> {
}
