package com.example.homework

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.homework.databinding.FragmentBlank1Binding


class BlankFragment1 : Fragment() {
    private lateinit var binding: FragmentBlank1Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlank1Binding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            if (checkOrientation(resources.configuration.orientation) == 1) {
                val fragment2 = BlankFragment2()
                val input = binding.editText.text.toString()
                val bundle = Bundle()
                bundle.putString("input", input)
                fragment2.arguments = bundle
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_container, fragment2)
                    .addToBackStack(null)
                    .commit()
            } else {
                var clickInterface: ClickInterface = requireContext() as ClickInterface
                val input = binding.editText.text.toString()
                clickInterface.oncLick(input)
            }
        }

        binding.startService.setOnClickListener {
           val intent = Intent(requireActivity(), MyForegroundService::class.java)
            ContextCompat.startForegroundService(requireActivity(), intent)
            Toast.makeText(requireContext(), "Аудиофайл воспроизводится", Toast.LENGTH_LONG).show()

        }
        binding.stopService.setOnClickListener {
            val intent = Intent(requireActivity(), MyForegroundService::class.java)
            requireActivity().stopService(intent)
        }


        return binding.root

    }

    private fun checkOrientation(orientation: Int): Int {
        return when (orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 1
            Configuration.ORIENTATION_LANDSCAPE -> 2
            else -> 0
        }

    }

}