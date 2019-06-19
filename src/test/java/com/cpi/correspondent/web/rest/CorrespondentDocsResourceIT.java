package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;
import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;
import com.cpi.correspondent.domain.CorrespondentDocs;
import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.repository.CorrespondentDocsRepository;
import com.cpi.correspondent.service.CorrespondentDocsService;
import com.cpi.correspondent.service.dto.CorrespondentDocsDTO;
import com.cpi.correspondent.service.mapper.CorrespondentDocsMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentDocsCriteria;
import com.cpi.correspondent.service.CorrespondentDocsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cpi.correspondent.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link CorrespondentDocsResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicorrespondentApp.class})
public class CorrespondentDocsResourceIT {

    private static final String DEFAULT_DOCUMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENT_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOAD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final byte[] DEFAULT_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private CorrespondentDocsRepository correspondentDocsRepository;

    @Autowired
    private CorrespondentDocsMapper correspondentDocsMapper;

    @Autowired
    private CorrespondentDocsService correspondentDocsService;

    @Autowired
    private CorrespondentDocsQueryService correspondentDocsQueryService;

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

    private MockMvc restCorrespondentDocsMockMvc;

    private CorrespondentDocs correspondentDocs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentDocsResource correspondentDocsResource = new CorrespondentDocsResource(correspondentDocsService, correspondentDocsQueryService);
        this.restCorrespondentDocsMockMvc = MockMvcBuilders.standaloneSetup(correspondentDocsResource)
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
    public static CorrespondentDocs createEntity(EntityManager em) {
        CorrespondentDocs correspondentDocs = new CorrespondentDocs()
            .documentName(DEFAULT_DOCUMENT_NAME)
            .uploadDate(DEFAULT_UPLOAD_DATE)
            .document(DEFAULT_DOCUMENT)
            .documentContentType(DEFAULT_DOCUMENT_CONTENT_TYPE);
        return correspondentDocs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorrespondentDocs createUpdatedEntity(EntityManager em) {
        CorrespondentDocs correspondentDocs = new CorrespondentDocs()
            .documentName(UPDATED_DOCUMENT_NAME)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE);
        return correspondentDocs;
    }

