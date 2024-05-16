package com.example.project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.CheckBox
import androidx.core.content.res.TypedArrayUtils.getText
import com.example.project1.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addTexts()
        checkIfClicked()
    }

    private fun addTexts(){
        textsForButtons()
        textForTitles()
        textForCheckBox()
    }

    private fun textsForButtons(){
        val buttons = arrayOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )

        buttons.forEachIndexed { index, button ->
            button.text = when (index) {
                    0 -> R.string.text2
                    1 -> R.string.text3
                    2, 3 -> R.string.yes
                    5 -> R.string.calm
                    6 -> R.string.plaayful
                    7 -> R.string.aggressive
                    else -> R.string.no
            }.let { getText(it) }

        }
    }

    private fun textForTitles(){
        val textViewMap:Map<TextView, Int> = mapOf(
            binding.textView10 to R.string.welcoming,
            binding.textView6 to R.string.details,
            binding.textView7 to R.string.text1,
            binding.textView to R.string.text4,
            binding.textView2 to R.string.breed,
            binding.textView3 to R.string.pictures,
            binding.textView4 to R.string.vaccination,
            binding.textView5 to R.string.ongoing_treatment,
            binding.textView8 to R.string.personality_traits,
            binding.textView9 to R.string.behaviour
        )
        textViewMap.forEach {(textView, stringResourceId) ->
            textView.text = getText(stringResourceId)
        }
    }

    private fun textForCheckBox(){
        val box1:CheckBox = binding.checkbox1
        box1.text = getText(R.string.well_trained)
        val box2:CheckBox = binding.checkbox2
        box2.text = getText(R.string.curious)
        val box3:CheckBox = binding.checkbox3
        box3.text = getText(R.string.loud)
    }

    private fun clicked(vararg buttons: Button) {
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val isSelected = button.isSelected

                if(isSelected) button.isSelected = false
                else buttons.forEachIndexed { innerIndex, innerButton ->
                        innerButton.isSelected = (index == innerIndex) }
            }
        }
    }

    private fun checkIfClicked() {
        clicked(binding.button1, binding.button2)
        clicked(binding.button3, binding.button9)
        clicked(binding.button4, binding.button5)
        clicked(binding.button6, binding.button7, binding.button8)
    }
}