package com.example.githubapidicoding.Layout

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapidicoding.Adapter.UserAdapter
import com.example.githubapidicoding.Model.User
import com.example.githubapidicoding.R
import com.example.githubapidicoding.RecyclerViewClickListener
import com.example.githubapidicoding.ViewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    RecyclerViewClickListener {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        _userRecyclerView.layoutManager = LinearLayoutManager(this)
        _userRecyclerView.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.getUser().observe(this, Observer { user ->
            if (user != null) {
                adapter.setData(user)
                showLoading(false)
            }
        })
        adapter.listener = this
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                main_instruction.text = ""
                showLoading(true)
                mainViewModel.setUser(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_about -> {
                val intentAbout = Intent(this, AboutActivity::class.java)
                startActivity(intentAbout)
                return true
            }
            else -> return true
        }
    }

    override fun onItemClicked(view: View, user: User) {
        val detailIntent = Intent(this, DetailActivity::class.java)
        detailIntent.putExtra("avatar", user.usrAvatar)
        detailIntent.putExtra("username",user.usrUsername)
        detailIntent.putExtra("name",user.usrName)
        detailIntent.putExtra("company",user.usrCompany)
        detailIntent.putExtra("location",user.usrLocation)
        detailIntent.putExtra("followers",user.usrFollower)
        detailIntent.putExtra("following",user.usrFollowing)
        startActivity(detailIntent)
    }
}