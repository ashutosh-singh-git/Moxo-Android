package com.rom.moxo.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rom.moxo.R
import com.rom.moxo.activity.LoginActivity

class UserFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel =
            ViewModelProviders.of(this).get(UserViewModel::class.java)
        val root = inflater.inflate(R.layout.user_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_user)
        userViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val btn = root.findViewById<View>(R.id.bt_signup) as Button
        btn.setOnClickListener {
            goToLogin(it);
        }
        return root
    }

    fun goToLogin(v: View) {
        // Open your SignUp Activity if the user wants to signup
        // Visit this article to get SignupActivity code https://handyopinion.com/signup-activity-in-android-studio-kotlin-java/
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }
}