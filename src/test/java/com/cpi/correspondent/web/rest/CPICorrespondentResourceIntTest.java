package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.domain.CorrespondentType;
import com.cpi.correspondent.domain.Club;
import com.cpi.correspondent.domain.ClubPerson;
import com.cpi.correspondent.repository.CPICorrespondentRepository;
import com.cpi.correspondent.service.CPICorrespondentService;
import com.cpi.correspondent.service.dto.CPICorrespondentDTO;
import com.cpi.correspondent.service.mapper.CPICorrespondentMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CPICorrespondentCriteria;
import com.cpi.correspondent.service.CPICorrespondentQueryService;

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
import org.springframework.util.Base64Utils;

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
 * Test class for the CPICorrespondentResource REST controller.
 *
 * @see CPICorrespondentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CPICorrespondentResourceIntTest {

    private static final String DEFAULT_CORRESPONDENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_VESSEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VESSEL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_REF = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_REF = "BBBBBBBBBB";

    private static final String DEFAULT_KEY_WORD = "AAAAAAAAAA";
    private static final String UPDATED_KEY_WORD = "BBBBBBBBBB";

    private static final Instant DEFAULT_REGISTER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGISTER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_HANDLER_USER = 1L;
    private static final Long UPDATED_HANDLER_USER = 2L;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private CPICorrespondentRepository cPICorrespondentRepository;

    @Autowired
    private CPICorrespondentMapper cPICorrespondentMapper;

    @Autowired
    private CPICorrespondentService cPICorrespondentService;

    @Autowired
    private CPICorrespondentQueryService cPICorrespondentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCPICorrespondentMockMvc;

    private CPICorrespondent cPICorrespondent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CPICorrespondentResource cPICorrespondentResource = new CPICorrespondentResource(cPICorrespondentService, cPICorrespondentQueryService);
        this.restCPICorrespondentMockMvc = MockMvcBuilders.standaloneSetup(cPICorrespondentResource)
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
    public static CPICorrespondent createEntity(EntityManager em) {
        CPICorrespondent cPICorrespondent = new CPICorrespondent()
            .correspondentCode(DEFAULT_CORRESPONDENT_CODE)
            .year(DEFAULT_YEAR)
            .vesselName(DEFAULT_VESSEL_NAME)
            .clientRef(DEFAULT_CLIENT_REF)
            .keyWord(DEFAULT_KEY_WORD)
            .registerDate(DEFAULT_REGISTER_DATE)
            .caseDate(DEFAULT_CASE_DATE)
            .handlerUser(DEFAULT_HANDLER_USER)
            .remark(DEFAULT_REMARK);
        return cPICorrespondent;
    }

    @Before
    public void initTest() {
        cPICorrespondent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCPICorrespondent() throws Exception {
        int databaseSizeBeforeCreate = cPICorrespondentRepository.findAll().size();

        // Create the CPICorrespondent
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(cPICorrespondent);
        restCPICorrespondentMockMvc.perform(post("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isCreated());

        // Validate the CPICorrespondent in the database
        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeCreate + 1);
        CPICorrespondent testCPICorrespondent = cPICorrespondentList.get(cPICorrespondentList.size() - 1);
        assertThat(testCPICorrespondent.getCorrespondentCode()).isEqualTo(DEFAULT_CORRESPONDENT_CODE);
        assertThat(testCPICorrespondent.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCPICorrespondent.getVesselName()).isEqualTo(DEFAULT_VESSEL_NAME);
        assertThat(testCPICorrespondent.getClientRef()).isEqualTo(DEFAULT_CLIENT_REF);
        assertThat(testCPICorrespondent.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testCPICorrespondent.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
        assertThat(testCPICorrespondent.getCaseDate()).isEqualTo(DEFAULT_CASE_DATE);
        assertThat(testCPICorrespondent.getHandlerUser()).isEqualTo(DEFAULT_HANDLER_USER);
        assertThat(testCPICorrespondent.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createCPICorrespondentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cPICorrespondentRepository.findAll().size();

        // Create the CPICorrespondent with an existing ID
        cPICorrespondent.setId(1L);
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(cPICorrespondent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCPICorrespondentMockMvc.perform(post("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CPICorrespondent in the database
        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPICorrespondentRepository.findAll().size();
        // set the field null
        cPICorrespondent.setCorrespondentCode(null);

        // Create the CPICorrespondent, which fails.
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(cPICorrespondent);

        restCPICorrespondentMockMvc.perform(post("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isBadRequest());

        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPICorrespondentRepository.findAll().size();
        // set the field null
        cPICorrespondent.setYear(null);

        // Create the CPICorrespondent, which fails.
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(cPICorrespondent);

        restCPICorrespondentMockMvc.perform(post("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isBadRequest());

        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVesselNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cPICorrespondentRepository.findAll().size();
        // set the field null
        cPICorrespondent.setVesselName(null);

        // Create the CPICorrespondent, which fails.
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(cPICorrespondent);

        restCPICorrespondentMockMvc.perform(post("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isBadRequest());

        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondents() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList
        restCPICorrespondentMockMvc.perform(get("/api/cpi-correspondents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPICorrespondent.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentCode").value(hasItem(DEFAULT_CORRESPONDENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].vesselName").value(hasItem(DEFAULT_VESSEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientRef").value(hasItem(DEFAULT_CLIENT_REF.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())))
            .andExpect(jsonPath("$.[*].caseDate").value(hasItem(DEFAULT_CASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].handlerUser").value(hasItem(DEFAULT_HANDLER_USER.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getCPICorrespondent() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get the cPICorrespondent
        restCPICorrespondentMockMvc.perform(get("/api/cpi-correspondents/{id}", cPICorrespondent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cPICorrespondent.getId().intValue()))
            .andExpect(jsonPath("$.correspondentCode").value(DEFAULT_CORRESPONDENT_CODE.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.vesselName").value(DEFAULT_VESSEL_NAME.toString()))
            .andExpect(jsonPath("$.clientRef").value(DEFAULT_CLIENT_REF.toString()))
            .andExpect(jsonPath("$.keyWord").value(DEFAULT_KEY_WORD.toString()))
            .andExpect(jsonPath("$.registerDate").value(DEFAULT_REGISTER_DATE.toString()))
            .andExpect(jsonPath("$.caseDate").value(DEFAULT_CASE_DATE.toString()))
            .andExpect(jsonPath("$.handlerUser").value(DEFAULT_HANDLER_USER.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByCorrespondentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where correspondentCode equals to DEFAULT_CORRESPONDENT_CODE
        defaultCPICorrespondentShouldBeFound("correspondentCode.equals=" + DEFAULT_CORRESPONDENT_CODE);

        // Get all the cPICorrespondentList where correspondentCode equals to UPDATED_CORRESPONDENT_CODE
        defaultCPICorrespondentShouldNotBeFound("correspondentCode.equals=" + UPDATED_CORRESPONDENT_CODE);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByCorrespondentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where correspondentCode in DEFAULT_CORRESPONDENT_CODE or UPDATED_CORRESPONDENT_CODE
        defaultCPICorrespondentShouldBeFound("correspondentCode.in=" + DEFAULT_CORRESPONDENT_CODE + "," + UPDATED_CORRESPONDENT_CODE);

        // Get all the cPICorrespondentList where correspondentCode equals to UPDATED_CORRESPONDENT_CODE
        defaultCPICorrespondentShouldNotBeFound("correspondentCode.in=" + UPDATED_CORRESPONDENT_CODE);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByCorrespondentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where correspondentCode is not null
        defaultCPICorrespondentShouldBeFound("correspondentCode.specified=true");

        // Get all the cPICorrespondentList where correspondentCode is null
        defaultCPICorrespondentShouldNotBeFound("correspondentCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where year equals to DEFAULT_YEAR
        defaultCPICorrespondentShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the cPICorrespondentList where year equals to UPDATED_YEAR
        defaultCPICorrespondentShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultCPICorrespondentShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the cPICorrespondentList where year equals to UPDATED_YEAR
        defaultCPICorrespondentShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where year is not null
        defaultCPICorrespondentShouldBeFound("year.specified=true");

        // Get all the cPICorrespondentList where year is null
        defaultCPICorrespondentShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByVesselNameIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where vesselName equals to DEFAULT_VESSEL_NAME
        defaultCPICorrespondentShouldBeFound("vesselName.equals=" + DEFAULT_VESSEL_NAME);

        // Get all the cPICorrespondentList where vesselName equals to UPDATED_VESSEL_NAME
        defaultCPICorrespondentShouldNotBeFound("vesselName.equals=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByVesselNameIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where vesselName in DEFAULT_VESSEL_NAME or UPDATED_VESSEL_NAME
        defaultCPICorrespondentShouldBeFound("vesselName.in=" + DEFAULT_VESSEL_NAME + "," + UPDATED_VESSEL_NAME);

        // Get all the cPICorrespondentList where vesselName equals to UPDATED_VESSEL_NAME
        defaultCPICorrespondentShouldNotBeFound("vesselName.in=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByVesselNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where vesselName is not null
        defaultCPICorrespondentShouldBeFound("vesselName.specified=true");

        // Get all the cPICorrespondentList where vesselName is null
        defaultCPICorrespondentShouldNotBeFound("vesselName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByClientRefIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where clientRef equals to DEFAULT_CLIENT_REF
        defaultCPICorrespondentShouldBeFound("clientRef.equals=" + DEFAULT_CLIENT_REF);

        // Get all the cPICorrespondentList where clientRef equals to UPDATED_CLIENT_REF
        defaultCPICorrespondentShouldNotBeFound("clientRef.equals=" + UPDATED_CLIENT_REF);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByClientRefIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where clientRef in DEFAULT_CLIENT_REF or UPDATED_CLIENT_REF
        defaultCPICorrespondentShouldBeFound("clientRef.in=" + DEFAULT_CLIENT_REF + "," + UPDATED_CLIENT_REF);

        // Get all the cPICorrespondentList where clientRef equals to UPDATED_CLIENT_REF
        defaultCPICorrespondentShouldNotBeFound("clientRef.in=" + UPDATED_CLIENT_REF);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByClientRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where clientRef is not null
        defaultCPICorrespondentShouldBeFound("clientRef.specified=true");

        // Get all the cPICorrespondentList where clientRef is null
        defaultCPICorrespondentShouldNotBeFound("clientRef.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByKeyWordIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where keyWord equals to DEFAULT_KEY_WORD
        defaultCPICorrespondentShouldBeFound("keyWord.equals=" + DEFAULT_KEY_WORD);

        // Get all the cPICorrespondentList where keyWord equals to UPDATED_KEY_WORD
        defaultCPICorrespondentShouldNotBeFound("keyWord.equals=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByKeyWordIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where keyWord in DEFAULT_KEY_WORD or UPDATED_KEY_WORD
        defaultCPICorrespondentShouldBeFound("keyWord.in=" + DEFAULT_KEY_WORD + "," + UPDATED_KEY_WORD);

        // Get all the cPICorrespondentList where keyWord equals to UPDATED_KEY_WORD
        defaultCPICorrespondentShouldNotBeFound("keyWord.in=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByKeyWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where keyWord is not null
        defaultCPICorrespondentShouldBeFound("keyWord.specified=true");

        // Get all the cPICorrespondentList where keyWord is null
        defaultCPICorrespondentShouldNotBeFound("keyWord.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByRegisterDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where registerDate equals to DEFAULT_REGISTER_DATE
        defaultCPICorrespondentShouldBeFound("registerDate.equals=" + DEFAULT_REGISTER_DATE);

        // Get all the cPICorrespondentList where registerDate equals to UPDATED_REGISTER_DATE
        defaultCPICorrespondentShouldNotBeFound("registerDate.equals=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByRegisterDateIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where registerDate in DEFAULT_REGISTER_DATE or UPDATED_REGISTER_DATE
        defaultCPICorrespondentShouldBeFound("registerDate.in=" + DEFAULT_REGISTER_DATE + "," + UPDATED_REGISTER_DATE);

        // Get all the cPICorrespondentList where registerDate equals to UPDATED_REGISTER_DATE
        defaultCPICorrespondentShouldNotBeFound("registerDate.in=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByRegisterDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where registerDate is not null
        defaultCPICorrespondentShouldBeFound("registerDate.specified=true");

        // Get all the cPICorrespondentList where registerDate is null
        defaultCPICorrespondentShouldNotBeFound("registerDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByCaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where caseDate equals to DEFAULT_CASE_DATE
        defaultCPICorrespondentShouldBeFound("caseDate.equals=" + DEFAULT_CASE_DATE);

        // Get all the cPICorrespondentList where caseDate equals to UPDATED_CASE_DATE
        defaultCPICorrespondentShouldNotBeFound("caseDate.equals=" + UPDATED_CASE_DATE);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByCaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where caseDate in DEFAULT_CASE_DATE or UPDATED_CASE_DATE
        defaultCPICorrespondentShouldBeFound("caseDate.in=" + DEFAULT_CASE_DATE + "," + UPDATED_CASE_DATE);

        // Get all the cPICorrespondentList where caseDate equals to UPDATED_CASE_DATE
        defaultCPICorrespondentShouldNotBeFound("caseDate.in=" + UPDATED_CASE_DATE);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByCaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where caseDate is not null
        defaultCPICorrespondentShouldBeFound("caseDate.specified=true");

        // Get all the cPICorrespondentList where caseDate is null
        defaultCPICorrespondentShouldNotBeFound("caseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByHandlerUserIsEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where handlerUser equals to DEFAULT_HANDLER_USER
        defaultCPICorrespondentShouldBeFound("handlerUser.equals=" + DEFAULT_HANDLER_USER);

        // Get all the cPICorrespondentList where handlerUser equals to UPDATED_HANDLER_USER
        defaultCPICorrespondentShouldNotBeFound("handlerUser.equals=" + UPDATED_HANDLER_USER);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByHandlerUserIsInShouldWork() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where handlerUser in DEFAULT_HANDLER_USER or UPDATED_HANDLER_USER
        defaultCPICorrespondentShouldBeFound("handlerUser.in=" + DEFAULT_HANDLER_USER + "," + UPDATED_HANDLER_USER);

        // Get all the cPICorrespondentList where handlerUser equals to UPDATED_HANDLER_USER
        defaultCPICorrespondentShouldNotBeFound("handlerUser.in=" + UPDATED_HANDLER_USER);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByHandlerUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where handlerUser is not null
        defaultCPICorrespondentShouldBeFound("handlerUser.specified=true");

        // Get all the cPICorrespondentList where handlerUser is null
        defaultCPICorrespondentShouldNotBeFound("handlerUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByHandlerUserIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where handlerUser greater than or equals to DEFAULT_HANDLER_USER
        defaultCPICorrespondentShouldBeFound("handlerUser.greaterOrEqualThan=" + DEFAULT_HANDLER_USER);

        // Get all the cPICorrespondentList where handlerUser greater than or equals to UPDATED_HANDLER_USER
        defaultCPICorrespondentShouldNotBeFound("handlerUser.greaterOrEqualThan=" + UPDATED_HANDLER_USER);
    }

    @Test
    @Transactional
    public void getAllCPICorrespondentsByHandlerUserIsLessThanSomething() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);

        // Get all the cPICorrespondentList where handlerUser less than or equals to DEFAULT_HANDLER_USER
        defaultCPICorrespondentShouldNotBeFound("handlerUser.lessThan=" + DEFAULT_HANDLER_USER);

        // Get all the cPICorrespondentList where handlerUser less than or equals to UPDATED_HANDLER_USER
        defaultCPICorrespondentShouldBeFound("handlerUser.lessThan=" + UPDATED_HANDLER_USER);
    }


    @Test
    @Transactional
    public void getAllCPICorrespondentsByCorrespondentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentType correspondentType = CorrespondentTypeResourceIntTest.createEntity(em);
        em.persist(correspondentType);
        em.flush();
        cPICorrespondent.setCorrespondentType(correspondentType);
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);
        Long correspondentTypeId = correspondentType.getId();

        // Get all the cPICorrespondentList where correspondentType equals to correspondentTypeId
        defaultCPICorrespondentShouldBeFound("correspondentTypeId.equals=" + correspondentTypeId);

        // Get all the cPICorrespondentList where correspondentType equals to correspondentTypeId + 1
        defaultCPICorrespondentShouldNotBeFound("correspondentTypeId.equals=" + (correspondentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCPICorrespondentsByClubIsEqualToSomething() throws Exception {
        // Initialize the database
        Club club = ClubResourceIntTest.createEntity(em);
        em.persist(club);
        em.flush();
        cPICorrespondent.setClub(club);
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);
        Long clubId = club.getId();

        // Get all the cPICorrespondentList where club equals to clubId
        defaultCPICorrespondentShouldBeFound("clubId.equals=" + clubId);

        // Get all the cPICorrespondentList where club equals to clubId + 1
        defaultCPICorrespondentShouldNotBeFound("clubId.equals=" + (clubId + 1));
    }


    @Test
    @Transactional
    public void getAllCPICorrespondentsByClubPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        ClubPerson clubPerson = ClubPersonResourceIntTest.createEntity(em);
        em.persist(clubPerson);
        em.flush();
        cPICorrespondent.setClubPerson(clubPerson);
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);
        Long clubPersonId = clubPerson.getId();

        // Get all the cPICorrespondentList where clubPerson equals to clubPersonId
        defaultCPICorrespondentShouldBeFound("clubPersonId.equals=" + clubPersonId);

        // Get all the cPICorrespondentList where clubPerson equals to clubPersonId + 1
        defaultCPICorrespondentShouldNotBeFound("clubPersonId.equals=" + (clubPersonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCPICorrespondentShouldBeFound(String filter) throws Exception {
        restCPICorrespondentMockMvc.perform(get("/api/cpi-correspondents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cPICorrespondent.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentCode").value(hasItem(DEFAULT_CORRESPONDENT_CODE.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].vesselName").value(hasItem(DEFAULT_VESSEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientRef").value(hasItem(DEFAULT_CLIENT_REF.toString())))
            .andExpect(jsonPath("$.[*].keyWord").value(hasItem(DEFAULT_KEY_WORD.toString())))
            .andExpect(jsonPath("$.[*].registerDate").value(hasItem(DEFAULT_REGISTER_DATE.toString())))
            .andExpect(jsonPath("$.[*].caseDate").value(hasItem(DEFAULT_CASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].handlerUser").value(hasItem(DEFAULT_HANDLER_USER.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCPICorrespondentShouldNotBeFound(String filter) throws Exception {
        restCPICorrespondentMockMvc.perform(get("/api/cpi-correspondents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCPICorrespondent() throws Exception {
        // Get the cPICorrespondent
        restCPICorrespondentMockMvc.perform(get("/api/cpi-correspondents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCPICorrespondent() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);
        int databaseSizeBeforeUpdate = cPICorrespondentRepository.findAll().size();

        // Update the cPICorrespondent
        CPICorrespondent updatedCPICorrespondent = cPICorrespondentRepository.findOne(cPICorrespondent.getId());
        // Disconnect from session so that the updates on updatedCPICorrespondent are not directly saved in db
        em.detach(updatedCPICorrespondent);
        updatedCPICorrespondent
            .correspondentCode(UPDATED_CORRESPONDENT_CODE)
            .year(UPDATED_YEAR)
            .vesselName(UPDATED_VESSEL_NAME)
            .clientRef(UPDATED_CLIENT_REF)
            .keyWord(UPDATED_KEY_WORD)
            .registerDate(UPDATED_REGISTER_DATE)
            .caseDate(UPDATED_CASE_DATE)
            .handlerUser(UPDATED_HANDLER_USER)
            .remark(UPDATED_REMARK);
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(updatedCPICorrespondent);

        restCPICorrespondentMockMvc.perform(put("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isOk());

        // Validate the CPICorrespondent in the database
        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeUpdate);
        CPICorrespondent testCPICorrespondent = cPICorrespondentList.get(cPICorrespondentList.size() - 1);
        assertThat(testCPICorrespondent.getCorrespondentCode()).isEqualTo(UPDATED_CORRESPONDENT_CODE);
        assertThat(testCPICorrespondent.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCPICorrespondent.getVesselName()).isEqualTo(UPDATED_VESSEL_NAME);
        assertThat(testCPICorrespondent.getClientRef()).isEqualTo(UPDATED_CLIENT_REF);
        assertThat(testCPICorrespondent.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testCPICorrespondent.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testCPICorrespondent.getCaseDate()).isEqualTo(UPDATED_CASE_DATE);
        assertThat(testCPICorrespondent.getHandlerUser()).isEqualTo(UPDATED_HANDLER_USER);
        assertThat(testCPICorrespondent.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingCPICorrespondent() throws Exception {
        int databaseSizeBeforeUpdate = cPICorrespondentRepository.findAll().size();

        // Create the CPICorrespondent
        CPICorrespondentDTO cPICorrespondentDTO = cPICorrespondentMapper.toDto(cPICorrespondent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCPICorrespondentMockMvc.perform(put("/api/cpi-correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cPICorrespondentDTO)))
            .andExpect(status().isCreated());

        // Validate the CPICorrespondent in the database
        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCPICorrespondent() throws Exception {
        // Initialize the database
        cPICorrespondentRepository.saveAndFlush(cPICorrespondent);
        int databaseSizeBeforeDelete = cPICorrespondentRepository.findAll().size();

        // Get the cPICorrespondent
        restCPICorrespondentMockMvc.perform(delete("/api/cpi-correspondents/{id}", cPICorrespondent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CPICorrespondent> cPICorrespondentList = cPICorrespondentRepository.findAll();
        assertThat(cPICorrespondentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPICorrespondent.class);
        CPICorrespondent cPICorrespondent1 = new CPICorrespondent();
        cPICorrespondent1.setId(1L);
        CPICorrespondent cPICorrespondent2 = new CPICorrespondent();
        cPICorrespondent2.setId(cPICorrespondent1.getId());
        assertThat(cPICorrespondent1).isEqualTo(cPICorrespondent2);
        cPICorrespondent2.setId(2L);
        assertThat(cPICorrespondent1).isNotEqualTo(cPICorrespondent2);
        cPICorrespondent1.setId(null);
        assertThat(cPICorrespondent1).isNotEqualTo(cPICorrespondent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CPICorrespondentDTO.class);
        CPICorrespondentDTO cPICorrespondentDTO1 = new CPICorrespondentDTO();
        cPICorrespondentDTO1.setId(1L);
        CPICorrespondentDTO cPICorrespondentDTO2 = new CPICorrespondentDTO();
        assertThat(cPICorrespondentDTO1).isNotEqualTo(cPICorrespondentDTO2);
        cPICorrespondentDTO2.setId(cPICorrespondentDTO1.getId());
        assertThat(cPICorrespondentDTO1).isEqualTo(cPICorrespondentDTO2);
        cPICorrespondentDTO2.setId(2L);
        assertThat(cPICorrespondentDTO1).isNotEqualTo(cPICorrespondentDTO2);
        cPICorrespondentDTO1.setId(null);
        assertThat(cPICorrespondentDTO1).isNotEqualTo(cPICorrespondentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(cPICorrespondentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(cPICorrespondentMapper.fromId(null)).isNull();
    }
}
