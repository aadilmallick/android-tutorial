package com.example.coffeemasters

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.coffeemasters.ui.theme.MyApplicationTheme
import com.example.coffeemasters.ui.theme.Primary


fun sendEmail(context: Context) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        this.type = "text/plain"
        this.putExtra(Intent.EXTRA_EMAIL, "waadlingaadil@gmail.com")
        this.putExtra(Intent.EXTRA_SUBJECT, "Subject: This is a test email")
        this.putExtra(Intent.EXTRA_TEXT, "This is a test email")
    }
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}


class MainActivity : ComponentActivity() {

    private val colorViewModel by viewModels<ColorViewModel>()
    private val imageViewModel by viewModels<ImageViewModel>()
    private val uriModel = UriModel(this)
    private val permissionsModel = PermissionsModel(this)
    private val workManager = WorkManager.getInstance(this)

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        // 1. get uri from intent
        val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM, Uri::class.java)
        } else {
            intent?.getParcelableExtra(Intent.EXTRA_STREAM)
        }

        // 2. create work request one time
        val request = OneTimeWorkRequestBuilder<PhotoCompressionWorker>()
            // 3. set input data by changing values in companion object
            .setInputData(
                workDataOf(
                    PhotoCompressionWorker.KEY_CONTENT_URI to uri.toString(),
                    PhotoCompressionWorker.KEY_COMPRESSION_THRESHOLD to 1000000L
                )
            )
            .setConstraints(Constraints(requiresBatteryNotLow = true))
            .build()
        // 4. start worker - put in queue
        workManager.enqueue(request)
    }

    private fun navigateToSecondActivity() {
        Intent(this, SecondActivity::class.java).also {
            Log.d("MainActivity", "navigating to 2nd activity")
            it.putExtra("person", PersonRepresentation("Josh", 25))
            startActivity(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(airplaneModeReceiver)
//        unregisterReceiver(customEventReceiver)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    android.Manifest.permission.POST_NOTIFICATIONS,
//                    android.Manifest.permission.FOREGROUND_SERVICE
//                ),
//                0
//            )
//        }

//        // this only works for API 33
//        ActivityCompat.requestPermissions(
//            this,
//            arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
//            0
//        )

//        registerReceiver(airplaneModeReceiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
//        registerReceiver(customEventReceiver, IntentFilter("CUSTOM_EVENT"))

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.background(colorViewModel.getColor())
                    ) {
                        NavigationButton()
//                        ColorChangeButton()
//                        CustomBroadcastButton()
//                        ImagePickerButton()
//                        StartServiceButton()
//                        StopServiceButton()
//                        RequestPermissionsButton()
                    }
                }
            }
        }
    }


    @Composable
    fun RequestPermissionsButton() {
        Button(onClick = {
            permissionsModel.checkPermissions()
        }) {
            Text(text = "request permissions")
        }
    }


    @Composable
    fun StartServiceButton() {
        Button(onClick = {
            Intent(this, RunningService::class.java).also {
                it.setAction(RunningService.START_ACTION)
                startService(it)
            }
        }) {
            Text(text = "start service")
        }
    }

    @Composable
    fun StopServiceButton() {
        Button(onClick = {
            Intent(this, RunningService::class.java).also {
                it.setAction(RunningService.STOP_ACTION)
                startService(it)
            }
        }) {
            Text(text = "stop service")
        }
    }



    @Composable
    fun ImagePickerButton() {
        val pickImage = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = { contentUri ->
                println(contentUri)
                if (contentUri != null)
                    imageViewModel.setUri(contentUri)
                else {
                    Log.w("MainActivity", "contentUri is null")
                }
            })
        Column {
            Button(onClick = {
                pickImage.launch("image/*")
            }) {
                Text(text = "Pick Image")
            }

            // create image from imageviewmodel uri
            val uri = imageViewModel.getUri()
            if (uri != Uri.EMPTY) {
                Image(
                    painter = BitmapPainter(
                        BitmapFactory.decodeStream(
                            contentResolver.openInputStream(uri)
                        ) as ImageBitmap
                    ),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
        }
    }

    @Composable
    fun ColorChangeButton() {
        Button(onClick = {
            colorViewModel.toggleColor()
        }) {
            Text(text = "Change color from ${colorViewModel.getColorString()}")
        }
    }

    @Composable
    fun NavigationButton() {
        val context = LocalContext.current
        Button(
            onClick = {
               Intent(context, NotificationsActivity::class.java).also {
                   startActivity(it)
               }
            },
        ) {
            Text(text = "click me to navigate")
        }
    }

}



