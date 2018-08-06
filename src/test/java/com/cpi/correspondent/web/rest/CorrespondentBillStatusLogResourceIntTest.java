package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CorrespondentBillStatusLog;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.repository.CorrespondentBillStatusLogRepository;
import com.cpi.correspondent.service.CorrespondentBillStatusLogService;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillStatusLogMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentBillStatusLogCriteria;
import com.cpi.correspondent.service.CorrespondentBillStatusLogQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cpi.correspondent.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CorrespondentBillStatusLogResource REST controller.
 *
 * @see CorrespondentBillStatusLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentBillStatusLogResourceIntTest {

    private static final String DEFAULT_BILL_STATUS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BILL_STATUS_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATE_USER = 1L;
    private static final Long UPDATED_UPDATE_USER = 2L;

    @Autowired
    private CorrespondentBillStatusLogRepository correspondentBillStatusLogRepository;

    @Autowired
    private CorrespondentBillStatusLogMapper correspondentBillStatusLogMapper;

    @Autowired
    private CorrespondentBillStatusLogService correspondentBillStatusLogService;

    @Autowired
    private CorrespondentBillStatusLogQueryService correspondentBillStatusLogQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentBillStatusLogMockMvc;

    private CorrespondentBillStatusLog correspondentBillStatusLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentBillStatusLogResource correspondentBillStatusLogResource = new CorrespondentBillStatusLogResource(correspondentBillStatusLogService, correspondentBillStatusLogQueryService);
        this.restCorrespondentBillStatusLogMockMvc = MockMvcBuilders.standaloneSetup(correspondentBillStatusLogResource)
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
    public static CorrespondentBillStatusLog createEntity(EntityManager em) {
        CorrespondentBillStatusLog correspondentBillStatusLog = new CorrespondentBillStatusLog()
            .billStatusName(DEFAULT_BILL_STATUS_NAME)
            .updateTime(DEFAULT_UPDATE_TIME)
            .updateUser(DEFAULT_UPDATE_USER);
        return correspondentBillStatusLog;
    }

    @Before
    public void initTest() {
        correspondentBillStatusLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentBillStatusLog() throws Exception {
        int databaseSizeBeforeCreate = correspondentBillStatusLogRepository.findAll().size();

        // Create the CorrespondentBillStatusLog
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogMapper.toDto(correspondentBillStatusLog);
        restCorrespondentBillStatusLogMockMvc.perform(post("/api/correspondent-bill-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusLogDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentBillStatusLog in the database
        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentBillStatusLog testCorrespondentBillStatusLog = correspondentBillStatusLogList.get(correspondentBillStatusLogList.size() - 1);
        assertThat(testCorrespondentBillStatusLog.getBillStatusName()).isEqualTo(DEFAULT_BILL_STATUS_NAME);
        assertThat(testCorrespondentBillStatusLog.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testCorrespondentBillStatusLog.getUpdateUser()).isEqualTo(DEFAULT_UPDATE_USER);
    }

    @Test
    @Transactional
    public void createCorrespondentBillStatusLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentBillStatusLogRepository.findAll().size();

        // Create the CorrespondentBillStatusLog with an existing ID
        correspondentBillStatusLog.setId(1L);
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogMapper.toDto(correspondentBillStatusLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentBillStatusLogMockMvc.perform(post("/api/correspondent-bill-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentBillStatusLog in the database
        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBillStatusNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentBillStatusLogRepository.findAll().size();
        // set the field null
        correspondentBillStatusLog.setBillStatusName(null);

        // Create the CorrespondentBillStatusLog, which fails.
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogMapper.toDto(correspondentBillStatusLog);

        restCorrespondentBillStatusLogMockMvc.perform(post("/api/correspondent-bill-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusLogDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentBillStatusLogRepository.findAll().size();
        // set the field null
        correspondentBillStatusLog.setUpdateTime(null);

        // Create the CorrespondentBillStatusLog, which fails.
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogMapper.toDto(correspondentBillStatusLog);

        restCorrespondentBillStatusLogMockMvc.perform(post("/api/correspondent-bill-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusLogDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogs() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList
        restCorrespondentBillStatusLogMockMvc.perform(get("/api/correspondent-bill-status-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentBillStatusLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].billStatusName").value(hasItem(DEFAULT_BILL_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER.intValue())));
    }

    @Test
    @Transactional
    public void getCorrespondentBillStatusLog() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get the correspondentBillStatusLog
        restCorrespondentBillStatusLogMockMvc.perform(get("/api/correspondent-bill-status-logs/{id}", correspondentBillStatusLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentBillStatusLog.getId().intValue()))
            .andExpect(jsonPath("$.billStatusName").value(DEFAULT_BILL_STATUS_NAME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.updateUser").value(DEFAULT_UPDATE_USER.intValue()));
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByBillStatusNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where billStatusName equals to DEFAULT_BILL_STATUS_NAME
        defaultCorrespondentBillStatusLogShouldBeFound("billStatusName.equals=" + DEFAULT_BILL_STATUS_NAME);

        // Get all the correspondentBillStatusLogList where billStatusName equals to UPDATED_BILL_STATUS_NAME
        defaultCorrespondentBillStatusLogShouldNotBeFound("billStatusName.equals=" + UPDATED_BILL_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByBillStatusNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where billStatusName in DEFAULT_BILL_STATUS_NAME or UPDATED_BILL_STATUS_NAME
        defaultCorrespondentBillStatusLogShouldBeFound("billStatusName.in=" + DEFAULT_BILL_STATUS_NAME + "," + UPDATED_BILL_STATUS_NAME);

        // Get all the correspondentBillStatusLogList where billStatusName equals to UPDATED_BILL_STATUS_NAME
        defaultCorrespondentBillStatusLogShouldNotBeFound("billStatusName.in=" + UPDATED_BILL_STATUS_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByBillStatusNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where billStatusName is not null
        defaultCorrespondentBillStatusLogShouldBeFound("billStatusName.specified=true");

        // Get all the correspondentBillStatusLogList where billStatusName is null
        defaultCorrespondentBillStatusLogShouldNotBeFound("billStatusName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateTime equals to DEFAULT_UPDATE_TIME
        defaultCorrespondentBillStatusLogShouldBeFound("updateTime.equals=" + DEFAULT_UPDATE_TIME);

        // Get all the correspondentBillStatusLogList where updateTime equals to UPDATED_UPDATE_TIME
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateTime.equals=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateTime in DEFAULT_UPDATE_TIME or UPDATED_UPDATE_TIME
        defaultCorrespondentBillStatusLogShouldBeFound("updateTime.in=" + DEFAULT_UPDATE_TIME + "," + UPDATED_UPDATE_TIME);

        // Get all the correspondentBillStatusLogList where updateTime equals to UPDATED_UPDATE_TIME
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateTime.in=" + UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateTime is not null
        defaultCorrespondentBillStatusLogShouldBeFound("updateTime.specified=true");

        // Get all the correspondentBillStatusLogList where updateTime is null
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateUserIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateUser equals to DEFAULT_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldBeFound("updateUser.equals=" + DEFAULT_UPDATE_USER);

        // Get all the correspondentBillStatusLogList where updateUser equals to UPDATED_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateUser.equals=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateUserIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateUser in DEFAULT_UPDATE_USER or UPDATED_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldBeFound("updateUser.in=" + DEFAULT_UPDATE_USER + "," + UPDATED_UPDATE_USER);

        // Get all the correspondentBillStatusLogList where updateUser equals to UPDATED_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateUser.in=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateUser is not null
        defaultCorrespondentBillStatusLogShouldBeFound("updateUser.specified=true");

        // Get all the correspondentBillStatusLogList where updateUser is null
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateUserIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateUser greater than or equals to DEFAULT_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldBeFound("updateUser.greaterOrEqualThan=" + DEFAULT_UPDATE_USER);

        // Get all the correspondentBillStatusLogList where updateUser greater than or equals to UPDATED_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateUser.greaterOrEqualThan=" + UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByUpdateUserIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);

        // Get all the correspondentBillStatusLogList where updateUser less than or equals to DEFAULT_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldNotBeFound("updateUser.lessThan=" + DEFAULT_UPDATE_USER);

        // Get all the correspondentBillStatusLogList where updateUser less than or equals to UPDATED_UPDATE_USER
        defaultCorrespondentBillStatusLogShouldBeFound("updateUser.lessThan=" + UPDATED_UPDATE_USER);
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillStatusLogsByCorrespondentBillIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentBill correspondentBill = CorrespondentBillResourceIntTest.createEntity(em);
        em.persist(correspondentBill);
        em.flush();
        correspondentBillStatusLog.setCorrespondentBill(correspondentBill);
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);
        Long correspondentBillId = correspondentBill.getId();

        // Get all the correspondentBillStatusLogList where correspondentBill equals to correspondentBillId
        defaultCorrespondentBillStatusLogShouldBeFound("correspondentBillId.equals=" + correspondentBillId);

        // Get all the correspondentBillStatusLogList where correspondentBill equals to correspondentBillId + 1
        defaultCorrespondentBillStatusLogShouldNotBeFound("correspondentBillId.equals=" + (correspondentBillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentBillStatusLogShouldBeFound(String filter) throws Exception {
        restCorrespondentBillStatusLogMockMvc.perform(get("/api/correspondent-bill-status-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentBillStatusLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].billStatusName").value(hasItem(DEFAULT_BILL_STATUS_NAME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateUser").value(hasItem(DEFAULT_UPDATE_USER.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentBillStatusLogShouldNotBeFound(String filter) throws Exception {
        restCorrespondentBillStatusLogMockMvc.perform(get("/api/correspondent-bill-status-logs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentBillStatusLog() throws Exception {
        // Get the correspondentBillStatusLog
        restCorrespondentBillStatusLogMockMvc.perform(get("/api/correspondent-bill-status-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentBillStatusLog() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);
        int databaseSizeBeforeUpdate = correspondentBillStatusLogRepository.findAll().size();

        // Update the correspondentBillStatusLog
        CorrespondentBillStatusLog updatedCorrespondentBillStatusLog = correspondentBillStatusLogRepository.findOne(correspondentBillStatusLog.getId());
        // Disconnect from session so that the updates on updatedCorrespondentBillStatusLog are not directly saved in db
        em.detach(updatedCorrespondentBillStatusLog);
        updatedCorrespondentBillStatusLog
            .billStatusName(UPDATED_BILL_STATUS_NAME)
            .updateTime(UPDATED_UPDATE_TIME)
            .updateUser(UPDATED_UPDATE_USER);
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogMapper.toDto(updatedCorrespondentBillStatusLog);

        restCorrespondentBillStatusLogMockMvc.perform(put("/api/correspondent-bill-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusLogDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentBillStatusLog in the database
        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentBillStatusLog testCorrespondentBillStatusLog = correspondentBillStatusLogList.get(correspondentBillStatusLogList.size() - 1);
        assertThat(testCorrespondentBillStatusLog.getBillStatusName()).isEqualTo(UPDATED_BILL_STATUS_NAME);
        assertThat(testCorrespondentBillStatusLog.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testCorrespondentBillStatusLog.getUpdateUser()).isEqualTo(UPDATED_UPDATE_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentBillStatusLog() throws Exception {
        int databaseSizeBeforeUpdate = correspondentBillStatusLogRepository.findAll().size();

        // Create the CorrespondentBillStatusLog
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO = correspondentBillStatusLogMapper.toDto(correspondentBillStatusLog);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentBillStatusLogMockMvc.perform(put("/api/correspondent-bill-status-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillStatusLogDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentBillStatusLog in the database
        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondentBillStatusLog() throws Exception {
        // Initialize the database
        correspondentBillStatusLogRepository.saveAndFlush(correspondentBillStatusLog);
        int databaseSizeBeforeDelete = correspondentBillStatusLogRepository.findAll().size();

        // Get the correspondentBillStatusLog
        restCorrespondentBillStatusLogMockMvc.perform(delete("/api/correspondent-bill-status-logs/{id}", correspondentBillStatusLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentBillStatusLog> correspondentBillStatusLogList = correspondentBillStatusLogRepository.findAll();
        assertThat(correspondentBillStatusLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentBillStatusLog.class);
        CorrespondentBillStatusLog correspondentBillStatusLog1 = new CorrespondentBillStatusLog();
        correspondentBillStatusLog1.setId(1L);
        CorrespondentBillStatusLog correspondentBillStatusLog2 = new CorrespondentBillStatusLog();
        correspondentBillStatusLog2.setId(correspondentBillStatusLog1.getId());
        assertThat(correspondentBillStatusLog1).isEqualTo(correspondentBillStatusLog2);
        correspondentBillStatusLog2.setId(2L);
        assertThat(correspondentBillStatusLog1).isNotEqualTo(correspondentBillStatusLog2);
        correspondentBillStatusLog1.setId(null);
        assertThat(correspondentBillStatusLog1).isNotEqualTo(correspondentBillStatusLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentBillStatusLogDTO.class);
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO1 = new CorrespondentBillStatusLogDTO();
        correspondentBillStatusLogDTO1.setId(1L);
        CorrespondentBillStatusLogDTO correspondentBillStatusLogDTO2 = new CorrespondentBillStatusLogDTO();
        assertThat(correspondentBillStatusLogDTO1).isNotEqualTo(correspondentBillStatusLogDTO2);
        correspondentBillStatusLogDTO2.setId(correspondentBillStatusLogDTO1.getId());
        assertThat(correspondentBillStatusLogDTO1).isEqualTo(correspondentBillStatusLogDTO2);
        correspondentBillStatusLogDTO2.setId(2L);
        assertThat(correspondentBillStatusLogDTO1).isNotEqualTo(correspondentBillStatusLogDTO2);
        correspondentBillStatusLogDTO1.setId(null);
        assertThat(correspondentBillStatusLogDTO1).isNotEqualTo(correspondentBillStatusLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentBillStatusLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentBillStatusLogMapper.fromId(null)).isNull();
    }
}
