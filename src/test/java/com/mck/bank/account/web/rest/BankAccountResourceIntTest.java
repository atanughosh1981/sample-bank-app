package com.mck.bank.account.web.rest;

import com.mck.bank.account.BankApp;

import com.mck.bank.account.domain.BankAccount;
import com.mck.bank.account.repository.BankAccountRepository;
import com.mck.bank.account.web.rest.errors.ExceptionTranslator;

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


import static com.mck.bank.account.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BankAccountResource REST controller.
 *
 * @see BankAccountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankApp.class)
public class BankAccountResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NUMBER = "BBBBBBBBBB";

    @Autowired
    private BankAccountRepository bankAccountRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBankAccountMockMvc;

    private BankAccount bankAccount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BankAccountResource bankAccountResource = new BankAccountResource(bankAccountRepository);
        this.restBankAccountMockMvc = MockMvcBuilders.standaloneSetup(bankAccountResource)
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
    public static BankAccount createEntity(EntityManager em) {
        BankAccount bankAccount = new BankAccount()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .panNumber(DEFAULT_PAN_NUMBER);
        return bankAccount;
    }

    @Before
    public void initTest() {
        bankAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createBankAccount() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // Create the BankAccount
        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isCreated());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate + 1);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testBankAccount.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testBankAccount.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testBankAccount.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testBankAccount.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBankAccount.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testBankAccount.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testBankAccount.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testBankAccount.getPanNumber()).isEqualTo(DEFAULT_PAN_NUMBER);
    }

    @Test
    @Transactional
    public void createBankAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bankAccountRepository.findAll().size();

        // Create the BankAccount with an existing ID
        bankAccount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setUsername(null);

        // Create the BankAccount, which fails.

        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setPassword(null);

        // Create the BankAccount, which fails.

        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setFirstName(null);

        // Create the BankAccount, which fails.

        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankAccountRepository.findAll().size();
        // set the field null
        bankAccount.setLastName(null);

        // Create the BankAccount, which fails.

        restBankAccountMockMvc.perform(post("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBankAccounts() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get all the bankAccountList
        restBankAccountMockMvc.perform(get("/api/bank-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER.toString())));
    }
    

    @Test
    @Transactional
    public void getBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", bankAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bankAccount.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.panNumber").value(DEFAULT_PAN_NUMBER.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBankAccount() throws Exception {
        // Get the bankAccount
        restBankAccountMockMvc.perform(get("/api/bank-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Update the bankAccount
        BankAccount updatedBankAccount = bankAccountRepository.findById(bankAccount.getId()).get();
        // Disconnect from session so that the updates on updatedBankAccount are not directly saved in db
        em.detach(updatedBankAccount);
        updatedBankAccount
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .panNumber(UPDATED_PAN_NUMBER);

        restBankAccountMockMvc.perform(put("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBankAccount)))
            .andExpect(status().isOk());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
        BankAccount testBankAccount = bankAccountList.get(bankAccountList.size() - 1);
        assertThat(testBankAccount.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testBankAccount.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testBankAccount.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testBankAccount.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testBankAccount.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBankAccount.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testBankAccount.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testBankAccount.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testBankAccount.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingBankAccount() throws Exception {
        int databaseSizeBeforeUpdate = bankAccountRepository.findAll().size();

        // Create the BankAccount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBankAccountMockMvc.perform(put("/api/bank-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bankAccount)))
            .andExpect(status().isBadRequest());

        // Validate the BankAccount in the database
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBankAccount() throws Exception {
        // Initialize the database
        bankAccountRepository.saveAndFlush(bankAccount);

        int databaseSizeBeforeDelete = bankAccountRepository.findAll().size();

        // Get the bankAccount
        restBankAccountMockMvc.perform(delete("/api/bank-accounts/{id}", bankAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BankAccount> bankAccountList = bankAccountRepository.findAll();
        assertThat(bankAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankAccount.class);
        BankAccount bankAccount1 = new BankAccount();
        bankAccount1.setId(1L);
        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setId(bankAccount1.getId());
        assertThat(bankAccount1).isEqualTo(bankAccount2);
        bankAccount2.setId(2L);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
        bankAccount1.setId(null);
        assertThat(bankAccount1).isNotEqualTo(bankAccount2);
    }
}
