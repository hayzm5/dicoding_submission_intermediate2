package com.example.githubapidicoding.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubapidicoding.layout.MainActivity
import com.example.githubapidicoding.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(queryUser: String) {
        try {
            val client = AsyncHttpClient()

            val listItems = ArrayList<User>()
//        val userUrl: MutableList<String> = ArrayList()

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

                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        client.get(item.getString("url"), object : AsyncHttpResponseHandler() {
                            override fun onSuccess(
                                statusCode: Int,
                                headers: Array<Header>,
                                responseBody: ByteArray
                            ) {
                                val resultDetail = String(responseBody)
                                Log.d(MainActivity.TAG + " Second Layer", resultDetail)

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

                                listUsers.postValue(listItems)
                            }

                            override fun onFailure(
                                statusCode: Int,
                                headers: Array<Header>,
                                responseBody: ByteArray,
                                error: Throwable
                            ) {
                                Log.d("onFailure detail", error.message.toString())
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
                    Log.d("onFailure header", error.message.toString())
                }

            })

        } catch (e: Exception) {
            Log.d("Exception", e.message.toString())
        }
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

}