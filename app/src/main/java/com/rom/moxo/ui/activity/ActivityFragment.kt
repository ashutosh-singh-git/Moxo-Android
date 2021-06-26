package com.rom.moxo.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rom.moxo.R

class ActivityFragment : Fragment() {

    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activityViewModel =
            ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        val root = inflater.inflate(R.layout.activity_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_activity)
        activityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}