package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.BillFinanceType;
import com.cpi.correspondent.repository.BillFinanceTypeRepository;
import com.cpi.correspondent.service.BillFinanceTypeService;
import com.cpi.correspondent.service.dto.BillFinanceTypeDTO;
import com.cpi.correspondent.service.mapper.BillFinanceTypeMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.BillFinanceTypeCriteria;
import com.cpi.correspondent.service.BillFinanceTypeQueryService;

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
 * Test class for the BillFinanceTypeResource REST controller.
 *
 * @see BillFinanceTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class BillFinanceTypeResourceIntTest {

    private static final String DEFAULT_BILL_FINANCE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BILL_FINANCE_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private BillFinanceTypeRepository billFinanceTypeRepository;

    @Autowired
    private BillFinanceTypeMapper billFinanceTypeMapper;

    @Autowired
    private BillFinanceTypeService billFinanceTypeService;

    @Autowired
    private BillFinanceTypeQueryService billFinanceTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBillFinanceTypeMockMvc;

    private BillFinanceType billFinanceType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BillFinanceTypeResource billFinanceTypeResource = new BillFinanceTypeResource(billFinanceTypeService, billFinanceTypeQueryService);
        this.restBillFinanceTypeMockMvc = MockMvcBuilders.standaloneSetup(billFinanceTypeResource)
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
    public static BillFinanceType createEntity(EntityManager em) {
        BillFinanceType billFinanceType = new BillFinanceType()
            .billFinanceTypeName(DEFAULT_BILL_FINANCE_TYPE_NAME)
            .sortNum(DEFAULT_SORT_NUM);
        return billFinanceType;
    }

    @Before
    public void initTest() {
        billFinanceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createBillFinanceType() throws Exception {
        int databaseSizeBeforeCreate = billFinanceTypeRepository.findAll().size();

        // Create the BillFinanceType
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeMapper.toDto(billFinanceType);
        restBillFinanceTypeMockMvc.perform(post("/api/bill-finance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billFinanceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BillFinanceType in the database
        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        BillFinanceType testBillFinanceType = billFinanceTypeList.get(billFinanceTypeList.size() - 1);
        assertThat(testBillFinanceType.getBillFinanceTypeName()).isEqualTo(DEFAULT_BILL_FINANCE_TYPE_NAME);
        assertThat(testBillFinanceType.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createBillFinanceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = billFinanceTypeRepository.findAll().size();

        // Create the BillFinanceType with an existing ID
        billFinanceType.setId(1L);
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeMapper.toDto(billFinanceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillFinanceTypeMockMvc.perform(post("/api/bill-finance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billFinanceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BillFinanceType in the database
        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkBillFinanceTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billFinanceTypeRepository.findAll().size();
        // set the field null
        billFinanceType.setBillFinanceTypeName(null);

        // Create the BillFinanceType, which fails.
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeMapper.toDto(billFinanceType);

        restBillFinanceTypeMockMvc.perform(post("/api/bill-finance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billFinanceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = billFinanceTypeRepository.findAll().size();
        // set the field null
        billFinanceType.setSortNum(null);

        // Create the BillFinanceType, which fails.
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeMapper.toDto(billFinanceType);

        restBillFinanceTypeMockMvc.perform(post("/api/bill-finance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billFinanceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypes() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList
        restBillFinanceTypeMockMvc.perform(get("/api/bill-finance-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billFinanceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].billFinanceTypeName").value(hasItem(DEFAULT_BILL_FINANCE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    @Test
    @Transactional
    public void getBillFinanceType() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get the billFinanceType
        restBillFinanceTypeMockMvc.perform(get("/api/bill-finance-types/{id}", billFinanceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(billFinanceType.getId().intValue()))
            .andExpect(jsonPath("$.billFinanceTypeName").value(DEFAULT_BILL_FINANCE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesByBillFinanceTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where billFinanceTypeName equals to DEFAULT_BILL_FINANCE_TYPE_NAME
        defaultBillFinanceTypeShouldBeFound("billFinanceTypeName.equals=" + DEFAULT_BILL_FINANCE_TYPE_NAME);

        // Get all the billFinanceTypeList where billFinanceTypeName equals to UPDATED_BILL_FINANCE_TYPE_NAME
        defaultBillFinanceTypeShouldNotBeFound("billFinanceTypeName.equals=" + UPDATED_BILL_FINANCE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesByBillFinanceTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where billFinanceTypeName in DEFAULT_BILL_FINANCE_TYPE_NAME or UPDATED_BILL_FINANCE_TYPE_NAME
        defaultBillFinanceTypeShouldBeFound("billFinanceTypeName.in=" + DEFAULT_BILL_FINANCE_TYPE_NAME + "," + UPDATED_BILL_FINANCE_TYPE_NAME);

        // Get all the billFinanceTypeList where billFinanceTypeName equals to UPDATED_BILL_FINANCE_TYPE_NAME
        defaultBillFinanceTypeShouldNotBeFound("billFinanceTypeName.in=" + UPDATED_BILL_FINANCE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesByBillFinanceTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where billFinanceTypeName is not null
        defaultBillFinanceTypeShouldBeFound("billFinanceTypeName.specified=true");

        // Get all the billFinanceTypeList where billFinanceTypeName is null
        defaultBillFinanceTypeShouldNotBeFound("billFinanceTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where sortNum equals to DEFAULT_SORT_NUM
        defaultBillFinanceTypeShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the billFinanceTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultBillFinanceTypeShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultBillFinanceTypeShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the billFinanceTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultBillFinanceTypeShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where sortNum is not null
        defaultBillFinanceTypeShouldBeFound("sortNum.specified=true");

        // Get all the billFinanceTypeList where sortNum is null
        defaultBillFinanceTypeShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultBillFinanceTypeShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the billFinanceTypeList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultBillFinanceTypeShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllBillFinanceTypesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);

        // Get all the billFinanceTypeList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultBillFinanceTypeShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the billFinanceTypeList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultBillFinanceTypeShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultBillFinanceTypeShouldBeFound(String filter) throws Exception {
        restBillFinanceTypeMockMvc.perform(get("/api/bill-finance-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billFinanceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].billFinanceTypeName").value(hasItem(DEFAULT_BILL_FINANCE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultBillFinanceTypeShouldNotBeFound(String filter) throws Exception {
        restBillFinanceTypeMockMvc.perform(get("/api/bill-finance-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingBillFinanceType() throws Exception {
        // Get the billFinanceType
        restBillFinanceTypeMockMvc.perform(get("/api/bill-finance-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillFinanceType() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);
        int databaseSizeBeforeUpdate = billFinanceTypeRepository.findAll().size();

        // Update the billFinanceType
        BillFinanceType updatedBillFinanceType = billFinanceTypeRepository.findOne(billFinanceType.getId());
        // Disconnect from session so that the updates on updatedBillFinanceType are not directly saved in db
        em.detach(updatedBillFinanceType);
        updatedBillFinanceType
            .billFinanceTypeName(UPDATED_BILL_FINANCE_TYPE_NAME)
            .sortNum(UPDATED_SORT_NUM);
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeMapper.toDto(updatedBillFinanceType);

        restBillFinanceTypeMockMvc.perform(put("/api/bill-finance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billFinanceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the BillFinanceType in the database
        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeUpdate);
        BillFinanceType testBillFinanceType = billFinanceTypeList.get(billFinanceTypeList.size() - 1);
        assertThat(testBillFinanceType.getBillFinanceTypeName()).isEqualTo(UPDATED_BILL_FINANCE_TYPE_NAME);
        assertThat(testBillFinanceType.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingBillFinanceType() throws Exception {
        int databaseSizeBeforeUpdate = billFinanceTypeRepository.findAll().size();

        // Create the BillFinanceType
        BillFinanceTypeDTO billFinanceTypeDTO = billFinanceTypeMapper.toDto(billFinanceType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBillFinanceTypeMockMvc.perform(put("/api/bill-finance-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(billFinanceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the BillFinanceType in the database
        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBillFinanceType() throws Exception {
        // Initialize the database
        billFinanceTypeRepository.saveAndFlush(billFinanceType);
        int databaseSizeBeforeDelete = billFinanceTypeRepository.findAll().size();

        // Get the billFinanceType
        restBillFinanceTypeMockMvc.perform(delete("/api/bill-finance-types/{id}", billFinanceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BillFinanceType> billFinanceTypeList = billFinanceTypeRepository.findAll();
        assertThat(billFinanceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillFinanceType.class);
        BillFinanceType billFinanceType1 = new BillFinanceType();
        billFinanceType1.setId(1L);
        BillFinanceType billFinanceType2 = new BillFinanceType();
        billFinanceType2.setId(billFinanceType1.getId());
        assertThat(billFinanceType1).isEqualTo(billFinanceType2);
        billFinanceType2.setId(2L);
        assertThat(billFinanceType1).isNotEqualTo(billFinanceType2);
        billFinanceType1.setId(null);
        assertThat(billFinanceType1).isNotEqualTo(billFinanceType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillFinanceTypeDTO.class);
        BillFinanceTypeDTO billFinanceTypeDTO1 = new BillFinanceTypeDTO();
        billFinanceTypeDTO1.setId(1L);
        BillFinanceTypeDTO billFinanceTypeDTO2 = new BillFinanceTypeDTO();
        assertThat(billFinanceTypeDTO1).isNotEqualTo(billFinanceTypeDTO2);
        billFinanceTypeDTO2.setId(billFinanceTypeDTO1.getId());
        assertThat(billFinanceTypeDTO1).isEqualTo(billFinanceTypeDTO2);
        billFinanceTypeDTO2.setId(2L);
        assertThat(billFinanceTypeDTO1).isNotEqualTo(billFinanceTypeDTO2);
        billFinanceTypeDTO1.setId(null);
        assertThat(billFinanceTypeDTO1).isNotEqualTo(billFinanceTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(billFinanceTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(billFinanceTypeMapper.fromId(null)).isNull();
    }
}
