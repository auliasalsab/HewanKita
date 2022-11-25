package com.capstone.hewankita.data.local.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.capstone.hewankita.data.local.database.DatabaseContract.CareColumns.Companion.TABLE_CARE
import com.capstone.hewankita.data.local.database.DatabaseContract.DoctorColumns.Companion.TABLE_DOCTOR
import com.capstone.hewankita.data.local.database.DatabaseContract.DoctorColumns.Companion.ID
import com.capstone.hewankita.data.local.database.DatabaseContract.GroomingColumns.Companion.TABLE_GROOMING
import com.capstone.hewankita.data.local.database.DatabaseContract.VaccinationColumns.Companion.TABLE_VACCINATION
import java.sql.SQLException

class ScheduleHelper(context: Context) {
    private val databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE_DOCTOR = TABLE_DOCTOR
        private const val DATABASE_TABLE_CARE = TABLE_CARE
        private const val DATABASE_TABLE_GROOMING = TABLE_GROOMING
        private const val DATABASE_TABLE_VACCINATION = TABLE_VACCINATION

        private var INSTANCE: ScheduleHelper? = null
        fun getInstance(context: Context): ScheduleHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ScheduleHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }
    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryDoctorAll(): Cursor {
        return database.query(
            DATABASE_TABLE_DOCTOR,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC")
    }

    fun queryCareAll(): Cursor {
        return database.query(
            DATABASE_TABLE_CARE,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC"
        )
    }

    fun queryGroomingAll(): Cursor {
        return database.query(
            DATABASE_TABLE_GROOMING,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC"
        )
    }

    fun queryVaccinationAll(): Cursor {
        return database.query(
            DATABASE_TABLE_VACCINATION,
            null,
            null,
            null,
            null,
            null,
            "$ID ASC"
        )
    }

    fun insertTableDoctor(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_DOCTOR, null, values)
    }

    fun insertTableCare(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_CARE, null, values)
    }

    fun insertTableGrooming(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_GROOMING, null, values)
    }

    fun insertTableVaccination(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE_VACCINATION, null, values)
    }
}