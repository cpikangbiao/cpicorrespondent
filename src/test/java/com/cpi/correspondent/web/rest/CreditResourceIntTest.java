package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.Credit;
import com.cpi.correspondent.repository.CreditRepository;
import com.cpi.correspondent.service.CreditService;
import com.cpi.correspondent.service.dto.CreditDTO;
import com.cpi.correspondent.service.mapper.CreditMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CreditCriteria;
import com.cpi.correspondent.service.CreditQueryService;

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
 * Test class for the CreditResource REST controller.
 *
 * @see CreditResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CreditResourceIntTest {

    private static final Integer DEFAULT_NUMBER_ID = 1;
    private static final Integer UPDATED_NUMBER_ID = 2;

    private static final String DEFAULT_CREDITOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CREDITOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CREDITOR_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CREDITOR_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_BANK_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CORR_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORR_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CORR_BANK_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CORR_BANK_ADDRESS = "BBBBBBBBBB";

    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private CreditMapper creditMapper;

    @Autowired
    private CreditService creditService;

    @Autowired
    private CreditQueryService creditQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCreditMockMvc;

    private Credit credit;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CreditResource creditResource = new CreditResource(creditService, creditQueryService);
        this.restCreditMockMvc = MockMvcBuilders.standaloneSetup(creditResource)
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
    public static Credit createEntity(EntityManager em) {
        Credit credit = new Credit()
            .numberId(DEFAULT_NUMBER_ID)
            .creditorName(DEFAULT_CREDITOR_NAME)
            .creditorAddress(DEFAULT_CREDITOR_ADDRESS)
            .bankName(DEFAULT_BANK_NAME)
            .bankAddress(DEFAULT_BANK_ADDRESS)
            .accountNo(DEFAULT_ACCOUNT_NO)
            .corrBankName(DEFAULT_CORR_BANK_NAME)
            .corrBankAddress(DEFAULT_CORR_BANK_ADDRESS);
        return credit;
    }

    @Before
    public void initTest() {
        credit = createEntity(em);
    }

    @Test
    @Transactional
    public void createCredit() throws Exception {
        int databaseSizeBeforeCreate = creditRepository.findAll().size();

        // Create the Credit
        CreditDTO creditDTO = creditMapper.toDto(credit);
        restCreditMockMvc.perform(post("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isCreated());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeCreate + 1);
        Credit testCredit = creditList.get(creditList.size() - 1);
        assertThat(testCredit.getNumberId()).isEqualTo(DEFAULT_NUMBER_ID);
        assertThat(testCredit.getCreditorName()).isEqualTo(DEFAULT_CREDITOR_NAME);
        assertThat(testCredit.getCreditorAddress()).isEqualTo(DEFAULT_CREDITOR_ADDRESS);
        assertThat(testCredit.getBankName()).isEqualTo(DEFAULT_BANK_NAME);
        assertThat(testCredit.getBankAddress()).isEqualTo(DEFAULT_BANK_ADDRESS);
        assertThat(testCredit.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testCredit.getCorrBankName()).isEqualTo(DEFAULT_CORR_BANK_NAME);
        assertThat(testCredit.getCorrBankAddress()).isEqualTo(DEFAULT_CORR_BANK_ADDRESS);
    }

    @Test
    @Transactional
    public void createCreditWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = creditRepository.findAll().size();

        // Create the Credit with an existing ID
        credit.setId(1L);
        CreditDTO creditDTO = creditMapper.toDto(credit);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditMockMvc.perform(post("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCredits() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList
        restCreditMockMvc.perform(get("/api/credits?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credit.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberId").value(hasItem(DEFAULT_NUMBER_ID)))
            .andExpect(jsonPath("$.[*].creditorName").value(hasItem(DEFAULT_CREDITOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].creditorAddress").value(hasItem(DEFAULT_CREDITOR_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankAddress").value(hasItem(DEFAULT_BANK_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.toString())))
            .andExpect(jsonPath("$.[*].corrBankName").value(hasItem(DEFAULT_CORR_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].corrBankAddress").value(hasItem(DEFAULT_CORR_BANK_ADDRESS.toString())));
    }

    @Test
    @Transactional
    public void getCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get the credit
        restCreditMockMvc.perform(get("/api/credits/{id}", credit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(credit.getId().intValue()))
            .andExpect(jsonPath("$.numberId").value(DEFAULT_NUMBER_ID))
            .andExpect(jsonPath("$.creditorName").value(DEFAULT_CREDITOR_NAME.toString()))
            .andExpect(jsonPath("$.creditorAddress").value(DEFAULT_CREDITOR_ADDRESS.toString()))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME.toString()))
            .andExpect(jsonPath("$.bankAddress").value(DEFAULT_BANK_ADDRESS.toString()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.toString()))
            .andExpect(jsonPath("$.corrBankName").value(DEFAULT_CORR_BANK_NAME.toString()))
            .andExpect(jsonPath("$.corrBankAddress").value(DEFAULT_CORR_BANK_ADDRESS.toString()));
    }

    @Test
    @Transactional
    public void getAllCreditsByNumberIdIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where numberId equals to DEFAULT_NUMBER_ID
        defaultCreditShouldBeFound("numberId.equals=" + DEFAULT_NUMBER_ID);

        // Get all the creditList where numberId equals to UPDATED_NUMBER_ID
        defaultCreditShouldNotBeFound("numberId.equals=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCreditsByNumberIdIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where numberId in DEFAULT_NUMBER_ID or UPDATED_NUMBER_ID
        defaultCreditShouldBeFound("numberId.in=" + DEFAULT_NUMBER_ID + "," + UPDATED_NUMBER_ID);

        // Get all the creditList where numberId equals to UPDATED_NUMBER_ID
        defaultCreditShouldNotBeFound("numberId.in=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCreditsByNumberIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where numberId is not null
        defaultCreditShouldBeFound("numberId.specified=true");

        // Get all the creditList where numberId is null
        defaultCreditShouldNotBeFound("numberId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByNumberIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where numberId greater than or equals to DEFAULT_NUMBER_ID
        defaultCreditShouldBeFound("numberId.greaterOrEqualThan=" + DEFAULT_NUMBER_ID);

        // Get all the creditList where numberId greater than or equals to UPDATED_NUMBER_ID
        defaultCreditShouldNotBeFound("numberId.greaterOrEqualThan=" + UPDATED_NUMBER_ID);
    }

    @Test
    @Transactional
    public void getAllCreditsByNumberIdIsLessThanSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where numberId less than or equals to DEFAULT_NUMBER_ID
        defaultCreditShouldNotBeFound("numberId.lessThan=" + DEFAULT_NUMBER_ID);

        // Get all the creditList where numberId less than or equals to UPDATED_NUMBER_ID
        defaultCreditShouldBeFound("numberId.lessThan=" + UPDATED_NUMBER_ID);
    }


    @Test
    @Transactional
    public void getAllCreditsByCreditorNameIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where creditorName equals to DEFAULT_CREDITOR_NAME
        defaultCreditShouldBeFound("creditorName.equals=" + DEFAULT_CREDITOR_NAME);

        // Get all the creditList where creditorName equals to UPDATED_CREDITOR_NAME
        defaultCreditShouldNotBeFound("creditorName.equals=" + UPDATED_CREDITOR_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditsByCreditorNameIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where creditorName in DEFAULT_CREDITOR_NAME or UPDATED_CREDITOR_NAME
        defaultCreditShouldBeFound("creditorName.in=" + DEFAULT_CREDITOR_NAME + "," + UPDATED_CREDITOR_NAME);

        // Get all the creditList where creditorName equals to UPDATED_CREDITOR_NAME
        defaultCreditShouldNotBeFound("creditorName.in=" + UPDATED_CREDITOR_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditsByCreditorNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where creditorName is not null
        defaultCreditShouldBeFound("creditorName.specified=true");

        // Get all the creditList where creditorName is null
        defaultCreditShouldNotBeFound("creditorName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByCreditorAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where creditorAddress equals to DEFAULT_CREDITOR_ADDRESS
        defaultCreditShouldBeFound("creditorAddress.equals=" + DEFAULT_CREDITOR_ADDRESS);

        // Get all the creditList where creditorAddress equals to UPDATED_CREDITOR_ADDRESS
        defaultCreditShouldNotBeFound("creditorAddress.equals=" + UPDATED_CREDITOR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCreditsByCreditorAddressIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where creditorAddress in DEFAULT_CREDITOR_ADDRESS or UPDATED_CREDITOR_ADDRESS
        defaultCreditShouldBeFound("creditorAddress.in=" + DEFAULT_CREDITOR_ADDRESS + "," + UPDATED_CREDITOR_ADDRESS);

        // Get all the creditList where creditorAddress equals to UPDATED_CREDITOR_ADDRESS
        defaultCreditShouldNotBeFound("creditorAddress.in=" + UPDATED_CREDITOR_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCreditsByCreditorAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where creditorAddress is not null
        defaultCreditShouldBeFound("creditorAddress.specified=true");

        // Get all the creditList where creditorAddress is null
        defaultCreditShouldNotBeFound("creditorAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where bankName equals to DEFAULT_BANK_NAME
        defaultCreditShouldBeFound("bankName.equals=" + DEFAULT_BANK_NAME);

        // Get all the creditList where bankName equals to UPDATED_BANK_NAME
        defaultCreditShouldNotBeFound("bankName.equals=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditsByBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where bankName in DEFAULT_BANK_NAME or UPDATED_BANK_NAME
        defaultCreditShouldBeFound("bankName.in=" + DEFAULT_BANK_NAME + "," + UPDATED_BANK_NAME);

        // Get all the creditList where bankName equals to UPDATED_BANK_NAME
        defaultCreditShouldNotBeFound("bankName.in=" + UPDATED_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditsByBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where bankName is not null
        defaultCreditShouldBeFound("bankName.specified=true");

        // Get all the creditList where bankName is null
        defaultCreditShouldNotBeFound("bankName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByBankAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where bankAddress equals to DEFAULT_BANK_ADDRESS
        defaultCreditShouldBeFound("bankAddress.equals=" + DEFAULT_BANK_ADDRESS);

        // Get all the creditList where bankAddress equals to UPDATED_BANK_ADDRESS
        defaultCreditShouldNotBeFound("bankAddress.equals=" + UPDATED_BANK_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCreditsByBankAddressIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where bankAddress in DEFAULT_BANK_ADDRESS or UPDATED_BANK_ADDRESS
        defaultCreditShouldBeFound("bankAddress.in=" + DEFAULT_BANK_ADDRESS + "," + UPDATED_BANK_ADDRESS);

        // Get all the creditList where bankAddress equals to UPDATED_BANK_ADDRESS
        defaultCreditShouldNotBeFound("bankAddress.in=" + UPDATED_BANK_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCreditsByBankAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where bankAddress is not null
        defaultCreditShouldBeFound("bankAddress.specified=true");

        // Get all the creditList where bankAddress is null
        defaultCreditShouldNotBeFound("bankAddress.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultCreditShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the creditList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCreditShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllCreditsByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultCreditShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the creditList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultCreditShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    public void getAllCreditsByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where accountNo is not null
        defaultCreditShouldBeFound("accountNo.specified=true");

        // Get all the creditList where accountNo is null
        defaultCreditShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByCorrBankNameIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where corrBankName equals to DEFAULT_CORR_BANK_NAME
        defaultCreditShouldBeFound("corrBankName.equals=" + DEFAULT_CORR_BANK_NAME);

        // Get all the creditList where corrBankName equals to UPDATED_CORR_BANK_NAME
        defaultCreditShouldNotBeFound("corrBankName.equals=" + UPDATED_CORR_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditsByCorrBankNameIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where corrBankName in DEFAULT_CORR_BANK_NAME or UPDATED_CORR_BANK_NAME
        defaultCreditShouldBeFound("corrBankName.in=" + DEFAULT_CORR_BANK_NAME + "," + UPDATED_CORR_BANK_NAME);

        // Get all the creditList where corrBankName equals to UPDATED_CORR_BANK_NAME
        defaultCreditShouldNotBeFound("corrBankName.in=" + UPDATED_CORR_BANK_NAME);
    }

    @Test
    @Transactional
    public void getAllCreditsByCorrBankNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where corrBankName is not null
        defaultCreditShouldBeFound("corrBankName.specified=true");

        // Get all the creditList where corrBankName is null
        defaultCreditShouldNotBeFound("corrBankName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCreditsByCorrBankAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where corrBankAddress equals to DEFAULT_CORR_BANK_ADDRESS
        defaultCreditShouldBeFound("corrBankAddress.equals=" + DEFAULT_CORR_BANK_ADDRESS);

        // Get all the creditList where corrBankAddress equals to UPDATED_CORR_BANK_ADDRESS
        defaultCreditShouldNotBeFound("corrBankAddress.equals=" + UPDATED_CORR_BANK_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCreditsByCorrBankAddressIsInShouldWork() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where corrBankAddress in DEFAULT_CORR_BANK_ADDRESS or UPDATED_CORR_BANK_ADDRESS
        defaultCreditShouldBeFound("corrBankAddress.in=" + DEFAULT_CORR_BANK_ADDRESS + "," + UPDATED_CORR_BANK_ADDRESS);

        // Get all the creditList where corrBankAddress equals to UPDATED_CORR_BANK_ADDRESS
        defaultCreditShouldNotBeFound("corrBankAddress.in=" + UPDATED_CORR_BANK_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCreditsByCorrBankAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);

        // Get all the creditList where corrBankAddress is not null
        defaultCreditShouldBeFound("corrBankAddress.specified=true");

        // Get all the creditList where corrBankAddress is null
        defaultCreditShouldNotBeFound("corrBankAddress.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCreditShouldBeFound(String filter) throws Exception {
        restCreditMockMvc.perform(get("/api/credits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(credit.getId().intValue())))
            .andExpect(jsonPath("$.[*].numberId").value(hasItem(DEFAULT_NUMBER_ID)))
            .andExpect(jsonPath("$.[*].creditorName").value(hasItem(DEFAULT_CREDITOR_NAME.toString())))
            .andExpect(jsonPath("$.[*].creditorAddress").value(hasItem(DEFAULT_CREDITOR_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].bankAddress").value(hasItem(DEFAULT_BANK_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.toString())))
            .andExpect(jsonPath("$.[*].corrBankName").value(hasItem(DEFAULT_CORR_BANK_NAME.toString())))
            .andExpect(jsonPath("$.[*].corrBankAddress").value(hasItem(DEFAULT_CORR_BANK_ADDRESS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCreditShouldNotBeFound(String filter) throws Exception {
        restCreditMockMvc.perform(get("/api/credits?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCredit() throws Exception {
        // Get the credit
        restCreditMockMvc.perform(get("/api/credits/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);
        int databaseSizeBeforeUpdate = creditRepository.findAll().size();

        // Update the credit
        Credit updatedCredit = creditRepository.findOne(credit.getId());
        // Disconnect from session so that the updates on updatedCredit are not directly saved in db
        em.detach(updatedCredit);
        updatedCredit
            .numberId(UPDATED_NUMBER_ID)
            .creditorName(UPDATED_CREDITOR_NAME)
            .creditorAddress(UPDATED_CREDITOR_ADDRESS)
            .bankName(UPDATED_BANK_NAME)
            .bankAddress(UPDATED_BANK_ADDRESS)
            .accountNo(UPDATED_ACCOUNT_NO)
            .corrBankName(UPDATED_CORR_BANK_NAME)
            .corrBankAddress(UPDATED_CORR_BANK_ADDRESS);
        CreditDTO creditDTO = creditMapper.toDto(updatedCredit);

        restCreditMockMvc.perform(put("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isOk());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeUpdate);
        Credit testCredit = creditList.get(creditList.size() - 1);
        assertThat(testCredit.getNumberId()).isEqualTo(UPDATED_NUMBER_ID);
        assertThat(testCredit.getCreditorName()).isEqualTo(UPDATED_CREDITOR_NAME);
        assertThat(testCredit.getCreditorAddress()).isEqualTo(UPDATED_CREDITOR_ADDRESS);
        assertThat(testCredit.getBankName()).isEqualTo(UPDATED_BANK_NAME);
        assertThat(testCredit.getBankAddress()).isEqualTo(UPDATED_BANK_ADDRESS);
        assertThat(testCredit.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testCredit.getCorrBankName()).isEqualTo(UPDATED_CORR_BANK_NAME);
        assertThat(testCredit.getCorrBankAddress()).isEqualTo(UPDATED_CORR_BANK_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingCredit() throws Exception {
        int databaseSizeBeforeUpdate = creditRepository.findAll().size();

        // Create the Credit
        CreditDTO creditDTO = creditMapper.toDto(credit);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCreditMockMvc.perform(put("/api/credits")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(creditDTO)))
            .andExpect(status().isCreated());

        // Validate the Credit in the database
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCredit() throws Exception {
        // Initialize the database
        creditRepository.saveAndFlush(credit);
        int databaseSizeBeforeDelete = creditRepository.findAll().size();

        // Get the credit
        restCreditMockMvc.perform(delete("/api/credits/{id}", credit.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Credit> creditList = creditRepository.findAll();
        assertThat(creditList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Credit.class);
        Credit credit1 = new Credit();
        credit1.setId(1L);
        Credit credit2 = new Credit();
        credit2.setId(credit1.getId());
        assertThat(credit1).isEqualTo(credit2);
        credit2.setId(2L);
        assertThat(credit1).isNotEqualTo(credit2);
        credit1.setId(null);
        assertThat(credit1).isNotEqualTo(credit2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CreditDTO.class);
        CreditDTO creditDTO1 = new CreditDTO();
        creditDTO1.setId(1L);
        CreditDTO creditDTO2 = new CreditDTO();
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
        creditDTO2.setId(creditDTO1.getId());
        assertThat(creditDTO1).isEqualTo(creditDTO2);
        creditDTO2.setId(2L);
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
        creditDTO1.setId(null);
        assertThat(creditDTO1).isNotEqualTo(creditDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(creditMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(creditMapper.fromId(null)).isNull();
    }
}
