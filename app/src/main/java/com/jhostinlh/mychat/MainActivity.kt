package com.jhostinlh.mychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.jhostinlh.mychat.databinding.ActivityMainBinding
import com.jhostinlh.mychat.databinding.ActivitySalaChatBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var correo: EditText
    lateinit var clave: EditText
    lateinit var btnEntrar:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        auth = Firebase.auth
        correo = findViewById<EditText>(R.id.editText_correo_ma)
        clave = findViewById(R.id.editText_clave_ma)
        btnEntrar = findViewById(R.id.btn_entrar_ma)
    }

    fun sigIn(email: String, password: String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Log.i("prueba", "signInWithEmail:success"+user?.email)


                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.i("prueba", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }

        btnEntrar.setOnClickListener {
            sigIn(correo.text.toString(),clave.text.toString())
        }

    }

    private fun reload() {
        val intent = Intent(this,SalaChat::class.java)
        startActivity(intent)
    }
    fun registrarse(v: View){
        val intent = Intent(this,Registrar::class.java)
        startActivity(intent)
    }
}