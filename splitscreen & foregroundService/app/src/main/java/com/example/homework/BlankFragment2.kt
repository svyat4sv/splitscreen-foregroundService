package com.example.homework

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.homework.databinding.FragmentBlank2Binding

class BlankFragment2 : Fragment() {
    private lateinit var binding: FragmentBlank2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlank2Binding.inflate(layoutInflater)
        val arguments = this.arguments
        val inputText = arguments?.getString("input")
        binding.outputText.text = inputText

        return binding.root
    }
    fun setText(text: String){
        binding.outputText.text = text
    }


}