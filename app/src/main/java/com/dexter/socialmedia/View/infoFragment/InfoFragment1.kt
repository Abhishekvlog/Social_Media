package com.dexter.socialmedia.View.infoFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dexter.socialmedia.R
import com.dexter.socialmedia.View.LoginActivity
import kotlinx.android.synthetic.main.fragment_info1.*


class InfoFragment1 : Fragment(R.layout.fragment_info1) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextBnt.setOnClickListener {
            startActivity(Intent(context,LoginActivity::class.java))
        }


    }


}