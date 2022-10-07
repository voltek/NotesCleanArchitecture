package com.voltek.core.data

import com.voltek.core.utils.Constants.EMPTY_STRING

data class Note(
    var title: String = EMPTY_STRING,
    var content: String = EMPTY_STRING,
    var creationTime: Long = 0,
    var updateTime: Long = 0,
    var id: Long = 0,
)
