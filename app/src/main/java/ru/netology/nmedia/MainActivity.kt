package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->

            with(binding) {
                authorTextView.text = post.author
                publisherTextView.text = post.published
                content.text = post.content

                likeCount.text = format(post.likes)
                shareCount.text = format(post.shares)
                viewsCount.text = format(post.views)

                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_like_filed_24 else R.drawable.ic_like_24
                )

            }
            binding.like.setOnClickListener {
                viewModel.onLikeClicked()
            }

            binding.share.setOnClickListener{
                viewModel.onShareClicked()
            }

            binding.views.setOnClickListener{
                viewModel.views()
            }

        }
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
