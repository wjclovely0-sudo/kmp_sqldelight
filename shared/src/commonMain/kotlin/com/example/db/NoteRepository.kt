package com.example.db

import app.cash.sqldelight.db.SqlDriver
import kotlin.time.Clock

class NoteRepository(driver: SqlDriver) {
    private val database = AppDatabase(driver)
    private val dbQuery = database.appDatabaseQueries

    fun insertNote(title: String, content: String) {
        dbQuery.insertNote(
            title = title,
            content = content,
            createdAt = Clock.System.now().toEpochMilliseconds(),
        )
    }

    fun getAllNotes(): List<Note> {
        return dbQuery.selectAllNotes().executeAsList()
    }

    fun deleteNote(id: Long) {
        dbQuery.deleteNoteById(id)
    }

    fun updateNote(id: Long, title: String, content: String) {
        dbQuery.updateNote(
            id = id,
            title = title,
            content = content,
        )
    }
}
