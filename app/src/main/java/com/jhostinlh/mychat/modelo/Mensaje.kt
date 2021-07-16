package com.jhostinlh.mychat.modelo

data class Mensaje(
    val id: String = "",
    val nombre: String? = "",
    val pathFoto: Int = 0,
    val fecha: Long = 0,
    val mensaje: String=""

) {

}