package com.example.coffeemasters.roomdb

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val phone: String,
)

@Dao
interface ContactORM {
    @Upsert
    suspend fun upsertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM Contact")
    suspend fun getAllContacts():List<Contact>

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    suspend fun getAllContactsOrderedByName(): List<Contact>
}

@Database(entities = [Contact::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract val contactORM: ContactORM
}