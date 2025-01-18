package gay.depau.worldclocktile.presentation.views

// SPDX-License-Identifier: Apache-2.0
// This file is part of World Clock Tile.

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.remote.interactions.RemoteActivityHelper
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.compose.snackbar.SnackbarHostState
import gay.depau.worldclocktile.BuildConfig
import gay.depau.worldclocktile.composables.MainView
import gay.depau.worldclocktile.composables.ScalingLazyColumnWithRSB
import gay.depau.worldclocktile.shared.utils.GetCustomContents
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import gay.depau.worldclocktile.shared.R as sharedR



@Composable
private fun simpleStringWithLink(prefix: String, linkText: String, link: String) = buildAnnotatedString {
    val str = prefix + linkText
    append(str)
    val startIndex = str.indexOf(linkText)
    val endIndex = startIndex + linkText.length

    addStyle(
        style = SpanStyle(
            color = MaterialTheme.colors.primary, textDecoration = TextDecoration.Underline
        ), start = startIndex, end = endIndex
    )
    addStringAnnotation(
        tag = "URL", annotation = link, start = startIndex, end = endIndex
    )
}


@Composable
fun FilePickerView() {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 1)
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val photoPicker = rememberLauncherForActivityResult(
        contract = GetCustomContents(isMultiple = false),
        onResult = { uris ->
            val urisJoined =  uris.joinToString(", ")
            scope.launch {
                snackbarHostState.showSnackbar("Uri's: $urisJoined")
            }
        })

    MainView(listState = listState) {
        ScalingLazyColumnWithRSB(
            state = listState,
            autoCentering = AutoCenteringParams(itemIndex = 1),
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp, alignment = Alignment.Top
            ),
        ) {
            item {
                Text(
                    softWrap = true, textAlign = TextAlign.Center, text = "Choose file",
                    style = MaterialTheme.typography.title1
                )
                Button({photoPicker.launch("image/*")}) {
                    Text("Open File Picker")

                }
            }
        }
    }
}

@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true, name = "Choose file screen")
@Composable
fun FilePickerViewPreview() {
    FilePickerView()
}