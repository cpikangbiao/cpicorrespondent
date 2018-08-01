package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CorrespondentBillStatus;
import com.cpi.correspondent.repository.CorrespondentBillStatusRepository;
import com.cpi.correspondent.service.CorrespondentBillStatusService;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusCriteria;
import com.cpi.correspondent.service.CorrespondentBillStatusQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cpi.correspondent.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CorrespondentBillStatusResource REST controller.
 *
 * @see CorrespondentBillStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentBillStatusResourceIntTest {

    private static final String DEFAULT_CORRESPONDENT_BILL_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_BILL_STATUS_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private CorrespondentBillStatusRepository correspondentBillStatusRepository;

    @Autowired
    private CorrespondentBillStatusMapper correspondentBillStatusMapper;

    @Autowired
    private CorrespondentBillStatusService correspondentBillStatusService;

    @Autowired
    private CorrespondentBillStatusQueryService correspondentBillStatusQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentBillStatusMockMvc;

    private CorrespondentBillStatus correspondentBillStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentBillStatusResource correspondentBillStatusResource = new CorrespondentBillStatusResource(correspondentBillStatusService, correspondentBillStatusQueryService);
        this.restCorrespondentBillStatusMockMvc = MockMvcBuilders.standaloneSetup(correspondentBillStatusResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorrespondentBillStatus createEntity(EntityManager em) {
        CorrespondentBillStatus correspondentBillStatus = new CorrespondentBillStatus()
            .correspondentBillStatusName(DEFAULT_CORRESPONDENT_BILL_STATUS_NAME)
            .sortNum(DEFAULT_SORT_NUM);
        return correspondentBillStatus;
    }

    @Before
    public void initTest() {
        correspondentBillStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentBillStatus() throws Exception {
        int databaseSizeBeforeCreate = correspondentBillStatusRepository.findAll().size();

        // Create the CorrespondentBillStatus
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusMapper.toDto(correspondentBillStatus);
        restCorrespondentBillStatusMockMvc.perform(post("/api/correspondent-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentBillStatus in the database
        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentBillStatus testCorrespondentBillStatus = correspondentBillStatusList.get(correspondentBillStatusList.size() - 1);
        assertThat(testCorrespondentBillStatus.getCorrespondentBillStatusName()).isEqualTo(DEFAULT_CORRESPONDENT_BILL_STATUS_NAME);
        assertThat(testCorrespondentBillStatus.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createCorrespondentBillStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentBillStatusRepository.findAll().size();

        // Create the CorrespondentBillStatus with an existing ID
        correspondentBillStatus.setId(1L);
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusMapper.toDto(correspondentBillStatus);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentBillStatusMockMvc.perform(post("/api/correspondent-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentBillStatus in the database
        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentBillStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentBillStatusRepository.findAll().size();
        // set the field null
        correspondentBillStatus.setCorrespondentBillStatusName(null);

        // Create the CorrespondentBillStatus, which fails.
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusMapper.toDto(correspondentBillStatus);

        restCorrespondentBillStatusMockMvc.perform(post("/api/correspondent-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentBillStatusRepository.findAll().size();
        // set the field null
        correspondentBillStatus.setSortNum(null);

        // Create the CorrespondentBillStatus, which fails.
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusMapper.toDto(correspondentBillStatus);

        restCorrespondentBillStatusMockMvc.perform(post("/api/correspondent-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatuses() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList
        restCorrespondentBillStatusMockMvc.perform(get("/api/correspondent-bill-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentBillStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentBillStatusName").value(hasItem(DEFAULT_CORRESPONDENT_BILL_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    @Test
    @Transactional
    public void getCorrespondentBillStatus() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get the correspondentBillStatus
        restCorrespondentBillStatusMockMvc.perform(get("/api/correspondent-bill-statuses/{id}", correspondentBillStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentBillStatus.getId().intValue()))
            .andExpect(jsonPath("$.correspondentBillStatusName").value(DEFAULT_CORRESPONDENT_BILL_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesByCorrespondentBillStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where correspondentBillStatusName equals to DEFAULT_CORRESPONDENT_BILL_STATUS_NAME
        defaultCorrespondentBillStatusShouldBeFound("correspondentBillStatusName.equals=" + DEFAULT_CORRESPONDENT_BILL_STATUS_NAME);

        // Get all the correspondentBillStatusList where correspondentBillStatusName equals to UPDATED_CORRESPONDENT_BILL_STATUS_NAME
        defaultCorrespondentBillStatusShouldNotBeFound("correspondentBillStatusName.equals=" + UPDATED_CORRESPONDENT_BILL_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesByCorrespondentBillStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where correspondentBillStatusName in DEFAULT_CORRESPONDENT_BILL_STATUS_NAME or UPDATED_CORRESPONDENT_BILL_STATUS_NAME
        defaultCorrespondentBillStatusShouldBeFound("correspondentBillStatusName.in=" + DEFAULT_CORRESPONDENT_BILL_STATUS_NAME + "," + UPDATED_CORRESPONDENT_BILL_STATUS_NAME);

        // Get all the correspondentBillStatusList where correspondentBillStatusName equals to UPDATED_CORRESPONDENT_BILL_STATUS_NAME
        defaultCorrespondentBillStatusShouldNotBeFound("correspondentBillStatusName.in=" + UPDATED_CORRESPONDENT_BILL_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesByCorrespondentBillStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where correspondentBillStatusName is not null
        defaultCorrespondentBillStatusShouldBeFound("correspondentBillStatusName.specified=true");

        // Get all the correspondentBillStatusList where correspondentBillStatusName is null
        defaultCorrespondentBillStatusShouldNotBeFound("correspondentBillStatusName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where sortNum equals to DEFAULT_SORT_NUM
        defaultCorrespondentBillStatusShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the correspondentBillStatusList where sortNum equals to UPDATED_SORT_NUM
        defaultCorrespondentBillStatusShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultCorrespondentBillStatusShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the correspondentBillStatusList where sortNum equals to UPDATED_SORT_NUM
        defaultCorrespondentBillStatusShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where sortNum is not null
        defaultCorrespondentBillStatusShouldBeFound("sortNum.specified=true");

        // Get all the correspondentBillStatusList where sortNum is null
        defaultCorrespondentBillStatusShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultCorrespondentBillStatusShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the correspondentBillStatusList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultCorrespondentBillStatusShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);

        // Get all the correspondentBillStatusList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultCorrespondentBillStatusShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the correspondentBillStatusList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultCorrespondentBillStatusShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentBillStatusShouldBeFound(String filter) throws Exception {
        restCorrespondentBillStatusMockMvc.perform(get("/api/correspondent-bill-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentBillStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentBillStatusName").value(hasItem(DEFAULT_CORRESPONDENT_BILL_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentBillStatusShouldNotBeFound(String filter) throws Exception {
        restCorrespondentBillStatusMockMvc.perform(get("/api/correspondent-bill-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentBillStatus() throws Exception {
        // Get the correspondentBillStatus
        restCorrespondentBillStatusMockMvc.perform(get("/api/correspondent-bill-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentBillStatus() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);
        int databaseSizeBeforeUpdate = correspondentBillStatusRepository.findAll().size();

        // Update the correspondentBillStatus
        CorrespondentBillStatus updatedCorrespondentBillStatus = correspondentBillStatusRepository.findOne(correspondentBillStatus.getId());
        // Disconnect from session so that the updates on updatedCorrespondentBillStatus are not directly saved in db
        em.detach(updatedCorrespondentBillStatus);
        updatedCorrespondentBillStatus
            .correspondentBillStatusName(UPDATED_CORRESPONDENT_BILL_STATUS_NAME)
            .sortNum(UPDATED_SORT_NUM);
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusMapper.toDto(updatedCorrespondentBillStatus);

        restCorrespondentBillStatusMockMvc.perform(put("/api/correspondent-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentBillStatus in the database
        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentBillStatus testCorrespondentBillStatus = correspondentBillStatusList.get(correspondentBillStatusList.size() - 1);
        assertThat(testCorrespondentBillStatus.getCorrespondentBillStatusName()).isEqualTo(UPDATED_CORRESPONDENT_BILL_STATUS_NAME);
        assertThat(testCorrespondentBillStatus.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentBillStatus() throws Exception {
        int databaseSizeBeforeUpdate = correspondentBillStatusRepository.findAll().size();

        // Create the CorrespondentBillStatus
        CorrespondentBillStatusDTO correspondentBillStatusDTO = correspondentBillStatusMapper.toDto(correspondentBillStatus);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentBillStatusMockMvc.perform(put("/api/correspondent-bill-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentBillStatus in the database
        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondentBillStatus() throws Exception {
        // Initialize the database
        correspondentBillStatusRepository.saveAndFlush(correspondentBillStatus);
        int databaseSizeBeforeDelete = correspondentBillStatusRepository.findAll().size();

        // Get the correspondentBillStatus
        restCorrespondentBillStatusMockMvc.perform(delete("/api/correspondent-bill-statuses/{id}", correspondentBillStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentBillStatus> correspondentBillStatusList = correspondentBillStatusRepository.findAll();
        assertThat(correspondentBillStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentBillStatus.class);
        CorrespondentBillStatus correspondentBillStatus1 = new CorrespondentBillStatus();
        correspondentBillStatus1.setId(1L);
        CorrespondentBillStatus correspondentBillStatus2 = new CorrespondentBillStatus();
        correspondentBillStatus2.setId(correspondentBillStatus1.getId());
        assertThat(correspondentBillStatus1).isEqualTo(correspondentBillStatus2);
        correspondentBillStatus2.setId(2L);
        assertThat(correspondentBillStatus1).isNotEqualTo(correspondentBillStatus2);
        correspondentBillStatus1.setId(null);
        assertThat(correspondentBillStatus1).isNotEqualTo(correspondentBillStatus2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentBillStatusDTO.class);
        CorrespondentBillStatusDTO correspondentBillStatusDTO1 = new CorrespondentBillStatusDTO();
        correspondentBillStatusDTO1.setId(1L);
        CorrespondentBillStatusDTO correspondentBillStatusDTO2 = new CorrespondentBillStatusDTO();
        assertThat(correspondentBillStatusDTO1).isNotEqualTo(correspondentBillStatusDTO2);
        correspondentBillStatusDTO2.setId(correspondentBillStatusDTO1.getId());
        assertThat(correspondentBillStatusDTO1).isEqualTo(correspondentBillStatusDTO2);
        correspondentBillStatusDTO2.setId(2L);
        assertThat(correspondentBillStatusDTO1).isNotEqualTo(correspondentBillStatusDTO2);
        correspondentBillStatusDTO1.setId(null);
        assertThat(correspondentBillStatusDTO1).isNotEqualTo(correspondentBillStatusDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentBillStatusMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentBillStatusMapper.fromId(null)).isNull();
    }
}
