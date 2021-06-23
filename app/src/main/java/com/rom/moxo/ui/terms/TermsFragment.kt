package com.rom.moxo.ui.terms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rom.moxo.R

class TermsFragment : Fragment() {

    private lateinit var termsViewModel: TermsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        termsViewModel =
            ViewModelProviders.of(this).get(TermsViewModel::class.java)
        val root = inflater.inflate(R.layout.terms_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_terms)
        termsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}