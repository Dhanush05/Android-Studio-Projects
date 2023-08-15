package com.example.navigationbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var mdrawer: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navView: NavigationView
    lateinit var toggle:ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //1. toolbar
        toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //drawer view
        mdrawer = findViewById(R.id.myDrawer)

        //Navigation drawer
        navView = findViewById(R.id.nvView)
        setupDrawerContent(navView)
    }

    private fun setupDrawerContent(navView: NavigationView?) {
        if (navView != null) {
            navView.setNavigationItemSelectedListener { menuItem->
                selectDrawerItem(menuItem)
                true
            }
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        var fragment: Fragment? = null

        var fragmentClass:Class<out Fragment>

        when(menuItem.itemId){
            R.id.nav_first_frag ->{
                fragmentClass = FirstFragment::class.java

            }
            R.id.nav_second_frag->{
                fragmentClass = SecondFragment::class.java

            }
            R.id.nav_third_frag->{
                fragmentClass = ThirdFragment::class.java
            }
            else -> fragmentClass = FirstFragment::class.java
        }
        try {
            fragment = fragmentClass.newInstance()
        }
        catch (e: IllegalAccessException){
            e.printStackTrace()
        }
        catch (e: InstantiationException){
            e.printStackTrace()
        }
        var fragmentManager:FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout,fragment).commit()
        }
        menuItem.setChecked(true)
        setTitle(menuItem.title)
        mdrawer.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
             android.R.id.home ->{
                 mdrawer.openDrawer(GravityCompat.START)
                 return true
             }

        }
        return super.onOptionsItemSelected(item)
    }
}