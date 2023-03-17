package com.example.firestorge1

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.firestorge1.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class MainActivity : AppCompatActivity() {
    val reqcode:Int = 100
    lateinit var imagePath:Uri
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val storage = Firebase.storage
        val ref = storage.reference
        binding.chose.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,reqcode)

        }
        binding.upload.setOnClickListener {
            ref.child("Images/:" +UUID.randomUUID().toString()).putFile(imagePath).
            addOnSuccessListener {

                Toast.makeText(applicationContext,"uploade is done",Toast.LENGTH_SHORT).show()
            }


                .addOnFailureListener {

                    Toast.makeText(applicationContext,"please / Uploade is Fail",Toast.LENGTH_SHORT).show()

                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==resultCode){
            imagePath = data!!.data!!
            val bitMap = MediaStore.Images.Media.getBitmap(contentResolver,imagePath)
            binding.imageView.setImageBitmap(bitMap)

        }
    }
}