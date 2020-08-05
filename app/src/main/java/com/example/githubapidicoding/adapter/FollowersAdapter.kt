package com.example.githubapidicoding.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapidicoding.model.User
import com.example.githubapidicoding.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_followers.view.*

class FollowersAdapter(

) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {

    private val mData = ArrayList<User>()

    class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userItems: User) {
            with(itemView) {
                _rowUsernameFollowers.text = userItems.usrUsername
                Picasso.get().load(Uri.parse(userItems.usrAvatar)).into(_rowAvatarFollowers)
            }
        }

    }

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowersViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_followers, viewGroup, false)
        return FollowersViewHolder(
            mView
        )
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}