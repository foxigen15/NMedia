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
        List(100) {index ->
            Post(
                id = index + 1L,
                author = "Netology",
                content = "Some random content $index",
                published = "21 мая в 18:36",
                likes = 999,
                shares = 995,
                views = 990,
                likedByMe = false
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map{
            if(it.id != postId) it
            else it.copy(likedByMe = !it.likedByMe)
        }



//        val currentPost = checkNotNull(data.value) {
//            "Data value should not be null"
//        }
//        val likedPost = currentPost.copy(
//            likedByMe = !currentPost.likedByMe
//        )
//        if (currentPost.likedByMe) likedPost.likes-- else likedPost.likes++
//
//        data.value = likedPost

    }

//    override fun share() {
//        val currentPost = checkNotNull(data.value) {
//            "Data value should not be null"
//        }
//        currentPost.shares++
//        data.value = currentPost
//    }
//
//    override fun views() {
//        val currentPost = checkNotNull(data.value) {
//            "Data value should not be null"
//        }
//        currentPost.views++
//        data.value = currentPost
//    }
}