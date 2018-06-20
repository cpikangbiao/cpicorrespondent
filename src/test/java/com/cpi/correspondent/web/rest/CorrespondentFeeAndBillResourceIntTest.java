package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.CorrespondentFeeAndBill;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.domain.CorrespondentFee;
import com.cpi.correspondent.domain.CorrespondentBill;
import com.cpi.correspondent.repository.CorrespondentFeeAndBillRepository;
import com.cpi.correspondent.service.CorrespondentFeeAndBillService;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillDTO;
import com.cpi.correspondent.service.mapper.CorrespondentFeeAndBillMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.CorrespondentFeeAndBillCriteria;
import com.cpi.correspondent.service.CorrespondentFeeAndBillQueryService;

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
 * Test class for the CorrespondentFeeAndBillResource REST controller.
 *
 * @see CorrespondentFeeAndBillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class CorrespondentFeeAndBillResourceIntTest {

    @Autowired
    private CorrespondentFeeAndBillRepository correspondentFeeAndBillRepository;

    @Autowired
    private CorrespondentFeeAndBillMapper correspondentFeeAndBillMapper;

    @Autowired
    private CorrespondentFeeAndBillService correspondentFeeAndBillService;

    @Autowired
    private CorrespondentFeeAndBillQueryService correspondentFeeAndBillQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCorrespondentFeeAndBillMockMvc;

    private CorrespondentFeeAndBill correspondentFeeAndBill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CorrespondentFeeAndBillResource correspondentFeeAndBillResource = new CorrespondentFeeAndBillResource(correspondentFeeAndBillService, correspondentFeeAndBillQueryService);
        this.restCorrespondentFeeAndBillMockMvc = MockMvcBuilders.standaloneSetup(correspondentFeeAndBillResource)
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
    public static CorrespondentFeeAndBill createEntity(EntityManager em) {
        CorrespondentFeeAndBill correspondentFeeAndBill = new CorrespondentFeeAndBill();
        return correspondentFeeAndBill;
    }

    @Before
    public void initTest() {
        correspondentFeeAndBill = createEntity(em);
    }

    @Test
    @Transactional
    public void createCorrespondentFeeAndBill() throws Exception {
        int databaseSizeBeforeCreate = correspondentFeeAndBillRepository.findAll().size();

        // Create the CorrespondentFeeAndBill
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO = correspondentFeeAndBillMapper.toDto(correspondentFeeAndBill);
        restCorrespondentFeeAndBillMockMvc.perform(post("/api/correspondent-fee-and-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeAndBillDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentFeeAndBill in the database
        List<CorrespondentFeeAndBill> correspondentFeeAndBillList = correspondentFeeAndBillRepository.findAll();
        assertThat(correspondentFeeAndBillList).hasSize(databaseSizeBeforeCreate + 1);
        CorrespondentFeeAndBill testCorrespondentFeeAndBill = correspondentFeeAndBillList.get(correspondentFeeAndBillList.size() - 1);
    }

    @Test
    @Transactional
    public void createCorrespondentFeeAndBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = correspondentFeeAndBillRepository.findAll().size();

        // Create the CorrespondentFeeAndBill with an existing ID
        correspondentFeeAndBill.setId(1L);
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO = correspondentFeeAndBillMapper.toDto(correspondentFeeAndBill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorrespondentFeeAndBillMockMvc.perform(post("/api/correspondent-fee-and-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeAndBillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CorrespondentFeeAndBill in the database
        List<CorrespondentFeeAndBill> correspondentFeeAndBillList = correspondentFeeAndBillRepository.findAll();
        assertThat(correspondentFeeAndBillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeAndBills() throws Exception {
        // Initialize the database
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);

        // Get all the correspondentFeeAndBillList
        restCorrespondentFeeAndBillMockMvc.perform(get("/api/correspondent-fee-and-bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentFeeAndBill.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCorrespondentFeeAndBill() throws Exception {
        // Initialize the database
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);

        // Get the correspondentFeeAndBill
        restCorrespondentFeeAndBillMockMvc.perform(get("/api/correspondent-fee-and-bills/{id}", correspondentFeeAndBill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(correspondentFeeAndBill.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllCorrespondentFeeAndBillsByCorrespondentDebitBillIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentBill correspondentDebitBill = CorrespondentBillResourceIntTest.createEntity(em);
        em.persist(correspondentDebitBill);
        em.flush();
        correspondentFeeAndBill.setCorrespondentDebitBill(correspondentDebitBill);
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);
        Long correspondentDebitBillId = correspondentDebitBill.getId();

        // Get all the correspondentFeeAndBillList where correspondentDebitBill equals to correspondentDebitBillId
        defaultCorrespondentFeeAndBillShouldBeFound("correspondentDebitBillId.equals=" + correspondentDebitBillId);

        // Get all the correspondentFeeAndBillList where correspondentDebitBill equals to correspondentDebitBillId + 1
        defaultCorrespondentFeeAndBillShouldNotBeFound("correspondentDebitBillId.equals=" + (correspondentDebitBillId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentFeeAndBillsByCorrespondentFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentFee correspondentFee = CorrespondentFeeResourceIntTest.createEntity(em);
        em.persist(correspondentFee);
        em.flush();
        correspondentFeeAndBill.setCorrespondentFee(correspondentFee);
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);
        Long correspondentFeeId = correspondentFee.getId();

        // Get all the correspondentFeeAndBillList where correspondentFee equals to correspondentFeeId
        defaultCorrespondentFeeAndBillShouldBeFound("correspondentFeeId.equals=" + correspondentFeeId);

        // Get all the correspondentFeeAndBillList where correspondentFee equals to correspondentFeeId + 1
        defaultCorrespondentFeeAndBillShouldNotBeFound("correspondentFeeId.equals=" + (correspondentFeeId + 1));
    }


    @Test
    @Transactional
    public void getAllCorrespondentFeeAndBillsByCorrespondentCreditBillIsEqualToSomething() throws Exception {
        // Initialize the database
        CorrespondentBill correspondentCreditBill = CorrespondentBillResourceIntTest.createEntity(em);
        em.persist(correspondentCreditBill);
        em.flush();
        correspondentFeeAndBill.setCorrespondentCreditBill(correspondentCreditBill);
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);
        Long correspondentCreditBillId = correspondentCreditBill.getId();

        // Get all the correspondentFeeAndBillList where correspondentCreditBill equals to correspondentCreditBillId
        defaultCorrespondentFeeAndBillShouldBeFound("correspondentCreditBillId.equals=" + correspondentCreditBillId);

        // Get all the correspondentFeeAndBillList where correspondentCreditBill equals to correspondentCreditBillId + 1
        defaultCorrespondentFeeAndBillShouldNotBeFound("correspondentCreditBillId.equals=" + (correspondentCreditBillId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCorrespondentFeeAndBillShouldBeFound(String filter) throws Exception {
        restCorrespondentFeeAndBillMockMvc.perform(get("/api/correspondent-fee-and-bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(correspondentFeeAndBill.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCorrespondentFeeAndBillShouldNotBeFound(String filter) throws Exception {
        restCorrespondentFeeAndBillMockMvc.perform(get("/api/correspondent-fee-and-bills?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCorrespondentFeeAndBill() throws Exception {
        // Get the correspondentFeeAndBill
        restCorrespondentFeeAndBillMockMvc.perform(get("/api/correspondent-fee-and-bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCorrespondentFeeAndBill() throws Exception {
        // Initialize the database
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);
        int databaseSizeBeforeUpdate = correspondentFeeAndBillRepository.findAll().size();

        // Update the correspondentFeeAndBill
        CorrespondentFeeAndBill updatedCorrespondentFeeAndBill = correspondentFeeAndBillRepository.findOne(correspondentFeeAndBill.getId());
        // Disconnect from session so that the updates on updatedCorrespondentFeeAndBill are not directly saved in db
        em.detach(updatedCorrespondentFeeAndBill);
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO = correspondentFeeAndBillMapper.toDto(updatedCorrespondentFeeAndBill);

        restCorrespondentFeeAndBillMockMvc.perform(put("/api/correspondent-fee-and-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeAndBillDTO)))
            .andExpect(status().isOk());

        // Validate the CorrespondentFeeAndBill in the database
        List<CorrespondentFeeAndBill> correspondentFeeAndBillList = correspondentFeeAndBillRepository.findAll();
        assertThat(correspondentFeeAndBillList).hasSize(databaseSizeBeforeUpdate);
        CorrespondentFeeAndBill testCorrespondentFeeAndBill = correspondentFeeAndBillList.get(correspondentFeeAndBillList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCorrespondentFeeAndBill() throws Exception {
        int databaseSizeBeforeUpdate = correspondentFeeAndBillRepository.findAll().size();

        // Create the CorrespondentFeeAndBill
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO = correspondentFeeAndBillMapper.toDto(correspondentFeeAndBill);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCorrespondentFeeAndBillMockMvc.perform(put("/api/correspondent-fee-and-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(correspondentFeeAndBillDTO)))
            .andExpect(status().isCreated());

        // Validate the CorrespondentFeeAndBill in the database
        List<CorrespondentFeeAndBill> correspondentFeeAndBillList = correspondentFeeAndBillRepository.findAll();
        assertThat(correspondentFeeAndBillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCorrespondentFeeAndBill() throws Exception {
        // Initialize the database
        correspondentFeeAndBillRepository.saveAndFlush(correspondentFeeAndBill);
        int databaseSizeBeforeDelete = correspondentFeeAndBillRepository.findAll().size();

        // Get the correspondentFeeAndBill
        restCorrespondentFeeAndBillMockMvc.perform(delete("/api/correspondent-fee-and-bills/{id}", correspondentFeeAndBill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CorrespondentFeeAndBill> correspondentFeeAndBillList = correspondentFeeAndBillRepository.findAll();
        assertThat(correspondentFeeAndBillList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentFeeAndBill.class);
        CorrespondentFeeAndBill correspondentFeeAndBill1 = new CorrespondentFeeAndBill();
        correspondentFeeAndBill1.setId(1L);
        CorrespondentFeeAndBill correspondentFeeAndBill2 = new CorrespondentFeeAndBill();
        correspondentFeeAndBill2.setId(correspondentFeeAndBill1.getId());
        assertThat(correspondentFeeAndBill1).isEqualTo(correspondentFeeAndBill2);
        correspondentFeeAndBill2.setId(2L);
        assertThat(correspondentFeeAndBill1).isNotEqualTo(correspondentFeeAndBill2);
        correspondentFeeAndBill1.setId(null);
        assertThat(correspondentFeeAndBill1).isNotEqualTo(correspondentFeeAndBill2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CorrespondentFeeAndBillDTO.class);
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO1 = new CorrespondentFeeAndBillDTO();
        correspondentFeeAndBillDTO1.setId(1L);
        CorrespondentFeeAndBillDTO correspondentFeeAndBillDTO2 = new CorrespondentFeeAndBillDTO();
        assertThat(correspondentFeeAndBillDTO1).isNotEqualTo(correspondentFeeAndBillDTO2);
        correspondentFeeAndBillDTO2.setId(correspondentFeeAndBillDTO1.getId());
        assertThat(correspondentFeeAndBillDTO1).isEqualTo(correspondentFeeAndBillDTO2);
        correspondentFeeAndBillDTO2.setId(2L);
        assertThat(correspondentFeeAndBillDTO1).isNotEqualTo(correspondentFeeAndBillDTO2);
        correspondentFeeAndBillDTO1.setId(null);
        assertThat(correspondentFeeAndBillDTO1).isNotEqualTo(correspondentFeeAndBillDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(correspondentFeeAndBillMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(correspondentFeeAndBillMapper.fromId(null)).isNull();
    }
}
