package dev.ogabek.androidmvcpattern.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import dev.ogabek.androidmvcpattern.R
import dev.ogabek.androidmvcpattern.model.Post
import dev.ogabek.androidmvcpattern.networking.RetrofitHttp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateActivity : AppCompatActivity() {
    private lateinit var et_title: EditText
    private lateinit var et_body: EditText
    private lateinit var btn_update: Button
    lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        initViews()

        getData()

    }

    private fun getData() {
        post = intent.getSerializableExtra("post") as Post
        et_title.setText(post.title)
        et_body.setText(post.body)
    }

    private fun initViews() {

        et_title = findViewById(R.id.et_title_update)
        et_body = findViewById(R.id.et_body_update)
        btn_update = findViewById(R.id.btn_update)

        btn_update.setOnClickListener {
            if (!et_title.text.isNullOrEmpty() && !et_body.text.isNullOrEmpty()) {
                updatePost(Post(post.id, post.userId, et_title.text.toString(), et_body.text.toString()))
            }
        }

    }

    private fun updatePost(post: Post) {
        RetrofitHttp.postService.updatePost(post.id, post).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                Log.d("RetrofitHttps", "createPost::onSuccess - $post")
                Toast.makeText(this@UpdateActivity, "Updated", Toast.LENGTH_SHORT).show()
                sendResult(true)
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("RetrofitHttps", "createPost::onError - $t")
                Toast.makeText(this@UpdateActivity, "Failed Error: $t", Toast.LENGTH_SHORT).show()
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