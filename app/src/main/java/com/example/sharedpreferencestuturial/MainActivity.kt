package com.example.sharedpreferencestuturial

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sharedpreferencestuturial.databinding.ActivityMainBinding


const val APP_PREFERENCES = "APP_PREFERENCES"                                                       // you can store preferences in companion object (without name) if you want to use it in current Activity
const val PREF_SOME_TEXT_KEY = "PREF_SOME_TEXT"                                                     // we need a key for preferences

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    lateinit var preferences: SharedPreferences

    private val sharedPreferencesListener =
        SharedPreferences.OnSharedPreferenceChangeListener { pref, key ->                           // to listen changes in preferences
            if (key == PREF_SOME_TEXT_KEY){
                binding.currentDataTextView.text = pref.getString(key, "")                          // and listener will update info in currentDataTextView
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val currentValue = preferences.getString(PREF_SOME_TEXT_KEY, "")                     // key and default value
        binding.enterDataEditText.setText(currentValue)
        binding.currentDataTextView.text = currentValue



        binding.saveDataButton.setOnClickListener {
            val data = binding.enterDataEditText.text.toString()
            preferences.edit()                                                                      // make "editable"
                .putString(PREF_SOME_TEXT_KEY, data)
                .apply()                                                                            // this method will trigger listener
        }

        preferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }
}