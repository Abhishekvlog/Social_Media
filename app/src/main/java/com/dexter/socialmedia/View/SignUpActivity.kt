package com.dexter.socialmedia.View

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.dexter.socialmedia.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {


    // viewBinding
    private lateinit var binding : ActivitySignUpBinding

    //Progrss Dialog
    private lateinit var progressDailog : ProgressDialog

    // firebaseAuth
    private lateinit var fireBaseAuth : FirebaseAuth
    private  var email = ""
    private var password = ""
    private var fullname = ""
    private var username = ""

    private lateinit var googleSignInClient: GoogleSignInClient

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
        binding.ivGoogleLogin.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(this, gso)
            //Sign In
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, Companion.RC_SIGN_IN)

//            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun validdateData() {
        email = binding.signMailId.text.toString().trim()
        password = binding.signPassword.text.toString().trim()
        username = binding.signUseranme.text.toString().trim()
        fullname = binding.signFullName.text.toString().trim()

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
                val Email = fireBaseUser!!.email


                createInformation(email, fullname, username )

                Toast.makeText(this, "create account as $Email ", Toast.LENGTH_SHORT).show()

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

    private fun createInformation(email: String, fullname: String, username: String) {
        val currUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef  :DatabaseReference = FirebaseDatabase.getInstance().reference.child("users")
        val userMap = HashMap<String,Any>()
        userMap["userid"] = currUserId
        userMap["email"] = email
        userMap["fullname"] = fullname
        userMap["username"] = username
        userMap["bio"] = "hey i am using social media...."
        userMap["iamge"] = "https://firebasestorage.googleapis.com/v0/b/social-signup-7fa16.appspot.com/o/default%20Image%2Fprofile.xml?alt=media&token=a1611a44-1bde-4949-8072-275f97136746"

        userRef.child(currUserId).setValue(userMap)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    progressDailog.dismiss()
                }
                else{
                    FirebaseAuth.getInstance().signOut()
                    progressDailog.dismiss()
                }
            }
     }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity

        return super.onSupportNavigateUp()
    }

    companion object {
        private const val RC_SIGN_IN = 100
    }
}