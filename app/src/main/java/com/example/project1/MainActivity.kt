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

    }

    private fun addTexts(){
        textsForButtons()
        textForTitles()
        textForCheckBox()
    }

    private fun textsForButtons(){
        val mButton: Button = findViewById(R.id.button1)
        mButton.text = getText(R.string.text2)
        val mButton2: Button = findViewById(R.id.button2)
        mButton2.text = getText(R.string.text3)
        val mButton3: Button = findViewById(R.id.button3)
        mButton3.text = getText(R.string.yes)
        val mButton4: Button = findViewById(R.id.button4)
        mButton4.text = getText(R.string.yes)
        val mButton5: Button = findViewById(R.id.button5)
        mButton5.text = getText(R.string.no)
        val mButton6: Button = findViewById(R.id.button6)
        mButton6.text = getText(R.string.calm)
        val mButton7: Button = findViewById(R.id.button7)
        mButton7.text = getText(R.string.plaayful)
        val mButton8: Button = findViewById(R.id.button8)
        mButton8.text = getText(R.string.aggressive)
        val mButton9: Button = findViewById(R.id.button9)
        mButton9.text = getText(R.string.no)
    }

    private fun textForTitles(){
        val view : TextView = findViewById(R.id.textView10)
        view.text = getText(R.string.welcoming)
        val view2 : TextView = findViewById(R.id.textView6)
        view2.text = getText(R.string.details)
        val view3 : TextView = findViewById(R.id.textView7)
        view3.text = getText(R.string.text1)
        val view4 : TextView = findViewById(R.id.textView)
        view4.text = getText(R.string.text4)
        val view5 : TextView = findViewById(R.id.textView2)
        view5.text = getText(R.string.breed)
        val view6 : TextView = findViewById(R.id.textView3)
        view6.text = getText(R.string.pictures)
        val view7 : TextView = findViewById(R.id.textView4)
        view7.text = getText(R.string.vaccination)
        val view8 : TextView = findViewById(R.id.textView5)
        view8.text = getText(R.string.ongoing_treatment)
        val view9 : TextView = findViewById(R.id.textView8)
        view9.text = getText(R.string.personality_traits)
        val view10 : TextView = findViewById(R.id.textView9)
        view10.text = getText(R.string.behaviour)
    }

    private fun textForCheckBox(){
        val box1:CheckBox = findViewById(R.id.checkbox1)
        box1.text = getText(R.string.well_trained)
        val box2:CheckBox = findViewById(R.id.checkbox2)
        box2.text = getText(R.string.curious)
        val box3:CheckBox = findViewById(R.id.checkbox3)
        box3.text = getText(R.string.loud)
    }

}