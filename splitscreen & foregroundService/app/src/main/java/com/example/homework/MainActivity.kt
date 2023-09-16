package com.example.homework

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.homework.R

class MainActivity : AppCompatActivity(), ClickInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


    override fun oncLick(text: String) {
        var blankFragment2: BlankFragment2 = supportFragmentManager
            .findFragmentById(R.id.container2)!! as BlankFragment2
        blankFragment2.setText(text)
    }


}