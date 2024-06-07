package com.example.project1

import ButtonsViewModel
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
import com.example.project1.databinding.FragmentSecondBinding


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private val buttonViewModel: ButtonsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addTexts()
        checkIfClicked()
        binding.actionModeCloseButton.setOnClickListener {
            findNavController().navigateUp()
        }
        askForApp()


    }

    private fun askForApp() {
        binding.button.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
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
                        val intent = Intent(context, CameraXApp::class.java)
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
            when (requestCode) {
                1 -> {
                    val bitmap = BitmapFactory.decodeStream(
                        context?.openFileInput("myImage")
                    )
                    if (binding.imvRoundedSquare2.getDrawable() == null)
                        binding.imvRoundedSquare2.setImageBitmap(bitmap)
                    else if (binding.imvRoundedSquare3.getDrawable() == null)
                        binding.imvRoundedSquare3.setImageBitmap(bitmap)
                    else binding.imvRoundedSquare.setImageBitmap(bitmap)
                }

                2 -> {
                    if (data?.data != null) {
                        val selectedImage: Uri? = data.data
                        if (binding.imvRoundedSquare2.getDrawable() == null)
                            binding.imvRoundedSquare2.setImageURI(selectedImage)
                        else if (binding.imvRoundedSquare3.getDrawable() == null)
                            binding.imvRoundedSquare3.setImageURI(selectedImage)
                        else binding.imvRoundedSquare.setImageURI(selectedImage)
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

        buttons.forEach { button ->
            buttonViewModel.buttonStates.observe(viewLifecycleOwner, Observer { buttonStates ->
                buttonStates?.let {
                    button.isSelected = it[button.id] == true
                }
            })

            button.setOnClickListener {
                val newState = !(buttonViewModel.buttonStates.value?.get(button.id) ?: false)

                if (newState) {
                    buttons.forEach {
                        buttonViewModel.setButtonState(it.id, (it.id == button.id))
                    }
                } else {
                    buttonViewModel.setButtonState(button.id, false)
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