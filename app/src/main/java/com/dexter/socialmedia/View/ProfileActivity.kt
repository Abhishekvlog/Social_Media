package com.dexter.socialmedia.View

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.dexter.socialmedia.R
import com.dexter.socialmedia.databinding.ActivityProfileBinding
import com.dexter.socialmedia.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    // viewBinding
    private lateinit var binding : ActivityProfileBinding

    // firebaseAuth
    private lateinit var fireBaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // firebase Auth
        fireBaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // click on log out
        binding.btnLogout.setOnClickListener {
            fireBaseAuth.signOut()
            checkUser()
        }

    }

    private fun checkUser() {
        // user is logged in or not

        val firebaseUser = fireBaseAuth.currentUser
        if (firebaseUser != null){
            // user is present
            val email = firebaseUser.email
            binding.showEmail.text = email


        }
        else{
            // user is not present
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}