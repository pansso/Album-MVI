package com.jungha.editor.navigator

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jungha.domain.model.Photo
import com.jungha.editor.EditorScreen

fun NavGraphBuilder.editorNavGraph(
    onItemClick: () -> Unit,
    onBackPressed: () -> Unit
) {
    composable(
        route = "${EditorRoute.ROUTE}?photo={photo}",
        arguments = listOf(
            navArgument(EditorRoute.KEY_INTENT_PHOTO) {
                type = NavType.StringType
            }
        )
    ) {
        EditorScreen(
            onBackPressed = onBackPressed,
            onItemClick = onItemClick
        )
    }
}

fun NavController.navigateToEditorNavGraph(photo: Photo) {
    val encodePhoto = Uri.encode(Gson().toJson(photo))
    navigate("${EditorRoute.ROUTE}?photo=$encodePhoto")
}

object EditorRoute {
    const val ROUTE: String = "editor"
    const val KEY_INTENT_PHOTO = "photo"
}