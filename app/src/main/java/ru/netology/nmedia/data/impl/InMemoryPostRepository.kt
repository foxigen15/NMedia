package ru.netology.nmedia.data.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.dto.Post

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(100) { index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Some random content $index",
                published = "21 мая в 18:36",
                likes = 999,
                shares = 995,
                views = 999,
                likedByMe = false
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else {
                val likedPost = it.copy(likedByMe = !it.likedByMe)
                if (it.likedByMe) likedPost.likes-- else likedPost.likes++
                likedPost
            }
        }
    }

    override fun share(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else {
                val sharedPost = it.copy()
                sharedPost.shares++
                sharedPost
            }
        }
    }

//    override fun views(postId: Long) {
//        data.value = posts.map {
//            if (it.id != postId) it
//            else {
//                val viewededPost = it.copy()
//                viewededPost.views++
//                viewededPost
//            }
//        }
//    }


}