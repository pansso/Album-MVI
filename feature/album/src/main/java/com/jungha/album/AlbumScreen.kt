package com.jungha.album

import android.app.PictureInPictureUiState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jungha.designsystem.AlbumCard
import com.jungha.designsystem.HColor
import com.jungha.designsystem.HText
import com.jungha.domain.model.Album
import kotlinx.coroutines.launch

@Composable
internal fun AlbumScreen(
    viewModel: AlbumViewModel = hiltViewModel(),
    onItemClick: (Album) -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()


//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        HandleMediaPermission(
//            onPermissionGranted = {
//
//            }
//        )
//    }

    Scaffold(
        containerColor = HColor.AppBaseColor,
        topBar = { AlbumTopBar() },
        content = { paddingValues ->
            AlbumMainScreen(
                paddingValues = paddingValues,
                uiState = state,
                onItemClick = onItemClick,
                showErrorSnackBar = {
                    scope.launch {
                        snackBarHostState.showSnackbar(it ?: "알 수 없는 오류가 발생했습니다.")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    )
}

@Composable
private fun AlbumTopBar(
) {
    Box(
        modifier = Modifier
            .height(115.dp)
            .fillMaxWidth()
            .shadow(1.dp)
    ) {
        Box(Modifier.padding(start = 28.dp, top = 39.dp)) {
            HText(
                text = "ALBUM LIST",
                color = HColor.Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Box(Modifier.padding(start = 31.dp, top = 87.dp)) {
            HText(
                text = "My Albums",
                color = HColor.Green,
                fontSize = 13.sp,
            )
        }
        Box(Modifier.padding(start = 31.dp, top = 113.dp)) {
            Box(
                modifier = Modifier
                    .height(2.dp)
                    .width(120.dp)
                    .background(color = HColor.Green)
            )
        }
    }
}

@Composable
private fun AlbumMainScreen(
    paddingValues: PaddingValues,
    uiState: AlbumState,
    onItemClick: (Album) -> Unit,
    showErrorSnackBar: (String?) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        if (uiState.loadAlbums.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                HText(
                    text = "앨범이 없습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    color = HColor.Green
                )
            }
        } else {
//            Spacer(modifier = Modifier.height(15.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(uiState.loadAlbums) { index,album ->

                    Column {
                        if (index < 2){
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                        AlbumCard(
                            thumbnail = album.thumbnail,
                            title = album.name,
                            photoCount = album.photoCount,
                            onItemClick = {
                                onItemClick(album)
                            }
                        )
                    }

                }
            }
        }
    }

}