package com.example.texttospeak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.texttospeak.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var binding: ActivityMainBinding

    private var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tts = TextToSpeech(this, this)

        binding.btnButton.setOnClickListener { _ ->
            if (binding.etEditText.text!!.isEmpty()) {
                Toast.makeText(this@MainActivity, "Enter the text", Toast.LENGTH_SHORT).show()

            } else {
                speakOut(binding.etEditText.text.toString())

            }

        }
    }

    override fun onInit(status: Int) {
        if  (status == TextToSpeech.SUCCESS) {
            val resutl = tts!!.setLanguage(Locale.US)

            if (resutl == TextToSpeech.LANG_MISSING_DATA || resutl == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language specified is not supported")
            } else {
                Log.e("TTS", "Initialization Failed")
            }
        }

    }

    private fun speakOut(text: String){
        tts?.speak(text,TextToSpeech.QUEUE_ADD, null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts != null) {
            tts?.stop()
            tts?.shutdown()
        }
    }
}