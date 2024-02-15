package com.example.coffeemasters

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffeemasters.ui.theme.OnPrimary
import com.example.coffeemasters.ui.theme.Primary

class Temp : Application() {

    @Preview()
    @Composable
    fun AppPreview() {
        App(this)
    }
}


interface Actions {
    fun buttfuck()
    fun isOlder(age: Int): Boolean
    fun fuck() {
        println("This guy is getting fucked")
    }
}

class Person(private var firstname: String, private var lastname: String) : Actions {
    private var age: Int = 0
    override fun buttfuck() {
        TODO("Not yet implemented")
    }

    override fun isOlder(age: Int): Boolean {
        return this.age > age
    }

    override fun fuck() {
        super.fuck()
        println("he now has a disease")
    }

    companion object {
        fun createPerson(): Person {
            return Person("Josh", "Allen")
        }
    }
}

fun add(vararg numbers: Int): Int {
    var sum = 0
    for (number in numbers) {
        sum += number
    }


    return sum
}

@Composable
fun ParentComp(content: @Composable () -> Unit) {
    Box(modifier = Modifier
        .background(Color.Green)
        .padding(16.dp)) {
        content()
    }
}

@Composable
fun Example(modifier: Modifier = Modifier) {
}

@Composable
fun GridExample(modifier: Modifier = Modifier) {
    val myList = listOf("Bob", "Bill", "Joe", "Gary")

    LazyVerticalGrid(columns = GridCells.Adaptive(100.dp)) {
        itemsIndexed(myList) {index, element ->
            Text(text=element)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { AppTitle() },
                colors = topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = OnPrimary,
                ),
            )
        },
        bottomBar = {}
    ) { scaffoldMeasurements ->
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    this.type = "text/plain"
                    this.putExtra(Intent.EXTRA_EMAIL, "waadlingaadil@gmail.com")
                    this.putExtra(Intent.EXTRA_SUBJECT, "Subject: This is a test email")
                    this.putExtra(Intent.EXTRA_TEXT, "This is a test email")
                }
                if (intent.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent)
                }

            },
            modifier = Modifier.padding(16.dp, scaffoldMeasurements.calculateTopPadding())
        ) {
            Text(text = "click me to navigate")
        }
    }
}

@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(text = "Coffee Masters")
    }
}

