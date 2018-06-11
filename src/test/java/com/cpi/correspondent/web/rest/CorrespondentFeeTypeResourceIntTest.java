package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CorrespondentFeeType;
import com.cpi.correspondent.repository.CorrespondentFeeTypeRepository;
import com.cpi.correspondent.service.CorrespondentFeeTypeService;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeTypeMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentFeeTypeCriteria;
import com.cpi.correspondent.service.CorrespondentFeeTypeQueryService;

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
 * Test class for the CorrespondentFeeTypeResource REST controller.
 *
 * @see CorrespondentFeeTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentFeeTypeResourceIntTest {

    private static final String DEFAULT_CORRESPONDENT_FEE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_FEE_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private CorrespondentFeeTypeRepository correspondentFeeTypeRepository;

    @Autowired
    private CorrespondentFeeTypeMapper correspondentFeeTypeMapper;

    @Autowired
    private CorrespondentFeeTypeService correspondentFeeTypeService;

    @Autowired
    private CorrespondentFeeTypeQueryService correspondentFeeTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentFeeTypeMockMvc;

    private CorrespondentFeeType correspondentFeeType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentFeeTypeResource correspondentFeeTypeResource = new CorrespondentFeeTypeResource(correspondentFeeTypeService, correspondentFeeTypeQueryService);
        this.restCorrespondentFeeTypeMockMvc = MockMvcBuilders.standaloneSetup(correspondentFeeTypeResource)
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
    public static CorrespondentFeeType createEntity(EntityManager em) {
        CorrespondentFeeType correspondentFeeType = new CorrespondentFeeType()
            .correspondentFeeTypeName(DEFAULT_CORRESPONDENT_FEE_TYPE_NAME)
            .sortNum(DEFAULT_SORT_NUM);
        return correspondentFeeType;
    }

    @Before
    public void initTest() {
        correspondentFeeType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentFeeType() throws Exception {
        int databaseSizeBeforeCreate = correspondentFeeTypeRepository.findAll().size();

        // Create the CorrespondentFeeType
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeMapper.toDto(correspondentFeeType);
        restCorrespondentFeeTypeMockMvc.perform(post("/api/correspondent-fee-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentFeeType in the database
        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentFeeType testCorrespondentFeeType = correspondentFeeTypeList.get(correspondentFeeTypeList.size() - 1);
        assertThat(testCorrespondentFeeType.getCorrespondentFeeTypeName()).isEqualTo(DEFAULT_CORRESPONDENT_FEE_TYPE_NAME);
        assertThat(testCorrespondentFeeType.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createCorrespondentFeeTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentFeeTypeRepository.findAll().size();

        // Create the CorrespondentFeeType with an existing ID
        correspondentFeeType.setId(1L);
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeMapper.toDto(correspondentFeeType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentFeeTypeMockMvc.perform(post("/api/correspondent-fee-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentFeeType in the database
        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentFeeTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentFeeTypeRepository.findAll().size();
        // set the field null
        correspondentFeeType.setCorrespondentFeeTypeName(null);

        // Create the CorrespondentFeeType, which fails.
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeMapper.toDto(correspondentFeeType);

        restCorrespondentFeeTypeMockMvc.perform(post("/api/correspondent-fee-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentFeeTypeRepository.findAll().size();
        // set the field null
        correspondentFeeType.setSortNum(null);

        // Create the CorrespondentFeeType, which fails.
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeMapper.toDto(correspondentFeeType);

        restCorrespondentFeeTypeMockMvc.perform(post("/api/correspondent-fee-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypes() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList
        restCorrespondentFeeTypeMockMvc.perform(get("/api/correspondent-fee-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentFeeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentFeeTypeName").value(hasItem(DEFAULT_CORRESPONDENT_FEE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    @Test
    @Transactional
    public void getCorrespondentFeeType() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get the correspondentFeeType
        restCorrespondentFeeTypeMockMvc.perform(get("/api/correspondent-fee-types/{id}", correspondentFeeType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentFeeType.getId().intValue()))
            .andExpect(jsonPath("$.correspondentFeeTypeName").value(DEFAULT_CORRESPONDENT_FEE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesByCorrespondentFeeTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where correspondentFeeTypeName equals to DEFAULT_CORRESPONDENT_FEE_TYPE_NAME
        defaultCorrespondentFeeTypeShouldBeFound("correspondentFeeTypeName.equals=" + DEFAULT_CORRESPONDENT_FEE_TYPE_NAME);

        // Get all the correspondentFeeTypeList where correspondentFeeTypeName equals to UPDATED_CORRESPONDENT_FEE_TYPE_NAME
        defaultCorrespondentFeeTypeShouldNotBeFound("correspondentFeeTypeName.equals=" + UPDATED_CORRESPONDENT_FEE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesByCorrespondentFeeTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where correspondentFeeTypeName in DEFAULT_CORRESPONDENT_FEE_TYPE_NAME or UPDATED_CORRESPONDENT_FEE_TYPE_NAME
        defaultCorrespondentFeeTypeShouldBeFound("correspondentFeeTypeName.in=" + DEFAULT_CORRESPONDENT_FEE_TYPE_NAME + "," + UPDATED_CORRESPONDENT_FEE_TYPE_NAME);

        // Get all the correspondentFeeTypeList where correspondentFeeTypeName equals to UPDATED_CORRESPONDENT_FEE_TYPE_NAME
        defaultCorrespondentFeeTypeShouldNotBeFound("correspondentFeeTypeName.in=" + UPDATED_CORRESPONDENT_FEE_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesByCorrespondentFeeTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where correspondentFeeTypeName is not null
        defaultCorrespondentFeeTypeShouldBeFound("correspondentFeeTypeName.specified=true");

        // Get all the correspondentFeeTypeList where correspondentFeeTypeName is null
        defaultCorrespondentFeeTypeShouldNotBeFound("correspondentFeeTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where sortNum equals to DEFAULT_SORT_NUM
        defaultCorrespondentFeeTypeShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the correspondentFeeTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultCorrespondentFeeTypeShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultCorrespondentFeeTypeShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the correspondentFeeTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultCorrespondentFeeTypeShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where sortNum is not null
        defaultCorrespondentFeeTypeShouldBeFound("sortNum.specified=true");

        // Get all the correspondentFeeTypeList where sortNum is null
        defaultCorrespondentFeeTypeShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultCorrespondentFeeTypeShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the correspondentFeeTypeList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultCorrespondentFeeTypeShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeTypesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);

        // Get all the correspondentFeeTypeList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultCorrespondentFeeTypeShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the correspondentFeeTypeList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultCorrespondentFeeTypeShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentFeeTypeShouldBeFound(String filter) throws Exception {
        restCorrespondentFeeTypeMockMvc.perform(get("/api/correspondent-fee-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentFeeType.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentFeeTypeName").value(hasItem(DEFAULT_CORRESPONDENT_FEE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentFeeTypeShouldNotBeFound(String filter) throws Exception {
        restCorrespondentFeeTypeMockMvc.perform(get("/api/correspondent-fee-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentFeeType() throws Exception {
        // Get the correspondentFeeType
        restCorrespondentFeeTypeMockMvc.perform(get("/api/correspondent-fee-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentFeeType() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);
        int databaseSizeBeforeUpdate = correspondentFeeTypeRepository.findAll().size();

        // Update the correspondentFeeType
        CorrespondentFeeType updatedCorrespondentFeeType = correspondentFeeTypeRepository.findOne(correspondentFeeType.getId());
        // Disconnect from session so that the updates on updatedCorrespondentFeeType are not directly saved in db
        em.detach(updatedCorrespondentFeeType);
        updatedCorrespondentFeeType
            .correspondentFeeTypeName(UPDATED_CORRESPONDENT_FEE_TYPE_NAME)
            .sortNum(UPDATED_SORT_NUM);
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeMapper.toDto(updatedCorrespondentFeeType);

        restCorrespondentFeeTypeMockMvc.perform(put("/api/correspondent-fee-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentFeeType in the database
        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentFeeType testCorrespondentFeeType = correspondentFeeTypeList.get(correspondentFeeTypeList.size() - 1);
        assertThat(testCorrespondentFeeType.getCorrespondentFeeTypeName()).isEqualTo(UPDATED_CORRESPONDENT_FEE_TYPE_NAME);
        assertThat(testCorrespondentFeeType.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentFeeType() throws Exception {
        int databaseSizeBeforeUpdate = correspondentFeeTypeRepository.findAll().size();

        // Create the CorrespondentFeeType
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO = correspondentFeeTypeMapper.toDto(correspondentFeeType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentFeeTypeMockMvc.perform(put("/api/correspondent-fee-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentFeeType in the database
        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondentFeeType() throws Exception {
        // Initialize the database
        correspondentFeeTypeRepository.saveAndFlush(correspondentFeeType);
        int databaseSizeBeforeDelete = correspondentFeeTypeRepository.findAll().size();

        // Get the correspondentFeeType
        restCorrespondentFeeTypeMockMvc.perform(delete("/api/correspondent-fee-types/{id}", correspondentFeeType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentFeeType> correspondentFeeTypeList = correspondentFeeTypeRepository.findAll();
        assertThat(correspondentFeeTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentFeeType.class);
        CorrespondentFeeType correspondentFeeType1 = new CorrespondentFeeType();
        correspondentFeeType1.setId(1L);
        CorrespondentFeeType correspondentFeeType2 = new CorrespondentFeeType();
        correspondentFeeType2.setId(correspondentFeeType1.getId());
        assertThat(correspondentFeeType1).isEqualTo(correspondentFeeType2);
        correspondentFeeType2.setId(2L);
        assertThat(correspondentFeeType1).isNotEqualTo(correspondentFeeType2);
        correspondentFeeType1.setId(null);
        assertThat(correspondentFeeType1).isNotEqualTo(correspondentFeeType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentFeeTypeDTO.class);
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO1 = new CorrespondentFeeTypeDTO();
        correspondentFeeTypeDTO1.setId(1L);
        CorrespondentFeeTypeDTO correspondentFeeTypeDTO2 = new CorrespondentFeeTypeDTO();
        assertThat(correspondentFeeTypeDTO1).isNotEqualTo(correspondentFeeTypeDTO2);
        correspondentFeeTypeDTO2.setId(correspondentFeeTypeDTO1.getId());
        assertThat(correspondentFeeTypeDTO1).isEqualTo(correspondentFeeTypeDTO2);
        correspondentFeeTypeDTO2.setId(2L);
        assertThat(correspondentFeeTypeDTO1).isNotEqualTo(correspondentFeeTypeDTO2);
        correspondentFeeTypeDTO1.setId(null);
        assertThat(correspondentFeeTypeDTO1).isNotEqualTo(correspondentFeeTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentFeeTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentFeeTypeMapper.fromId(null)).isNull();
    }
}
