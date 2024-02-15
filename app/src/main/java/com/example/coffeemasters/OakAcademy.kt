package com.example.coffeemasters

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.widget.ProgressBar
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
@Composable
fun NotificationRepeating() {
    val context = LocalContext.current
    SideEffect {
        NotificationsGlobalObject.repeatingNotificationsModel.createNotificationsChannel(context)

    }

    Button(onClick = {
        val calendar = Calendar.getInstance().apply {
            this.set(Calendar.HOUR_OF_DAY, 9)
            this.set(Calendar.MINUTE, 53)
            this.set(Calendar.SECOND, 0)
        }
        Log.d("AlarmManager", "calendar time in millis: ${calendar.timeInMillis}")

        val intent = Intent(context, AlarmManagerReceiver::class.java)
        intent.action = "repeating-noti"
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager =
            context.getSystemService(android.content.Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }) {
        Text("click to set repeating notification")
    }
}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
@Composable
fun CameraPractice() {
    var cameraBitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { result -> cameraBitmap = result }
    )
    Column {
        Button(onClick = {
            launcher.launch()
        }) {
            Text("Take a picture")
        }
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Red)
        )
        cameraBitmap?.let {
            Image(
                bitmap = cameraBitmap!!.asImageBitmap(),
                contentDescription = "Camera Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
    }
}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
@Composable
fun ComposePractice() {
    var shouldShowIndicator by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    Column {
        Button(onClick = {
            shouldShowIndicator = true
            coroutineScope.launch {
                delay(2000)
                shouldShowIndicator = false
            }
        }) {
            Text("show indicator for 2 seconds")
        }
        if (shouldShowIndicator) {
            CircularProgressIndicator()
        }

    }
}

@Composable
fun ProgressBarPractice() {
    var offsetX by remember {
        mutableStateOf(0f)
    }
    var offsetY by remember {
        mutableStateOf(0f)
    }


    Column {
        CircularProgressIndicator(
            modifier = Modifier
                .size(50.dp)
                .offset { IntOffset(offsetX.toInt(), offsetY.toInt()) }
                .pointerInput(Unit) {
//                    detectTapGestures(
//                        onDoubleTap = { offset -> },
//                        onTap = { offset -> },
//                        onLongPress = { offset -> },
//                        onPress = { offset -> }
//                    )
                    detectDragGestures { change, dragAmount ->
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                },
            color = MaterialTheme.colorScheme.primary
        )
        LinearProgressIndicator(
            modifier = Modifier.padding(16.dp),
            color = Color.Blue
        )
    }

}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
@Composable
fun ImagePractice() {
    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }
    var selectedText by remember {
        mutableStateOf("Select an item")
    }
    Column {
        Button(onClick = {
            isDropdownExpanded = !isDropdownExpanded
        }) {
            Text("toggle drop down")
        }
        ReusableDropdown(
            isExpanded = isDropdownExpanded,
            labels = listOf("First", "Second", "Third"),
            onClose = {
                isDropdownExpanded = false
            },
            onSelect = { dropdownLabel ->
                selectedText = dropdownLabel
            }
        )
    }
}

@Composable
fun ReusableDropdown(
    isExpanded: Boolean,
    labels: List<String>,
    onClose: () -> Unit,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onClose }
    ) {
        labels.forEach { label ->
            DropdownMenuItem(
                onClick = {
                    onSelect(label)
                    onClose()
                },
                text = {
                    Text(text = label)
                }
            )
        }
    }
}

//@Preview(
//    showSystemUi = true,
//    showBackground = true
//)
@Composable
private fun ButtonPractice() {
    var selectedIndex by remember {
        mutableStateOf(-1)
    }

    val labels = listOf("First", "Second", "Third")
    RadioButtonGroup(labels = labels, selectedIndex = selectedIndex, onClick = { newIndex ->
        selectedIndex = newIndex
    })
}

@Composable
fun RadioButtonGroup(
    labels: List<String>,
    selectedIndex: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        labels.forEachIndexed { index, label ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
                RadioButton(selected = index == selectedIndex, onClick = {
                    onClick(index)
                })
                Text(text = label)
            }
        }
    }
}

@Composable
fun CustomButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            onClick()
        },
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true
)
@Composable
fun NavApp() {
    Navigation()
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val domain = "https://navpractice.com"
    NavHost(navController = navController, startDestination = "/1") {
        composable(
            route = "/{id}",
            arguments = listOf(
                navArgument("id") {
                    this.type = NavType.StringType
                }
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = "$domain/{id}" }
            )
        ) { navArgs ->
            val id = navArgs.arguments?.getString("id")
            FirstPage(navController, id ?: "0")
        }
        composable(route = "/about/{store}/{productId}",
            arguments = listOf(
                navArgument("store") {
                    this.type = NavType.StringType
                },
                navArgument("productId") {
                    this.type = NavType.StringType
                }
            )
        ) { navArgs ->
            val store = navArgs.arguments?.getString("store")
            val productId = navArgs.arguments?.getString("productId")
            SecondPage(navController, store ?: "no_store", productId ?: "no_product_id")
        }
    }
}

@Composable
fun FirstPage(navController: NavController, id: String) {
    Column {
        Text(text = "First Page, id is $id")
        CustomButton(text = "Navigate to second page", onClick = {
            navController.navigate("/about/gaySexStore/1234")
        })
    }
}

@Composable
fun SecondPage(navController: NavController, store: String, productId: String) {

    Column {
        Text(text = "Second Page, store is $store, productId is $productId")
        CustomButton(text = "Navigate back to first page", onClick = {
            navController.popBackStack()
        })
    }
}