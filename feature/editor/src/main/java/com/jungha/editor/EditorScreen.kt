package com.jungha.editor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.caverock.androidsvg.SVG
import com.jungha.designsystem.HColor
import com.jungha.designsystem.HImage
import com.jungha.designsystem.HText
import com.jungha.designsystem.R
import com.jungha.designsystem.clickableWithNoRipple
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

@Composable
internal fun EditorScreen(
    onBackPressed: () -> Unit,
    onItemClick: () -> Unit,
    viewModel: EditorViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = HColor.AppBaseColor,
        topBar = {
            EditorTopBar(
                onBackPressed = onBackPressed,
                title = state.selectedPhoto?.albumName ?: "Album",
                isOverlayButtonVisible = !state.selectSvgImage.isNullOrEmpty()
            )
        },
        content = { paddingValues ->
            state.selectedPhoto?.let {
                EditorMainScreen(
                    padding = paddingValues,
                    uiState = state,
                    onItemClick = {
                        viewModel.onEvent(EditorEvent.SelectSvgImage(it))
                    },
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
fun EditorTopBar(
    onBackPressed: () -> Unit,
    title: String,
    isOverlayButtonVisible: Boolean,
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
        if (isOverlayButtonVisible) {
            Box(
                modifier = Modifier
                    .padding(start = 238.dp, top = 30.dp)
                    .height(33.dp)
                    .width(103.dp)
                    .background(HColor.Green, shape = RoundedCornerShape(64)),
                contentAlignment = Alignment.Center
            ) {
                HText(text = "Overlay", color = Color.White, fontSize = 12.sp)
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun EditorMainScreen(
    padding: PaddingValues,
    uiState: EditorState,
    onItemClick: (String) -> Unit,
    showErrorSnackBar: (String?) -> Unit,
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Box(
            modifier = Modifier
                .height(412.dp)
                .fillMaxWidth()
        ) {

            HImage(model = uiState.selectedPhoto?.image, contentScale = ContentScale.Crop)
            if (!uiState.selectSvgImage.isNullOrEmpty()) {
                Timber.d("sjh select image ${uiState.selectSvgImage}")
                val bitmap = loadSvgAsBitmap(context, uiState.selectSvgImage)
                GlideImage(
                    model = bitmap,
                    contentDescription = "",
                    modifier = Modifier
                        .size(240.dp)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(HColor.White),
            contentAlignment = Alignment.CenterStart
        ) {
            SvgImageList(uiState.svgPath) {
                onItemClick(it)

            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun SvgImageList(svgPaths: List<String>, onSvgSelected: (String) -> Unit) {

    val context = LocalContext.current
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp, end = 40.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(svgPaths.size) { svgPath ->

            val bitmap = loadSvgAsBitmap(context, "svg/${svgPaths[svgPath]}")

            bitmap?.let {
                Box(
                    modifier = Modifier
                        .size(80.dp) // 이미지 크기 설정
                        .border(width = 2.dp, color = Color.Black)
                        .clickableWithNoRipple {
                            onSvgSelected("svg/${svgPaths[svgPath]}")
                        }
                ) {
                    GlideImage(
                        model = it,
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center),
                        contentScale = ContentScale.Fit
                    )
                }


            }
        }
    }
}


private fun loadSvgAsBitmap(context: Context, assetFilePath: String): Bitmap? {
    try {
        val assetManager = context.assets
        val inputStream: InputStream = assetManager.open(assetFilePath)
        val svg = SVG.getFromInputStream(inputStream)
        val picture = svg.renderToPicture()

        val bitmap = Bitmap.createBitmap(
            picture.width, picture.height, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        picture.draw(canvas)

        return bitmap
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}