package com.example.githubapidicoding.Layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapidicoding.Adapter.FollowingAdapter
import com.example.githubapidicoding.R
import com.example.githubapidicoding.ViewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    companion object{

        private val ARG_USERNAME = "username"

        fun newInstance(username: String?): FollowingFragment {
            val fragment =
                FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _followingRecyclerView.apply {

            followingAdapter =
                FollowingAdapter()

            _followingRecyclerView.layoutManager = LinearLayoutManager(activity)
            _followingRecyclerView.adapter = followingAdapter

            followingViewModel = activity?.let {
                ViewModelProvider(
                    it,
                    ViewModelProvider.NewInstanceFactory()
                ).get(FollowingViewModel::class.java)
            }!!

            val username = arguments?.getString(ARG_USERNAME)
            if (username != null) {
                followingViewModel.setUser(username)
            }

            followingViewModel.getUser().observe(activity!!, Observer { user ->
                if (user != null) {
                    followingAdapter.setData(user)
//                    Log.d("Following", user.toString())
                }
            })

        }
    }

}