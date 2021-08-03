package com.jhostinlh.mychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class Registrar : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var btnRegistrar: Button
    lateinit var textViewCorreo: EditText
    lateinit var textViewPassword: EditText
    lateinit var textViewRepitePass: EditText
    lateinit var editTextNombre: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        auth = Firebase.auth
        btnRegistrar = findViewById(R.id.btn_registrar_lReg)
        textViewCorreo = findViewById(R.id.editText_correo_lReg)
        textViewPassword = findViewById(R.id.editText_clave_lReg)
        textViewRepitePass = findViewById(R.id.editText_repetirClave_lReg)
        editTextNombre = findViewById(R.id.editText_nombre_lReg)
    }

    private fun reload() {
        val intent = Intent(this,ElegirGrupo::class.java)
        startActivity(intent)
    }

    fun createAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("registro", "createUserWithEmail:success")
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName =editTextNombre.text.toString()
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("update", "User profile updated.")
                            }
                        }
                    reload()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("registro", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }

    override fun onStart() {
        super.onStart()

            btnRegistrar.setOnClickListener {
                if (textViewPassword.text.toString().equals(textViewRepitePass.text.toString()) && editTextNombre.text.isNotEmpty()) {
                createAccount(textViewCorreo.text.toString(), textViewPassword.text.toString())
                }else{
                    Toast.makeText(this,"¡Las contraseñas no coinciden! ${textViewPassword.text} -- ${textViewRepitePass.text}",Toast.LENGTH_LONG).show()
                }
            }

    }
}