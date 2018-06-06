package com.cpi.correspondent.web.rest;

import com.cpi.correspondent.CpicorrespondentApp;

import com.cpi.correspondent.config.SecurityBeanOverrideConfiguration;

import com.cpi.correspondent.domain.ClubPerson;
import com.cpi.correspondent.domain.Club;
import com.cpi.correspondent.repository.ClubPersonRepository;
import com.cpi.correspondent.service.ClubPersonService;
import com.cpi.correspondent.service.dto.ClubPersonDTO;
import com.cpi.correspondent.service.mapper.ClubPersonMapper;
import com.cpi.correspondent.web.rest.errors.ExceptionTranslator;
import com.cpi.correspondent.service.dto.ClubPersonCriteria;
import com.cpi.correspondent.service.ClubPersonQueryService;

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
 * Test class for the ClubPersonResource REST controller.
 *
 * @see ClubPersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CpicorrespondentApp.class, SecurityBeanOverrideConfiguration.class})
public class ClubPersonResourceIntTest {

    private static final String DEFAULT_CLUB_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLUB_PERSON_NAME = "BBBBBBBBBB";

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
    private ClubPersonRepository clubPersonRepository;

    @Autowired
    private ClubPersonMapper clubPersonMapper;

    @Autowired
    private ClubPersonService clubPersonService;

    @Autowired
    private ClubPersonQueryService clubPersonQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClubPersonMockMvc;

    private ClubPerson clubPerson;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClubPersonResource clubPersonResource = new ClubPersonResource(clubPersonService, clubPersonQueryService);
        this.restClubPersonMockMvc = MockMvcBuilders.standaloneSetup(clubPersonResource)
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
    public static ClubPerson createEntity(EntityManager em) {
        ClubPerson clubPerson = new ClubPerson()
            .clubPersonName(DEFAULT_CLUB_PERSON_NAME)
            .url(DEFAULT_URL)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .mobilePhone(DEFAULT_MOBILE_PHONE)
            .address(DEFAULT_ADDRESS)
            .zip(DEFAULT_ZIP)
            .remark(DEFAULT_REMARK);
        return clubPerson;
    }

    @Before
    public void initTest() {
        clubPerson = createEntity(em);
    }

