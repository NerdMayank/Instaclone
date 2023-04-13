package com.example.insta

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var register: Button
    private lateinit var loginUser: TextView
    private lateinit var mProgress: Dialog
    private lateinit var mRootRef: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userName = findViewById(R.id.userName)
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        register = findViewById(R.id.register)
        loginUser = findViewById(R.id.loginUser)

        mRootRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()



        loginUser.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }


        register.setOnClickListener {
            val txtUserName = userName.text.toString()
            val txtName = name.text.toString()
            val txtEmail = email.text.toString()
            val txtPassword = password.text.toString()

            if (txtEmail.isEmpty() || txtUserName.isEmpty() || txtName.isEmpty() || txtEmail.isEmpty() || txtPassword.isEmpty()) {
                Toast.makeText(this, "Empty credentials", Toast.LENGTH_SHORT).show()
            } else if (txtPassword.length < 6) {
                Toast.makeText(this, "Password too short!", Toast.LENGTH_LONG).show()
            } else {
                registerUser(txtUserName, txtName, txtEmail, txtPassword)
            }

        }

    }

    private fun registerUser(
        userName: String,
        name: String,
        email: String,
        password: String
    ) {
        showProgressDialog()
        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            val map= HashMap<String, Any>()
            map["name"] = name
            map["email"]=email
            map["username"]=userName
            map["id"]=mAuth.currentUser!!.uid
            map["bio"]=""
            map["imageUrl"]="default"

            mRootRef.child("Users").child(mAuth.currentUser!!.uid).setValue(map).addOnCompleteListener {
                if(it.isSuccessful){
                    dismissProgressDialog()
                    Toast.makeText(this,"Update the profile",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }.addOnFailureListener {
            dismissProgressDialog()
            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgressDialog(){
        mProgress=Dialog(this)
        mProgress.setContentView(R.layout.progress_dialog)
        mProgress.show()
    }
    private fun dismissProgressDialog(){
        if(mProgress.isShowing){
            mProgress.dismiss()
        }
    }
}