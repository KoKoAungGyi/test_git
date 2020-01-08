package com.trustcoreict.demo.web.rest;

import com.trustcoreict.demo.DemoApp;
import com.trustcoreict.demo.domain.Header;
import com.trustcoreict.demo.repository.HeaderRepository;
import com.trustcoreict.demo.repository.search.HeaderSearchRepository;
import com.trustcoreict.demo.service.HeaderService;
import com.trustcoreict.demo.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static com.trustcoreict.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link HeaderResource} REST controller.
 */
@SpringBootTest(classes = DemoApp.class)
public class HeaderResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private HeaderRepository headerRepository;

    @Autowired
    private HeaderService headerService;

    /**
     * This repository is mocked in the com.trustcoreict.demo.repository.search test package.
     *
     * @see com.trustcoreict.demo.repository.search.HeaderSearchRepositoryMockConfiguration
     */
    @Autowired
    private HeaderSearchRepository mockHeaderSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restHeaderMockMvc;

    private Header header;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HeaderResource headerResource = new HeaderResource(headerService);
        this.restHeaderMockMvc = MockMvcBuilders.standaloneSetup(headerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Header createEntity(EntityManager em) {
        Header header = new Header()
            .code(DEFAULT_CODE)
            .description(DEFAULT_DESCRIPTION);
        return header;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Header createUpdatedEntity(EntityManager em) {
        Header header = new Header()
            .code(UPDATED_CODE)
            .description(UPDATED_DESCRIPTION);
        return header;
    }

    @BeforeEach
    public void initTest() {
        header = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllHeaders() throws Exception {
        // Initialize the database
        headerRepository.saveAndFlush(header);

        // Get all the headerList
        restHeaderMockMvc.perform(get("/api/headers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(header.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getHeader() throws Exception {
        // Initialize the database
        headerRepository.saveAndFlush(header);

        // Get the header
        restHeaderMockMvc.perform(get("/api/headers/{id}", header.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(header.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingHeader() throws Exception {
        // Get the header
        restHeaderMockMvc.perform(get("/api/headers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void searchHeader() throws Exception {
        // Initialize the database
        headerService.save(header);
        when(mockHeaderSearchRepository.search(queryStringQuery("id:" + header.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(header), PageRequest.of(0, 1), 1));
        // Search the header
        restHeaderMockMvc.perform(get("/api/_search/headers?query=id:" + header.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(header.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
