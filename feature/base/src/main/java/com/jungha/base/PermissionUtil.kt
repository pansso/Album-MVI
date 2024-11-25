package com.jungha.base

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

object PermissionUtil {
    @Composable
    fun HandleMediaPermission(
        onPermissionGranted:() -> Unit
    ) {
        var permissionStatus by remember { mutableStateOf(PermissionStatus(status = PermissionStatus.Status.NotRequested)) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted ->
                permissionStatus = if (isGranted) {
                    PermissionStatus(status = PermissionStatus.Status.Granted)
                } else {
                    PermissionStatus(status = PermissionStatus.Status.Denied)
                }
            }
        )

        LaunchedEffect(permissionStatus.status) {
            when (permissionStatus.status) {
                is PermissionStatus.Status.NotRequested -> {
                    launcher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }

                is PermissionStatus.Status.Denied -> {
                    launcher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }

                is PermissionStatus.Status.Granted -> {
                    onPermissionGranted()
                }
            }
        }
    }

    data class PermissionStatus(
        val status: Status
    ) {
        sealed class Status {
            data object NotRequested : Status()
            data object Denied : Status()
            data object Granted : Status()
        }
    }

}