package com.example.coffeemasters

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class NotificationsActivity : ComponentActivity() {
    private val viewModel by viewModels<NotificationsPermissionRequesterViewModel>()
    // 5. in onResume, check if permission is granted and set it
    override fun onResume() {
        super.onResume()
        viewModel.getPermission(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // 2. create launcher, which also updates permissions
            viewModel.CreateLauncher()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.White),
            ) {
                if (viewModel.isGranted) {
                    Text("Permission is granted")
                } else {
                    // request permissions
                    Text("Permission is not granted")
                }
                
                // 3. request permission only if not granted
                if (!viewModel.isGranted) {
                    Button(onClick = {
                        viewModel.RequestPermission()
                    }) {
                        Text("Request permission")
                    }
                }
                
                // 4. render dialog if should show rationale
                viewModel.ShowDialog(context = this@NotificationsActivity)
            }
        }
    }
}

