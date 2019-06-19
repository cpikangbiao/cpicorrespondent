package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;
import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.CorrespondentBillStatus;
import com.cpi.correspondent.domain.Credit;
import com.cpi.correspondent.domain.CPICorrespondent;
import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.repository.CorrespondentBillRepository;
import com.cpi.correspondent.service.CorrespondentBillService;
import com.cpi.correspondent.service.dto.CorrespondentBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentBillMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentBillCriteria;
import com.cpi.correspondent.service.CorrespondentBillQueryService;

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
 * Integration tests for the {@Link CorrespondentBillResource} REST controller.
 */
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, CpicorrespondentApp.class})
public class CorrespondentBillResourceIT {

    private static final Integer DEFAULT_NUMBER_ID = 1;
    private static final Integer UPDATED_NUMBER_ID = 2;

    private static final String DEFAULT_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_YEAR = "BBBBBBBBBB";

    private static final String DEFAULT_CORRESPONDENT_BILL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_BILL_CODE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CORRESPONDENT_BILL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CORRESPONDENT_BILL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);

    private static final Long DEFAULT_CURRENCY = 1L;
    private static final Long UPDATED_CURRENCY = 2L;

    private static final Float DEFAULT_CURRENCY_RATE = 1F;
    private static final Float UPDATED_CURRENCY_RATE = 2F;

    private static final Instant DEFAULT_EXCHANGE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXCHANGE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_EXCHANGE_CURRENCY = 1L;
    private static final Long UPDATED_EXCHANGE_CURRENCY = 2L;

    private static final Float DEFAULT_EXCHANGE_RATE = 1F;
    private static final Float UPDATED_EXCHANGE_RATE = 2F;

    private static final BigDecimal DEFAULT_EXCHANGE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_EXCHANGE_AMOUNT = new BigDecimal(2);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private CorrespondentBillRepository correspondentBillRepository;

    @Autowired
    private CorrespondentBillMapper correspondentBillMapper;

    @Autowired
    private CorrespondentBillService correspondentBillService;

    @Autowired
    private CorrespondentBillQueryService correspondentBillQueryService;

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

    private MockMvc restCorrespondentBillMockMvc;

    private CorrespondentBill correspondentBill;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentBillResource correspondentBillResource = new CorrespondentBillResource(correspondentBillService, correspondentBillQueryService);
        this.restCorrespondentBillMockMvc = MockMvcBuilders.standaloneSetup(correspondentBillResource)
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
    public static CorrespondentBill createEntity(EntityManager em) {
        CorrespondentBill correspondentBill = new CorrespondentBill()
            .numberId(DEFAULT_NUMBER_ID)
            .year(DEFAULT_YEAR)
            .correspondentBillCode(DEFAULT_CORRESPONDENT_BILL_CODE)
            .correspondentBillDate(DEFAULT_CORRESPONDENT_BILL_DATE)
            .receiver(DEFAULT_RECEIVER)
            .mainContent(DEFAULT_MAIN_CONTENT)
            .dueDate(DEFAULT_DUE_DATE)
            .amount(DEFAULT_AMOUNT)
            .currency(DEFAULT_CURRENCY)
            .currencyRate(DEFAULT_CURRENCY_RATE)
            .exchangeDate(DEFAULT_EXCHANGE_DATE)
            .exchangeCurrency(DEFAULT_EXCHANGE_CURRENCY)
            .exchangeRate(DEFAULT_EXCHANGE_RATE)
            .exchangeAmount(DEFAULT_EXCHANGE_AMOUNT)
            .remark(DEFAULT_REMARK);
        return correspondentBill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CorrespondentBill createUpdatedEntity(EntityManager em) {
        CorrespondentBill correspondentBill = new CorrespondentBill()
            .numberId(UPDATED_NUMBER_ID)
            .year(UPDATED_YEAR)
            .correspondentBillCode(UPDATED_CORRESPONDENT_BILL_CODE)
            .correspondentBillDate(UPDATED_CORRESPONDENT_BILL_DATE)
            .receiver(UPDATED_RECEIVER)
            .dueDate(UPDATED_DUE_DATE)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .currencyRate(UPDATED_CURRENCY_RATE)
            .exchangeDate(UPDATED_EXCHANGE_DATE)
            .exchangeCurrency(UPDATED_EXCHANGE_CURRENCY)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .exchangeAmount(UPDATED_EXCHANGE_AMOUNT)
            .remark(UPDATED_REMARK);
        return correspondentBill;
    }

    @BeforeEach
    public void initTest() {
        correspondentBill = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentBill() throws Exception {
        int databaseSizeBeforeCreate = correspondentBillRepository.findAll().size();

        // Create the CorrespondentBill
        CorrespondentBillDTO correspondentBillDTO = correspondentBillMapper.toDto(correspondentBill);
        restCorrespondentBillMockMvc.perform(post("/api/correspondent-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentBill in the database
        List<CorrespondentBill> correspondentBillList = correspondentBillRepository.findAll();
        assertThat(correspondentBillList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentBill testCorrespondentBill = correspondentBillList.get(correspondentBillList.size() - 1);
        assertThat(testCorrespondentBill.getNumberId()).isEqualTo(DEFAULT_NUMBER_ID);
        assertThat(testCorrespondentBill.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testCorrespondentBill.getCorrespondentBillCode()).isEqualTo(DEFAULT_CORRESPONDENT_BILL_CODE);
        assertThat(testCorrespondentBill.getCorrespondentBillDate()).isEqualTo(DEFAULT_CORRESPONDENT_BILL_DATE);
        assertThat(testCorrespondentBill.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testCorrespondentBill.getMainContent()).isEqualTo(DEFAULT_MAIN_CONTENT);
        assertThat(testCorrespondentBill.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testCorrespondentBill.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCorrespondentBill.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testCorrespondentBill.getCurrencyRate()).isEqualTo(DEFAULT_CURRENCY_RATE);
        assertThat(testCorrespondentBill.getExchangeDate()).isEqualTo(DEFAULT_EXCHANGE_DATE);
        assertThat(testCorrespondentBill.getExchangeCurrency()).isEqualTo(DEFAULT_EXCHANGE_CURRENCY);
        assertThat(testCorrespondentBill.getExchangeRate()).isEqualTo(DEFAULT_EXCHANGE_RATE);
        assertThat(testCorrespondentBill.getExchangeAmount()).isEqualTo(DEFAULT_EXCHANGE_AMOUNT);
        assertThat(testCorrespondentBill.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createCorrespondentBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentBillRepository.findAll().size();

        // Create the CorrespondentBill with an existing ID
        correspondentBill.setId(1L);
        CorrespondentBillDTO correspondentBillDTO = correspondentBillMapper.toDto(correspondentBill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentBillMockMvc.perform(post("/api/correspondent-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentBill in the database
        List<CorrespondentBill> correspondentBillList = correspondentBillRepository.findAll();
        assertThat(correspondentBillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCorrespondentBills() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentBill.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberId").value(hasItem(DEFAULT_NUMBER_ID)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())))
            .andExpect(jsonPath("$.[*].correspondentBillCode").value(hasItem(DEFAULT_CORRESPONDENT_BILL_CODE.toString())))
            .andExpect(jsonPath("$.[*].correspondentBillDate").value(hasItem(DEFAULT_CORRESPONDENT_BILL_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
            .andExpect(jsonPath("$.[*].mainContent").value(hasItem(DEFAULT_MAIN_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].currencyRate").value(hasItem(DEFAULT_CURRENCY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].exchangeDate").value(hasItem(DEFAULT_EXCHANGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].exchangeCurrency").value(hasItem(DEFAULT_EXCHANGE_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].exchangeAmount").value(hasItem(DEFAULT_EXCHANGE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }
    
    @Test
    @Transactional
    public void getCorrespondentBill() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get the correspondentBill
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills/{id}", correspondentBill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentBill.getId().intValue()))
            .andExpect(jsonPath("$.numberId").value(DEFAULT_NUMBER_ID))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()))
            .andExpect(jsonPath("$.correspondentBillCode").value(DEFAULT_CORRESPONDENT_BILL_CODE.toString()))
            .andExpect(jsonPath("$.correspondentBillDate").value(DEFAULT_CORRESPONDENT_BILL_DATE.toString()))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER.toString()))
            .andExpect(jsonPath("$.mainContent").value(DEFAULT_MAIN_CONTENT.toString()))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.intValue()))
            .andExpect(jsonPath("$.currencyRate").value(DEFAULT_CURRENCY_RATE.doubleValue()))
            .andExpect(jsonPath("$.exchangeDate").value(DEFAULT_EXCHANGE_DATE.toString()))
            .andExpect(jsonPath("$.exchangeCurrency").value(DEFAULT_EXCHANGE_CURRENCY.intValue()))
            .andExpect(jsonPath("$.exchangeRate").value(DEFAULT_EXCHANGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.exchangeAmount").value(DEFAULT_EXCHANGE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByNumberIdIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where numberId equals to DEFAULT_NUMBER_ID
        defaultCorrespondentBillShouldBeFound("numberId.equals=" + DEFAULT_NUMBER_ID);

        // Get all the correspondentBillList where numberId equals to UPDATED_NUMBER_ID
        defaultCorrespondentBillShouldNotBeFound("numberId.equals=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByNumberIdIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where numberId in DEFAULT_NUMBER_ID or UPDATED_NUMBER_ID
        defaultCorrespondentBillShouldBeFound("numberId.in=" + DEFAULT_NUMBER_ID + "," + UPDATED_NUMBER_ID);

        // Get all the correspondentBillList where numberId equals to UPDATED_NUMBER_ID
        defaultCorrespondentBillShouldNotBeFound("numberId.in=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByNumberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where numberId is not null
        defaultCorrespondentBillShouldBeFound("numberId.specified=true");

        // Get all the correspondentBillList where numberId is null
        defaultCorrespondentBillShouldNotBeFound("numberId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByNumberIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where numberId greater than or equals to DEFAULT_NUMBER_ID
        defaultCorrespondentBillShouldBeFound("numberId.greaterOrEqualThan=" + DEFAULT_NUMBER_ID);

        // Get all the correspondentBillList where numberId greater than or equals to UPDATED_NUMBER_ID
        defaultCorrespondentBillShouldNotBeFound("numberId.greaterOrEqualThan=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByNumberIdIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where numberId less than or equals to DEFAULT_NUMBER_ID
        defaultCorrespondentBillShouldNotBeFound("numberId.lessThan=" + DEFAULT_NUMBER_ID);

        // Get all the correspondentBillList where numberId less than or equals to UPDATED_NUMBER_ID
        defaultCorrespondentBillShouldBeFound("numberId.lessThan=" + UPDATED_NUMBER_ID);
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillsByYearIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where year equals to DEFAULT_YEAR
        defaultCorrespondentBillShouldBeFound("year.equals=" + DEFAULT_YEAR);

        // Get all the correspondentBillList where year equals to UPDATED_YEAR
        defaultCorrespondentBillShouldNotBeFound("year.equals=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByYearIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where year in DEFAULT_YEAR or UPDATED_YEAR
        defaultCorrespondentBillShouldBeFound("year.in=" + DEFAULT_YEAR + "," + UPDATED_YEAR);

        // Get all the correspondentBillList where year equals to UPDATED_YEAR
        defaultCorrespondentBillShouldNotBeFound("year.in=" + UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where year is not null
        defaultCorrespondentBillShouldBeFound("year.specified=true");

        // Get all the correspondentBillList where year is null
        defaultCorrespondentBillShouldNotBeFound("year.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where correspondentBillCode equals to DEFAULT_CORRESPONDENT_BILL_CODE
        defaultCorrespondentBillShouldBeFound("correspondentBillCode.equals=" + DEFAULT_CORRESPONDENT_BILL_CODE);

        // Get all the correspondentBillList where correspondentBillCode equals to UPDATED_CORRESPONDENT_BILL_CODE
        defaultCorrespondentBillShouldNotBeFound("correspondentBillCode.equals=" + UPDATED_CORRESPONDENT_BILL_CODE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillCodeIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where correspondentBillCode in DEFAULT_CORRESPONDENT_BILL_CODE or UPDATED_CORRESPONDENT_BILL_CODE
        defaultCorrespondentBillShouldBeFound("correspondentBillCode.in=" + DEFAULT_CORRESPONDENT_BILL_CODE + "," + UPDATED_CORRESPONDENT_BILL_CODE);

        // Get all the correspondentBillList where correspondentBillCode equals to UPDATED_CORRESPONDENT_BILL_CODE
        defaultCorrespondentBillShouldNotBeFound("correspondentBillCode.in=" + UPDATED_CORRESPONDENT_BILL_CODE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where correspondentBillCode is not null
        defaultCorrespondentBillShouldBeFound("correspondentBillCode.specified=true");

        // Get all the correspondentBillList where correspondentBillCode is null
        defaultCorrespondentBillShouldNotBeFound("correspondentBillCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where correspondentBillDate equals to DEFAULT_CORRESPONDENT_BILL_DATE
        defaultCorrespondentBillShouldBeFound("correspondentBillDate.equals=" + DEFAULT_CORRESPONDENT_BILL_DATE);

        // Get all the correspondentBillList where correspondentBillDate equals to UPDATED_CORRESPONDENT_BILL_DATE
        defaultCorrespondentBillShouldNotBeFound("correspondentBillDate.equals=" + UPDATED_CORRESPONDENT_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where correspondentBillDate in DEFAULT_CORRESPONDENT_BILL_DATE or UPDATED_CORRESPONDENT_BILL_DATE
        defaultCorrespondentBillShouldBeFound("correspondentBillDate.in=" + DEFAULT_CORRESPONDENT_BILL_DATE + "," + UPDATED_CORRESPONDENT_BILL_DATE);

        // Get all the correspondentBillList where correspondentBillDate equals to UPDATED_CORRESPONDENT_BILL_DATE
        defaultCorrespondentBillShouldNotBeFound("correspondentBillDate.in=" + UPDATED_CORRESPONDENT_BILL_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where correspondentBillDate is not null
        defaultCorrespondentBillShouldBeFound("correspondentBillDate.specified=true");

        // Get all the correspondentBillList where correspondentBillDate is null
        defaultCorrespondentBillShouldNotBeFound("correspondentBillDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByReceiverIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where receiver equals to DEFAULT_RECEIVER
        defaultCorrespondentBillShouldBeFound("receiver.equals=" + DEFAULT_RECEIVER);

        // Get all the correspondentBillList where receiver equals to UPDATED_RECEIVER
        defaultCorrespondentBillShouldNotBeFound("receiver.equals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByReceiverIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where receiver in DEFAULT_RECEIVER or UPDATED_RECEIVER
        defaultCorrespondentBillShouldBeFound("receiver.in=" + DEFAULT_RECEIVER + "," + UPDATED_RECEIVER);

        // Get all the correspondentBillList where receiver equals to UPDATED_RECEIVER
        defaultCorrespondentBillShouldNotBeFound("receiver.in=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByReceiverIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where receiver is not null
        defaultCorrespondentBillShouldBeFound("receiver.specified=true");

        // Get all the correspondentBillList where receiver is null
        defaultCorrespondentBillShouldNotBeFound("receiver.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByMainContentIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where mainContent equals to DEFAULT_MAIN_CONTENT
        defaultCorrespondentBillShouldBeFound("mainContent.equals=" + DEFAULT_MAIN_CONTENT);

        // Get all the correspondentBillList where mainContent equals to UPDATED_MAIN_CONTENT
        defaultCorrespondentBillShouldNotBeFound("mainContent.equals=" + UPDATED_MAIN_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByMainContentIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where mainContent in DEFAULT_MAIN_CONTENT or UPDATED_MAIN_CONTENT
        defaultCorrespondentBillShouldBeFound("mainContent.in=" + DEFAULT_MAIN_CONTENT + "," + UPDATED_MAIN_CONTENT);

        // Get all the correspondentBillList where mainContent equals to UPDATED_MAIN_CONTENT
        defaultCorrespondentBillShouldNotBeFound("mainContent.in=" + UPDATED_MAIN_CONTENT);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByMainContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where mainContent is not null
        defaultCorrespondentBillShouldBeFound("mainContent.specified=true");

        // Get all the correspondentBillList where mainContent is null
        defaultCorrespondentBillShouldNotBeFound("mainContent.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where dueDate equals to DEFAULT_DUE_DATE
        defaultCorrespondentBillShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the correspondentBillList where dueDate equals to UPDATED_DUE_DATE
        defaultCorrespondentBillShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultCorrespondentBillShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the correspondentBillList where dueDate equals to UPDATED_DUE_DATE
        defaultCorrespondentBillShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where dueDate is not null
        defaultCorrespondentBillShouldBeFound("dueDate.specified=true");

        // Get all the correspondentBillList where dueDate is null
        defaultCorrespondentBillShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where amount equals to DEFAULT_AMOUNT
        defaultCorrespondentBillShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the correspondentBillList where amount equals to UPDATED_AMOUNT
        defaultCorrespondentBillShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCorrespondentBillShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the correspondentBillList where amount equals to UPDATED_AMOUNT
        defaultCorrespondentBillShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where amount is not null
        defaultCorrespondentBillShouldBeFound("amount.specified=true");

        // Get all the correspondentBillList where amount is null
        defaultCorrespondentBillShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currency equals to DEFAULT_CURRENCY
        defaultCorrespondentBillShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the correspondentBillList where currency equals to UPDATED_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultCorrespondentBillShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the correspondentBillList where currency equals to UPDATED_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currency is not null
        defaultCorrespondentBillShouldBeFound("currency.specified=true");

        // Get all the correspondentBillList where currency is null
        defaultCorrespondentBillShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currency greater than or equals to DEFAULT_CURRENCY
        defaultCorrespondentBillShouldBeFound("currency.greaterOrEqualThan=" + DEFAULT_CURRENCY);

        // Get all the correspondentBillList where currency greater than or equals to UPDATED_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("currency.greaterOrEqualThan=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currency less than or equals to DEFAULT_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("currency.lessThan=" + DEFAULT_CURRENCY);

        // Get all the correspondentBillList where currency less than or equals to UPDATED_CURRENCY
        defaultCorrespondentBillShouldBeFound("currency.lessThan=" + UPDATED_CURRENCY);
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currencyRate equals to DEFAULT_CURRENCY_RATE
        defaultCorrespondentBillShouldBeFound("currencyRate.equals=" + DEFAULT_CURRENCY_RATE);

        // Get all the correspondentBillList where currencyRate equals to UPDATED_CURRENCY_RATE
        defaultCorrespondentBillShouldNotBeFound("currencyRate.equals=" + UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyRateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currencyRate in DEFAULT_CURRENCY_RATE or UPDATED_CURRENCY_RATE
        defaultCorrespondentBillShouldBeFound("currencyRate.in=" + DEFAULT_CURRENCY_RATE + "," + UPDATED_CURRENCY_RATE);

        // Get all the correspondentBillList where currencyRate equals to UPDATED_CURRENCY_RATE
        defaultCorrespondentBillShouldNotBeFound("currencyRate.in=" + UPDATED_CURRENCY_RATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCurrencyRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where currencyRate is not null
        defaultCorrespondentBillShouldBeFound("currencyRate.specified=true");

        // Get all the correspondentBillList where currencyRate is null
        defaultCorrespondentBillShouldNotBeFound("currencyRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeDate equals to DEFAULT_EXCHANGE_DATE
        defaultCorrespondentBillShouldBeFound("exchangeDate.equals=" + DEFAULT_EXCHANGE_DATE);

        // Get all the correspondentBillList where exchangeDate equals to UPDATED_EXCHANGE_DATE
        defaultCorrespondentBillShouldNotBeFound("exchangeDate.equals=" + UPDATED_EXCHANGE_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeDateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeDate in DEFAULT_EXCHANGE_DATE or UPDATED_EXCHANGE_DATE
        defaultCorrespondentBillShouldBeFound("exchangeDate.in=" + DEFAULT_EXCHANGE_DATE + "," + UPDATED_EXCHANGE_DATE);

        // Get all the correspondentBillList where exchangeDate equals to UPDATED_EXCHANGE_DATE
        defaultCorrespondentBillShouldNotBeFound("exchangeDate.in=" + UPDATED_EXCHANGE_DATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeDate is not null
        defaultCorrespondentBillShouldBeFound("exchangeDate.specified=true");

        // Get all the correspondentBillList where exchangeDate is null
        defaultCorrespondentBillShouldNotBeFound("exchangeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeCurrency equals to DEFAULT_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldBeFound("exchangeCurrency.equals=" + DEFAULT_EXCHANGE_CURRENCY);

        // Get all the correspondentBillList where exchangeCurrency equals to UPDATED_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("exchangeCurrency.equals=" + UPDATED_EXCHANGE_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeCurrency in DEFAULT_EXCHANGE_CURRENCY or UPDATED_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldBeFound("exchangeCurrency.in=" + DEFAULT_EXCHANGE_CURRENCY + "," + UPDATED_EXCHANGE_CURRENCY);

        // Get all the correspondentBillList where exchangeCurrency equals to UPDATED_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("exchangeCurrency.in=" + UPDATED_EXCHANGE_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeCurrency is not null
        defaultCorrespondentBillShouldBeFound("exchangeCurrency.specified=true");

        // Get all the correspondentBillList where exchangeCurrency is null
        defaultCorrespondentBillShouldNotBeFound("exchangeCurrency.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeCurrencyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeCurrency greater than or equals to DEFAULT_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldBeFound("exchangeCurrency.greaterOrEqualThan=" + DEFAULT_EXCHANGE_CURRENCY);

        // Get all the correspondentBillList where exchangeCurrency greater than or equals to UPDATED_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("exchangeCurrency.greaterOrEqualThan=" + UPDATED_EXCHANGE_CURRENCY);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeCurrencyIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeCurrency less than or equals to DEFAULT_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldNotBeFound("exchangeCurrency.lessThan=" + DEFAULT_EXCHANGE_CURRENCY);

        // Get all the correspondentBillList where exchangeCurrency less than or equals to UPDATED_EXCHANGE_CURRENCY
        defaultCorrespondentBillShouldBeFound("exchangeCurrency.lessThan=" + UPDATED_EXCHANGE_CURRENCY);
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeRateIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeRate equals to DEFAULT_EXCHANGE_RATE
        defaultCorrespondentBillShouldBeFound("exchangeRate.equals=" + DEFAULT_EXCHANGE_RATE);

        // Get all the correspondentBillList where exchangeRate equals to UPDATED_EXCHANGE_RATE
        defaultCorrespondentBillShouldNotBeFound("exchangeRate.equals=" + UPDATED_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeRateIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeRate in DEFAULT_EXCHANGE_RATE or UPDATED_EXCHANGE_RATE
        defaultCorrespondentBillShouldBeFound("exchangeRate.in=" + DEFAULT_EXCHANGE_RATE + "," + UPDATED_EXCHANGE_RATE);

        // Get all the correspondentBillList where exchangeRate equals to UPDATED_EXCHANGE_RATE
        defaultCorrespondentBillShouldNotBeFound("exchangeRate.in=" + UPDATED_EXCHANGE_RATE);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeRate is not null
        defaultCorrespondentBillShouldBeFound("exchangeRate.specified=true");

        // Get all the correspondentBillList where exchangeRate is null
        defaultCorrespondentBillShouldNotBeFound("exchangeRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeAmount equals to DEFAULT_EXCHANGE_AMOUNT
        defaultCorrespondentBillShouldBeFound("exchangeAmount.equals=" + DEFAULT_EXCHANGE_AMOUNT);

        // Get all the correspondentBillList where exchangeAmount equals to UPDATED_EXCHANGE_AMOUNT
        defaultCorrespondentBillShouldNotBeFound("exchangeAmount.equals=" + UPDATED_EXCHANGE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeAmountIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeAmount in DEFAULT_EXCHANGE_AMOUNT or UPDATED_EXCHANGE_AMOUNT
        defaultCorrespondentBillShouldBeFound("exchangeAmount.in=" + DEFAULT_EXCHANGE_AMOUNT + "," + UPDATED_EXCHANGE_AMOUNT);

        // Get all the correspondentBillList where exchangeAmount equals to UPDATED_EXCHANGE_AMOUNT
        defaultCorrespondentBillShouldNotBeFound("exchangeAmount.in=" + UPDATED_EXCHANGE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByExchangeAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        // Get all the correspondentBillList where exchangeAmount is not null
        defaultCorrespondentBillShouldBeFound("exchangeAmount.specified=true");

        // Get all the correspondentBillList where exchangeAmount is null
        defaultCorrespondentBillShouldNotBeFound("exchangeAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentBillsByCorrespondentBillStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentBillStatus correspondentBillStatus = CorrespondentBillStatusResourceIT.createEntity(em);
        em.persist(correspondentBillStatus);
        em.flush();
        correspondentBill.setCorrespondentBillStatus(correspondentBillStatus);
        correspondentBillRepository.saveAndFlush(correspondentBill);
        Long correspondentBillStatusId = correspondentBillStatus.getId();

        // Get all the correspondentBillList where correspondentBillStatus equals to correspondentBillStatusId
        defaultCorrespondentBillShouldBeFound("correspondentBillStatusId.equals=" + correspondentBillStatusId);

        // Get all the correspondentBillList where correspondentBillStatus equals to correspondentBillStatusId + 1
        defaultCorrespondentBillShouldNotBeFound("correspondentBillStatusId.equals=" + (correspondentBillStatusId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillsByCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        Credit credit = CreditResourceIT.createEntity(em);
        em.persist(credit);
        em.flush();
        correspondentBill.setCredit(credit);
        correspondentBillRepository.saveAndFlush(correspondentBill);
        Long creditId = credit.getId();

        // Get all the correspondentBillList where credit equals to creditId
        defaultCorrespondentBillShouldBeFound("creditId.equals=" + creditId);

        // Get all the correspondentBillList where credit equals to creditId + 1
        defaultCorrespondentBillShouldNotBeFound("creditId.equals=" + (creditId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillsByCpiCorrespondentIsEqualToSomething() throws Exception {
        // Initialize the database
        CPICorrespondent cpiCorrespondent = CPICorrespondentResourceIT.createEntity(em);
        em.persist(cpiCorrespondent);
        em.flush();
        correspondentBill.setCpiCorrespondent(cpiCorrespondent);
        correspondentBillRepository.saveAndFlush(correspondentBill);
        Long cpiCorrespondentId = cpiCorrespondent.getId();

        // Get all the correspondentBillList where cpiCorrespondent equals to cpiCorrespondentId
        defaultCorrespondentBillShouldBeFound("cpiCorrespondentId.equals=" + cpiCorrespondentId);

        // Get all the correspondentBillList where cpiCorrespondent equals to cpiCorrespondentId + 1
        defaultCorrespondentBillShouldNotBeFound("cpiCorrespondentId.equals=" + (cpiCorrespondentId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentBillsByBillFinanceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        BillFinanceType billFinanceType = BillFinanceTypeResourceIT.createEntity(em);
        em.persist(billFinanceType);
        em.flush();
        correspondentBill.setBillFinanceType(billFinanceType);
        correspondentBillRepository.saveAndFlush(correspondentBill);
        Long billFinanceTypeId = billFinanceType.getId();

        // Get all the correspondentBillList where billFinanceType equals to billFinanceTypeId
        defaultCorrespondentBillShouldBeFound("billFinanceTypeId.equals=" + billFinanceTypeId);

        // Get all the correspondentBillList where billFinanceType equals to billFinanceTypeId + 1
        defaultCorrespondentBillShouldNotBeFound("billFinanceTypeId.equals=" + (billFinanceTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCorrespondentBillShouldBeFound(String filter) throws Exception {
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentBill.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberId").value(hasItem(DEFAULT_NUMBER_ID)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].correspondentBillCode").value(hasItem(DEFAULT_CORRESPONDENT_BILL_CODE)))
            .andExpect(jsonPath("$.[*].correspondentBillDate").value(hasItem(DEFAULT_CORRESPONDENT_BILL_DATE.toString())))

            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER)))

            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].currencyRate").value(hasItem(DEFAULT_CURRENCY_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].exchangeDate").value(hasItem(DEFAULT_EXCHANGE_DATE.toString())))
            .andExpect(jsonPath("$.[*].exchangeCurrency").value(hasItem(DEFAULT_EXCHANGE_CURRENCY.intValue())))
            .andExpect(jsonPath("$.[*].exchangeRate").value(hasItem(DEFAULT_EXCHANGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].exchangeAmount").value(hasItem(DEFAULT_EXCHANGE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));

        // Check, that the count call also returns 1
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCorrespondentBillShouldNotBeFound(String filter) throws Exception {
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentBill() throws Exception {
        // Get the correspondentBill
        restCorrespondentBillMockMvc.perform(get("/api/correspondent-bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentBill() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        int databaseSizeBeforeUpdate = correspondentBillRepository.findAll().size();

        // Update the correspondentBill
        CorrespondentBill updatedCorrespondentBill = correspondentBillRepository.findById(correspondentBill.getId()).get();
        // Disconnect from session so that the updates on updatedCorrespondentBill are not directly saved in db
        em.detach(updatedCorrespondentBill);
        updatedCorrespondentBill
            .numberId(UPDATED_NUMBER_ID)
            .year(UPDATED_YEAR)
            .correspondentBillCode(UPDATED_CORRESPONDENT_BILL_CODE)
            .correspondentBillDate(UPDATED_CORRESPONDENT_BILL_DATE)
            .receiver(UPDATED_RECEIVER)
            .mainContent(UPDATED_MAIN_CONTENT)
            .dueDate(UPDATED_DUE_DATE)
            .amount(UPDATED_AMOUNT)
            .currency(UPDATED_CURRENCY)
            .currencyRate(UPDATED_CURRENCY_RATE)
            .exchangeDate(UPDATED_EXCHANGE_DATE)
            .exchangeCurrency(UPDATED_EXCHANGE_CURRENCY)
            .exchangeRate(UPDATED_EXCHANGE_RATE)
            .exchangeAmount(UPDATED_EXCHANGE_AMOUNT)
            .remark(UPDATED_REMARK);
        CorrespondentBillDTO correspondentBillDTO = correspondentBillMapper.toDto(updatedCorrespondentBill);

        restCorrespondentBillMockMvc.perform(put("/api/correspondent-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentBill in the database
        List<CorrespondentBill> correspondentBillList = correspondentBillRepository.findAll();
        assertThat(correspondentBillList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentBill testCorrespondentBill = correspondentBillList.get(correspondentBillList.size() - 1);
        assertThat(testCorrespondentBill.getNumberId()).isEqualTo(UPDATED_NUMBER_ID);
        assertThat(testCorrespondentBill.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testCorrespondentBill.getCorrespondentBillCode()).isEqualTo(UPDATED_CORRESPONDENT_BILL_CODE);
        assertThat(testCorrespondentBill.getCorrespondentBillDate()).isEqualTo(UPDATED_CORRESPONDENT_BILL_DATE);
        assertThat(testCorrespondentBill.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testCorrespondentBill.getMainContent()).isEqualTo(UPDATED_MAIN_CONTENT);
        assertThat(testCorrespondentBill.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testCorrespondentBill.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCorrespondentBill.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testCorrespondentBill.getCurrencyRate()).isEqualTo(UPDATED_CURRENCY_RATE);
        assertThat(testCorrespondentBill.getExchangeDate()).isEqualTo(UPDATED_EXCHANGE_DATE);
        assertThat(testCorrespondentBill.getExchangeCurrency()).isEqualTo(UPDATED_EXCHANGE_CURRENCY);
        assertThat(testCorrespondentBill.getExchangeRate()).isEqualTo(UPDATED_EXCHANGE_RATE);
        assertThat(testCorrespondentBill.getExchangeAmount()).isEqualTo(UPDATED_EXCHANGE_AMOUNT);
        assertThat(testCorrespondentBill.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentBill() throws Exception {
        int databaseSizeBeforeUpdate = correspondentBillRepository.findAll().size();

        // Create the CorrespondentBill
        CorrespondentBillDTO correspondentBillDTO = correspondentBillMapper.toDto(correspondentBill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorrespondentBillMockMvc.perform(put("/api/correspondent-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentBillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentBill in the database
        List<CorrespondentBill> correspondentBillList = correspondentBillRepository.findAll();
        assertThat(correspondentBillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCorrespondentBill() throws Exception {
        // Initialize the database
        correspondentBillRepository.saveAndFlush(correspondentBill);

        int databaseSizeBeforeDelete = correspondentBillRepository.findAll().size();

        // Delete the correspondentBill
        restCorrespondentBillMockMvc.perform(delete("/api/correspondent-bills/{id}", correspondentBill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CorrespondentBill> correspondentBillList = correspondentBillRepository.findAll();
        assertThat(correspondentBillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentBill.class);
        CorrespondentBill correspondentBill1 = new CorrespondentBill();
        correspondentBill1.setId(1L);
        CorrespondentBill correspondentBill2 = new CorrespondentBill();
        correspondentBill2.setId(correspondentBill1.getId());
        assertThat(correspondentBill1).isEqualTo(correspondentBill2);
        correspondentBill2.setId(2L);
        assertThat(correspondentBill1).isNotEqualTo(correspondentBill2);
        correspondentBill1.setId(null);
        assertThat(correspondentBill1).isNotEqualTo(correspondentBill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentBillDTO.class);
        CorrespondentBillDTO correspondentBillDTO1 = new CorrespondentBillDTO();
        correspondentBillDTO1.setId(1L);
        CorrespondentBillDTO correspondentBillDTO2 = new CorrespondentBillDTO();
        assertThat(correspondentBillDTO1).isNotEqualTo(correspondentBillDTO2);
        correspondentBillDTO2.setId(correspondentBillDTO1.getId());
        assertThat(correspondentBillDTO1).isEqualTo(correspondentBillDTO2);
        correspondentBillDTO2.setId(2L);
        assertThat(correspondentBillDTO1).isNotEqualTo(correspondentBillDTO2);
        correspondentBillDTO1.setId(null);
        assertThat(correspondentBillDTO1).isNotEqualTo(correspondentBillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentBillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentBillMapper.fromId(null)).isNull();
    }
}
