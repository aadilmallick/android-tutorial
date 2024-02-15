package com.example.coffeemasters

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coffeemasters.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.io.Serializable

data class PersonRepresentation(val name: String, val age: Int) : Serializable
class SecondActivity : ComponentActivity() {
    private val wildWords = FontFamily(
        Font(
            R.font.wild_words,
            FontWeight.Bold,
            FontStyle.Normal
        ),
    )
    private lateinit var notificationsModel: NotificationsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationsModel = NotificationsModel(this)
        notificationsModel.createNotificationsChannel()
        val person = intent.getSerializableExtra("person") as? PersonRepresentation
        Log.d("SecondActivity", "payload: ${person.toString()}")

        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Container()
            }
        }
    }

    fun goBack() {
        finish()
    }

    //    @Preview
    @Composable
    fun Container() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            SecondApp()
            Button(onClick = {
                val context = this@SecondActivity
                val intent = Intent(context, ThirdActivity::class.java)
                context.startActivity(intent)
            }) {
                Text("Go to third activity")
            }
        }
    }

    @Composable
    fun OptimizationExample() {
        var nums = remember {
            mutableStateOf((1..10).toMutableList())
        }.value

        val evenNums = remember {
            derivedStateOf {
                nums.filter { it % 2 == 0 }
            }
        }.value

        LazyColumn {
            itemsIndexed(evenNums) { index, element ->
                Text(text = "At index $index, we have $element")
            }
        }

    }


    @Composable
    fun SecondApp() {
        Row(
            modifier = Modifier
                .background(Color.Green),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Button(onClick = {
                notificationsModel.createNotification("Hello", "This is a notification")
            }) {
                Text(text = "Show Notification")
            }
            Button(onClick = {
                notificationsModel.createNotification("Hello", "This is a notification")
            }) {
                Text(text = "Show Notification")
            }
        }
    }

    @Composable
    fun Parent() {
        var color = remember { mutableStateOf(Color.White) }.value

        val onColorChange: (Color) -> Unit = { passedInColor ->
            color = passedInColor
        }
        Column {
            Text(
                text = "Hello",
                modifier = Modifier.padding(16.dp),
                color = color,
                fontSize = 24.sp
            )
            Child(onColorChange)
        }
    }

    @Composable
    fun Child(onColorChange: (Color) -> Unit) {
        Button(onClick = {
            onColorChange(Color.Red)
        }) {
            Text(
                text = "Hello",
                color = Color.White,
            )
        }
    }

}

@Composable
fun getElevation(amount: Int): CardElevation {
    return CardDefaults.cardElevation(
        defaultElevation = amount.dp
    )
}

@Preview(showBackground = true)
@Composable
fun ImageCardApp() {
    ImageCardPractice.Container()
}

object ImageCardPractice {
    @Composable
    fun Container() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageCard()
        }
    }

    @Composable
    fun ImageCard() {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(modifier = Modifier
                .height(200.dp)
                .width(150.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.iteration1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
                    .padding(8.dp)
                    ,
                    verticalArrangement = Arrangement.Bottom,
                    ) {
                    Text(text="Gay sex card. Come get your gay sex here.",
                        color=Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                        )
                }
            }
        }
    }
}

//fun helloWorld() {
//    println("Hello, world!")
//}
