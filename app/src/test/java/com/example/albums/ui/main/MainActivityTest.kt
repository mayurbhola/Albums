package com.example.albums.ui.main

import com.example.albums.db.data.AlbumsData
import org.junit.Assert
import org.junit.Test

class MainActivityTest {

    @Test
    fun onCreate() {
        val task = listOf(AlbumsData(1, 1, "test"))
        Assert.assertEquals(1, task.size)
    }
}