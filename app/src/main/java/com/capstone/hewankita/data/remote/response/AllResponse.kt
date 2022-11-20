package com.capstone.hewankita.data.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,

    @field:SerializedName("listStory")
    val listStory: List<ListStoryItem>
): Parcelable

@Parcelize
data class LoginResult(

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("token")
    var token: String? = null,

    @field:SerializedName("isLogin")
    var isLogin: Boolean = false
): Parcelable

@Parcelize
@Entity(tableName = "story")
data class ListStoryItem(

    @field:PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("photoUrl")
    val photoUrl: String,
): Parcelable
