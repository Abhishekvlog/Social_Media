package com.dexter.socialmedia.View

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.dexter.socialmedia.R
import com.dexter.socialmedia.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    // view binding
    private lateinit var binding: ActivityLoginBinding

    // progress Dialog
    private lateinit var progressDialog: ProgressDialog

    // firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // configure progress bar
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("Login In..")
        progressDialog.setCanceledOnTouchOutside(false)

        // firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // click for new user
        binding.noAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))

        }

        // handle login button
        binding.btnEmailLogin.setOnClickListener {
            // valid data
            ValidDateData()

        }


    }

    private fun ValidDateData() {
        // get data
        email = binding.mailId.text.toString().trim()
        password = binding.password.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid
            binding.mailId.error = "Invalid Email!!"
        } else if (TextUtils.isEmpty(password)) {
            binding.password.error = "Please Enter password"
        } else {
            // data is correct
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {

                // in success
                progressDialog.dismiss()

                // get user information
                val fireBaseUser = firebaseAuth.currentUser
                val email = fireBaseUser!!.email
                Toast.makeText(this, "LogedIn as $email ", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()


            }
            .addOnFailureListener { e ->
                // in failure
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
        // if user already logged in go to next page
        // get current user
        val firebaseuser = firebaseAuth.currentUser
        if (firebaseuser != null) {
            // user is logged in
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }
    }
}