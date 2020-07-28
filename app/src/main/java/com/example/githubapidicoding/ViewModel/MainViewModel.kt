package com.example.githubapidicoding.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapidicoding.Layout.MainActivity
import com.example.githubapidicoding.Model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(queryUser: String) {

        val client = AsyncHttpClient()

        val listItems = ArrayList<User>()
        val userUrl: MutableList<String> = ArrayList()

        val searchUrl = "https://api.github.com/search/users?q=$queryUser"

        client.addHeader("Authorization", "token b3b19438e86e9cb535196361874f8153ca90fdd1")
        client.addHeader("User-Agent", "request")

        client.get(searchUrl, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(MainActivity.TAG + " First Layer", result)

                try {
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        userUrl.add(item.getString("url"))
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

                for (i in 0 until userUrl.lastIndex + 1) {
                    client.get(userUrl[i], object : AsyncHttpResponseHandler() {
                        override fun onSuccess(
                            statusCode: Int,
                            headers: Array<Header>,
                            responseBody: ByteArray
                        ) {
                            val resultDetail = String(responseBody)
                            Log.d(MainActivity.TAG + " Second Layer", resultDetail)

                            try {
                                val responseObject = JSONObject(resultDetail)

                                val user =
                                    User()
                                user.usrUsername = responseObject.getString("login")
                                user.usrName = responseObject.getString("name")
                                user.usrAvatar = responseObject.getString("avatar_url")
                                user.usrCompany = responseObject.getString("company")
                                user.usrLocation = responseObject.getString("location")
                                user.usrRepository = responseObject.getInt("public_repos")
                                user.usrFollower = responseObject.getInt("followers")
                                user.usrFollowing = responseObject.getInt("following")
                                listItems.add(user)

                            } catch (e: Exception) {
                                Log.d("Exception", e.message.toString())
                            }

                        listUsers.postValue(listItems)
                        }

                        override fun onFailure(
                            statusCode: Int,
                            headers: Array<Header>,
                            responseBody: ByteArray,
                            error: Throwable
                        ) {
                            Log.d("onFailure", error.message.toString())
                        }

                    })

                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

}