package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.domain.CorrespondentFeeType;
import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.repository.CorrespondentFeeRepository;
import com.cpi.correspondent.service.CorrespondentFeeService;
import com.cpi.correspondent.service.dto.CorrespondentFeeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentFeeCriteria;
import com.cpi.correspondent.service.CorrespondentFeeQueryService;

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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.cpi.correspondent.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CorrespondentFeeResource REST controller.
 *
 * @see CorrespondentFeeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentFeeResourceIntTest {

    private static final String DEFAULT_CLIENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_ID = 1;
    private static final Integer UPDATED_NUMBER_ID = 2;

    private static final Long DEFAULT_CURRENCY = 1L;
    private static final Long UPDATED_CURRENCY = 2L;

    private static final Float DEFAULT_CURRENCY_RATE = 1F;
    private static final Float UPDATED_CURRENCY_RATE = 2F;

    private static final BigDecimal DEFAULT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST = new BigDecimal(2);

    private static final Instant DEFAULT_COST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_COST_DOLLAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_COST_DOLLAR = new BigDecimal(2);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private CorrespondentFeeRepository correspondentFeeRepository;

    @Autowired
    private CorrespondentFeeMapper correspondentFeeMapper;

    @Autowired
    private CorrespondentFeeService correspondentFeeService;

    @Autowired
    private CorrespondentFeeQueryService correspondentFeeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentFeeMockMvc;

    private CorrespondentFee correspondentFee;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentFeeResource correspondentFeeResource = new CorrespondentFeeResource(correspondentFeeService, correspondentFeeQueryService);
        this.restCorrespondentFeeMockMvc = MockMvcBuilders.standaloneSetup(correspondentFeeResource)
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
    public static CorrespondentFee createEntity(EntityManager em) {
        CorrespondentFee correspondentFee = new CorrespondentFee()
            .clientNo(DEFAULT_CLIENT_NO)
            .numberId(DEFAULT_NUMBER_ID)
            .currency(DEFAULT_CURRENCY)
            .currencyRate(DEFAULT_CURRENCY_RATE)
            .cost(DEFAULT_COST)
            .costDate(DEFAULT_COST_DATE)
            .costDollar(DEFAULT_COST_DOLLAR)
            .remark(DEFAULT_REMARK);
        return correspondentFee;
    }

    @Before
    public void initTest() {
        correspondentFee = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentFee() throws Exception {
        int databaseSizeBeforeCreate = correspondentFeeRepository.findAll().size();

        // Create the CorrespondentFee
        CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeMapper.toDto(correspondentFee);
        restCorrespondentFeeMockMvc.perform(post("/api/correspondent-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentFee in the database
        List<CorrespondentFee> correspondentFeeList = correspondentFeeRepository.findAll();
        assertThat(correspondentFeeList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentFee testCorrespondentFee = correspondentFeeList.get(correspondentFeeList.size() - 1);
        assertThat(testCorrespondentFee.getClientNo()).isEqualTo(DEFAULT_CLIENT_NO);
        assertThat(testCorrespondentFee.getNumberId()).isEqualTo(DEFAULT_NUMBER_ID);
        assertThat(testCorrespondentFee.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testCorrespondentFee.getCurrencyRate()).isEqualTo(DEFAULT_CURRENCY_RATE);
        assertThat(testCorrespondentFee.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testCorrespondentFee.getCostDate()).isEqualTo(DEFAULT_COST_DATE);
        assertThat(testCorrespondentFee.getCostDollar()).isEqualTo(DEFAULT_COST_DOLLAR);
        assertThat(testCorrespondentFee.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createCorrespondentFeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentFeeRepository.findAll().size();

        // Create the CorrespondentFee with an existing ID
        correspondentFee.setId(1L);
        CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeMapper.toDto(correspondentFee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentFeeMockMvc.perform(post("/api/correspondent-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentFee in the database
        List<CorrespondentFee> correspondentFeeList = correspondentFeeRepository.findAll();
        assertThat(correspondentFeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFees() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList
        restCorrespondentFeeMockMvc.perform(get("/api/correspondent-fees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentFee.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientNo").value(hasItem(DEFAULT_CLIENT_NO.toString())))
            .andExpect(jsonPath("$.[*].numberId").value(hasItem(DEFAULT_NUMBER_ID)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].currencyRate").value(hasItem(DEFAULT_CURRENCY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
            .andExpect(jsonPath("$.[*].costDate").value(hasItem(DEFAULT_COST_DATE.toString())))
            .andExpect(jsonPath("$.[*].costDollar").value(hasItem(DEFAULT_COST_DOLLAR.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getCorrespondentFee() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get the correspondentFee
        restCorrespondentFeeMockMvc.perform(get("/api/correspondent-fees/{id}", correspondentFee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentFee.getId().intValue()))
            .andExpect(jsonPath("$.clientNo").value(DEFAULT_CLIENT_NO.toString()))
            .andExpect(jsonPath("$.numberId").value(DEFAULT_NUMBER_ID))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.intValue()))
            .andExpect(jsonPath("$.currencyRate").value(DEFAULT_CURRENCY_RATE.doubleValue()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()))
            .andExpect(jsonPath("$.costDate").value(DEFAULT_COST_DATE.toString()))
            .andExpect(jsonPath("$.costDollar").value(DEFAULT_COST_DOLLAR.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByClientNoIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where clientNo equals to DEFAULT_CLIENT_NO
        defaultCorrespondentFeeShouldBeFound("clientNo.equals=" + DEFAULT_CLIENT_NO);

        // Get all the correspondentFeeList where clientNo equals to UPDATED_CLIENT_NO
        defaultCorrespondentFeeShouldNotBeFound("clientNo.equals=" + UPDATED_CLIENT_NO);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByClientNoIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where clientNo in DEFAULT_CLIENT_NO or UPDATED_CLIENT_NO
        defaultCorrespondentFeeShouldBeFound("clientNo.in=" + DEFAULT_CLIENT_NO + "," + UPDATED_CLIENT_NO);

        // Get all the correspondentFeeList where clientNo equals to UPDATED_CLIENT_NO
        defaultCorrespondentFeeShouldNotBeFound("clientNo.in=" + UPDATED_CLIENT_NO);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByClientNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where clientNo is not null
        defaultCorrespondentFeeShouldBeFound("clientNo.specified=true");

        // Get all the correspondentFeeList where clientNo is null
        defaultCorrespondentFeeShouldNotBeFound("clientNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByNumberIdIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where numberId equals to DEFAULT_NUMBER_ID
        defaultCorrespondentFeeShouldBeFound("numberId.equals=" + DEFAULT_NUMBER_ID);

        // Get all the correspondentFeeList where numberId equals to UPDATED_NUMBER_ID
        defaultCorrespondentFeeShouldNotBeFound("numberId.equals=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByNumberIdIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where numberId in DEFAULT_NUMBER_ID or UPDATED_NUMBER_ID
        defaultCorrespondentFeeShouldBeFound("numberId.in=" + DEFAULT_NUMBER_ID + "," + UPDATED_NUMBER_ID);

        // Get all the correspondentFeeList where numberId equals to UPDATED_NUMBER_ID
        defaultCorrespondentFeeShouldNotBeFound("numberId.in=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByNumberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where numberId is not null
        defaultCorrespondentFeeShouldBeFound("numberId.specified=true");

        // Get all the correspondentFeeList where numberId is null
        defaultCorrespondentFeeShouldNotBeFound("numberId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByNumberIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where numberId greater than or equals to DEFAULT_NUMBER_ID
        defaultCorrespondentFeeShouldBeFound("numberId.greaterOrEqualThan=" + DEFAULT_NUMBER_ID);

        // Get all the correspondentFeeList where numberId greater than or equals to UPDATED_NUMBER_ID
        defaultCorrespondentFeeShouldNotBeFound("numberId.greaterOrEqualThan=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByNumberIdIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where numberId less than or equals to DEFAULT_NUMBER_ID
        defaultCorrespondentFeeShouldNotBeFound("numberId.lessThan=" + DEFAULT_NUMBER_ID);

        // Get all the correspondentFeeList where numberId less than or equals to UPDATED_NUMBER_ID
        defaultCorrespondentFeeShouldBeFound("numberId.lessThan=" + UPDATED_NUMBER_ID);
    }


    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currency equals to DEFAULT_CURRENCY
        defaultCorrespondentFeeShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the correspondentFeeList where currency equals to UPDATED_CURRENCY
        defaultCorrespondentFeeShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultCorrespondentFeeShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the correspondentFeeList where currency equals to UPDATED_CURRENCY
        defaultCorrespondentFeeShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currency is not null
        defaultCorrespondentFeeShouldBeFound("currency.specified=true");

        // Get all the correspondentFeeList where currency is null
        defaultCorrespondentFeeShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currency greater than or equals to DEFAULT_CURRENCY
        defaultCorrespondentFeeShouldBeFound("currency.greaterOrEqualThan=" + DEFAULT_CURRENCY);

        // Get all the correspondentFeeList where currency greater than or equals to UPDATED_CURRENCY
        defaultCorrespondentFeeShouldNotBeFound("currency.greaterOrEqualThan=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currency less than or equals to DEFAULT_CURRENCY
        defaultCorrespondentFeeShouldNotBeFound("currency.lessThan=" + DEFAULT_CURRENCY);

        // Get all the correspondentFeeList where currency less than or equals to UPDATED_CURRENCY
        defaultCorrespondentFeeShouldBeFound("currency.lessThan=" + UPDATED_CURRENCY);
    }


    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currencyRate equals to DEFAULT_CURRENCY_RATE
        defaultCorrespondentFeeShouldBeFound("currencyRate.equals=" + DEFAULT_CURRENCY_RATE);

        // Get all the correspondentFeeList where currencyRate equals to UPDATED_CURRENCY_RATE
        defaultCorrespondentFeeShouldNotBeFound("currencyRate.equals=" + UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyRateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currencyRate in DEFAULT_CURRENCY_RATE or UPDATED_CURRENCY_RATE
        defaultCorrespondentFeeShouldBeFound("currencyRate.in=" + DEFAULT_CURRENCY_RATE + "," + UPDATED_CURRENCY_RATE);

        // Get all the correspondentFeeList where currencyRate equals to UPDATED_CURRENCY_RATE
        defaultCorrespondentFeeShouldNotBeFound("currencyRate.in=" + UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCurrencyRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where currencyRate is not null
        defaultCorrespondentFeeShouldBeFound("currencyRate.specified=true");

        // Get all the correspondentFeeList where currencyRate is null
        defaultCorrespondentFeeShouldNotBeFound("currencyRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where cost equals to DEFAULT_COST
        defaultCorrespondentFeeShouldBeFound("cost.equals=" + DEFAULT_COST);

        // Get all the correspondentFeeList where cost equals to UPDATED_COST
        defaultCorrespondentFeeShouldNotBeFound("cost.equals=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where cost in DEFAULT_COST or UPDATED_COST
        defaultCorrespondentFeeShouldBeFound("cost.in=" + DEFAULT_COST + "," + UPDATED_COST);

        // Get all the correspondentFeeList where cost equals to UPDATED_COST
        defaultCorrespondentFeeShouldNotBeFound("cost.in=" + UPDATED_COST);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where cost is not null
        defaultCorrespondentFeeShouldBeFound("cost.specified=true");

        // Get all the correspondentFeeList where cost is null
        defaultCorrespondentFeeShouldNotBeFound("cost.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where costDate equals to DEFAULT_COST_DATE
        defaultCorrespondentFeeShouldBeFound("costDate.equals=" + DEFAULT_COST_DATE);

        // Get all the correspondentFeeList where costDate equals to UPDATED_COST_DATE
        defaultCorrespondentFeeShouldNotBeFound("costDate.equals=" + UPDATED_COST_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where costDate in DEFAULT_COST_DATE or UPDATED_COST_DATE
        defaultCorrespondentFeeShouldBeFound("costDate.in=" + DEFAULT_COST_DATE + "," + UPDATED_COST_DATE);

        // Get all the correspondentFeeList where costDate equals to UPDATED_COST_DATE
        defaultCorrespondentFeeShouldNotBeFound("costDate.in=" + UPDATED_COST_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where costDate is not null
        defaultCorrespondentFeeShouldBeFound("costDate.specified=true");

        // Get all the correspondentFeeList where costDate is null
        defaultCorrespondentFeeShouldNotBeFound("costDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostDollarIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where costDollar equals to DEFAULT_COST_DOLLAR
        defaultCorrespondentFeeShouldBeFound("costDollar.equals=" + DEFAULT_COST_DOLLAR);

        // Get all the correspondentFeeList where costDollar equals to UPDATED_COST_DOLLAR
        defaultCorrespondentFeeShouldNotBeFound("costDollar.equals=" + UPDATED_COST_DOLLAR);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostDollarIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where costDollar in DEFAULT_COST_DOLLAR or UPDATED_COST_DOLLAR
        defaultCorrespondentFeeShouldBeFound("costDollar.in=" + DEFAULT_COST_DOLLAR + "," + UPDATED_COST_DOLLAR);

        // Get all the correspondentFeeList where costDollar equals to UPDATED_COST_DOLLAR
        defaultCorrespondentFeeShouldNotBeFound("costDollar.in=" + UPDATED_COST_DOLLAR);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCostDollarIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);

        // Get all the correspondentFeeList where costDollar is not null
        defaultCorrespondentFeeShouldBeFound("costDollar.specified=true");

        // Get all the correspondentFeeList where costDollar is null
        defaultCorrespondentFeeShouldNotBeFound("costDollar.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeesByCorrespondentFeeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentFeeType correspondentFeeType = CorrespondentFeeTypeResourceIntTest.createEntity(em);
        em.persist(correspondentFeeType);
        em.flush();
        correspondentFee.setCorrespondentFeeType(correspondentFeeType);
        correspondentFeeRepository.saveAndFlush(correspondentFee);
        Long correspondentFeeTypeId = correspondentFeeType.getId();

        // Get all the correspondentFeeList where correspondentFeeType equals to correspondentFeeTypeId
        defaultCorrespondentFeeShouldBeFound("correspondentFeeTypeId.equals=" + correspondentFeeTypeId);

        // Get all the correspondentFeeList where correspondentFeeType equals to correspondentFeeTypeId + 1
        defaultCorrespondentFeeShouldNotBeFound("correspondentFeeTypeId.equals=" + (correspondentFeeTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentFeesByCpiCorrespondentIsEqualToSomething() throws Exception {
        // Initialize the database
        CPICorrespondent cpiCorrespondent = CPICorrespondentResourceIntTest.createEntity(em);
        em.persist(cpiCorrespondent);
        em.flush();
        correspondentFee.setCpiCorrespondent(cpiCorrespondent);
        correspondentFeeRepository.saveAndFlush(correspondentFee);
        Long cpiCorrespondentId = cpiCorrespondent.getId();

        // Get all the correspondentFeeList where cpiCorrespondent equals to cpiCorrespondentId
        defaultCorrespondentFeeShouldBeFound("cpiCorrespondentId.equals=" + cpiCorrespondentId);

        // Get all the correspondentFeeList where cpiCorrespondent equals to cpiCorrespondentId + 1
        defaultCorrespondentFeeShouldNotBeFound("cpiCorrespondentId.equals=" + (cpiCorrespondentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentFeeShouldBeFound(String filter) throws Exception {
        restCorrespondentFeeMockMvc.perform(get("/api/correspondent-fees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentFee.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientNo").value(hasItem(DEFAULT_CLIENT_NO.toString())))
            .andExpect(jsonPath("$.[*].numberId").value(hasItem(DEFAULT_NUMBER_ID)))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].currencyRate").value(hasItem(DEFAULT_CURRENCY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())))
            .andExpect(jsonPath("$.[*].costDate").value(hasItem(DEFAULT_COST_DATE.toString())))
            .andExpect(jsonPath("$.[*].costDollar").value(hasItem(DEFAULT_COST_DOLLAR.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentFeeShouldNotBeFound(String filter) throws Exception {
        restCorrespondentFeeMockMvc.perform(get("/api/correspondent-fees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentFee() throws Exception {
        // Get the correspondentFee
        restCorrespondentFeeMockMvc.perform(get("/api/correspondent-fees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentFee() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);
        int databaseSizeBeforeUpdate = correspondentFeeRepository.findAll().size();

        // Update the correspondentFee
        CorrespondentFee updatedCorrespondentFee = correspondentFeeRepository.findOne(correspondentFee.getId());
        // Disconnect from session so that the updates on updatedCorrespondentFee are not directly saved in db
        em.detach(updatedCorrespondentFee);
        updatedCorrespondentFee
            .clientNo(UPDATED_CLIENT_NO)
            .numberId(UPDATED_NUMBER_ID)
            .currency(UPDATED_CURRENCY)
            .currencyRate(UPDATED_CURRENCY_RATE)
            .cost(UPDATED_COST)
            .costDate(UPDATED_COST_DATE)
            .costDollar(UPDATED_COST_DOLLAR)
            .remark(UPDATED_REMARK);
        CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeMapper.toDto(updatedCorrespondentFee);

        restCorrespondentFeeMockMvc.perform(put("/api/correspondent-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentFee in the database
        List<CorrespondentFee> correspondentFeeList = correspondentFeeRepository.findAll();
        assertThat(correspondentFeeList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentFee testCorrespondentFee = correspondentFeeList.get(correspondentFeeList.size() - 1);
        assertThat(testCorrespondentFee.getClientNo()).isEqualTo(UPDATED_CLIENT_NO);
        assertThat(testCorrespondentFee.getNumberId()).isEqualTo(UPDATED_NUMBER_ID);
        assertThat(testCorrespondentFee.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testCorrespondentFee.getCurrencyRate()).isEqualTo(UPDATED_CURRENCY_RATE);
        assertThat(testCorrespondentFee.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testCorrespondentFee.getCostDate()).isEqualTo(UPDATED_COST_DATE);
        assertThat(testCorrespondentFee.getCostDollar()).isEqualTo(UPDATED_COST_DOLLAR);
        assertThat(testCorrespondentFee.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentFee() throws Exception {
        int databaseSizeBeforeUpdate = correspondentFeeRepository.findAll().size();

        // Create the CorrespondentFee
        CorrespondentFeeDTO correspondentFeeDTO = correspondentFeeMapper.toDto(correspondentFee);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentFeeMockMvc.perform(put("/api/correspondent-fees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentFee in the database
        List<CorrespondentFee> correspondentFeeList = correspondentFeeRepository.findAll();
        assertThat(correspondentFeeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondentFee() throws Exception {
        // Initialize the database
        correspondentFeeRepository.saveAndFlush(correspondentFee);
        int databaseSizeBeforeDelete = correspondentFeeRepository.findAll().size();

        // Get the correspondentFee
        restCorrespondentFeeMockMvc.perform(delete("/api/correspondent-fees/{id}", correspondentFee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentFee> correspondentFeeList = correspondentFeeRepository.findAll();
        assertThat(correspondentFeeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentFee.class);
        CorrespondentFee correspondentFee1 = new CorrespondentFee();
        correspondentFee1.setId(1L);
        CorrespondentFee correspondentFee2 = new CorrespondentFee();
        correspondentFee2.setId(correspondentFee1.getId());
        assertThat(correspondentFee1).isEqualTo(correspondentFee2);
        correspondentFee2.setId(2L);
        assertThat(correspondentFee1).isNotEqualTo(correspondentFee2);
        correspondentFee1.setId(null);
        assertThat(correspondentFee1).isNotEqualTo(correspondentFee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentFeeDTO.class);
        CorrespondentFeeDTO correspondentFeeDTO1 = new CorrespondentFeeDTO();
        correspondentFeeDTO1.setId(1L);
        CorrespondentFeeDTO correspondentFeeDTO2 = new CorrespondentFeeDTO();
        assertThat(correspondentFeeDTO1).isNotEqualTo(correspondentFeeDTO2);
        correspondentFeeDTO2.setId(correspondentFeeDTO1.getId());
        assertThat(correspondentFeeDTO1).isEqualTo(correspondentFeeDTO2);
        correspondentFeeDTO2.setId(2L);
        assertThat(correspondentFeeDTO1).isNotEqualTo(correspondentFeeDTO2);
        correspondentFeeDTO1.setId(null);
        assertThat(correspondentFeeDTO1).isNotEqualTo(correspondentFeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentFeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentFeeMapper.fromId(null)).isNull();
    }
}
