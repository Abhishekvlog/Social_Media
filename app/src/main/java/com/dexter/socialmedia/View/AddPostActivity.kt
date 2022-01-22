package com.dexter.socialmedia.View

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.dexter.socialmedia.R
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.activity_add_post.*

class AddPostActivity : AppCompatActivity() {
    private var url = ""
    private var imageUri : Uri? = null
    private var storagePosteRef : StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        storagePosteRef = FirebaseStorage.getInstance().reference.child("post Picture")

        CropImage.activity()
            .setAspectRatio(1 , 1)
            .start(this@AddPostActivity)

        btnpost.setOnClickListener {
            uploadImage()
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK ){
            val result = CropImage.getActivityResult(data)
            imageUri = result.uri
            postImage.setImageURI(imageUri)
        }
    }

    private fun uploadImage() {
        when{
            imageUri == null -> Toast.makeText(this, "please make sure select image", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(caption_post.text.toString()) -> Toast.makeText(this,"Please write caption",Toast.LENGTH_SHORT).show()

            else -> {

                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("adding new post")
                progressDialog.setMessage("please wait for uploading...")
                progressDialog.show()
                val fileRef = storagePosteRef!!.child(System.currentTimeMillis().toString() + ".jpg")

                var uploadTask : StorageTask<*>
                uploadTask = fileRef.putFile(imageUri!!)

                uploadTask.continueWith(Continuation <UploadTask.TaskSnapshot,Task<Uri>>{task ->
                    if (!task.isSuccessful){
                        task.exception.let {
                            throw it!!
                            progressDialog.dismiss()
                        }
                    }
                    return@Continuation fileRef.downloadUrl
                })
                    .addOnCompleteListener(OnCompleteListener { task ->
                        val downloaduri = task.result
                        url = downloaduri.toString()

                        val ref = FirebaseDatabase.getInstance().reference.child("posts")
                        var postId = ref.push().key
                        val userMap = HashMap<String , Any>()
                        userMap["postid"] = postId!!
                        userMap["caption"] = caption_post.text.toString()
                        userMap["publisher"] = FirebaseAuth.getInstance().currentUser!!.uid
                        userMap["postimage"] = url

                        ref.child(postId).updateChildren(userMap)

                        Toast.makeText(this, "uploaded successfully", Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this,ProfileActivity::class.java))
                        progressDialog.dismiss()
                        finish()


                    })

            }
        }
    }
}