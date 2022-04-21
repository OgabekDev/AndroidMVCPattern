package dev.ogabek.androidmvcpattern.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dev.ogabek.androidmvcpattern.R
import dev.ogabek.androidmvcpattern.model.Post
import dev.ogabek.androidmvcpattern.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity : AppCompatActivity() {

    private lateinit var et_title: EditText
    private lateinit var et_body: EditText
    private lateinit var btn_create: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        initViews()

    }

    private fun initViews() {

        et_title = findViewById(R.id.et_title)
        et_body = findViewById(R.id.et_body)
        btn_create = findViewById(R.id.btn_create)

        btn_create.setOnClickListener {
            if (!et_title.text.isNullOrEmpty() && !et_body.text.isNullOrEmpty()) {
                createPost(Post(0, 0, et_title.text.toString(), et_body.text.toString()))
            }
        }

    }

    private fun createPost(post: Post) {
        RetrofitHttp.postService.createPost(post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                Log.d("RetrofitHttps", "createPost::onSuccess - $post")
                Toast.makeText(this@CreateActivity, "Saved", Toast.LENGTH_SHORT).show()
                sendResult(true)
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("RetrofitHttps", "createPost::onError - $t")
                Toast.makeText(this@CreateActivity, "Failed Error: $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun sendResult(isDone: Boolean) {
        val intent = Intent()
        intent.putExtra("status", isDone)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        sendResult(false)
    }

}