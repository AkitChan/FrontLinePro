package cn.akit.frontlinepro.retrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET

/**
 * Created by chenYuXuan on 2019/7/2.
 * email : southxvii@163.com
 */
class RetrofitDemo {

    fun test(){

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
//                .addCallAdapterFactory()
//                .add
                .build()

        val api = retrofit.create(RetrofitApi::class.java)

        api.call().enqueue(object :Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {

            }

            override fun onResponse(call: Call<String>, response: Response<String>) {

            }
        })



        val client = OkHttpClient()
        val call = client.newCall(Request.Builder().build())
    }

}

interface RetrofitApi {

    @GET
    fun call(): Call<String>

}