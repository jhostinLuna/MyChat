package com.jhostinlh.mychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.jhostinlh.mychat.databinding.ActivityElegirGrupoBinding

class ElegirGrupo : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityElegirGrupoBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityElegirGrupoBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        selectSalaChat()

    }
    fun selectSalaChat(){
        binding.imageButtonGrupoAmistad.setOnClickListener(this)
        binding.imageButtonGrupoCine.setOnClickListener(this)
        binding.imageButtonGrupoDeporte.setOnClickListener(this)
        binding.imageButtonGrupoTrabajo.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var myIntent: Intent = Intent(this,SalaChat::class.java)
        if(v?.id == binding.imageButtonGrupoDeporte.id){
            myIntent.putExtra("sala","deporte")
        }
        else if(v?.id == binding.imageButtonGrupoAmistad.id){
            myIntent.putExtra("sala","amistad")

        }
        else if(v?.id == binding.imageButtonGrupoTrabajo.id){
            myIntent.putExtra("sala","trabajo")

        }
        else if(v?.id == binding.imageButtonGrupoCine.id){
            myIntent.putExtra("sala","cine")

        }

        startActivity(myIntent)
    }


}