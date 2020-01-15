package com.ajocardreader

import com.ajocardreader.models.CardVerificationResponse
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ajocardreader.datasource.repository.Repository
import com.ajocardreader.viewmodel.MainActivityViewModel
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import com.ajocardreader.datasource.CardReaderApiService
import io.reactivex.Single
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when`
import org.mockito.Mockito.`when`






class MainActivityViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

//    @Rule
//    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit  var cardReaderApiService: CardReaderApiService

    private var repo: Repository = mock()
    @Mock
    private lateinit var viewModel: MainActivityViewModel

    private val cardObserver: Observer<CardVerificationResponse> = mock()
    private val errorObserver: Observer<String> = mock()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        //repo = Repository(cardReaderApiService)
        viewModel = MainActivityViewModel(repo)
    }

    @Test
    fun fetchCardDetails_ShouldReturnDetails() {
        val expectedUser = CardVerificationResponse(number = null,bank = null,
            brand = "MasterCard",country = null,scheme = "344",prepaid = true,type = "Master")

        `when`(repo.getCardDetails("45717360")).thenReturn(Single.just(expectedUser))
        viewModel.cardVerificationDetailEvent.observeForever(cardObserver)


        viewModel.getCardDetails("45717360")


        assertTrue(viewModel.cardVerificationDetailEvent.hasObservers())

        val captor = ArgumentCaptor.forClass(CardVerificationResponse::class.java)
        captor.run {
            verify(cardObserver, times(1)).onChanged(capture())
            assertEquals(expectedUser, value)
        }
    }

    @Test
    fun fetchCardDetails_ShouldReturnError() {
        val throwable = Throwable("No card Found")

        `when`(repo.getCardDetails("45717360")).thenReturn(Single.error(throwable))
        viewModel.errorEvent.observeForever(errorObserver)


        viewModel.getCardDetails("45717360")

        assertTrue(viewModel.errorEvent.hasObservers())

        val captor = ArgumentCaptor.forClass(String::class.java)
        captor.run {
            verify(errorObserver, times(1)).onChanged(capture())
            assertEquals("No card Found", value)
        }
    }

}