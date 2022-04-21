package dev.ogabek.androidmvcpattern.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.ogabek.androidmvcpattern.R
import dev.ogabek.androidmvcpattern.activity.MainActivity
import dev.ogabek.androidmvcpattern.model.Post
import java.util.*
import kotlin.collections.ArrayList

class PostAdapter(val activity: MainActivity, val items: ArrayList<Post>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poster_list, parent, false)
        return PostViewHolder(view)
    }

    class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_poster: LinearLayout = view.findViewById(R.id.ll_poster)
        val tv_title: TextView = view.findViewById(R.id.tv_title)
        val tv_body: TextView = view.findViewById(R.id.tv_body)
        val iv_edit: ImageView = view.findViewById(R.id.iv_edit)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post: Post = items[position]

        if (holder is PostViewHolder) {
            holder.apply {
                tv_title.text = post.title.uppercase(Locale.getDefault())
                tv_body.text = post.body

                iv_edit.setOnClickListener {
                    activity.openUpdateActivity(post)
                }

                ll_poster.setOnLongClickListener {
                activity.deletePostDialog(post)
                    false
                }

            }
        }

    }

    override fun getItemCount() = items.size
}