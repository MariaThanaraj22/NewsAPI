package com.maria.newsapi.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class KotlinMainResponse : Serializable {
    @SerializedName("status")
    var status: String? = null

    @SerializedName("totalResults")
    var totalResults: Int? = null

    @SerializedName("articles")
    var data: ArrayList<Article>? = null

}

class Article : Serializable {
    @SerializedName("source")
    var source: Source? = null

    @SerializedName("author")
    var author: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("url")
    var url: String? = null

    @SerializedName("urlToImage")
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    var publishedAt: String? = null

    @SerializedName("content")
    var content: String? = null
}

class Source : Serializable {
    @SerializedName("id")
    var id: Any? = null

    @SerializedName("name")
    var name: String? = null
}