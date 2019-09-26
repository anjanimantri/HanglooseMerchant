package com.vungle.hangloosemerchant.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val email: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val mobile: String,
    val restaurantId: String
)