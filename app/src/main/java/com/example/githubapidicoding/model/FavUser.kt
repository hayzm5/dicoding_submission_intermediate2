package com.example.githubapidicoding.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavUser(
    var _id: Int = 0,
    var username: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var company: String? = null,
    var location: String? = null,
    var follower: String? = null,
    var following: String? = null,
    var favorite: String? = null
) : Parcelable