package com.example.coffeemasters.roomdb

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface ContactEvent {
    data class OnContactSelected(val contact: Contact) : ContactEvent
    data class OnContactDeleted(val contact: Contact) : ContactEvent
    data class OnGetAllContacts(val contacts: List<Contact>) : ContactEvent
    data class OnAddContact(val contact: Contact) : ContactEvent
}


class ContactViewModel: ViewModel() {
    private val contacts = mutableStateOf(listOf<Contact>())


    fun fetchContacts(db: Database) {
        // get all contacts from the database
        viewModelScope.launch(Dispatchers.IO) {
            val allContacts = db.contactORM.getAllContacts()
            withContext(Dispatchers.Main) {
                contacts.value = allContacts
            }
        }
    }

    fun addContact(db: Database, contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            db.contactORM.upsertContact(contact)
            withContext(Dispatchers.Main) {
                contacts.value += contact
            }
        }
    }

    fun deleteContact(db: Database, contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            db.contactORM.deleteContact(contact)
            withContext(Dispatchers.Main) {
                contacts.value = contacts.value.filter { it.id != contact.id }
            }
        }
    }

    fun getContacts() : List<Contact> {
        return contacts.value
    }
}