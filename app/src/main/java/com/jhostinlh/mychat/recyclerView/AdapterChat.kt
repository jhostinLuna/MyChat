package com.jhostinlh.mychat.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseUser
import com.jhostinlh.mychat.R
import com.jhostinlh.mychat.modelo.Mensaje
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

class AdapterChat constructor(val arraylistPersonas: ArrayList<Mensaje>): RecyclerView.Adapter<AdapterChat.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_chat,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.imgPerfil.setImageResource(R.drawable.usuario)
        holder.txtFecha.text = getdateformatted(arraylistPersonas.get(position).fecha,"HH:mm")
        holder.textMensaje.text = arraylistPersonas.get(position).mensaje
        holder.txtNombre.text = arraylistPersonas.get(position).nombre
    }

    override fun getItemCount(): Int {
        return arraylistPersonas.size
    }


    class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txt_nombre_itemChat)
        val txtFecha: TextView = itemView.findViewById(R.id.text_fecha_itemchat)
        val textMensaje :TextView = itemView.findViewById(R.id.text_mensaje_itemChat)
        val imgPerfil :ImageView = itemView.findViewById(R.id.imageView_perfil)
    }
    fun getdateformatted(milliseconds: Long, pattern: String): String {

        val instant = Instant.ofEpochMilli(milliseconds)
        val date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        var auxdateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)
        return date.format(auxdateTimeFormatter)
    }

}