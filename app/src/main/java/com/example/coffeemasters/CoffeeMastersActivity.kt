package com.example.coffeemasters

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.coffeemasters.ui.theme.Alternative1
import com.example.coffeemasters.ui.theme.OnPrimary
import com.example.coffeemasters.ui.theme.Primary

class CoffeeMastersActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun App() {
    var route = remember { mutableStateOf(Routes.home.route) }

    fun onChange(newRoute: String) {
        route.value = newRoute
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = OnPrimary,
                ),
                title = {
                    AppTitle()
                }
            )
        },
        bottomBar = {
            Navbar(
                onChange = { newRoute -> onChange(newRoute) },
                selectedRoute = route.value
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Container(
            modifier = Modifier.padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
        ) {
            ScreenSelector(route = route.value)
        }
    }
}

@Composable
fun ScreenSelector(route: String) {
    when (route) {
        Routes.home.route -> HomePage()
        Routes.orders.route -> OrdersPage()
        Routes.profile.route -> ProfilePage()
    }
}

@Composable
fun HomePage() {
    val dataManager = DataManagerViewModel()
    LazyColumn {
        itemsIndexed(dataManager.menu.value) { index, category ->
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(12.dp)
                    .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
            ) {
                for (product in category.products) {
                    ProductItem(
                        product = product,
                        onAdd = { dataManager.cartAdd(product) }
                    )
                }
            }
        }
    }
}

@Composable
fun OrdersPage() {
    WebViewComposable()
}

@Composable
fun WebViewComposable() {
    // Declare a string that contains a url
    val url = "https://firtman.github.io/coffeemasters/webapp"

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })
}


@Composable
fun ProfilePage() {
    Text("Profile")
}




