package com.trustcoreict.demo.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link HeaderSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class HeaderSearchRepositoryMockConfiguration {

    @MockBean
    private HeaderSearchRepository mockHeaderSearchRepository;

}
