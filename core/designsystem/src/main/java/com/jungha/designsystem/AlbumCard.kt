package com.jungha.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.FlowPreview

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    thumbnail: String?,
    title: String,
    photoCount: Int,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = HColor.AlbumBaseColor,
        ),
        shape = RoundedCornerShape(3.dp),
        onClick = onItemClick
    ) {
        Column(
            modifier = Modifier
                .height(219.dp)
                .width(170.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(170.dp)
                    .fillMaxWidth()
            ) {
                if (thumbnail.isNullOrEmpty()) {
                    HImage(
                        modifier = Modifier.fillMaxSize(),
                        model = painterResource(id = R.drawable.baseline_adb_24),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(HColor.White)
                    )
                } else {
                    HImage(
                        model = thumbnail,
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            HText(
                text = title,
                color = HColor.Green,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 9.dp, top = 9.dp)
            )
            HText(
                text = "$photoCount Images",
                color = HColor.Green,
                fontSize = 11.sp,
                modifier = Modifier.padding(top = 3.dp, start = 9.dp)
            )
        }
    }
}

