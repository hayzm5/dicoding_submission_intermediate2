package com.example.githubapidicoding

import android.view.View
import com.example.githubapidicoding.Model.User

interface RecyclerViewClickListener {

    fun onItemClicked(view: View, user: User)

}