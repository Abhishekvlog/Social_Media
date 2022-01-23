package com.dexter.socialmedia.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dexter.socialmedia.R
import com.dexter.socialmedia.View.infoFragment.InfoFragment1
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_info1.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            val InfoFragment1 = InfoFragment1()

            makeCurrentFragment(InfoFragment1)

    }
    private fun makeCurrentFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_info_fragment,fragment).commit()
        }
}