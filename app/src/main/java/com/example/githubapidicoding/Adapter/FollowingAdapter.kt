package com.example.githubapidicoding.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapidicoding.Model.User
import com.example.githubapidicoding.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_following.view.*

class FollowingAdapter (

) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val mData = ArrayList<User>()

    class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userItems: User) {
            with(itemView) {
                _rowUsernameFollowing.text = userItems.usrUsername
                Picasso.get().load(Uri.parse(userItems.usrAvatar)).into(_rowAvatarFollowing)
            }
        }

    }

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_following, viewGroup, false)
        return FollowingViewHolder(
            mView
        )
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}