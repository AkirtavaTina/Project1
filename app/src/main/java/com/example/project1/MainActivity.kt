package com.example.project1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.CheckBox



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
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
            R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9
        )

        buttons.forEachIndexed { index, id ->
            findViewById<Button>(id).apply {
                text = when (index) {
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
    }

    private fun textForTitles(){
        val textViewMap = mapOf(
            R.id.textView10 to R.string.welcoming,
            R.id.textView6 to R.string.details,
            R.id.textView7 to R.string.text1,
            R.id.textView to R.string.text4,
            R.id.textView2 to R.string.breed,
            R.id.textView3 to R.string.pictures,
            R.id.textView4 to R.string.vaccination,
            R.id.textView5 to R.string.ongoing_treatment,
            R.id.textView8 to R.string.personality_traits,
            R.id.textView9 to R.string.behaviour
        )

        textViewMap.forEach {(textViewId, stringResourceId) ->
            findViewById<TextView>(textViewId).text = getText(stringResourceId)
        }
    }

    private fun textForCheckBox(){
        val box1:CheckBox = findViewById(R.id.checkbox1)
        box1.text = getText(R.string.well_trained)
        val box2:CheckBox = findViewById(R.id.checkbox2)
        box2.text = getText(R.string.curious)
        val box3:CheckBox = findViewById(R.id.checkbox3)
        box3.text = getText(R.string.loud)
    }

    private fun clicked(vararg buttonIds: Int) {
        val buttons = buttonIds.map { findViewById<Button>(it) }

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
        clicked(R.id.button1, R.id.button2)
        clicked(R.id.button3, R.id.button9)
        clicked(R.id.button4, R.id.button5)
        clicked(R.id.button6, R.id.button7, R.id.button8)
    }
}