package com.example.project1

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project1.databinding.ActivityMainBinding
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

        binding.actionModeCloseButton.setOnClickListener { onBackPressed() }

        binding.button.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

            builder.setMessage("Which app to use?")
                .setCancelable(false)
                .setPositiveButton("Gallery",
                    DialogInterface.OnClickListener { dialog, _ ->
                        openGallery()
                        dialog.cancel()
                    })
                .setNegativeButton(
                    "CameraX",
                    DialogInterface.OnClickListener { dialog, _ ->
                        val intent = Intent(this, CameraXApp::class.java)
                        resultLauncher.launch(intent)
                        dialog.cancel()
                    })
            val alert: android.app.AlertDialog? = builder.create()
            alert?.setTitle("Open With")
            alert?.show()
        }
    }


    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                when (data?.getStringExtra("source")) {
                    "camera" -> activityResult(1, result.resultCode, data)
                    else -> activityResult(2, result.resultCode, data)
                }
            }
        }

    private fun openGallery() {
        val pickPhotoIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        val chooserIntent = Intent.createChooser(pickPhotoIntent, "Select Action")
        pickPhotoIntent.putExtra("source", "gallery")
        resultLauncher.launch(chooserIntent)
    }

    private fun activityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val imageView1 = binding.imvRoundedSquare2
            val imageView2 = binding.imvRoundedSquare3
            val imageView3 = binding.imvRoundedSquare
            when (requestCode) {
                1 -> {
                    val bitmap = BitmapFactory.decodeStream(
                        this.openFileInput("myImage")
                    )
                    if (imageView1.getDrawable() == null)
                        imageView1.setImageBitmap(bitmap)
                    else if (imageView2.getDrawable() == null)
                        imageView2.setImageBitmap(bitmap)
                    else imageView3.setImageBitmap(bitmap)
                }

                2 -> {
                    if (data?.data != null) {
                        val selectedImage: Uri? = data.data
                        if (imageView1.getDrawable() == null)
                            imageView1.setImageURI(selectedImage)
                        else if (imageView2.getDrawable() == null)
                            imageView2.setImageURI(selectedImage)
                        else imageView3.setImageURI(selectedImage)
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
        binding.button1.text = getText(R.string.text2)
        binding.button2.text = getText(R.string.text3)
        binding.button3.text = getText(R.string.yes)
        binding.button4.text = getText(R.string.yes)
        binding.button5.text = getText(R.string.no)
        binding.button6.text = getText(R.string.calm)
        binding.button7.text = getText(R.string.playful)
        binding.button8.text = getText(R.string.aggressive)
        binding.button9.text = getText(R.string.no)
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