    @BeforeEach
    public void initTest() {
        correspondentDocs = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentDocs() throws Exception {
        int databaseSizeBeforeCreate = correspondentDocsRepository.findAll().size();

        // Create the CorrespondentDocs
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsMapper.toDto(correspondentDocs);
        restCorrespondentDocsMockMvc.perform(post("/api/correspondent-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDocsDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentDocs in the database
        List<CorrespondentDocs> correspondentDocsList = correspondentDocsRepository.findAll();
        assertThat(correspondentDocsList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentDocs testCorrespondentDocs = correspondentDocsList.get(correspondentDocsList.size() - 1);
        assertThat(testCorrespondentDocs.getDocumentName()).isEqualTo(DEFAULT_DOCUMENT_NAME);
        assertThat(testCorrespondentDocs.getUploadDate()).isEqualTo(DEFAULT_UPLOAD_DATE);
        assertThat(testCorrespondentDocs.getDocument()).isEqualTo(DEFAULT_DOCUMENT);
        assertThat(testCorrespondentDocs.getDocumentContentType()).isEqualTo(DEFAULT_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCorrespondentDocsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentDocsRepository.findAll().size();

        // Create the CorrespondentDocs with an existing ID
        correspondentDocs.setId(1L);
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsMapper.toDto(correspondentDocs);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentDocsMockMvc.perform(post("/api/correspondent-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDocsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentDocs in the database
        List<CorrespondentDocs> correspondentDocsList = correspondentDocsRepository.findAll();
        assertThat(correspondentDocsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCorrespondentDocs() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentDocs.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))));
    }
    
    @Test
    @Transactional
    public void getCorrespondentDocs() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get the correspondentDocs
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs/{id}", correspondentDocs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentDocs.getId().intValue()))
            .andExpect(jsonPath("$.documentName").value(DEFAULT_DOCUMENT_NAME.toString()))
            .andExpect(jsonPath("$.uploadDate").value(DEFAULT_UPLOAD_DATE.toString()))
            .andExpect(jsonPath("$.documentContentType").value(DEFAULT_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.document").value(Base64Utils.encodeToString(DEFAULT_DOCUMENT)));
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByDocumentNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList where documentName equals to DEFAULT_DOCUMENT_NAME
        defaultCorrespondentDocsShouldBeFound("documentName.equals=" + DEFAULT_DOCUMENT_NAME);

        // Get all the correspondentDocsList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultCorrespondentDocsShouldNotBeFound("documentName.equals=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByDocumentNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList where documentName in DEFAULT_DOCUMENT_NAME or UPDATED_DOCUMENT_NAME
        defaultCorrespondentDocsShouldBeFound("documentName.in=" + DEFAULT_DOCUMENT_NAME + "," + UPDATED_DOCUMENT_NAME);

        // Get all the correspondentDocsList where documentName equals to UPDATED_DOCUMENT_NAME
        defaultCorrespondentDocsShouldNotBeFound("documentName.in=" + UPDATED_DOCUMENT_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByDocumentNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList where documentName is not null
        defaultCorrespondentDocsShouldBeFound("documentName.specified=true");

        // Get all the correspondentDocsList where documentName is null
        defaultCorrespondentDocsShouldNotBeFound("documentName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByUploadDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList where uploadDate equals to DEFAULT_UPLOAD_DATE
        defaultCorrespondentDocsShouldBeFound("uploadDate.equals=" + DEFAULT_UPLOAD_DATE);

        // Get all the correspondentDocsList where uploadDate equals to UPDATED_UPLOAD_DATE
        defaultCorrespondentDocsShouldNotBeFound("uploadDate.equals=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByUploadDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList where uploadDate in DEFAULT_UPLOAD_DATE or UPDATED_UPLOAD_DATE
        defaultCorrespondentDocsShouldBeFound("uploadDate.in=" + DEFAULT_UPLOAD_DATE + "," + UPDATED_UPLOAD_DATE);

        // Get all the correspondentDocsList where uploadDate equals to UPDATED_UPLOAD_DATE
        defaultCorrespondentDocsShouldNotBeFound("uploadDate.in=" + UPDATED_UPLOAD_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByUploadDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        // Get all the correspondentDocsList where uploadDate is not null
        defaultCorrespondentDocsShouldBeFound("uploadDate.specified=true");

        // Get all the correspondentDocsList where uploadDate is null
        defaultCorrespondentDocsShouldNotBeFound("uploadDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentDocsByCpiCorrespondentIsEqualToSomething() throws Exception {
        // Initialize the database
        CPICorrespondent cpiCorrespondent = CPICorrespondentResourceIT.createEntity(em);
        em.persist(cpiCorrespondent);
        em.flush();
        correspondentDocs.setCpiCorrespondent(cpiCorrespondent);
        correspondentDocsRepository.saveAndFlush(correspondentDocs);
        Long cpiCorrespondentId = cpiCorrespondent.getId();

        // Get all the correspondentDocsList where cpiCorrespondent equals to cpiCorrespondentId
        defaultCorrespondentDocsShouldBeFound("cpiCorrespondentId.equals=" + cpiCorrespondentId);

        // Get all the correspondentDocsList where cpiCorrespondent equals to cpiCorrespondentId + 1
        defaultCorrespondentDocsShouldNotBeFound("cpiCorrespondentId.equals=" + (cpiCorrespondentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCorrespondentDocsShouldBeFound(String filter) throws Exception {
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentDocs.getId().intValue())))
            .andExpect(jsonPath("$.[*].documentName").value(hasItem(DEFAULT_DOCUMENT_NAME)))
            .andExpect(jsonPath("$.[*].uploadDate").value(hasItem(DEFAULT_UPLOAD_DATE.toString())))
            .andExpect(jsonPath("$.[*].documentContentType").value(hasItem(DEFAULT_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].document").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENT))));

        // Check, that the count call also returns 1
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCorrespondentDocsShouldNotBeFound(String filter) throws Exception {
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentDocs() throws Exception {
        // Get the correspondentDocs
        restCorrespondentDocsMockMvc.perform(get("/api/correspondent-docs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentDocs() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        int databaseSizeBeforeUpdate = correspondentDocsRepository.findAll().size();

        // Update the correspondentDocs
        CorrespondentDocs updatedCorrespondentDocs = correspondentDocsRepository.findById(correspondentDocs.getId()).get();
        // Disconnect from session so that the updates on updatedCorrespondentDocs are not directly saved in db
        em.detach(updatedCorrespondentDocs);
        updatedCorrespondentDocs
            .documentName(UPDATED_DOCUMENT_NAME)
            .uploadDate(UPDATED_UPLOAD_DATE)
            .document(UPDATED_DOCUMENT)
            .documentContentType(UPDATED_DOCUMENT_CONTENT_TYPE);
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsMapper.toDto(updatedCorrespondentDocs);

        restCorrespondentDocsMockMvc.perform(put("/api/correspondent-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDocsDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentDocs in the database
        List<CorrespondentDocs> correspondentDocsList = correspondentDocsRepository.findAll();
        assertThat(correspondentDocsList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentDocs testCorrespondentDocs = correspondentDocsList.get(correspondentDocsList.size() - 1);
        assertThat(testCorrespondentDocs.getDocumentName()).isEqualTo(UPDATED_DOCUMENT_NAME);
        assertThat(testCorrespondentDocs.getUploadDate()).isEqualTo(UPDATED_UPLOAD_DATE);
        assertThat(testCorrespondentDocs.getDocument()).isEqualTo(UPDATED_DOCUMENT);
        assertThat(testCorrespondentDocs.getDocumentContentType()).isEqualTo(UPDATED_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentDocs() throws Exception {
        int databaseSizeBeforeUpdate = correspondentDocsRepository.findAll().size();

        // Create the CorrespondentDocs
        CorrespondentDocsDTO correspondentDocsDTO = correspondentDocsMapper.toDto(correspondentDocs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrespondentDocsMockMvc.perform(put("/api/correspondent-docs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDocsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentDocs in the database
        List<CorrespondentDocs> correspondentDocsList = correspondentDocsRepository.findAll();
        assertThat(correspondentDocsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorrespondentDocs() throws Exception {
        // Initialize the database
        correspondentDocsRepository.saveAndFlush(correspondentDocs);

        int databaseSizeBeforeDelete = correspondentDocsRepository.findAll().size();

        // Delete the correspondentDocs
        restCorrespondentDocsMockMvc.perform(delete("/api/correspondent-docs/{id}", correspondentDocs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CorrespondentDocs> correspondentDocsList = correspondentDocsRepository.findAll();
        assertThat(correspondentDocsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentDocs.class);
        CorrespondentDocs correspondentDocs1 = new CorrespondentDocs();
        correspondentDocs1.setId(1L);
        CorrespondentDocs correspondentDocs2 = new CorrespondentDocs();
        correspondentDocs2.setId(correspondentDocs1.getId());
        assertThat(correspondentDocs1).isEqualTo(correspondentDocs2);
        correspondentDocs2.setId(2L);
        assertThat(correspondentDocs1).isNotEqualTo(correspondentDocs2);
        correspondentDocs1.setId(null);
        assertThat(correspondentDocs1).isNotEqualTo(correspondentDocs2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentDocsDTO.class);
        CorrespondentDocsDTO correspondentDocsDTO1 = new CorrespondentDocsDTO();
        correspondentDocsDTO1.setId(1L);
        CorrespondentDocsDTO correspondentDocsDTO2 = new CorrespondentDocsDTO();
        assertThat(correspondentDocsDTO1).isNotEqualTo(correspondentDocsDTO2);
        correspondentDocsDTO2.setId(correspondentDocsDTO1.getId());
        assertThat(correspondentDocsDTO1).isEqualTo(correspondentDocsDTO2);
        correspondentDocsDTO2.setId(2L);
        assertThat(correspondentDocsDTO1).isNotEqualTo(correspondentDocsDTO2);
        correspondentDocsDTO1.setId(null);
        assertThat(correspondentDocsDTO1).isNotEqualTo(correspondentDocsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentDocsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentDocsMapper.fromId(null)).isNull();
    }
}
