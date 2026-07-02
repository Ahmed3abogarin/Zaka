package com.vtol.zaka.domain.models.auth

data class User(
    val uid: String = "",
    val imgPath: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = ""
)
