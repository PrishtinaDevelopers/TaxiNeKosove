package com.posks.taxikosovashqip.ui.activity

import android.support.v4.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.posks.taxikosovashqip.R
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.posks.taxikosovashqip.util.SettingsManager
import android.widget.Toast
import com.posks.taxikosovashqip.ui.fragment.MainFragment


class MainActivity : AppCompatActivity() {
    private lateinit var settingsManager: SettingsManager
    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(R.string.app_name);

        settingsManager = SettingsManager(application)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        setFragment(MainFragment())

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerLayout = navigationView.getHeaderView(0)
        val userAvatar = headerLayout.findViewById<ImageView>(R.id.navigation_user_avatar)
        val usernameText = headerLayout.findViewById<TextView>(R.id.navigation_user_username)

        userAvatar.setImageResource(settingsManager.avatarId)
        usernameText.text = settingsManager.username
        userAvatar.setOnClickListener({ goToPreferences() })
        usernameText.setOnClickListener({ goToPreferences() })

        navigationView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            var fragment: Fragment = MainFragment()
            when {
                menuItem.itemId == R.id.choose_city -> {
                    fragment = MainFragment()
                }
                menuItem.itemId == R.id.add_taxi -> {
                    fragment = MainFragment()
                }
                menuItem.itemId == R.id.preferences -> goToPreferences()
            }

            setFragment(fragment)

            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()

            true
        }

        mDrawerLayout.addDrawerListener(
                object : DrawerLayout.DrawerListener {
                    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                        // Respond when the drawer's position changes
                    }

                    override fun onDrawerOpened(drawerView: View) {
                        // Respond when the drawer is opened
                    }

                    override fun onDrawerClosed(drawerView: View) {
                        // Respond when the drawer is closed
                    }

                    override fun onDrawerStateChanged(newState: Int) {
                        // Respond when the drawer motion state changes
                    }
                }
        )
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()
    }

    private fun goToPreferences() {
        startActivity(Intent(this, PreferencesActivity::class.java))
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var backPressed: Long = 0

    //onBack doubleback finish()
    override fun onBackPressed() {
        if (backPressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()

        } else {
            val fragment: Fragment = MainFragment()

            setFragment(fragment)
            Toast.makeText(baseContext, "Shtyp prap për dalje", Toast.LENGTH_SHORT).show()
        }
        backPressed = System.currentTimeMillis()
    }
}