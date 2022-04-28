package ru.netology.nmedia

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 999,
            shares = 995,
            views = 990,
            likedByMe = false
        )
        with(binding) {
            authorTextView.text = post.author
            publisherTextView.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_like_filed_24)
            }
            likeCount.text = format(post.likes)
            shareCount.text = format(post.shares)
            viewsCount.text = format(post.views)

            like.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_like_filed_24 else R.drawable.ic_like_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                likeCount.text = format(post.likes)
            }

            share.setOnClickListener {
                shareCount.text = format(post.shares++)
            }

            views.setOnClickListener {
                viewsCount.text = format(post.views++)
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
