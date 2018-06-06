package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.Correspondent;
import com.cpi.correspondent.domain.CorrespondentType;
import com.cpi.correspondent.domain.Club;
import com.cpi.correspondent.domain.ClubPerson;
import com.cpi.correspondent.repository.CorrespondentRepository;
import com.cpi.correspondent.service.CorrespondentService;
import com.cpi.correspondent.service.dto.CorrespondentDTO;
import com.cpi.correspondent.service.mapper.CorrespondentMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentCriteria;
import com.cpi.correspondent.service.CorrespondentQueryService;

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
 * Test class for the CorrespondentResource REST controller.
 *
 * @see CorrespondentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentResourceIntTest {

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
    private CorrespondentRepository correspondentRepository;

    @Autowired
    private CorrespondentMapper correspondentMapper;

    @Autowired
    private CorrespondentService correspondentService;

    @Autowired
    private CorrespondentQueryService correspondentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentMockMvc;

    private Correspondent correspondent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentResource correspondentResource = new CorrespondentResource(correspondentService, correspondentQueryService);
        this.restCorrespondentMockMvc = MockMvcBuilders.standaloneSetup(correspondentResource)
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
    public static Correspondent createEntity(EntityManager em) {
        Correspondent correspondent = new Correspondent()
            .correspondentCode(DEFAULT_CORRESPONDENT_CODE)
            .year(DEFAULT_YEAR)
            .vesselName(DEFAULT_VESSEL_NAME)
            .clientRef(DEFAULT_CLIENT_REF)
            .keyWord(DEFAULT_KEY_WORD)
            .registerDate(DEFAULT_REGISTER_DATE)
            .caseDate(DEFAULT_CASE_DATE)
            .handlerUser(DEFAULT_HANDLER_USER)
            .remark(DEFAULT_REMARK);
        return correspondent;
    }

    @Before
    public void initTest() {
        correspondent = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondent() throws Exception {
        int databaseSizeBeforeCreate = correspondentRepository.findAll().size();

        // Create the Correspondent
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);
        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isCreated());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeCreate + 1);
        Correspondent testCorrespondent = correspondentList.get(correspondentList.size() - 1);
        assertThat(testCorrespondent.getCorrespondentCode()).isEqualTo(DEFAULT_CORRESPONDENT_CODE);
        assertThat(testCorrespondent.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCorrespondent.getVesselName()).isEqualTo(DEFAULT_VESSEL_NAME);
        assertThat(testCorrespondent.getClientRef()).isEqualTo(DEFAULT_CLIENT_REF);
        assertThat(testCorrespondent.getKeyWord()).isEqualTo(DEFAULT_KEY_WORD);
        assertThat(testCorrespondent.getRegisterDate()).isEqualTo(DEFAULT_REGISTER_DATE);
        assertThat(testCorrespondent.getCaseDate()).isEqualTo(DEFAULT_CASE_DATE);
        assertThat(testCorrespondent.getHandlerUser()).isEqualTo(DEFAULT_HANDLER_USER);
        assertThat(testCorrespondent.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createCorrespondentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentRepository.findAll().size();

        // Create the Correspondent with an existing ID
        correspondent.setId(1L);
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentRepository.findAll().size();
        // set the field null
        correspondent.setCorrespondentCode(null);

        // Create the Correspondent, which fails.
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentRepository.findAll().size();
        // set the field null
        correspondent.setYear(null);

        // Create the Correspondent, which fails.
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVesselNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentRepository.findAll().size();
        // set the field null
        correspondent.setVesselName(null);

        // Create the Correspondent, which fails.
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        restCorrespondentMockMvc.perform(post("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isBadRequest());

        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondents() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList
        restCorrespondentMockMvc.perform(get("/api/correspondents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondent.getId().intValue())))
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
    public void getCorrespondent() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get the correspondent
        restCorrespondentMockMvc.perform(get("/api/correspondents/{id}", correspondent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondent.getId().intValue()))
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
    public void getAllCorrespondentsByCorrespondentCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where correspondentCode equals to DEFAULT_CORRESPONDENT_CODE
        defaultCorrespondentShouldBeFound("correspondentCode.equals=" + DEFAULT_CORRESPONDENT_CODE);

        // Get all the correspondentList where correspondentCode equals to UPDATED_CORRESPONDENT_CODE
        defaultCorrespondentShouldNotBeFound("correspondentCode.equals=" + UPDATED_CORRESPONDENT_CODE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCorrespondentCodeIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where correspondentCode in DEFAULT_CORRESPONDENT_CODE or UPDATED_CORRESPONDENT_CODE
        defaultCorrespondentShouldBeFound("correspondentCode.in=" + DEFAULT_CORRESPONDENT_CODE + "," + UPDATED_CORRESPONDENT_CODE);

        // Get all the correspondentList where correspondentCode equals to UPDATED_CORRESPONDENT_CODE
        defaultCorrespondentShouldNotBeFound("correspondentCode.in=" + UPDATED_CORRESPONDENT_CODE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCorrespondentCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where correspondentCode is not null
        defaultCorrespondentShouldBeFound("correspondentCode.specified=true");

        // Get all the correspondentList where correspondentCode is null
        defaultCorrespondentShouldNotBeFound("correspondentCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where year equals to DEFAULT_YEAR
        defaultCorrespondentShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the correspondentList where year equals to UPDATED_YEAR
        defaultCorrespondentShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultCorrespondentShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the correspondentList where year equals to UPDATED_YEAR
        defaultCorrespondentShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where year is not null
        defaultCorrespondentShouldBeFound("year.specified=true");

        // Get all the correspondentList where year is null
        defaultCorrespondentShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByVesselNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where vesselName equals to DEFAULT_VESSEL_NAME
        defaultCorrespondentShouldBeFound("vesselName.equals=" + DEFAULT_VESSEL_NAME);

        // Get all the correspondentList where vesselName equals to UPDATED_VESSEL_NAME
        defaultCorrespondentShouldNotBeFound("vesselName.equals=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByVesselNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where vesselName in DEFAULT_VESSEL_NAME or UPDATED_VESSEL_NAME
        defaultCorrespondentShouldBeFound("vesselName.in=" + DEFAULT_VESSEL_NAME + "," + UPDATED_VESSEL_NAME);

        // Get all the correspondentList where vesselName equals to UPDATED_VESSEL_NAME
        defaultCorrespondentShouldNotBeFound("vesselName.in=" + UPDATED_VESSEL_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByVesselNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where vesselName is not null
        defaultCorrespondentShouldBeFound("vesselName.specified=true");

        // Get all the correspondentList where vesselName is null
        defaultCorrespondentShouldNotBeFound("vesselName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByClientRefIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where clientRef equals to DEFAULT_CLIENT_REF
        defaultCorrespondentShouldBeFound("clientRef.equals=" + DEFAULT_CLIENT_REF);

        // Get all the correspondentList where clientRef equals to UPDATED_CLIENT_REF
        defaultCorrespondentShouldNotBeFound("clientRef.equals=" + UPDATED_CLIENT_REF);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByClientRefIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where clientRef in DEFAULT_CLIENT_REF or UPDATED_CLIENT_REF
        defaultCorrespondentShouldBeFound("clientRef.in=" + DEFAULT_CLIENT_REF + "," + UPDATED_CLIENT_REF);

        // Get all the correspondentList where clientRef equals to UPDATED_CLIENT_REF
        defaultCorrespondentShouldNotBeFound("clientRef.in=" + UPDATED_CLIENT_REF);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByClientRefIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where clientRef is not null
        defaultCorrespondentShouldBeFound("clientRef.specified=true");

        // Get all the correspondentList where clientRef is null
        defaultCorrespondentShouldNotBeFound("clientRef.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByKeyWordIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where keyWord equals to DEFAULT_KEY_WORD
        defaultCorrespondentShouldBeFound("keyWord.equals=" + DEFAULT_KEY_WORD);

        // Get all the correspondentList where keyWord equals to UPDATED_KEY_WORD
        defaultCorrespondentShouldNotBeFound("keyWord.equals=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByKeyWordIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where keyWord in DEFAULT_KEY_WORD or UPDATED_KEY_WORD
        defaultCorrespondentShouldBeFound("keyWord.in=" + DEFAULT_KEY_WORD + "," + UPDATED_KEY_WORD);

        // Get all the correspondentList where keyWord equals to UPDATED_KEY_WORD
        defaultCorrespondentShouldNotBeFound("keyWord.in=" + UPDATED_KEY_WORD);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByKeyWordIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where keyWord is not null
        defaultCorrespondentShouldBeFound("keyWord.specified=true");

        // Get all the correspondentList where keyWord is null
        defaultCorrespondentShouldNotBeFound("keyWord.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByRegisterDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where registerDate equals to DEFAULT_REGISTER_DATE
        defaultCorrespondentShouldBeFound("registerDate.equals=" + DEFAULT_REGISTER_DATE);

        // Get all the correspondentList where registerDate equals to UPDATED_REGISTER_DATE
        defaultCorrespondentShouldNotBeFound("registerDate.equals=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByRegisterDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where registerDate in DEFAULT_REGISTER_DATE or UPDATED_REGISTER_DATE
        defaultCorrespondentShouldBeFound("registerDate.in=" + DEFAULT_REGISTER_DATE + "," + UPDATED_REGISTER_DATE);

        // Get all the correspondentList where registerDate equals to UPDATED_REGISTER_DATE
        defaultCorrespondentShouldNotBeFound("registerDate.in=" + UPDATED_REGISTER_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByRegisterDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where registerDate is not null
        defaultCorrespondentShouldBeFound("registerDate.specified=true");

        // Get all the correspondentList where registerDate is null
        defaultCorrespondentShouldNotBeFound("registerDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where caseDate equals to DEFAULT_CASE_DATE
        defaultCorrespondentShouldBeFound("caseDate.equals=" + DEFAULT_CASE_DATE);

        // Get all the correspondentList where caseDate equals to UPDATED_CASE_DATE
        defaultCorrespondentShouldNotBeFound("caseDate.equals=" + UPDATED_CASE_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where caseDate in DEFAULT_CASE_DATE or UPDATED_CASE_DATE
        defaultCorrespondentShouldBeFound("caseDate.in=" + DEFAULT_CASE_DATE + "," + UPDATED_CASE_DATE);

        // Get all the correspondentList where caseDate equals to UPDATED_CASE_DATE
        defaultCorrespondentShouldNotBeFound("caseDate.in=" + UPDATED_CASE_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByCaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where caseDate is not null
        defaultCorrespondentShouldBeFound("caseDate.specified=true");

        // Get all the correspondentList where caseDate is null
        defaultCorrespondentShouldNotBeFound("caseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByHandlerUserIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where handlerUser equals to DEFAULT_HANDLER_USER
        defaultCorrespondentShouldBeFound("handlerUser.equals=" + DEFAULT_HANDLER_USER);

        // Get all the correspondentList where handlerUser equals to UPDATED_HANDLER_USER
        defaultCorrespondentShouldNotBeFound("handlerUser.equals=" + UPDATED_HANDLER_USER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByHandlerUserIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where handlerUser in DEFAULT_HANDLER_USER or UPDATED_HANDLER_USER
        defaultCorrespondentShouldBeFound("handlerUser.in=" + DEFAULT_HANDLER_USER + "," + UPDATED_HANDLER_USER);

        // Get all the correspondentList where handlerUser equals to UPDATED_HANDLER_USER
        defaultCorrespondentShouldNotBeFound("handlerUser.in=" + UPDATED_HANDLER_USER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByHandlerUserIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where handlerUser is not null
        defaultCorrespondentShouldBeFound("handlerUser.specified=true");

        // Get all the correspondentList where handlerUser is null
        defaultCorrespondentShouldNotBeFound("handlerUser.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByHandlerUserIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where handlerUser greater than or equals to DEFAULT_HANDLER_USER
        defaultCorrespondentShouldBeFound("handlerUser.greaterOrEqualThan=" + DEFAULT_HANDLER_USER);

        // Get all the correspondentList where handlerUser greater than or equals to UPDATED_HANDLER_USER
        defaultCorrespondentShouldNotBeFound("handlerUser.greaterOrEqualThan=" + UPDATED_HANDLER_USER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentsByHandlerUserIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);

        // Get all the correspondentList where handlerUser less than or equals to DEFAULT_HANDLER_USER
        defaultCorrespondentShouldNotBeFound("handlerUser.lessThan=" + DEFAULT_HANDLER_USER);

        // Get all the correspondentList where handlerUser less than or equals to UPDATED_HANDLER_USER
        defaultCorrespondentShouldBeFound("handlerUser.lessThan=" + UPDATED_HANDLER_USER);
    }


    @Test
    @Transactional
    public void getAllCorrespondentsByCorrespondentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentType correspondentType = CorrespondentTypeResourceIntTest.createEntity(em);
        em.persist(correspondentType);
        em.flush();
        correspondent.setCorrespondentType(correspondentType);
        correspondentRepository.saveAndFlush(correspondent);
        Long correspondentTypeId = correspondentType.getId();

        // Get all the correspondentList where correspondentType equals to correspondentTypeId
        defaultCorrespondentShouldBeFound("correspondentTypeId.equals=" + correspondentTypeId);

        // Get all the correspondentList where correspondentType equals to correspondentTypeId + 1
        defaultCorrespondentShouldNotBeFound("correspondentTypeId.equals=" + (correspondentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentsByClubIsEqualToSomething() throws Exception {
        // Initialize the database
        Club club = ClubResourceIntTest.createEntity(em);
        em.persist(club);
        em.flush();
        correspondent.setClub(club);
        correspondentRepository.saveAndFlush(correspondent);
        Long clubId = club.getId();

        // Get all the correspondentList where club equals to clubId
        defaultCorrespondentShouldBeFound("clubId.equals=" + clubId);

        // Get all the correspondentList where club equals to clubId + 1
        defaultCorrespondentShouldNotBeFound("clubId.equals=" + (clubId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentsByClubPersonIsEqualToSomething() throws Exception {
        // Initialize the database
        ClubPerson clubPerson = ClubPersonResourceIntTest.createEntity(em);
        em.persist(clubPerson);
        em.flush();
        correspondent.setClubPerson(clubPerson);
        correspondentRepository.saveAndFlush(correspondent);
        Long clubPersonId = clubPerson.getId();

        // Get all the correspondentList where clubPerson equals to clubPersonId
        defaultCorrespondentShouldBeFound("clubPersonId.equals=" + clubPersonId);

        // Get all the correspondentList where clubPerson equals to clubPersonId + 1
        defaultCorrespondentShouldNotBeFound("clubPersonId.equals=" + (clubPersonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentShouldBeFound(String filter) throws Exception {
        restCorrespondentMockMvc.perform(get("/api/correspondents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondent.getId().intValue())))
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
    private void defaultCorrespondentShouldNotBeFound(String filter) throws Exception {
        restCorrespondentMockMvc.perform(get("/api/correspondents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondent() throws Exception {
        // Get the correspondent
        restCorrespondentMockMvc.perform(get("/api/correspondents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondent() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);
        int databaseSizeBeforeUpdate = correspondentRepository.findAll().size();

        // Update the correspondent
        Correspondent updatedCorrespondent = correspondentRepository.findOne(correspondent.getId());
        // Disconnect from session so that the updates on updatedCorrespondent are not directly saved in db
        em.detach(updatedCorrespondent);
        updatedCorrespondent
            .correspondentCode(UPDATED_CORRESPONDENT_CODE)
            .year(UPDATED_YEAR)
            .vesselName(UPDATED_VESSEL_NAME)
            .clientRef(UPDATED_CLIENT_REF)
            .keyWord(UPDATED_KEY_WORD)
            .registerDate(UPDATED_REGISTER_DATE)
            .caseDate(UPDATED_CASE_DATE)
            .handlerUser(UPDATED_HANDLER_USER)
            .remark(UPDATED_REMARK);
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(updatedCorrespondent);

        restCorrespondentMockMvc.perform(put("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isOk());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeUpdate);
        Correspondent testCorrespondent = correspondentList.get(correspondentList.size() - 1);
        assertThat(testCorrespondent.getCorrespondentCode()).isEqualTo(UPDATED_CORRESPONDENT_CODE);
        assertThat(testCorrespondent.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCorrespondent.getVesselName()).isEqualTo(UPDATED_VESSEL_NAME);
        assertThat(testCorrespondent.getClientRef()).isEqualTo(UPDATED_CLIENT_REF);
        assertThat(testCorrespondent.getKeyWord()).isEqualTo(UPDATED_KEY_WORD);
        assertThat(testCorrespondent.getRegisterDate()).isEqualTo(UPDATED_REGISTER_DATE);
        assertThat(testCorrespondent.getCaseDate()).isEqualTo(UPDATED_CASE_DATE);
        assertThat(testCorrespondent.getHandlerUser()).isEqualTo(UPDATED_HANDLER_USER);
        assertThat(testCorrespondent.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondent() throws Exception {
        int databaseSizeBeforeUpdate = correspondentRepository.findAll().size();

        // Create the Correspondent
        CorrespondentDTO correspondentDTO = correspondentMapper.toDto(correspondent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentMockMvc.perform(put("/api/correspondents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentDTO)))
            .andExpect(status().isCreated());

        // Validate the Correspondent in the database
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondent() throws Exception {
        // Initialize the database
        correspondentRepository.saveAndFlush(correspondent);
        int databaseSizeBeforeDelete = correspondentRepository.findAll().size();

        // Get the correspondent
        restCorrespondentMockMvc.perform(delete("/api/correspondents/{id}", correspondent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Correspondent> correspondentList = correspondentRepository.findAll();
        assertThat(correspondentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Correspondent.class);
        Correspondent correspondent1 = new Correspondent();
        correspondent1.setId(1L);
        Correspondent correspondent2 = new Correspondent();
        correspondent2.setId(correspondent1.getId());
        assertThat(correspondent1).isEqualTo(correspondent2);
        correspondent2.setId(2L);
        assertThat(correspondent1).isNotEqualTo(correspondent2);
        correspondent1.setId(null);
        assertThat(correspondent1).isNotEqualTo(correspondent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentDTO.class);
        CorrespondentDTO correspondentDTO1 = new CorrespondentDTO();
        correspondentDTO1.setId(1L);
        CorrespondentDTO correspondentDTO2 = new CorrespondentDTO();
        assertThat(correspondentDTO1).isNotEqualTo(correspondentDTO2);
        correspondentDTO2.setId(correspondentDTO1.getId());
        assertThat(correspondentDTO1).isEqualTo(correspondentDTO2);
        correspondentDTO2.setId(2L);
        assertThat(correspondentDTO1).isNotEqualTo(correspondentDTO2);
        correspondentDTO1.setId(null);
        assertThat(correspondentDTO1).isNotEqualTo(correspondentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentMapper.fromId(null)).isNull();
    }
}
