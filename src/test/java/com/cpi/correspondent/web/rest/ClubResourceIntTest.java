package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.Club;
import com.cpi.correspondent.repository.ClubRepository;
import com.cpi.correspondent.service.ClubService;
import com.cpi.correspondent.service.dto.ClubDTO;
import com.cpi.correspondent.service.mapper.ClubMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.ClubCriteria;
import com.cpi.correspondent.service.ClubQueryService;

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
import java.util.List;

import static com.cpi.correspondent.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClubResource REST controller.
 *
 * @see ClubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class ClubResourceIntTest {

    private static final String DEFAULT_CLUB_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLUB_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private ClubService clubService;

    @Autowired
    private ClubQueryService clubQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClubMockMvc;

    private Club club;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClubResource clubResource = new ClubResource(clubService, clubQueryService);
        this.restClubMockMvc = MockMvcBuilders.standaloneSetup(clubResource)
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
    public static Club createEntity(EntityManager em) {
        Club club = new Club()
            .clubName(DEFAULT_CLUB_NAME)
            .url(DEFAULT_URL)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .address(DEFAULT_ADDRESS)
            .zip(DEFAULT_ZIP)
            .remark(DEFAULT_REMARK);
        return club;
    }

    @Before
    public void initTest() {
        club = createEntity(em);
    }

    @Test
    @Transactional
    public void createClub() throws Exception {
        int databaseSizeBeforeCreate = clubRepository.findAll().size();

        // Create the Club
        ClubDTO clubDTO = clubMapper.toDto(club);
        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
            .andExpect(status().isCreated());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeCreate + 1);
        Club testClub = clubList.get(clubList.size() - 1);
        assertThat(testClub.getClubName()).isEqualTo(DEFAULT_CLUB_NAME);
        assertThat(testClub.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testClub.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClub.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClub.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testClub.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testClub.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClub.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testClub.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createClubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clubRepository.findAll().size();

        // Create the Club with an existing ID
        club.setId(1L);
        ClubDTO clubDTO = clubMapper.toDto(club);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClubNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubRepository.findAll().size();
        // set the field null
        club.setClubName(null);

        // Create the Club, which fails.
        ClubDTO clubDTO = clubMapper.toDto(club);

        restClubMockMvc.perform(post("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
            .andExpect(status().isBadRequest());

        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClubs() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList
        restClubMockMvc.perform(get("/api/clubs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(club.getId().intValue())))
            .andExpect(jsonPath("$.[*].clubName").value(hasItem(DEFAULT_CLUB_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    @Test
    @Transactional
    public void getClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", club.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(club.getId().intValue()))
            .andExpect(jsonPath("$.clubName").value(DEFAULT_CLUB_NAME.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.mobilePhone").value(DEFAULT_MOBILE_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK.toString()));
    }

    @Test
    @Transactional
    public void getAllClubsByClubNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where clubName equals to DEFAULT_CLUB_NAME
        defaultClubShouldBeFound("clubName.equals=" + DEFAULT_CLUB_NAME);

        // Get all the clubList where clubName equals to UPDATED_CLUB_NAME
        defaultClubShouldNotBeFound("clubName.equals=" + UPDATED_CLUB_NAME);
    }

    @Test
    @Transactional
    public void getAllClubsByClubNameIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where clubName in DEFAULT_CLUB_NAME or UPDATED_CLUB_NAME
        defaultClubShouldBeFound("clubName.in=" + DEFAULT_CLUB_NAME + "," + UPDATED_CLUB_NAME);

        // Get all the clubList where clubName equals to UPDATED_CLUB_NAME
        defaultClubShouldNotBeFound("clubName.in=" + UPDATED_CLUB_NAME);
    }

    @Test
    @Transactional
    public void getAllClubsByClubNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where clubName is not null
        defaultClubShouldBeFound("clubName.specified=true");

        // Get all the clubList where clubName is null
        defaultClubShouldNotBeFound("clubName.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where url equals to DEFAULT_URL
        defaultClubShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the clubList where url equals to UPDATED_URL
        defaultClubShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllClubsByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where url in DEFAULT_URL or UPDATED_URL
        defaultClubShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the clubList where url equals to UPDATED_URL
        defaultClubShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllClubsByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where url is not null
        defaultClubShouldBeFound("url.specified=true");

        // Get all the clubList where url is null
        defaultClubShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where email equals to DEFAULT_EMAIL
        defaultClubShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the clubList where email equals to UPDATED_EMAIL
        defaultClubShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClubsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultClubShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the clubList where email equals to UPDATED_EMAIL
        defaultClubShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClubsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where email is not null
        defaultClubShouldBeFound("email.specified=true");

        // Get all the clubList where email is null
        defaultClubShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where phone equals to DEFAULT_PHONE
        defaultClubShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the clubList where phone equals to UPDATED_PHONE
        defaultClubShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultClubShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the clubList where phone equals to UPDATED_PHONE
        defaultClubShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where phone is not null
        defaultClubShouldBeFound("phone.specified=true");

        // Get all the clubList where phone is null
        defaultClubShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where fax equals to DEFAULT_FAX
        defaultClubShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the clubList where fax equals to UPDATED_FAX
        defaultClubShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClubsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultClubShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the clubList where fax equals to UPDATED_FAX
        defaultClubShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClubsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where fax is not null
        defaultClubShouldBeFound("fax.specified=true");

        // Get all the clubList where fax is null
        defaultClubShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByMobilePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where mobilePhone equals to DEFAULT_MOBILE_PHONE
        defaultClubShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

        // Get all the clubList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultClubShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubsByMobilePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where mobilePhone in DEFAULT_MOBILE_PHONE or UPDATED_MOBILE_PHONE
        defaultClubShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

        // Get all the clubList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultClubShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubsByMobilePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where mobilePhone is not null
        defaultClubShouldBeFound("mobilePhone.specified=true");

        // Get all the clubList where mobilePhone is null
        defaultClubShouldNotBeFound("mobilePhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where address equals to DEFAULT_ADDRESS
        defaultClubShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the clubList where address equals to UPDATED_ADDRESS
        defaultClubShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClubsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultClubShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the clubList where address equals to UPDATED_ADDRESS
        defaultClubShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClubsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where address is not null
        defaultClubShouldBeFound("address.specified=true");

        // Get all the clubList where address is null
        defaultClubShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubsByZipIsEqualToSomething() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where zip equals to DEFAULT_ZIP
        defaultClubShouldBeFound("zip.equals=" + DEFAULT_ZIP);

        // Get all the clubList where zip equals to UPDATED_ZIP
        defaultClubShouldNotBeFound("zip.equals=" + UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void getAllClubsByZipIsInShouldWork() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where zip in DEFAULT_ZIP or UPDATED_ZIP
        defaultClubShouldBeFound("zip.in=" + DEFAULT_ZIP + "," + UPDATED_ZIP);

        // Get all the clubList where zip equals to UPDATED_ZIP
        defaultClubShouldNotBeFound("zip.in=" + UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void getAllClubsByZipIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);

        // Get all the clubList where zip is not null
        defaultClubShouldBeFound("zip.specified=true");

        // Get all the clubList where zip is null
        defaultClubShouldNotBeFound("zip.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClubShouldBeFound(String filter) throws Exception {
        restClubMockMvc.perform(get("/api/clubs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(club.getId().intValue())))
            .andExpect(jsonPath("$.[*].clubName").value(hasItem(DEFAULT_CLUB_NAME.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].mobilePhone").value(hasItem(DEFAULT_MOBILE_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClubShouldNotBeFound(String filter) throws Exception {
        restClubMockMvc.perform(get("/api/clubs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClub() throws Exception {
        // Get the club
        restClubMockMvc.perform(get("/api/clubs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);
        int databaseSizeBeforeUpdate = clubRepository.findAll().size();

        // Update the club
        Club updatedClub = clubRepository.findOne(club.getId());
        // Disconnect from session so that the updates on updatedClub are not directly saved in db
        em.detach(updatedClub);
        updatedClub
            .clubName(UPDATED_CLUB_NAME)
            .url(UPDATED_URL)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .address(UPDATED_ADDRESS)
            .zip(UPDATED_ZIP)
            .remark(UPDATED_REMARK);
        ClubDTO clubDTO = clubMapper.toDto(updatedClub);

        restClubMockMvc.perform(put("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
            .andExpect(status().isOk());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeUpdate);
        Club testClub = clubList.get(clubList.size() - 1);
        assertThat(testClub.getClubName()).isEqualTo(UPDATED_CLUB_NAME);
        assertThat(testClub.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testClub.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClub.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClub.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testClub.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testClub.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClub.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testClub.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingClub() throws Exception {
        int databaseSizeBeforeUpdate = clubRepository.findAll().size();

        // Create the Club
        ClubDTO clubDTO = clubMapper.toDto(club);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClubMockMvc.perform(put("/api/clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubDTO)))
            .andExpect(status().isCreated());

        // Validate the Club in the database
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClub() throws Exception {
        // Initialize the database
        clubRepository.saveAndFlush(club);
        int databaseSizeBeforeDelete = clubRepository.findAll().size();

        // Get the club
        restClubMockMvc.perform(delete("/api/clubs/{id}", club.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Club> clubList = clubRepository.findAll();
        assertThat(clubList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Club.class);
        Club club1 = new Club();
        club1.setId(1L);
        Club club2 = new Club();
        club2.setId(club1.getId());
        assertThat(club1).isEqualTo(club2);
        club2.setId(2L);
        assertThat(club1).isNotEqualTo(club2);
        club1.setId(null);
        assertThat(club1).isNotEqualTo(club2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClubDTO.class);
        ClubDTO clubDTO1 = new ClubDTO();
        clubDTO1.setId(1L);
        ClubDTO clubDTO2 = new ClubDTO();
        assertThat(clubDTO1).isNotEqualTo(clubDTO2);
        clubDTO2.setId(clubDTO1.getId());
        assertThat(clubDTO1).isEqualTo(clubDTO2);
        clubDTO2.setId(2L);
        assertThat(clubDTO1).isNotEqualTo(clubDTO2);
        clubDTO1.setId(null);
        assertThat(clubDTO1).isNotEqualTo(clubDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clubMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clubMapper.fromId(null)).isNull();
    }
}
