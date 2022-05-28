package com.example.myapplication


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivitySelectImageBinding
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*
import kotlin.collections.HashMap

class SelectImageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var selectImage: Uri
    private lateinit var dialog: AlertDialog
    private lateinit var binding: ActivitySelectImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_image)
        binding = ActivitySelectImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        binding.userImage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        binding.Button.setOnClickListener{
            uploadImage()
            //메인 페이지로 이동
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                selectImage = data.data!!
                binding.userImage.setImageURI(selectImage)
            }
        }
    }

    private fun uploadImage() {
        databaseReference =
            FirebaseDatabase.getInstance().getReference().child("Users").child(auth.uid!!).child("userImage")
        storageReference = FirebaseStorage.getInstance().getReference().child("Image").child(auth.uid!!)
        var hashmap: HashMap<String, String> = HashMap()
        hashmap.put("userImage",selectImage.toString())

        databaseReference.setValue(hashmap)
        storageReference.putFile(selectImage)
    }
}