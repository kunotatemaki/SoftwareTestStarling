package com.raul.androidapps.softwareteststarling

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.network.StarlingApi
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.ui.NetworkViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response


class NetworkViewModelTest {

    @Mock
    lateinit var persistenceManager: PersistenceManager
    @Mock
    lateinit var networkServiceFactory: NetworkServiceFactory
    @Mock
    lateinit var starlingApi: StarlingApi

    lateinit var accountsResponse: AccountsResponse
    lateinit var balanceResponse: BalanceResponse
    lateinit var identifiersResponse: IdentifiersResponse
    lateinit var viewModelTest: NetworkViewModel
    private val accountUid = "accountUid"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModelTest =
            NetworkViewModel(persistenceManager = persistenceManager, networkServiceFactory = networkServiceFactory)

        val inputAccountsResponse = this.javaClass.classLoader?.getResourceAsStream("accounts.json")
        val stringAccountsResponse = inputAccountsResponse?.convertToString() ?: ""
        accountsResponse = Gson().fromJson(stringAccountsResponse, AccountsResponse::class.java)

        val inputAccountBalanceResponse = this.javaClass.classLoader?.getResourceAsStream("account_balance.json")
        val stringBalanceResponse = inputAccountBalanceResponse?.convertToString()
        balanceResponse = Gson().fromJson(stringBalanceResponse, BalanceResponse::class.java)

        val inputAccountIdentifiersResponse =
            this.javaClass.classLoader?.getResourceAsStream("account_identifiers.json")
        val stringIdentifiersResponse = inputAccountIdentifiersResponse?.convertToString()
        identifiersResponse = Gson().fromJson(stringIdentifiersResponse, IdentifiersResponse::class.java)

        Mockito.`when`(networkServiceFactory.getServiceInstance()).thenReturn(
            starlingApi
        )


    }

    @Test
    fun accountsResponseSuccess() {
        runBlocking(Dispatchers.Default) {
            Mockito.`when`(starlingApi.getAccounts()).thenReturn(
                Response.success(accountsResponse)
            )
            val response = viewModelTest.getAccounts()
            response.join()
            verify(persistenceManager, times(1)).saveAccounts(accountsResponse)
        }
    }

    @Test
    fun accountsResponseError() {
        runBlocking(Dispatchers.Default) {
            Mockito.`when`(starlingApi.getAccounts()).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccounts()
            response.join()
            verify(persistenceManager, times(0)).saveAccounts(accountsResponse)
        }
    }

    @Test
    fun accountBalanceResponseSuccess() {
        runBlocking(Dispatchers.Default) {
            Mockito.`when`(starlingApi.getAccountBalance(accountUid)).thenReturn(
                Response.success(balanceResponse)
            )
            val response = viewModelTest.getAccountBalance(accountUid)
            response.join()
            verify(persistenceManager, times(1)).saveBalance(accountUid, balanceResponse)
        }
    }

    @Test
    fun accountBalanceResponseError() {
        runBlocking(Dispatchers.Default) {
            Mockito.`when`(starlingApi.getAccountBalance(accountUid)).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccountBalance(accountUid)
            response.join()
            verify(persistenceManager, times(0)).saveBalance(accountUid, balanceResponse)
        }
    }

    @Test
    fun accountIdentifiersResponseSuccess() {
        runBlocking(Dispatchers.Default) {
            Mockito.`when`(starlingApi.getAccountIdentifiers(accountUid)).thenReturn(
                Response.success(identifiersResponse)
            )
            val response = viewModelTest.getAccountIdentifiers(accountUid)
            response.join()
            verify(persistenceManager, times(1)).saveIdentifiers(accountUid, identifiersResponse)
        }
    }

    @Test
    fun accountIdentifiersResponseError() {
        runBlocking(Dispatchers.Default) {
            Mockito.`when`(starlingApi.getAccountIdentifiers(accountUid)).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccountIdentifiers(accountUid)
            response.join()
            verify(persistenceManager, times(0)).saveIdentifiers(accountUid, identifiersResponse)
        }
    }

}
