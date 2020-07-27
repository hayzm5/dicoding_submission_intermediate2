package com.example.githubapidicoding.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapidicoding.Model.User
import com.example.githubapidicoding.R
import com.example.githubapidicoding.RecyclerViewClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_row_githubuser.view.*

class UserAdapter (

//    private val listener: (User) -> Unit

): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mData = ArrayList<User>()

    var listener: RecyclerViewClickListener? = null

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(userItems: User) {
            with(itemView){
                _rowName.text = userItems.usrUsername
                Picasso.get().load(Uri.parse(userItems.usrAvatar)).into(_rowAvatar)
                _rowCompany.text = userItems.usrCompany
            }
        }

    }

    fun setData(items: ArrayList<User>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_githubuser, viewGroup, false)
        return UserViewHolder(
            mView
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, mData[position])
        }
    }

    override fun getItemCount(): Int = mData.size
}