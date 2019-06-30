package com.raul.androidapps.softwareteststarling

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.daos.AccountDao
import com.raul.androidapps.softwareteststarling.persistence.daos.BalanceDao
import com.raul.androidapps.softwareteststarling.persistence.daos.IdentifiersDao
import com.raul.androidapps.softwareteststarling.persistence.databases.StarlingDatabase
import com.raul.androidapps.softwareteststarling.persistence.entities.AccountEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.BalanceEntity
import com.raul.androidapps.softwareteststarling.persistence.entities.IdentifiersEntity
import com.raul.androidapps.softwareteststarling.security.Encryption
import com.raul.androidapps.softwareteststarling.utils.AssetFileUtil
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class LocalDBTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: StarlingDatabase
    private lateinit var accountDao: AccountDao
    private lateinit var identifiersDao: IdentifiersDao
    private lateinit var balanceDao: BalanceDao

    private lateinit var accountsResponse: AccountsResponse
    private lateinit var balanceResponse: BalanceResponse
    private lateinit var identifiersResponse: IdentifiersResponse
    @Mock
    lateinit var encryption: Encryption

    @Throws(Exception::class)
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            StarlingDatabase::class.java
        ).allowMainThreadQueries().build() // allowing main thread queries, just for testing

        accountDao = database.accountDao()
        identifiersDao = database.identifiersDao()
        balanceDao = database.balanceDao()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        initDb()

        val assetsLoader = AssetFileUtil(InstrumentationRegistry.getInstrumentation().context)
        val stringAccountsResponse = assetsLoader.loadJSONFromAsset("accounts.json")
        accountsResponse = Gson().fromJson(stringAccountsResponse, AccountsResponse::class.java)

        val stringBalanceResponse = assetsLoader.loadJSONFromAsset("account_balance.json")
        balanceResponse = Gson().fromJson(stringBalanceResponse, BalanceResponse::class.java)

        val stringIdentifiersResponse = assetsLoader.loadJSONFromAsset("account_identifiers.json")
        identifiersResponse =
            Gson().fromJson(stringIdentifiersResponse, IdentifiersResponse::class.java)

        Mockito.`when`(encryption.encryptString(anyString(), anyString()))
            .thenAnswer { i ->
                i.getArgument<String>(0)
            }
        Mockito.`when`(encryption.decryptString(anyString(), anyString()))
            .thenAnswer { i ->
                i.getArgument<String>(0)
            }


    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(InterruptedException::class)
    fun getAccountEmpty() {
        runBlocking {
            val list = accountDao.getAccounts()
            assertEquals(list.getValueBlocking()?.size, 0)
        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun getAccount() {
        runBlocking {
            accountDao.insert(accountsResponse.accounts.map { AccountEntity.fromAccountResponse(it) })
            val list = accountDao.getAccounts().getValueBlocking()
            assertTrue(list?.size == 1)
            assertTrue(list?.firstOrNull()?.accountUid == accountsResponse.accounts.firstOrNull()?.accountUid)

        }
    }

    @Test
    @Throws(InterruptedException::class)
    fun getAccountWithAllInfo() {//todo transactions
        runBlocking {
            val accountUid = accountsResponse.accounts.firstOrNull()?.accountUid
            accountDao.insert(accountsResponse.accounts.map { AccountEntity.fromAccountResponse(it) })
            identifiersDao.insert(
                IdentifiersEntity.fromAccountIdentifierUnencrypted(
                    accountUid!!,
                    identifiersResponse,
                    encryption
                )
            )
            balanceDao.insert(
                BalanceEntity.fromAccountBalance(
                    accountUid,
                    balanceResponse
                )
            )
            val list = accountDao.getAccountsWithAllInfo().getValueBlocking()
            if (list.isNullOrEmpty()) {
                assertTrue(false)
            }
            list?.firstOrNull()?.apply {
                val accountFromDb = account.toAccountPojo()
                val identifiersFromDb =
                    identifiers.map { it.toAccountIdentifierUnencrypted(encryption) }
                val balanceFromDb = balance.map { it.toAccountBalance() }
                assertTrue(list.size == 1)
                assertTrue(accountFromDb == accountsResponse.accounts.firstOrNull())
                assertTrue(identifiersFromDb.firstOrNull() == identifiersResponse)
                assertTrue(balanceFromDb.firstOrNull() == balanceResponse)
            }

        }
    }

}