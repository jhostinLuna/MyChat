package com.jhostinlh.mychat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.jakewharton.threetenabp.AndroidThreeTen
import com.jhostinlh.mychat.databinding.ActivitySalaChatBinding
import com.jhostinlh.mychat.modelo.Mensaje
import com.jhostinlh.mychat.recyclerView.AdapterChat
import kotlinx.coroutines.*
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import kotlin.coroutines.CoroutineContext

class SalaChat : AppCompatActivity() {

    lateinit var binding: ActivitySalaChatBinding
    lateinit var recyclerChat: RecyclerView
    private val fireDatabase: FirebaseDatabase = Firebase.database("https://mychat-ae547-default-rtdb.europe-west1.firebasedatabase.app/")
    private val user: FirebaseUser? = Firebase.auth.currentUser
    private val arrayListMensaje = arrayListOf<Mensaje>()
    private lateinit var firebaseCchat: DatabaseReference
    lateinit var adapterRecyclerChat: AdapterChat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySalaChatBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        recyclerChat = binding.recyclerView
        recyclerChat.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)

        AndroidThreeTen.init(this)
        adapterRecyclerChat = AdapterChat(arrayListMensaje)

        firebaseCchat = fireDatabase.reference.child(intent.getStringExtra("sala")!!)

    }

    override fun onStart() {
        super.onStart()

        val adapterRecyclerChat = AdapterChat(arrayListMensaje)
        recyclerChat.adapter = adapterRecyclerChat
        /*
        if (arrayListMensaje.isEmpty()){
            firebaseCchat.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        arrayListMensaje.clear()
                        for (value: DataSnapshot in snapshot.children){
                            value.getValue(Mensaje::class.java)?.let { arrayListMensaje.add(it) }
                        }
                        Log.i("arrayMensajes", "tengo: " + arrayListMensaje.toString())

                        adapterRecyclerChat.setLista(arrayListMensaje)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

         */

        /*
        firebaseCchat.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){


                    if (snapshot.children.last().hasChildren()){
                        val lastMsg: DataSnapshot = snapshot.children.last()
                        lastMsg.getValue(Mensaje::class.java)?.let { arrayListMensaje.add(it) }
                        adapterRecyclerChat.notifyDataSetChanged()

                    }


                }
                Log.i("arrayMensajes", "tengo: " + arrayListMensaje.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SalaChat,"realtime database Cancelado!",Toast.LENGTH_LONG).show()

            }

        })

         */



        firebaseCchat.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.getValue(Mensaje::class.java)?.let { arrayListMensaje.add(it) }
                adapterRecyclerChat.notifyDataSetChanged()
                Log.i("arrayMensajes", "tengo: " + arrayListMensaje.toString())
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



        Log.i("foto",""+user?.photoUrl?.path)
        binding.imageButtonEnviarMa.setOnClickListener {
            val horaActual: LocalDateTime = LocalDateTime.now()
            val zoneDateTime: ZonedDateTime = horaActual.atZone(ZoneId.systemDefault())
            val dt = zoneDateTime.toInstant().toEpochMilli()

            val msg = Mensaje(user!!.uid,user.displayName,R.drawable.fui_ic_twitter_bird_white_24dp,dt,binding.editTextMensajeSalaChat.text.toString())

            if (binding.editTextMensajeSalaChat.text.toString().isNotEmpty()){
                enviarMensaje(msg, dt)
            }else{
                Toast.makeText(this,"¡Escribe un mensaje antes!",Toast.LENGTH_LONG).show()
            }
        }

    }



    override fun onPause() {
        super.onPause()
        arrayListMensaje.clear()
    }

    override fun onStop() {
        super.onStop()
        arrayListMensaje.clear()

    }

    fun enviarMensaje(msg: Mensaje, dt: Long){
        firebaseCchat.child(""+dt).setValue(msg)
        binding.editTextMensajeSalaChat.text.clear()

    }



}