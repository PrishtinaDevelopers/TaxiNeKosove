package com.posks.taxikosovashqip.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.posks.taxikosovashqip.util.LocaleUtils
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.util.SettingsManager


class PreferencesActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var settingsManager: SettingsManager
    private lateinit var localeUtils: LocaleUtils

    private val avatarArray = arrayOf(R.drawable.ic_avatar_asistante, R.drawable.ic_avatar_boy,
            R.drawable.ic_avatar_businness_man, R.drawable.ic_avatar_chef,
            R.drawable.ic_avatar_doctor, R.drawable.ic_avatar_gentleman, R.drawable.ic_avatar_girl,
            R.drawable.ic_avatar_king, R.drawable.ic_avatar_man, R.drawable.ic_avatar_man_two,
            R.drawable.ic_avatar_police_man, R.drawable.ic_avatar_police_women,
            R.drawable.ic_avatar_queen, R.drawable.ic_avatar_speaker, R.drawable.ic_avatar_spy,
            R.drawable.ic_avatar_superman, R.drawable.ic_avatar_women,
            R.drawable.ic_avatar_women_two, R.drawable.ic_avatar_wonder_women,
            R.drawable.ic_avatar_worker)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        setTitle(R.string.app_name);

        settingsManager = SettingsManager(application)
        localeUtils = LocaleUtils()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)


        usernameEditText = findViewById(R.id.username_edit_text)

        usernameEditText.setText(if (settingsManager.username != null) settingsManager.username else null)

        findViewById<ImageView>(R.id.language_albanian).setOnClickListener({ setLanguageAlbanian() })
        findViewById<ImageView>(R.id.language_english).setOnClickListener({ setLanguageEnglish() })
        findViewById<ImageView>(R.id.language_german).setOnClickListener({ setLanguageGerman() })

        val grid = findViewById<GridView>(R.id.preference_avatar_grid_view)
        grid.adapter = AvatarGridAdapter(this)
        grid.onItemClickListener = this
    }

    private fun restartPreferencesActivity() {
        startActivity(Intent(this, PreferencesActivity::class.java))
        finish()
    }

    private fun setLanguageAlbanian() {
        localeUtils.setLocale(this, LocaleUtils.LOCALE_ALBANIAN)
        settingsManager.languageLocale = LocaleUtils.LOCALE_ALBANIAN.toLanguageTag()
        restartPreferencesActivity()
    }

    private fun setLanguageEnglish() {
        localeUtils.setLocale(this, LocaleUtils.LOCALE_ENGLISH)
        settingsManager.languageLocale = LocaleUtils.LOCALE_ENGLISH.toLanguageTag()
        restartPreferencesActivity()
    }

    private fun setLanguageGerman() {
        localeUtils.setLocale(this, LocaleUtils.LOCALE_GERMAN)
        settingsManager.languageLocale = LocaleUtils.LOCALE_GERMAN.toLanguageTag()
        restartPreferencesActivity()
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (TextUtils.isEmpty(usernameEditText.text)) {
            Toast.makeText(this, getString(R.string.type_name_or_nickname), Toast.LENGTH_SHORT).show()
        } else {
            settingsManager.username = usernameEditText.text.toString()
            settingsManager.avatarId = avatarArray[p2]
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    inner class AvatarGridAdapter(private val context: Context) : BaseAdapter() {

        override fun getCount(): Int {
            return avatarArray.size
        }

        override fun getItem(position: Int): Any {
            return position
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val imageView: ImageView
            if (convertView == null) {
                imageView = ImageView(context)
                imageView.layoutParams = AbsListView.LayoutParams(200, 200)
                imageView.layoutParams = GridView@ AbsListView.LayoutParams(200, 200)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                imageView.setPadding(16, 16, 16, 16)
            } else {
                imageView = (convertView as ImageView?)!!
            }
            imageView.setImageResource(avatarArray[position])
            return imageView
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}