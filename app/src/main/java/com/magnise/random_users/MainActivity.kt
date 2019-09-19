package com.magnise.random_users

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class MainActivity : FragmentActivity(), MainView  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openMainFragment()
        }
    }

    override fun openMainFragment() {
        replaceFragment(MainFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_space, fragment)
        fragmentTransaction.commit()
    }
}
