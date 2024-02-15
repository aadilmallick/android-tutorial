package com.example.coffeemasters.roomdb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.room.Room

class RoomActivity : ComponentActivity() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            Database::class.java,
            "contacts.db"
        ).build()
    }
    private val contactViewModel by viewModels<ContactViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // fetch first
        contactViewModel.fetchContacts(db)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Add your composable here
                ContactsScreen()
            }
        }
    }

    @Composable
    fun ContactsScreen() {
        var isDialogOpen by remember { mutableStateOf(false) }

        Column {
            Button(onClick = {
                isDialogOpen = true
            }) {
                Text(text = "Add New Contact")
            }
            ContactsList(contacts = contactViewModel.getContacts())
            if (isDialogOpen) {
                AddNewContactDialog(
                    onDismissRequest = {
                        isDialogOpen = false
                    },
                    onConfirm = { name, phone ->
                        contactViewModel.addContact(
                            db,
                            Contact(
                                name = name,
                                phone = phone
                            )
                        )
                        isDialogOpen = false
                    }
                )
            }
        }
    }
}

@Composable
fun ContactsList(contacts: List<Contact>) {
    if (contacts.isEmpty()) {
        Text(text = "No contacts found")
        return
    }
    LazyColumn {
        itemsIndexed(contacts) { index, contact ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.Cyan)
                    .padding(16.dp),
            ) {
                Text(text = contact.name)
                Text(text = contact.phone)
            }
        }
    }
}

@Composable
fun AddNewContactDialog(
    onDismissRequest: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(name, phone)
                }
            ) {
                Text(text = "Add")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = "Cancel")
            }
        },
        title = {
            Text(text = "Add New Contact")
        },
        text = {
            // Add two textfields for name and phone
            Column {
                TextField(value = name, onValueChange = { name = it },
                    placeholder = { Text("Name") }
                )
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    placeholder = { Text("Phone") }
                )
            }
        }
    )
}