package ru.netology.nmedia.data.impl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import ru.netology.nmedia.dto.Post
import kotlin.properties.Delegates

internal class PostsAdapter(
    private val onLikeClicked: (Post) -> Unit
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) = with(binding) {
            authorTextView.text = post.author
            publisherTextView.text = post.published
            content.text = post.content

            likeCount.text = format(post.likes)
            shareCount.text = format(post.shares)
            viewsCount.text = format(post.views)

            like.setImageResource(
                if (post.likedByMe) ru.netology.nmedia.R.drawable.ic_like_filed_24 else ru.netology.nmedia.R.drawable.ic_like_24
            )

            like.setOnClickListener { onLikeClicked(post) }
//          share.setOnClickListener {viewModel.onShareClicked(post)}
//          views.setOnClickListener {viewModel.views(post)}
        }

        private fun format(number: Int): String {
            val suffix = arrayOf("k", "m", "b", "t")
            var size = if (number != 0) Math.log10(number.toDouble())
                .toInt() else 0
            if (size >= 3) {
                while (size % 3 != 0) {
                    size = size - 1
                }
            }
            val notation = Math.pow(10.0, size.toDouble())
            return if (size >= 3) ((Math.round(number / notation * 100) / 100.0).toString() + suffix[size / 3 - 1]) else number.toString() + ""
        }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {

        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }
}