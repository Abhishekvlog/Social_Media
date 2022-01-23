package com.dexter.socialmedia.View.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dexter.socialmedia.R
import com.dexter.socialmedia.View.PostModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class PostAdapter(
    private val list : List<PostModel>
) : RecyclerView.Adapter<PostViewHolder>(){

    private var firebseUser : FirebaseUser? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_item_loyout,parent,false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        firebseUser = FirebaseAuth.getInstance().currentUser
        val post = list[position]
        Picasso.get().load(post.getPostImage()).into(holder.Post_Image)
        publisherInfo(holder.Post_Image , holder.Profile_Name, holder.publicsher, post.getPublisher())

    }

    private fun publisherInfo(
        postImage: ImageView,
        profileName: TextView,
        publicsher: TextView,
        publisher: String
    ) {
        val userref = FirebaseDatabase.getInstance().reference.child("users").child(publisher)
        userref.addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
//                    val user = snapshot.getValue<>()
                }
            }

            override fun onCancelled(error: DatabaseError) {


            }
        })

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
class PostViewHolder(val itemView : View) :RecyclerView.ViewHolder(itemView){

    val ProfileImage : ImageView
    val Profile_Name : TextView
    val Post_Image : ImageView
    val Btnlike : ImageView
    val Btncomment : ImageView
    val publicsher : TextView
    val caption : TextView
    val comments : TextView
    val likes : TextView
    init {
        ProfileImage = itemView.findViewById(R.id.profile)
        Profile_Name = itemView.findViewById(R.id.user_Username)
        Post_Image = itemView.findViewById(R.id.profile_show)
        Btncomment = itemView.findViewById(R.id.commentbtn)
        Btnlike = itemView.findViewById(R.id.likebtn)
        publicsher = itemView.findViewById(R.id.profile_show)
        caption = itemView.findViewById(R.id.profile_show)
        comments = itemView.findViewById(R.id.profile_show)
        likes = itemView.findViewById(R.id.profile_show)

    }




}