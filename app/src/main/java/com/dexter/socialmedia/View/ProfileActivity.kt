package com.dexter.socialmedia.View

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.dexter.socialmedia.R
import com.dexter.socialmedia.View.fragmnet.*
import com.dexter.socialmedia.databinding.ActivityProfileBinding
import com.dexter.socialmedia.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    // viewBinding
    private lateinit var binding: ActivityProfileBinding

    // firebaseAuth
    private lateinit var fireBaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // firebase Auth
        fireBaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // click on log out
//        binding.btnLogout.setOnClickListener {
//            fireBaseAuth.signOut()
//            checkUser()
//        }

        val homeFragment = HomeFragment()
        val addFragment = AddFragment()
        val searchFragment = SearchFragment()
        val likeFragment = LikeFragment()
        val profileFragment = ProfileFragment()

        makeCurrentFragment(homeFragment)
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_Post -> makeCurrentFragment(addFragment)
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_like -> makeCurrentFragment(likeFragment)
                R.id.ic_profile -> makeCurrentFragment(profileFragment)
            }
            true
        }


    }

    private fun checkUser() {
        // user is logged in or not

        val firebaseUser = fireBaseAuth.currentUser
        if (firebaseUser != null) {
            // user is present
            val email = firebaseUser.email



        } else {
            // user is not present
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun makeCurrentFragment(fragment : Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment).commit()
        }
}