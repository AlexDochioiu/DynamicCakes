package com.github.alexdochioiu.main_feature_common_objects

import com.squareup.moshi.Json

data class Cake(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "desc") val description: String,
    @field:Json(name = "image") val image_url: String
)