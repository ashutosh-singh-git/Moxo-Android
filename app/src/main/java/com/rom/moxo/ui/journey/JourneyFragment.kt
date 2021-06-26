package com.rom.moxo.ui.journey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rom.moxo.R

class JourneyFragment : Fragment() {

    private lateinit var journeyViewModel: JourneyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        journeyViewModel =
            ViewModelProviders.of(this).get(JourneyViewModel::class.java)
        val root = inflater.inflate(R.layout.journey_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_journey)
        journeyViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}