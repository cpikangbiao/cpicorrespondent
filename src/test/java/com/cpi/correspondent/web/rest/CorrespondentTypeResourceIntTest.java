package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CorrespondentType;
import com.cpi.correspondent.repository.CorrespondentTypeRepository;
import com.cpi.correspondent.service.CorrespondentTypeService;
import com.cpi.correspondent.service.dto.CorrespondentTypeDTO;
import com.cpi.correspondent.service.mapper.CorrespondentTypeMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentTypeCriteria;
import com.cpi.correspondent.service.CorrespondentTypeQueryService;

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
 * Test class for the CorrespondentTypeResource REST controller.
 *
 * @see CorrespondentTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentTypeResourceIntTest {

    private static final String DEFAULT_CORRESPONDENT_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CORRESPONDENT_TYPE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_NUM = 1;
    private static final Integer UPDATED_SORT_NUM = 2;

    @Autowired
    private CorrespondentTypeRepository correspondentTypeRepository;

    @Autowired
    private CorrespondentTypeMapper correspondentTypeMapper;

    @Autowired
    private CorrespondentTypeService correspondentTypeService;

    @Autowired
    private CorrespondentTypeQueryService correspondentTypeQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentTypeMockMvc;

    private CorrespondentType correspondentType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentTypeResource correspondentTypeResource = new CorrespondentTypeResource(correspondentTypeService, correspondentTypeQueryService);
        this.restCorrespondentTypeMockMvc = MockMvcBuilders.standaloneSetup(correspondentTypeResource)
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
    public static CorrespondentType createEntity(EntityManager em) {
        CorrespondentType correspondentType = new CorrespondentType()
            .correspondentTypeName(DEFAULT_CORRESPONDENT_TYPE_NAME)
            .sortNum(DEFAULT_SORT_NUM);
        return correspondentType;
    }

    @Before
    public void initTest() {
        correspondentType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentType() throws Exception {
        int databaseSizeBeforeCreate = correspondentTypeRepository.findAll().size();

        // Create the CorrespondentType
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeMapper.toDto(correspondentType);
        restCorrespondentTypeMockMvc.perform(post("/api/correspondent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentType in the database
        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentType testCorrespondentType = correspondentTypeList.get(correspondentTypeList.size() - 1);
        assertThat(testCorrespondentType.getCorrespondentTypeName()).isEqualTo(DEFAULT_CORRESPONDENT_TYPE_NAME);
        assertThat(testCorrespondentType.getSortNum()).isEqualTo(DEFAULT_SORT_NUM);
    }

    @Test
    @Transactional
    public void createCorrespondentTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentTypeRepository.findAll().size();

        // Create the CorrespondentType with an existing ID
        correspondentType.setId(1L);
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeMapper.toDto(correspondentType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentTypeMockMvc.perform(post("/api/correspondent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentType in the database
        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCorrespondentTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentTypeRepository.findAll().size();
        // set the field null
        correspondentType.setCorrespondentTypeName(null);

        // Create the CorrespondentType, which fails.
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeMapper.toDto(correspondentType);

        restCorrespondentTypeMockMvc.perform(post("/api/correspondent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSortNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = correspondentTypeRepository.findAll().size();
        // set the field null
        correspondentType.setSortNum(null);

        // Create the CorrespondentType, which fails.
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeMapper.toDto(correspondentType);

        restCorrespondentTypeMockMvc.perform(post("/api/correspondent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentTypeDTO)))
            .andExpect(status().isBadRequest());

        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypes() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList
        restCorrespondentTypeMockMvc.perform(get("/api/correspondent-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentTypeName").value(hasItem(DEFAULT_CORRESPONDENT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    @Test
    @Transactional
    public void getCorrespondentType() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get the correspondentType
        restCorrespondentTypeMockMvc.perform(get("/api/correspondent-types/{id}", correspondentType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentType.getId().intValue()))
            .andExpect(jsonPath("$.correspondentTypeName").value(DEFAULT_CORRESPONDENT_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.sortNum").value(DEFAULT_SORT_NUM));
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesByCorrespondentTypeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where correspondentTypeName equals to DEFAULT_CORRESPONDENT_TYPE_NAME
        defaultCorrespondentTypeShouldBeFound("correspondentTypeName.equals=" + DEFAULT_CORRESPONDENT_TYPE_NAME);

        // Get all the correspondentTypeList where correspondentTypeName equals to UPDATED_CORRESPONDENT_TYPE_NAME
        defaultCorrespondentTypeShouldNotBeFound("correspondentTypeName.equals=" + UPDATED_CORRESPONDENT_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesByCorrespondentTypeNameIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where correspondentTypeName in DEFAULT_CORRESPONDENT_TYPE_NAME or UPDATED_CORRESPONDENT_TYPE_NAME
        defaultCorrespondentTypeShouldBeFound("correspondentTypeName.in=" + DEFAULT_CORRESPONDENT_TYPE_NAME + "," + UPDATED_CORRESPONDENT_TYPE_NAME);

        // Get all the correspondentTypeList where correspondentTypeName equals to UPDATED_CORRESPONDENT_TYPE_NAME
        defaultCorrespondentTypeShouldNotBeFound("correspondentTypeName.in=" + UPDATED_CORRESPONDENT_TYPE_NAME);
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesByCorrespondentTypeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where correspondentTypeName is not null
        defaultCorrespondentTypeShouldBeFound("correspondentTypeName.specified=true");

        // Get all the correspondentTypeList where correspondentTypeName is null
        defaultCorrespondentTypeShouldNotBeFound("correspondentTypeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesBySortNumIsEqualToSomething() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where sortNum equals to DEFAULT_SORT_NUM
        defaultCorrespondentTypeShouldBeFound("sortNum.equals=" + DEFAULT_SORT_NUM);

        // Get all the correspondentTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultCorrespondentTypeShouldNotBeFound("sortNum.equals=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesBySortNumIsInShouldWork() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where sortNum in DEFAULT_SORT_NUM or UPDATED_SORT_NUM
        defaultCorrespondentTypeShouldBeFound("sortNum.in=" + DEFAULT_SORT_NUM + "," + UPDATED_SORT_NUM);

        // Get all the correspondentTypeList where sortNum equals to UPDATED_SORT_NUM
        defaultCorrespondentTypeShouldNotBeFound("sortNum.in=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesBySortNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where sortNum is not null
        defaultCorrespondentTypeShouldBeFound("sortNum.specified=true");

        // Get all the correspondentTypeList where sortNum is null
        defaultCorrespondentTypeShouldNotBeFound("sortNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesBySortNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where sortNum greater than or equals to DEFAULT_SORT_NUM
        defaultCorrespondentTypeShouldBeFound("sortNum.greaterOrEqualThan=" + DEFAULT_SORT_NUM);

        // Get all the correspondentTypeList where sortNum greater than or equals to UPDATED_SORT_NUM
        defaultCorrespondentTypeShouldNotBeFound("sortNum.greaterOrEqualThan=" + UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void getAllCorrespondentTypesBySortNumIsLessThanSomething() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);

        // Get all the correspondentTypeList where sortNum less than or equals to DEFAULT_SORT_NUM
        defaultCorrespondentTypeShouldNotBeFound("sortNum.lessThan=" + DEFAULT_SORT_NUM);

        // Get all the correspondentTypeList where sortNum less than or equals to UPDATED_SORT_NUM
        defaultCorrespondentTypeShouldBeFound("sortNum.lessThan=" + UPDATED_SORT_NUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentTypeShouldBeFound(String filter) throws Exception {
        restCorrespondentTypeMockMvc.perform(get("/api/correspondent-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentType.getId().intValue())))
            .andExpect(jsonPath("$.[*].correspondentTypeName").value(hasItem(DEFAULT_CORRESPONDENT_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].sortNum").value(hasItem(DEFAULT_SORT_NUM)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentTypeShouldNotBeFound(String filter) throws Exception {
        restCorrespondentTypeMockMvc.perform(get("/api/correspondent-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentType() throws Exception {
        // Get the correspondentType
        restCorrespondentTypeMockMvc.perform(get("/api/correspondent-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentType() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);
        int databaseSizeBeforeUpdate = correspondentTypeRepository.findAll().size();

        // Update the correspondentType
        CorrespondentType updatedCorrespondentType = correspondentTypeRepository.findOne(correspondentType.getId());
        // Disconnect from session so that the updates on updatedCorrespondentType are not directly saved in db
        em.detach(updatedCorrespondentType);
        updatedCorrespondentType
            .correspondentTypeName(UPDATED_CORRESPONDENT_TYPE_NAME)
            .sortNum(UPDATED_SORT_NUM);
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeMapper.toDto(updatedCorrespondentType);

        restCorrespondentTypeMockMvc.perform(put("/api/correspondent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentTypeDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentType in the database
        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentType testCorrespondentType = correspondentTypeList.get(correspondentTypeList.size() - 1);
        assertThat(testCorrespondentType.getCorrespondentTypeName()).isEqualTo(UPDATED_CORRESPONDENT_TYPE_NAME);
        assertThat(testCorrespondentType.getSortNum()).isEqualTo(UPDATED_SORT_NUM);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentType() throws Exception {
        int databaseSizeBeforeUpdate = correspondentTypeRepository.findAll().size();

        // Create the CorrespondentType
        CorrespondentTypeDTO correspondentTypeDTO = correspondentTypeMapper.toDto(correspondentType);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentTypeMockMvc.perform(put("/api/correspondent-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentType in the database
        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondentType() throws Exception {
        // Initialize the database
        correspondentTypeRepository.saveAndFlush(correspondentType);
        int databaseSizeBeforeDelete = correspondentTypeRepository.findAll().size();

        // Get the correspondentType
        restCorrespondentTypeMockMvc.perform(delete("/api/correspondent-types/{id}", correspondentType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentType> correspondentTypeList = correspondentTypeRepository.findAll();
        assertThat(correspondentTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentType.class);
        CorrespondentType correspondentType1 = new CorrespondentType();
        correspondentType1.setId(1L);
        CorrespondentType correspondentType2 = new CorrespondentType();
        correspondentType2.setId(correspondentType1.getId());
        assertThat(correspondentType1).isEqualTo(correspondentType2);
        correspondentType2.setId(2L);
        assertThat(correspondentType1).isNotEqualTo(correspondentType2);
        correspondentType1.setId(null);
        assertThat(correspondentType1).isNotEqualTo(correspondentType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentTypeDTO.class);
        CorrespondentTypeDTO correspondentTypeDTO1 = new CorrespondentTypeDTO();
        correspondentTypeDTO1.setId(1L);
        CorrespondentTypeDTO correspondentTypeDTO2 = new CorrespondentTypeDTO();
        assertThat(correspondentTypeDTO1).isNotEqualTo(correspondentTypeDTO2);
        correspondentTypeDTO2.setId(correspondentTypeDTO1.getId());
        assertThat(correspondentTypeDTO1).isEqualTo(correspondentTypeDTO2);
        correspondentTypeDTO2.setId(2L);
        assertThat(correspondentTypeDTO1).isNotEqualTo(correspondentTypeDTO2);
        correspondentTypeDTO1.setId(null);
        assertThat(correspondentTypeDTO1).isNotEqualTo(correspondentTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentTypeMapper.fromId(null)).isNull();
    }
}
