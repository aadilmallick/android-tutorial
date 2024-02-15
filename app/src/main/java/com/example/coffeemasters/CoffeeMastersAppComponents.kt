package com.example.coffeemasters

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.coffeemasters.ui.theme.Alternative1
import com.example.coffeemasters.ui.theme.OnPrimary
import com.example.coffeemasters.ui.theme.Primary
import java.net.URI

@Composable
fun AppTitle() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart, contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Coffee Masters")
    }
}

@Composable
fun Container(
    modifier: Modifier = Modifier,
    composable: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        composable()
    }
}


object Routes {
    val home = NavPage("Home", Icons.Filled.Home, "home")
    val orders = NavPage("Orders", Icons.Filled.Menu, "orders")
    val profile = NavPage("Profile", Icons.Filled.Person, "profile")
}

@Composable
fun Navbar(
    onChange: (String) -> Unit,
    selectedRoute: String = Routes.home.route
) {
    val pages = listOf(
        Routes.home,
        Routes.orders,
        Routes.profile
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Primary)
            .padding(
                top = 8.dp,
                bottom = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        pages.forEach { page ->
            Box(
                modifier=Modifier.clickable {
                    onChange(page.route)
                }
            ) {

                NavBarItem(page,
                    selected = page.route == selectedRoute
                )
            }
        }
    }
}


data class NavPage(val name: String, val icon: ImageVector, val route: String)

@Composable
fun NavBarItem(page: NavPage, modifier: Modifier = Modifier, selected: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
        Image(
            imageVector = page.icon,
            contentDescription = page.name,
            colorFilter = ColorFilter.tint(
                if (selected) Color.White else OnPrimary
            ),
            modifier = Modifier
                .padding(bottom = 8.dp)
                .size(24.dp)
        )
        Text(
            page.name,
            fontSize = 12.sp,
            color = if (selected) Color.White else OnPrimary
        )
    }
}


class Product(var id: Int, var name: String, var price: Double, var image: String) {
    fun getImageUrl(): String {
        return "https://firtman.github.io/coffeemasters/api/images/${this.image}"
    }

//    fun getImageContentUri(): URI {
//
//    }
}

class Category(var name: String, var products: MutableList<Product>)

class ItemInCart(var product: Product, var quantity: Int)


fun Double.format(digits: Int) = "%.${digits}f".format(this)


@Composable
fun ProductItem(product: Product, onAdd: (Product)->Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AsyncImage(model = product.getImageUrl(), contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(product.name, fontWeight = FontWeight.Bold)
                Text("$${product.price.format(2)} ea")
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Primary
                ),
                onClick = {
                    onAdd(product)
                },
            ) {
                Text("Add")
            }
        }
    }
}