package com.dexter.socialmedia.View

class PostModel {
    private var postId: String = ""
    private var postImage: String = ""
    private var publisher: String = ""
    private var caption: String = ""

    constructor(postId: String, postImage: String, publisher: String, caption: String) {
        this.postId = postId
        this.postImage = postImage
        this.publisher = publisher
        this.caption = caption
    }

    fun getPostId(): String {
        return postId
    }

    fun getPostImage(): String {
        return postImage
    }

    fun getCaption(): String {
        return caption
    }

    fun getPublisher(): String {
        return publisher
    }

    fun setPostId(postId: String) {
        this.postId = postId
    }

    fun setPostImage(postImage: String) {
        this.postImage = postImage
    }

    fun setPublisher(publisher: String) {
        this.publisher = publisher
    }

    fun setCaption(caption: String) {
        this.caption = caption
    }

}
