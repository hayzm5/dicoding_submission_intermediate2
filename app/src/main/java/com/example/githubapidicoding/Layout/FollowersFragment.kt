package com.example.githubapidicoding.Layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapidicoding.Adapter.FollowersAdapter
import com.example.githubapidicoding.R
import com.example.githubapidicoding.ViewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*



class FollowersFragment : Fragment() {

    private lateinit var followersAdapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    companion object{

        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowersFragment {
            val fragment =
                FollowersFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _followerRecyclerView.apply {

            followersAdapter =
                FollowersAdapter()

            _followerRecyclerView.layoutManager = LinearLayoutManager(activity)
            _followerRecyclerView.adapter = followersAdapter

            followersViewModel = activity?.let {
                ViewModelProvider(
                    it,
                    ViewModelProvider.NewInstanceFactory()
                ).get(FollowersViewModel::class.java)
            }!!

            val username = arguments?.getString(ARG_USERNAME)
            if (username != null) {
                followersViewModel.setUser(username)
            }

            followersViewModel.getUser().observe(activity!!, Observer { user ->
                if (user != null) {
                    followersAdapter.setData(user)
                }
            })

        }
    }

}