package com.olmi.android.memes.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.olmi.android.memes.R
import com.olmi.android.memes.ui.memes.add.MemAddFragment
import com.olmi.android.memes.ui.memes.list.MemesListFragment
import com.olmi.android.memes.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main_screen.*


class MainScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        initFieldsListeners()
    }

    private fun initFieldsListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            item ->
            when(item.itemId) {
                R.id.memes_view_btn -> {
                    val currentFragment = MemesListFragment()
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment, currentFragment)
                    ft.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.memes_add_btn -> {
                    val currentFragment = MemAddFragment()
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment, currentFragment)
                    ft.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.user_profile_btn -> {
                    val currentFragment = ProfileFragment()
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.fragment, currentFragment)
                    ft.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }
}