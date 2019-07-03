package com.raul.androidapps.softwareteststarling

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.network.StarlingApi
import com.raul.androidapps.softwareteststarling.network.responses.AccountsResponse
import com.raul.androidapps.softwareteststarling.network.responses.BalanceResponse
import com.raul.androidapps.softwareteststarling.network.responses.FeedsResponse
import com.raul.androidapps.softwareteststarling.network.responses.IdentifiersResponse
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import com.raul.androidapps.softwareteststarling.resources.ResourcesManager
import com.raul.androidapps.softwareteststarling.ui.NetworkViewModel
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
    lateinit var preferencesManager: PreferencesManager
    @Mock
    lateinit var resourcesManager: ResourcesManager
    @Mock
    lateinit var starlingApi: StarlingApi

    private lateinit var accountsResponse: AccountsResponse
    private lateinit var balanceResponse: BalanceResponse
    private lateinit var identifiersResponse: IdentifiersResponse
    private lateinit var feedsResponse: FeedsResponse
    private lateinit var viewModelTest: NetworkViewModel
    private val accountUid = "accountUid"
    private val categoryId = "catwegoryId"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModelTest =
            NetworkViewModel(persistenceManager = persistenceManager, networkServiceFactory = networkServiceFactory,
                preferencesManager = preferencesManager, resourcesManager = resourcesManager)

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

        val inputAccountFeedsResponse =
            this.javaClass.classLoader?.getResourceAsStream("feeds.json")
        val stringFeedsResponse = inputAccountFeedsResponse?.convertToString()
        feedsResponse = Gson().fromJson(stringFeedsResponse, FeedsResponse::class.java)

        Mockito.`when`(networkServiceFactory.getServiceInstance()).thenReturn(
            starlingApi
        )


    }

    @Test
    fun accountsResponseSuccess() {
        runBlocking {
            Mockito.`when`(starlingApi.getAccounts()).thenReturn(
                Response.success(accountsResponse)
            )
            val response = viewModelTest.getAccountsAsync()
            response.join()
            verify(persistenceManager, times(1)).saveAccounts(accountsResponse)
        }
    }

    @Test
    fun accountsResponseError() {
        runBlocking {
            Mockito.`when`(starlingApi.getAccounts()).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccountsAsync()
            response.join()
            verify(persistenceManager, times(0)).saveAccounts(accountsResponse)
        }
    }

    @Test
    fun accountBalanceResponseSuccess() {
        runBlocking {
            Mockito.`when`(starlingApi.getAccountBalance(accountUid)).thenReturn(
                Response.success(balanceResponse)
            )
            val response = viewModelTest.getAccountBalanceAsync(accountUid)
            response.join()
            verify(persistenceManager, times(1)).saveBalance(accountUid, balanceResponse)
        }
    }

    @Test
    fun accountBalanceResponseError() {
        runBlocking {
            Mockito.`when`(starlingApi.getAccountBalance(accountUid)).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccountBalanceAsync(accountUid)
            response.join()
            verify(persistenceManager, times(0)).saveBalance(accountUid, balanceResponse)
        }
    }

    @Test
    fun accountIdentifiersResponseSuccess() {
        runBlocking {
            Mockito.`when`(starlingApi.getAccountIdentifiers(accountUid)).thenReturn(
                Response.success(identifiersResponse)
            )
            val response = viewModelTest.getAccountIdentifiersAsync(accountUid)
            response.join()
            verify(persistenceManager, times(1)).saveIdentifiers(accountUid, identifiersResponse)
        }
    }

    @Test
    fun accountIdentifiersResponseError() {
        runBlocking {
            Mockito.`when`(starlingApi.getAccountIdentifiers(accountUid)).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccountIdentifiersAsync(accountUid)
            response.join()
            verify(persistenceManager, times(0)).saveIdentifiers(accountUid, identifiersResponse)
        }
    }

    @Test
    fun accountFeedsResponseSuccess() {
        runBlocking {
            Mockito.`when`(starlingApi.getFeeds(accountUid, categoryId)).thenReturn(
                Response.success(feedsResponse)
            )
            val response = viewModelTest.getAccountFeedsAsync(accountUid, categoryId)
            response.join()
            verify(persistenceManager, times(1)).saveFeeds(accountUid, feedsResponse)
        }
    }

    @Test
    fun accountFeedsResponseError() {
        runBlocking {
            Mockito.`when`(starlingApi.getFeeds(accountUid, categoryId)).thenReturn(
                Response.error(400, ResponseBody.create(null, ""))
            )
            val response = viewModelTest.getAccountFeedsAsync(accountUid, categoryId)
            response.join()
            verify(persistenceManager, times(0)).saveFeeds(accountUid, feedsResponse)
        }
    }

}
