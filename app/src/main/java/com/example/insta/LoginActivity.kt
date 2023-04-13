package com.example.insta

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var mAuth:FirebaseAuth
    private lateinit var login:Button
    private lateinit var registerUser:TextView
    private lateinit var mProgress: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email=findViewById(R.id.email)
        password=findViewById(R.id.password)
        login=findViewById(R.id.login)
        registerUser=findViewById(R.id.registerUser)

        mAuth= FirebaseAuth.getInstance()

        registerUser.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }

        login.setOnClickListener {
            val txtEmail=email.text.toString()
            val txtPassword= password.text.toString()
            if(txtEmail.isEmpty() || txtPassword.isEmpty()){
                Toast.makeText(this,"Empty Credentials",Toast.LENGTH_SHORT).show()
            }
            else{
                loginUser(txtEmail,txtPassword)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        showProgressDialog()
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if(it.isSuccessful){
                    dismissProgressDialog()
                    Toast.makeText(this,"Update the profile",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }.addOnFailureListener{
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStart() {
        super.onStart()
        if(mAuth.currentUser!=null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    private fun showProgressDialog() {
        mProgress=Dialog(this)
        mProgress.setContentView(R.layout.progress_dialog)
        mProgress.show()
    }

    private fun dismissProgressDialog() {
        mProgress.dismiss()
    }
}