package com.example.insta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Profile
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.example.insta.fragments.HomeFragment
import com.example.insta.fragments.NotificationFragment
import com.example.insta.fragments.ProfileFragment
import com.example.insta.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private var selectorFragment: Fragment?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView=findViewById(R.id.bottomNavigation)
        bottomNavigationView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {

            when(it.itemId){
                R.id.navHome->selectorFragment=HomeFragment()
                R.id.navSearch->selectorFragment=SearchFragment()
                R.id.navHeart->selectorFragment=NotificationFragment()
                R.id.navPerson->selectorFragment=ProfileFragment()
                else->{
                    selectorFragment=null
                    startActivity(Intent(this,PostActivity::class.java))
                }
            }
            if(selectorFragment!=null){
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    selectorFragment!!
                ).commit()
            }


            return@OnItemSelectedListener true

        })

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeFragment()).commit()
    }
}