package com.vungle.hangloosemerchant.model

import com.google.gson.annotations.SerializedName

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

data class RestaurantList(
    @SerializedName("address") var address: String?,
    @SerializedName("createdAt") var createdAt: String?,
    @SerializedName("discount") var discount: String?,
    @SerializedName("id") var id: String?,
    @SerializedName("images") var images: List<String>?,
    @SerializedName("latitude") var latitude: String?,
    @SerializedName("longitude") var longitude: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("offer") var offer: String?,
    @SerializedName("priceFortwo") var priceFortwo: String?,
    @SerializedName("ratings") var ratings: String?,
    @SerializedName("restaurantType") var restaurantType: String?,
    @SerializedName("updatedAt") var updatedAt: String?,
    @SerializedName("distanceFromLocation") var distanceFromLocation: Double?,
    @SerializedName("about") var about: String?,
    @SerializedName("tags") var tags: List<String>?,
    @SerializedName("openCloseTime") var openCloseTime: String?,
    @SerializedName("number") var number: String?,
    @SerializedName("documents") var documents: List<Document>?,
    @SerializedName("likes") var likes: String?,
    @SerializedName("disLikes") var disLikes: String?
)

data class Document(
    @SerializedName("createdAt") var createdAt: String?,
    @SerializedName("updatedAt") var updatedAt: String?,
    @SerializedName("id") var id: String?,
    @SerializedName("location") var location: String?,
    @SerializedName("ownerId") var ownerId: String?,
    @SerializedName("documentType") var documentType: String?
)