package com.ajocardreader.ui

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ajocardreader.R
import com.ajocardreader.models.CardVerificationResponse
import com.ajocardreader.viewmodel.MainActivityViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel by lazy {
        ViewModelProviders.of(this@MainActivity)
            .get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        btnVerify.setOnClickListener {
            val card  = edCard.text.toString()
            mainActivityViewModel.getCardDetails(card)
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
}
