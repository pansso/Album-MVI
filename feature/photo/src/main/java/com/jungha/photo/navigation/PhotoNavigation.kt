package com.jungha.photo.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jungha.domain.model.Album
import com.jungha.domain.model.Photo
import com.jungha.photo.PhotoScreen

fun NavGraphBuilder.photoNavGraph(
    onItemClick: (Photo) -> Unit,
    onBackPressed: () -> Unit
) {
    composable(
        route = "${PhotoRoute.ROUTE}?album={album}",
        arguments = listOf(
            navArgument(PhotoRoute.KEY_INTENT_ALBUM) {
                type = NavType.StringType
            }
        )
    ) {
        PhotoScreen(
            onBackPressed = onBackPressed,
            onItemClick = onItemClick
        )
    }
}

fun NavController.navigateToPhotoNavGraph(album: Album) {
    val encodeAlbum = Uri.encode(Gson().toJson(album))
    navigate("${PhotoRoute.ROUTE}?album=$encodeAlbum")
}

object PhotoRoute {
    const val ROUTE: String = "photo"
    const val KEY_INTENT_ALBUM = "album"
}