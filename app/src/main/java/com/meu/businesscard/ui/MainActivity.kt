package com.meu.businesscard.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.meu.businesscard.App
import com.meu.businesscard.data.BusinessCard
import com.meu.businesscard.databinding.ActivityMainBinding
import com.meu.businesscard.util.Image

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy { BusinessCardAdapter() }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPermissions()
        binding.rvCards.adapter = adapter
        getAllBusinessCard()
        insertListeners()
    }

    private fun setUpPermissions() {
        // write permission to access the storage
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
    }

    private fun insertListeners() {
        // Open AddBusinessCard
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity,AddBusinessCard::class.java)
            startActivity(intent)
        }
        //share card
        //TODO Não está abrindo a tela de compartilhar!
        adapter.listenerShare = {
            Image.share(this@MainActivity, card = it)
        }

        adapter.deleteListener = {
            deleteCard(it)
        }
    }

    private fun deleteCard(businessCard: BusinessCard){
        mainViewModel.delete(businessCard)
    }

    private fun getAllBusinessCard() {
        mainViewModel.getAll().observe(this) {
            adapter.submitList(it)
        }
    }

}