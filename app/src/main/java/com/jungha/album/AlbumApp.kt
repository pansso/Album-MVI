package com.jungha.album

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jungha.album.navigation.AlbumRoute
import com.jungha.album.navigation.albumNavGraph
import com.jungha.editor.navigator.editorNavGraph
import com.jungha.editor.navigator.navigateToEditorNavGraph
import com.jungha.photo.navigation.navigateToPhotoNavGraph
import com.jungha.photo.navigation.photoNavGraph

@Composable
internal fun AlbumApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AlbumRoute.ROUTE
    ) {
        albumNavGraph {
            navController.navigateToPhotoNavGraph(it)
        }

        photoNavGraph(onItemClick = {
            navController.navigateToEditorNavGraph(it)
        }) {
            navController.popBackStack()
        }

        editorNavGraph(onItemClick = {
//            navController.navigateToPhotoNavGraph()
        }){
            navController.popBackStack()
        }
    }
}