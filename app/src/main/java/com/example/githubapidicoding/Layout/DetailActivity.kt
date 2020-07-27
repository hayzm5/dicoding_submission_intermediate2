package com.example.githubapidicoding.Layout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapidicoding.Adapter.SectionsPagerAdapter
import com.example.githubapidicoding.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val avatar = intent.getStringExtra("avatar")
        val username = intent.getStringExtra("username")
        val name = intent.getStringExtra("name")
        val company = intent.getStringExtra("company")
        val location = intent.getStringExtra("location")
        val followers = intent.getIntExtra("followers", 0)
        val following = intent.getIntExtra("following", 0)

        Picasso.get().load(Uri.parse(avatar)).into(detail_avatar)
        detail_name.text = name
        detail_username.text = username
        detail_company.text = company
        detail_location.text = location

//        Toast.makeText(applicationContext,username,Toast.LENGTH_LONG).show()

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        sectionsPagerAdapter.username = username
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f

        tabs.getTabAt(0)?.setText("FOLLOWERS: " + followers)
        tabs.getTabAt(1)?.setText("FOLLOWING: " + following)
        tabs.getTabAt(0)?.setIcon(R.drawable.followers_icon)
        tabs.getTabAt(1)?.setIcon(R.drawable.following_icon)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.launch_menu_detail, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_launch -> {
                val usernameGithub = detail_username.text
                val urlGithub = "http://github.com/"
                val implicit =
                    Intent(Intent.ACTION_VIEW, Uri.parse(urlGithub + usernameGithub))
                startActivity(implicit)
                return true
            }
            else -> return true
        }
    }
}