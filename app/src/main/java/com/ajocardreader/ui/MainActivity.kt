package com.ajocardreader.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ajocardreader.R
import com.ajocardreader.config.CardReaderApplication
import com.ajocardreader.config.CardReaderDaggerComponent
import com.ajocardreader.models.CardVerificationResponse
import com.ajocardreader.viewmodel.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import android.app.Activity
import cards.pay.paycardsrecognizer.sdk.utils.CardUtils.getCardNumberRedacted
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Parcelable
import android.util.Log
import cards.pay.paycardsrecognizer.sdk.Card


class MainActivity : AppCompatActivity() {

    lateinit var cardReaderDaggerComponent: CardReaderDaggerComponent

    val REQUEST_CODE_SCAN_CARD = 1

    private val mainActivityViewModel by lazy {
        ViewModelProviders.of(this@MainActivity,viewModelProviderFactory)
            .get(MainActivityViewModel::class.java)
    }

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        cardReaderDaggerComponent = (application as CardReaderApplication).daggerComponent
        cardReaderDaggerComponent.inject(this)

        btnVerify.setOnClickListener {
            val card  = edCard.text.toString()
            mainActivityViewModel.getCardDetails(card)
        }

        btnScan.setOnClickListener {
            scanCard()
        }


        with(mainActivityViewModel) {
            getErrorMessageEvent().observe(this@MainActivity, Observer { uiEvent ->
                showError(uiEvent)
            })

            getProgress().observe(this@MainActivity, Observer { uiEvent ->
            showProgress(uiEvent)
            })

            getCardVerificationEvent().observe(this@MainActivity, Observer { cardEvent ->
            showInfo(cardEvent)
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showError(errorMessage: String){
        infoWrapper.visibility = View.GONE
        progressWrapper.visibility = View.GONE
        errorWrapper.visibility = View.VISIBLE

        tvError.text = errorMessage

    }

    private fun showProgress(show: Boolean){
        if(show){
            infoWrapper.visibility = View.GONE
            progressWrapper.visibility = View.VISIBLE
            errorWrapper.visibility = View.GONE
        }
    }

    private fun showInfo(details: CardVerificationResponse){
        tvNetwork.text = details.scheme
        tvCountry.text = details.country?.name
        tvBrand.text = details.brand
        tvBank.text = details.bank?.name

        infoWrapper.visibility = View.VISIBLE
        progressWrapper.visibility = View.GONE
        errorWrapper.visibility = View.GONE
    }

    private fun scanCard() {
        val intent = ScanCardIntent.Builder(this).build()
        startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK) {
                val card : Card? = data?.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)
                edCard.setText(card?.cardNumber)
                card?.cardNumber?.let { mainActivityViewModel.getCardDetails(it)}
               Log.i("scanCard", "ç: ${card?.cardNumber}")
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("scanCard", "Scan canceled")
            } else {
                Log.i("scanCard", "Scan failed")
            }
        }
    }
}

