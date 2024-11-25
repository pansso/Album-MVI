package com.jungha.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jungha.designsystem.HColor
import com.jungha.designsystem.HImage
import com.jungha.designsystem.HText
import com.jungha.designsystem.R
import com.jungha.designsystem.clickableWithNoRipple
import com.jungha.domain.model.Photo
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
internal fun PhotoScreen(
    onBackPressed: () -> Unit,
    onItemClick: (Photo) -> Unit,
    viewModel: PhotoViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        containerColor = HColor.AppBaseColor,
        topBar = {
            PhotoTopBar(
                onBackPressed = onBackPressed,
                title = state.photoList?.first()?.albumName ?: "Album"
            )
        },
        content = { paddingValues ->
            state.photoList?.let {
                PhotoMainScreen(
                    padding = paddingValues,
                    uiState = state,
                    onItemClick = onItemClick,
                    showErrorSnackBar = {
                        scope.launch {
                            snackBarHostState.showSnackbar(it ?: "알 수 없는 오류가 발생했습니다.")
                        }
                    }
                )
            }
        },
    )

}

@Composable
fun PhotoTopBar(
    onBackPressed: () -> Unit,
    title: String
) {
    Box(
        modifier = Modifier
            .height(77.dp)
            .fillMaxWidth()
            .shadow(1.dp)
    ) {
        Row(
            Modifier
                .padding(top = 40.dp)
                .height(26.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                Modifier
                    .clickableWithNoRipple(
                        onClick = onBackPressed
                    )
                    .padding(start = 9.dp)
            ) {
                HImage(
                    model = painterResource(id = R.drawable.backbtn),
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(
                modifier = Modifier
                    .width(17.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .background(HColor.BarColor)
            )
            HText(
                text = title,
                color = HColor.Green,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 17.dp)
            )
        }
    }
}

@Composable
private fun PhotoMainScreen(
    padding: PaddingValues,
    uiState: PhotoState,
    onItemClick: (Photo) -> Unit,
    showErrorSnackBar: (String?) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        if (uiState.photoList?.isEmpty() == true) {
            Box(modifier = Modifier.fillMaxSize()) {
                HText(
                    text = "앨범이 없습니다.",
                    modifier = Modifier.align(Alignment.Center),
                    color = HColor.Green
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                uiState.photoList?.let {
                    itemsIndexed(uiState.photoList) { index, photo ->
                        Column {
                            if (index < 3) {
                                Spacer(modifier = Modifier.height(15.dp))
                            }
                            Box(
                                modifier = Modifier
                                    .height(119.dp)
                                    .clickableWithNoRipple {
                                        Timber.d("sjh pcik photo $photo")
                                        onItemClick(photo) }
                            ) {
                                HImage(model = photo.image, contentScale = ContentScale.Crop)
                            }
                        }

                    }
                }

            }
        }
    }
}