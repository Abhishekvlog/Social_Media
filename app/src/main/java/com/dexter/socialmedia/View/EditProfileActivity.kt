package com.dexter.socialmedia.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dexter.socialmedia.R
import com.dexter.socialmedia.databinding.ActivityEditProfileBinding
import com.dexter.socialmedia.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class EditProfileActivity : AppCompatActivity() {

    // view binding
    private lateinit var binding: ActivityEditProfileBinding


    // firebaseAuth
    private lateinit var fireBaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireBaseAuth = FirebaseAuth.getInstance()

        binding.signOut.setOnClickListener {

            fireBaseAuth.signOut()
            checkUser()
        }
        binding.changeImage.setOnClickListener {

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

}