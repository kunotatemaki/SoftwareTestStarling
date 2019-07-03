package com.raul.androidapps.softwareteststarling

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.raul.androidapps.softwareteststarling.model.Goal
import com.raul.androidapps.softwareteststarling.model.Money
import com.raul.androidapps.softwareteststarling.model.SavingBody
import com.raul.androidapps.softwareteststarling.model.TransferBody
import com.raul.androidapps.softwareteststarling.network.NetworkServiceFactory
import com.raul.androidapps.softwareteststarling.network.StarlingApi
import com.raul.androidapps.softwareteststarling.network.responses.*
import com.raul.androidapps.softwareteststarling.persistence.PersistenceManager
import com.raul.androidapps.softwareteststarling.preferences.PreferencesManager
import com.raul.androidapps.softwareteststarling.resources.ResourcesManager
import com.raul.androidapps.softwareteststarling.ui.NetworkViewModel
import com.raul.androidapps.softwareteststarling.utils.AppConstants
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
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
    private lateinit var createGoalResponse: CreateGoalResponse
    private lateinit var createGoalResponseFail: CreateGoalResponse
    private lateinit var createTransferResponse: CreateTransferResponse
    private lateinit var createTransferResponseFail: CreateTransferResponse
    private lateinit var saveToGoalResponse: SaveToGoalResponse
    private lateinit var saveToGoalResponseFail: SaveToGoalResponse
    private lateinit var viewModelTest: NetworkViewModel
    private val accountUid = "accountUid"
    private val categoryId = "categoryId"
    private val listOfFeedIds = listOf("feed1", "feed2")
    private val amountToSave: Long = 80
    private val currency: String = "GBP"
    private val errorText: String = "error text"

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModelTest =
            NetworkViewModel(
                persistenceManager = persistenceManager,
                networkServiceFactory = networkServiceFactory,
                preferencesManager = preferencesManager,
                resourcesManager = resourcesManager
            )

        val inputAccountsResponse = this.javaClass.classLoader?.getResourceAsStream("accounts.json")
        val stringAccountsResponse = inputAccountsResponse?.convertToString() ?: ""
        accountsResponse = Gson().fromJson(stringAccountsResponse, AccountsResponse::class.java)

        val inputAccountBalanceResponse =
            this.javaClass.classLoader?.getResourceAsStream("account_balance.json")
        val stringBalanceResponse = inputAccountBalanceResponse?.convertToString()
        balanceResponse = Gson().fromJson(stringBalanceResponse, BalanceResponse::class.java)

        val inputAccountIdentifiersResponse =
            this.javaClass.classLoader?.getResourceAsStream("account_identifiers.json")
        val stringIdentifiersResponse = inputAccountIdentifiersResponse?.convertToString()
        identifiersResponse =
            Gson().fromJson(stringIdentifiersResponse, IdentifiersResponse::class.java)

        val inputAccountFeedsResponse =
            this.javaClass.classLoader?.getResourceAsStream("feeds.json")
        val stringFeedsResponse = inputAccountFeedsResponse?.convertToString()
        feedsResponse = Gson().fromJson(stringFeedsResponse, FeedsResponse::class.java)

        val inputCreateGoalResponse =
            this.javaClass.classLoader?.getResourceAsStream("create_goal.json")
        val stringCreateGoalResponse = inputCreateGoalResponse?.convertToString()
        createGoalResponse =
            Gson().fromJson(stringCreateGoalResponse, CreateGoalResponse::class.java)

        val inputCreateGoalResponseFail =
            this.javaClass.classLoader?.getResourceAsStream("create_goal_fail.json")
        val stringCreateGoalResponseFail = inputCreateGoalResponseFail?.convertToString()
        createGoalResponseFail =
            Gson().fromJson(stringCreateGoalResponseFail, CreateGoalResponse::class.java)

        val inputCreateTransferResponse =
            this.javaClass.classLoader?.getResourceAsStream("create_transfer.json")
        val stringCreateTransferResponse = inputCreateTransferResponse?.convertToString()
        createTransferResponse =
            Gson().fromJson(stringCreateTransferResponse, CreateTransferResponse::class.java)

        val inputCreateTransferResponseFail =
            this.javaClass.classLoader?.getResourceAsStream("create_transfer_fail.json")
        val stringCreateTransferResponseFail = inputCreateTransferResponseFail?.convertToString()
        createTransferResponseFail =
            Gson().fromJson(stringCreateTransferResponseFail, CreateTransferResponse::class.java)

        val inputSaveToGoalResponse =
            this.javaClass.classLoader?.getResourceAsStream("save_to_goal.json")
        val stringSaveToGoalResponse = inputSaveToGoalResponse?.convertToString()
        saveToGoalResponse =
            Gson().fromJson(stringSaveToGoalResponse, SaveToGoalResponse::class.java)

        val inputSaveToGoalResponseFail =
            this.javaClass.classLoader?.getResourceAsStream("save_to_goal_fail.json")
        val stringSaveToGoalResponseFail = inputSaveToGoalResponseFail?.convertToString()
        saveToGoalResponseFail =
            Gson().fromJson(stringSaveToGoalResponseFail, SaveToGoalResponse::class.java)


        Mockito.`when`(networkServiceFactory.getServiceInstance()).thenReturn(
            starlingApi
        )

        Mockito.`when`(resourcesManager.getString(R.string.error_sending_goal))
            .thenReturn(errorText)

    }

    private fun mockGoalInPreferences() {
        Mockito.`when`(preferencesManager.getStringFromPreferences(AppConstants.GOAL_ID))
            .thenReturn(createGoalResponse.savingsGoalUid)
    }

    private fun mockGoalNotInPreferences() {
        Mockito.`when`(preferencesManager.getStringFromPreferences(AppConstants.GOAL_ID))
            .thenReturn("")
    }

    private suspend fun mockCreateGoalFailCallback() {
        Mockito.`when`(starlingApi.createGoal(accountUid, Goal())).thenReturn(
            Response.error(400, ResponseBody.create(null, ""))
        )
    }

    private suspend fun mockCreateGoalFailResponseField() {
        Mockito.`when`(starlingApi.createGoal(accountUid, Goal())).thenReturn(
            Response.success(createGoalResponseFail)
        )
    }

    private suspend fun mockCreateGoalSuccess() {
        Mockito.`when`(starlingApi.createGoal(accountUid, Goal())).thenReturn(
            Response.success(createGoalResponse)
        )
    }

    private suspend fun mockCreateTransferFailCallback() {
        Mockito.`when`(
            starlingApi.createTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid,
                TransferBody(currency, amountToSave)
            )
        ).thenReturn(
            Response.error(400, ResponseBody.create(null, ""))
        )
    }

    private suspend fun mockCreateTransferFailResponseField() {
        Mockito.`when`(
            starlingApi.createTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid,
                TransferBody(currency, amountToSave)
            )
        ).thenReturn(
            Response.success(createTransferResponseFail)
        )
    }

    private suspend fun mockCreateTransferSuccess() {
        Mockito.`when`(
            starlingApi.createTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid,
                TransferBody(currency, amountToSave)
            )
        ).thenReturn(
            Response.success(createTransferResponse)
        )
    }

    private suspend fun mockSaveToGoalFailCallback() {
        Mockito.`when`(
            starlingApi.saveToGoal(
                accountUid,
                createGoalResponse.savingsGoalUid,
                createTransferResponse.transferUid,
                SavingBody(
                    Money(currency, amountToSave)
                )
            )
        ).thenReturn(
            Response.error(400, ResponseBody.create(null, ""))
        )
    }

    private suspend fun mockSaveToGoalFailResponseField() {
        Mockito.`when`(
            starlingApi.saveToGoal(
                accountUid,
                createGoalResponse.savingsGoalUid,
                createTransferResponse.transferUid,
                SavingBody(
                    Money(currency, amountToSave)
                )
            )
        ).thenReturn(
            Response.success(saveToGoalResponseFail)
        )
    }

    private suspend fun mockSaveToGoalSuccess() {
        Mockito.`when`(
            starlingApi.saveToGoal(
                accountUid,
                createGoalResponse.savingsGoalUid,
                createTransferResponse.transferUid,
                SavingBody(
                    Money(currency, amountToSave)
                )
            )
        ).thenReturn(
            Response.success(saveToGoalResponse)
        )
    }

    private suspend fun mockDeleteTransfer() {
        Mockito.`when`(
            starlingApi.deleteTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid
            )
        ).thenReturn(
            Response.success(null)
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

    @Test
    fun noGoalInPreferencesAndCreateOne() {
        runBlocking {
            mockGoalNotInPreferences()
            mockCreateGoalFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(1)).createGoal(
                accountUid,
                Goal()
            )

            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

    @Test
    fun goalInPreferencesAndNotCreateOne() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(0)).createGoal(
                accountUid,
                Goal()
            )

            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

    @Test
    fun createGoalResponseNotSuccessful() {
        runBlocking {
            mockGoalNotInPreferences()
            mockCreateGoalFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(0)).createTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid,
                TransferBody(currency, amountToSave)
            )
            Assert.assertEquals(
                viewModelTest.getNetworkError().getValueBlockingForUnitTests(),
                errorText
            )
            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

    @Test
    fun createGoalResponseNotSuccessfulInField() {
        runBlocking {
            mockGoalNotInPreferences()
            mockCreateGoalFailResponseField()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(0)).createTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid,
                TransferBody(currency, amountToSave)
            )
            Assert.assertEquals(
                viewModelTest.getNetworkError().getValueBlockingForUnitTests(),
                errorText
            )
            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

    @Test
    fun createGoalResponseSuccessful() {
        runBlocking {
            mockGoalNotInPreferences()
            mockCreateGoalSuccess()
            mockCreateTransferFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(1)).createTransfer(
                accountUid,
                createGoalResponse.savingsGoalUid,
                TransferBody(currency, amountToSave)
            )
        }
    }

    @Test
    fun createTransferResponseNotSuccessful() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(0)).saveToGoal(
                accountUid,
                createGoalResponse.savingsGoalUid,
                createTransferResponse.transferUid,
                SavingBody(Money(currency, amountToSave))
            )
            Assert.assertEquals(
                viewModelTest.getNetworkError().getValueBlockingForUnitTests(),
                errorText
            )
            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

    @Test
    fun createTransferResponseNotSuccessfulInField() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferFailResponseField()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(0)).saveToGoal(
                accountUid,
                createGoalResponse.savingsGoalUid,
                createTransferResponse.transferUid,
                SavingBody(Money(currency, amountToSave))
            )
            Assert.assertEquals(
                viewModelTest.getNetworkError().getValueBlockingForUnitTests(),
                errorText
            )
            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

    @Test
    fun createTransferResponseSuccessful() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferSuccess()
            mockSaveToGoalFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()
            verify(starlingApi, times(1)).saveToGoal(
                accountUid,
                createGoalResponse.savingsGoalUid,
                createTransferResponse.transferUid,
                SavingBody(Money(currency, amountToSave))
            )
        }
    }

    @Test
    fun sendToGoalResponseNotSuccessful() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferSuccess()
            mockSaveToGoalFailCallback()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()

            Assert.assertEquals(
                viewModelTest.getNetworkError().getValueBlockingForUnitTests(),
                errorText
            )
        }
    }

    @Test
    fun sendToGoalResponseNotSuccessfulInField() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferSuccess()
            mockSaveToGoalFailResponseField()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()

            Assert.assertEquals(
                viewModelTest.getNetworkError().getValueBlockingForUnitTests(),
                errorText
            )
        }
    }

    @Test
    fun sendToGoalResponseSuccessful() {
        runBlocking {
            mockGoalInPreferences()
            mockCreateTransferSuccess()
            mockSaveToGoalSuccess()
            mockDeleteTransfer()

            val response =
                viewModelTest.sendToGoal(accountUid, listOfFeedIds, amountToSave, currency)
            response.join()

            verify(persistenceManager, times(1)).markFeedsAsSaved(
                listOfFeedIds
            )

            verify(starlingApi, times(1)).deleteTransfer(
                accountUid, createGoalResponse.savingsGoalUid
            )
            Assert.assertTrue(
                viewModelTest.getNetworkCallInProgress().getValueBlockingForUnitTests() == false
            )
        }
    }

}
