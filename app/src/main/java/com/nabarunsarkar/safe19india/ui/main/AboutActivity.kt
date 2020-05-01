package com.nabarunsarkar.safe19india.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.nabarunsarkar.safe19india.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        simulateDayNight( /* DAY */0)
        val adsElement = Element()
        adsElement.title = "Data API: covid19india.org"

        val aboutPage: View = AboutPage(this)
            .isRTL(false)
            .setDescription("Hi, this app is created voluntarily by Nabarun Sarkar, a grad student at IISc Bangalore. This is to help people to keep a track of count updates of Covid-19 spread in India.")
            .addItem(Element().setTitle("Version 1.1"))
            .addItem(adsElement)
            .addGroup("Connect with us")
            .addEmail("nabsarkar@gmail.com")
            .addWebsite("https://nabarunsarkar.com/blog/markdown/2020/04/10/covid-19-android-app.html")
            .addTwitter("nbrsr")
            .addInstagram("cruq")
            .addGitHub("nabaruns")
            .create()

        setContentView(aboutPage)
    }

    fun simulateDayNight(currentSetting: Int) {
        val DAY = 0
        val NIGHT = 1
        val FOLLOW_SYSTEM = 3
        val currentNightMode = (resources.configuration.uiMode
                and Configuration.UI_MODE_NIGHT_MASK)
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        return
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // API 5+ solution
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