    @Test
    @Transactional
    public void createClubPerson() throws Exception {
        int databaseSizeBeforeCreate = clubPersonRepository.findAll().size();

        // Create the ClubPerson
        ClubPersonDTO clubPersonDTO = clubPersonMapper.toDto(clubPerson);
        restClubPersonMockMvc.perform(post("/api/club-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the ClubPerson in the database
        List<ClubPerson> clubPersonList = clubPersonRepository.findAll();
        assertThat(clubPersonList).hasSize(databaseSizeBeforeCreate + 1);
        ClubPerson testClubPerson = clubPersonList.get(clubPersonList.size() - 1);
        assertThat(testClubPerson.getClubPersonName()).isEqualTo(DEFAULT_CLUB_PERSON_NAME);
        assertThat(testClubPerson.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testClubPerson.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testClubPerson.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClubPerson.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testClubPerson.getMobilePhone()).isEqualTo(DEFAULT_MOBILE_PHONE);
        assertThat(testClubPerson.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClubPerson.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testClubPerson.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    public void createClubPersonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clubPersonRepository.findAll().size();

        // Create the ClubPerson with an existing ID
        clubPerson.setId(1L);
        ClubPersonDTO clubPersonDTO = clubPersonMapper.toDto(clubPerson);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClubPersonMockMvc.perform(post("/api/club-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubPersonDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClubPerson in the database
        List<ClubPerson> clubPersonList = clubPersonRepository.findAll();
        assertThat(clubPersonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkClubPersonNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = clubPersonRepository.findAll().size();
        // set the field null
        clubPerson.setClubPersonName(null);

        // Create the ClubPerson, which fails.
        ClubPersonDTO clubPersonDTO = clubPersonMapper.toDto(clubPerson);

        restClubPersonMockMvc.perform(post("/api/club-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubPersonDTO)))
            .andExpect(status().isBadRequest());

        List<ClubPerson> clubPersonList = clubPersonRepository.findAll();
        assertThat(clubPersonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClubPeople() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList
        restClubPersonMockMvc.perform(get("/api/club-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clubPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].clubPersonName").value(hasItem(DEFAULT_CLUB_PERSON_NAME.toString())))
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
    public void getClubPerson() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get the clubPerson
        restClubPersonMockMvc.perform(get("/api/club-people/{id}", clubPerson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clubPerson.getId().intValue()))
            .andExpect(jsonPath("$.clubPersonName").value(DEFAULT_CLUB_PERSON_NAME.toString()))
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
    public void getAllClubPeopleByClubPersonNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where clubPersonName equals to DEFAULT_CLUB_PERSON_NAME
        defaultClubPersonShouldBeFound("clubPersonName.equals=" + DEFAULT_CLUB_PERSON_NAME);

        // Get all the clubPersonList where clubPersonName equals to UPDATED_CLUB_PERSON_NAME
        defaultClubPersonShouldNotBeFound("clubPersonName.equals=" + UPDATED_CLUB_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByClubPersonNameIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where clubPersonName in DEFAULT_CLUB_PERSON_NAME or UPDATED_CLUB_PERSON_NAME
        defaultClubPersonShouldBeFound("clubPersonName.in=" + DEFAULT_CLUB_PERSON_NAME + "," + UPDATED_CLUB_PERSON_NAME);

        // Get all the clubPersonList where clubPersonName equals to UPDATED_CLUB_PERSON_NAME
        defaultClubPersonShouldNotBeFound("clubPersonName.in=" + UPDATED_CLUB_PERSON_NAME);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByClubPersonNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where clubPersonName is not null
        defaultClubPersonShouldBeFound("clubPersonName.specified=true");

        // Get all the clubPersonList where clubPersonName is null
        defaultClubPersonShouldNotBeFound("clubPersonName.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where url equals to DEFAULT_URL
        defaultClubPersonShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the clubPersonList where url equals to UPDATED_URL
        defaultClubPersonShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where url in DEFAULT_URL or UPDATED_URL
        defaultClubPersonShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the clubPersonList where url equals to UPDATED_URL
        defaultClubPersonShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where url is not null
        defaultClubPersonShouldBeFound("url.specified=true");

        // Get all the clubPersonList where url is null
        defaultClubPersonShouldNotBeFound("url.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where email equals to DEFAULT_EMAIL
        defaultClubPersonShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the clubPersonList where email equals to UPDATED_EMAIL
        defaultClubPersonShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultClubPersonShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the clubPersonList where email equals to UPDATED_EMAIL
        defaultClubPersonShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where email is not null
        defaultClubPersonShouldBeFound("email.specified=true");

        // Get all the clubPersonList where email is null
        defaultClubPersonShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where phone equals to DEFAULT_PHONE
        defaultClubPersonShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the clubPersonList where phone equals to UPDATED_PHONE
        defaultClubPersonShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultClubPersonShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the clubPersonList where phone equals to UPDATED_PHONE
        defaultClubPersonShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where phone is not null
        defaultClubPersonShouldBeFound("phone.specified=true");

        // Get all the clubPersonList where phone is null
        defaultClubPersonShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where fax equals to DEFAULT_FAX
        defaultClubPersonShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the clubPersonList where fax equals to UPDATED_FAX
        defaultClubPersonShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultClubPersonShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the clubPersonList where fax equals to UPDATED_FAX
        defaultClubPersonShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where fax is not null
        defaultClubPersonShouldBeFound("fax.specified=true");

        // Get all the clubPersonList where fax is null
        defaultClubPersonShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByMobilePhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where mobilePhone equals to DEFAULT_MOBILE_PHONE
        defaultClubPersonShouldBeFound("mobilePhone.equals=" + DEFAULT_MOBILE_PHONE);

        // Get all the clubPersonList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultClubPersonShouldNotBeFound("mobilePhone.equals=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByMobilePhoneIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where mobilePhone in DEFAULT_MOBILE_PHONE or UPDATED_MOBILE_PHONE
        defaultClubPersonShouldBeFound("mobilePhone.in=" + DEFAULT_MOBILE_PHONE + "," + UPDATED_MOBILE_PHONE);

        // Get all the clubPersonList where mobilePhone equals to UPDATED_MOBILE_PHONE
        defaultClubPersonShouldNotBeFound("mobilePhone.in=" + UPDATED_MOBILE_PHONE);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByMobilePhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where mobilePhone is not null
        defaultClubPersonShouldBeFound("mobilePhone.specified=true");

        // Get all the clubPersonList where mobilePhone is null
        defaultClubPersonShouldNotBeFound("mobilePhone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where address equals to DEFAULT_ADDRESS
        defaultClubPersonShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the clubPersonList where address equals to UPDATED_ADDRESS
        defaultClubPersonShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultClubPersonShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the clubPersonList where address equals to UPDATED_ADDRESS
        defaultClubPersonShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where address is not null
        defaultClubPersonShouldBeFound("address.specified=true");

        // Get all the clubPersonList where address is null
        defaultClubPersonShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByZipIsEqualToSomething() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where zip equals to DEFAULT_ZIP
        defaultClubPersonShouldBeFound("zip.equals=" + DEFAULT_ZIP);

        // Get all the clubPersonList where zip equals to UPDATED_ZIP
        defaultClubPersonShouldNotBeFound("zip.equals=" + UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByZipIsInShouldWork() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where zip in DEFAULT_ZIP or UPDATED_ZIP
        defaultClubPersonShouldBeFound("zip.in=" + DEFAULT_ZIP + "," + UPDATED_ZIP);

        // Get all the clubPersonList where zip equals to UPDATED_ZIP
        defaultClubPersonShouldNotBeFound("zip.in=" + UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void getAllClubPeopleByZipIsNullOrNotNull() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);

        // Get all the clubPersonList where zip is not null
        defaultClubPersonShouldBeFound("zip.specified=true");

        // Get all the clubPersonList where zip is null
        defaultClubPersonShouldNotBeFound("zip.specified=false");
    }

    @Test
    @Transactional
    public void getAllClubPeopleByClubIsEqualToSomething() throws Exception {
        // Initialize the database
        Club club = ClubResourceIntTest.createEntity(em);
        em.persist(club);
        em.flush();
        clubPerson.setClub(club);
        clubPersonRepository.saveAndFlush(clubPerson);
        Long clubId = club.getId();

        // Get all the clubPersonList where club equals to clubId
        defaultClubPersonShouldBeFound("clubId.equals=" + clubId);

        // Get all the clubPersonList where club equals to clubId + 1
        defaultClubPersonShouldNotBeFound("clubId.equals=" + (clubId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClubPersonShouldBeFound(String filter) throws Exception {
        restClubPersonMockMvc.perform(get("/api/club-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clubPerson.getId().intValue())))
            .andExpect(jsonPath("$.[*].clubPersonName").value(hasItem(DEFAULT_CLUB_PERSON_NAME.toString())))
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
    private void defaultClubPersonShouldNotBeFound(String filter) throws Exception {
        restClubPersonMockMvc.perform(get("/api/club-people?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClubPerson() throws Exception {
        // Get the clubPerson
        restClubPersonMockMvc.perform(get("/api/club-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClubPerson() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);
        int databaseSizeBeforeUpdate = clubPersonRepository.findAll().size();

        // Update the clubPerson
        ClubPerson updatedClubPerson = clubPersonRepository.findOne(clubPerson.getId());
        // Disconnect from session so that the updates on updatedClubPerson are not directly saved in db
        em.detach(updatedClubPerson);
        updatedClubPerson
            .clubPersonName(UPDATED_CLUB_PERSON_NAME)
            .url(UPDATED_URL)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .mobilePhone(UPDATED_MOBILE_PHONE)
            .address(UPDATED_ADDRESS)
            .zip(UPDATED_ZIP)
            .remark(UPDATED_REMARK);
        ClubPersonDTO clubPersonDTO = clubPersonMapper.toDto(updatedClubPerson);

        restClubPersonMockMvc.perform(put("/api/club-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubPersonDTO)))
            .andExpect(status().isOk());

        // Validate the ClubPerson in the database
        List<ClubPerson> clubPersonList = clubPersonRepository.findAll();
        assertThat(clubPersonList).hasSize(databaseSizeBeforeUpdate);
        ClubPerson testClubPerson = clubPersonList.get(clubPersonList.size() - 1);
        assertThat(testClubPerson.getClubPersonName()).isEqualTo(UPDATED_CLUB_PERSON_NAME);
        assertThat(testClubPerson.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testClubPerson.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testClubPerson.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClubPerson.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testClubPerson.getMobilePhone()).isEqualTo(UPDATED_MOBILE_PHONE);
        assertThat(testClubPerson.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClubPerson.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testClubPerson.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    public void updateNonExistingClubPerson() throws Exception {
        int databaseSizeBeforeUpdate = clubPersonRepository.findAll().size();

        // Create the ClubPerson
        ClubPersonDTO clubPersonDTO = clubPersonMapper.toDto(clubPerson);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClubPersonMockMvc.perform(put("/api/club-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clubPersonDTO)))
            .andExpect(status().isCreated());

        // Validate the ClubPerson in the database
        List<ClubPerson> clubPersonList = clubPersonRepository.findAll();
        assertThat(clubPersonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClubPerson() throws Exception {
        // Initialize the database
        clubPersonRepository.saveAndFlush(clubPerson);
        int databaseSizeBeforeDelete = clubPersonRepository.findAll().size();

        // Get the clubPerson
        restClubPersonMockMvc.perform(delete("/api/club-people/{id}", clubPerson.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClubPerson> clubPersonList = clubPersonRepository.findAll();
        assertThat(clubPersonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClubPerson.class);
        ClubPerson clubPerson1 = new ClubPerson();
        clubPerson1.setId(1L);
        ClubPerson clubPerson2 = new ClubPerson();
        clubPerson2.setId(clubPerson1.getId());
        assertThat(clubPerson1).isEqualTo(clubPerson2);
        clubPerson2.setId(2L);
        assertThat(clubPerson1).isNotEqualTo(clubPerson2);
        clubPerson1.setId(null);
        assertThat(clubPerson1).isNotEqualTo(clubPerson2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClubPersonDTO.class);
        ClubPersonDTO clubPersonDTO1 = new ClubPersonDTO();
        clubPersonDTO1.setId(1L);
        ClubPersonDTO clubPersonDTO2 = new ClubPersonDTO();
        assertThat(clubPersonDTO1).isNotEqualTo(clubPersonDTO2);
        clubPersonDTO2.setId(clubPersonDTO1.getId());
        assertThat(clubPersonDTO1).isEqualTo(clubPersonDTO2);
        clubPersonDTO2.setId(2L);
        assertThat(clubPersonDTO1).isNotEqualTo(clubPersonDTO2);
        clubPersonDTO1.setId(null);
        assertThat(clubPersonDTO1).isNotEqualTo(clubPersonDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clubPersonMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clubPersonMapper.fromId(null)).isNull();
    }
}
