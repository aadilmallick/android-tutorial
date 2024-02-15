package com.example.coffeemasters

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.navArgs
import com.example.coffeemasters.destinations.FirstScreenDestination
import com.example.coffeemasters.destinations.SecondScreenDestination
import com.example.coffeemasters.destinations.ThirdScreenDestination
import com.example.coffeemasters.roomdb.RoomActivity
import com.google.gson.reflect.TypeToken
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable


class ThirdActivity : ComponentActivity() {
    lateinit var alarmManagerModel: AlarmManagerModel
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmManagerModel = AlarmManagerModel(this)
        setContent {
            DestinationsNavHost(navGraph = NavGraphs.root) {
                composable(FirstScreenDestination) {
                    FirstScreen(
                        navigator = this.destinationsNavigator,
                        alarmManagerModel = alarmManagerModel
                    )
                }
            }
        }
    }

}


@RootNavGraph(start = true)
@Destination
@Composable
fun FirstScreen(
    navigator: DestinationsNavigator,
    alarmManagerModel: AlarmManagerModel
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(text = "First Screen")
        Button(onClick = {
            alarmManagerModel.setRepeatingAlarm(60)
        }) {
            Text("start alarm")
        }
        Button(onClick = {
            alarmManagerModel.cancel()

        }) {
            Text("cancel alarm")
        }
        Button(onClick = {
            navigator.navigate(
                ThirdScreenDestination()
            )
        }) {
            Text(text = "Go to Second Screen")
        }
        Button(onClick = {
            val intent = Intent(context, CoffeeMastersActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Go to coffee masters activity")
        }
        Button(onClick = {
            val intent = Intent(context, RoomActivity::class.java)
            context.startActivity(intent)
        }) {
            Text(text = "Go to room Activity")
        }
    }
}

@Destination
@Composable
fun ThirdScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Third Screen")
    }
}

data class SecondScreenArgs(
    val someArg: String
) : Serializable

@Destination
@Composable
fun SecondScreen(
    args: SecondScreenArgs
) {

    val apiRequesterTodo = APIRequesterArrays.createClient(
        url = "https://jsonplaceholder.typicode.com/todos",
        TClass = Todo::class.java
    )
    var todos = remember {
        mutableStateListOf<Todo>()
    }

    LaunchedEffect(key1 = Unit) {
        val todoData = withContext(Dispatchers.IO) {
            apiRequesterTodo.getData()
        }
        todos.addAll(todoData?.toList() ?: emptyList())
        delay(1000)
        Log.d("SecondScreen", "todos: ${todos[0].toString()}")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Second Screen message from first screen ${args.someArg}")
        if (todos.size > 0) {
            for (item in todos) {
                Text(text = item.toString())
            }
        }
    }
}



