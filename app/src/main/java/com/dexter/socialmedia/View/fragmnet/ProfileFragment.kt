package com.dexter.socialmedia.View.fragmnet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.dexter.socialmedia.R
import com.dexter.socialmedia.View.EditProfileActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {


    // firebaseAuth
    private lateinit var fireBaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // firebase Auth
        fireBaseAuth = FirebaseAuth.getInstance()
        checkUser()

        btn_Edit.setOnClickListener {
            startActivity(Intent(context,EditProfileActivity::class.java))

        }

//        button_signOut.setOnClickListener {
//
//        }

    }

    private fun checkUser() {

        // user is logged in or not

        val firebaseUser = fireBaseAuth.currentUser
        if (firebaseUser != null) {
            // user is present
            val email = firebaseUser.email
            emailShow.text = email



        }
    }
}