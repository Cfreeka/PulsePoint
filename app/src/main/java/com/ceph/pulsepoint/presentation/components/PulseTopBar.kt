package com.ceph.pulsepoint.presentation.components


import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ceph.pulsepoint.ui.theme.primaryContainerDarkMediumContrast
import com.ceph.pulsepoint.ui.theme.primaryContainerLightMediumContrast
import com.ceph.pulsepoint.ui.theme.surfaceBrightLight


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PulseTopBar(
    scrollBehavior: TopAppBarScrollBehavior
) {

    TopAppBar(

        modifier = Modifier.clip(
            RoundedCornerShape(
                bottomStart = 15.dp,
                bottomEnd = 15.dp
            )
        ),
        scrollBehavior = scrollBehavior,
        title = {

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = surfaceBrightLight,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("Pulse")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = primaryContainerDarkMediumContrast,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    ) {
                        append("Point")
                    }

                }
            )
        },

        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = primaryContainerLightMediumContrast
        )
    )

}