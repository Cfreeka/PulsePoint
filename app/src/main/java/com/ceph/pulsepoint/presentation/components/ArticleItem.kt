package com.ceph.pulsepoint.presentation.components


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ceph.pulsepoint.R
import com.ceph.pulsepoint.domain.model.Article


@Composable
fun ArticleItem(
    article: Article,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    if (isBottomSheetVisible) {
        ArticleBottomSheet(
            article = article,
            onDismissRequest = {
                isBottomSheetVisible = false
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { isBottomSheetVisible = true },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp,
            pressedElevation = 10.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )

    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                if (article.urlToImage == null) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_image_not_supported_24),
                            contentDescription = "image",
                            modifier = Modifier.padding(5.dp),
                            tint = Color.LightGray
                        )
                    }

                } else {
                    AsyncImage(
                        model = article.urlToImage,
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.height(180.dp)
                    )
                }
            }
            Text(
                text = article.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(5.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "By ${article.author}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(start = 5.dp)

                )
                FavoriteButton(
                    isFavorite = isFavorite,
                    onClick = onFavoriteClick
                )
            }

        }


    }
}


@Composable
fun FavoriteButton(
    modifier: Modifier = Modifier,
    isFavorite: Boolean,
    onClick: () -> Unit
) {

    FilledIconToggleButton(
        modifier = modifier,
        checked = isFavorite,
        onCheckedChange = { onClick() },
        colors = IconButtonDefaults.iconToggleButtonColors(
            containerColor = Color.Transparent
        )


    ) {
        if (isFavorite) {

            Icon(
                painter = painterResource(id = R.drawable.ic_save_filled),
                contentDescription = "favorite"
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_save_outlined),
                contentDescription = "favorite"
            )

        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleBottomSheet(
    article: Article,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = article.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            AsyncImage(
                model = article.urlToImage,
                contentDescription = "article image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Text(text = "By ${article.author}", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(text = article.description ?: "No description available", fontSize = 14.sp)
            Text(
                text = article.content ?: "No content available", fontSize = 14.sp,
                maxLines = 5
            )
            var isClicked by remember { mutableStateOf(false) }
            TextButton(
                onClick = {
                    accessLink(context = context,uri = article.url)
                    isClicked = true

                }
            ) {
                Text(
                    text = "Read more",
                    color = if (isClicked) Color.Black else Color.Blue
                )

            }
            Button(onClick = onDismissRequest, modifier = Modifier.fillMaxWidth()) {
                Text("Close")
            }
        }
    }


}

fun accessLink(context: Context, uri: String) {

    if (uri.isNotEmpty()) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        context.startActivity(intent)
    }

}