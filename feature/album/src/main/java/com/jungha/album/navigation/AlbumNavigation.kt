package com.jungha.album.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.jungha.domain.model.Album
import com.jungha.album.AlbumScreen

fun NavGraphBuilder.albumNavGraph(
    onItemClick:(Album) -> Unit
) {
    composable(AlbumRoute.ROUTE) {
        AlbumScreen {
            onItemClick(it)
        }
    }
}

object AlbumRoute {
    const val ROUTE: String = "album"
}