package com.voltek.core.usecase

import com.voltek.core.data.Note

class GetWordCount {

    operator fun invoke(note: Note): Int {
        return getCount(note.title) + getCount(note.content)
    }

    private fun getCount(string: String): Int {
        return string.split(" ", "\n").count {
            it.contains(Regex(".*[a-zA-Z].*"))
        }
    }
}