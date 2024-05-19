package com.example.project1

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityMainBinding
import com.google.android.material.imageview.ShapeableImageView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageView: ShapeableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        addTexts()
        checkIfClicked()

        val buttonClick = binding.actionModeCloseButton
        buttonClick.setOnClickListener { onBackPressed() }

        imageView = binding.imvRoundedSquare

        val imageButton = binding.button
        imageButton.setOnClickListener {
            openImageChooser()
        }
    }

    private fun openImageChooser() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val chooserIntent = Intent.createChooser(takePictureIntent, "Select Action").apply {
            putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickPhotoIntent))
        }

        startActivityForResult(chooserIntent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                1 -> {
                    if (data?.data != null) {
                        val selectedImage: Uri? = data.data
                        imageView.setImageURI(selectedImage)
                    } else {
                        val extras = data?.extras
                        val imageBitmap = extras?.get("data") as? Bitmap
                        imageBitmap?.let {
                            imageView.setImageBitmap(it)
                        }
                    }
                }
            }
        }
    }

    private fun addTexts() {
        textsForButtons()
        textForTitles()
        textForCheckBox()
    }

    private fun textsForButtons() {
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

    private fun textForTitles() {
        val textViewMap: Map<TextView, Int> = mapOf(
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
        textViewMap.forEach { (textView, stringResourceId) ->
            textView.text = getText(stringResourceId)
        }
    }

    private fun textForCheckBox() = with(binding) {
        checkbox1.text = getText(R.string.well_trained)
        checkbox2.text = getText(R.string.curious)
        checkbox3.text = getText(R.string.loud)
    }

    private fun clicked(vararg buttons: Button) {
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val isSelected = button.isSelected

                if (isSelected) button.isSelected = false
                else buttons.forEachIndexed { innerIndex, innerButton ->
                    innerButton.isSelected = (index == innerIndex)
                }
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
