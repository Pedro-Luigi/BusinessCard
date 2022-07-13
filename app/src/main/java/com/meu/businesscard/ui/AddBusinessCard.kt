package com.meu.businesscard.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.meu.businesscard.App
import com.meu.businesscard.data.BusinessCard
import com.meu.businesscard.databinding.ActivityAddBusinessCardBinding
import yuku.ambilwarna.AmbilWarnaDialog
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener

class AddBusinessCard : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    companion object{
        var PICKERCOLOR:Int = 0
        var COLOR:String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

    }

    fun PickerColor() {
        val dialog = AmbilWarnaDialog(this, PICKERCOLOR, object : OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog) {}
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                COLOR = "#"+Integer.toHexString(color).uppercase()
                binding.ivViewColor.setColorFilter(color)
            }
        })
        dialog.show()
    }

    private fun insertListeners() {
        binding.btnSelectColor.setOnClickListener {
            PickerColor()
        }
        binding.btnConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                name = binding.tilName.editText?.text.toString(),
                phone = binding.tilPhone.editText?.text.toString(),
                email = binding.tilEmail.editText?.text.toString(),
                company = binding.tilCompany.editText?.text.toString(),
                color = COLOR.toString()
            )
            mainViewModel.insert(businessCard)
            Toast.makeText(this, "CRIADO COM SUCESSO", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}

