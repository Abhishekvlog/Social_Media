package com.dexter.socialmedia.View

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.dexter.socialmedia.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    // viewBinding
    private lateinit var binding : ActivitySignUpBinding

    //Progrss Dialog
    private lateinit var progressDailog : ProgressDialog

    // firebaseAuth
    private lateinit var fireBaseAuth : FirebaseAuth
    private  var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // configure progressDialog
        progressDailog = ProgressDialog(this)
        progressDailog.setTitle("Please Wait..")
        progressDailog.setMessage("Creating account in ...")
        progressDailog.setCanceledOnTouchOutside(false)

        // firebase Auth
        fireBaseAuth = FirebaseAuth.getInstance()

        //begin signup
        binding.btnEmailSign.setOnClickListener {
            // valid data
            validdateData()
        }
    }

    private fun validdateData() {
        email = binding.signMailId.text.toString().trim()
        password = binding.signPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid
            binding.signMailId.error = "Invalid Email!!"
        } else if (TextUtils.isEmpty(password)) {
            binding.signPassword.error = "Please Enter password"
        }
        else if (password.length < 6){
            binding.signPassword.error = "Please Enter password length more than 6"
        }
        else{
            fireBaseSignUp()
        }

    }

    private fun fireBaseSignUp() {
        // show progress
        progressDailog.show()

        // create
        fireBaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                // in success
                progressDailog.dismiss()

                // get user information
                val fireBaseUser = fireBaseAuth.currentUser
                val email = fireBaseUser!!.email
                Toast.makeText(this, "create account as $email ", Toast.LENGTH_SHORT).show()

                // open profile
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                // on failure
                progressDailog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity

        return super.onSupportNavigateUp()
    }
}