package com.capstone.hewankita.data.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.capstone.hewankita.data.local.database.DatabaseContract.CareColumns.Companion.TABLE_CARE
import com.capstone.hewankita.data.local.database.DatabaseContract.DoctorColumns.Companion.TABLE_DOCTOR
import com.capstone.hewankita.data.local.database.DatabaseContract.GroomingColumns.Companion.TABLE_GROOMING
import com.capstone.hewankita.data.local.database.DatabaseContract.VaccinationColumns.Companion.TABLE_VACCINATION

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "db_schedule"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_DOCTOR = "CREATE TABLE $TABLE_DOCTOR" +
                " (${DatabaseContract.DoctorColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.DoctorColumns.OUTLET} TEXT NOT NULL," +
                " ${DatabaseContract.DoctorColumns.BOOKING_DATE} TEXT NOT NULL," +
                " ${DatabaseContract.DoctorColumns.BOOKING_TIME} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_CARE = "CREATE TABLE $TABLE_CARE" +
                " (${DatabaseContract.CareColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.CareColumns.OUTLET} TEXT NOT NULL," +
                " ${DatabaseContract.CareColumns.CHECK_IN} TEXT NOT NULL," +
                " ${DatabaseContract.CareColumns.CHECK_OUT} TEXT NOT NULL," +
                " ${DatabaseContract.CareColumns.TIME_OF_ARRIVAL} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_GROOMING = "CREATE TABLE $TABLE_GROOMING" +
                " (${DatabaseContract.GroomingColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.GroomingColumns.OUTLET} TEXT NOT NULL," +
                " ${DatabaseContract.GroomingColumns.BOOKING_DATE} TEXT NOT NULL," +
                " ${DatabaseContract.GroomingColumns.BOOKING_TIME} TEXT NOT NULL)"

        private const val SQL_CREATE_TABLE_VACCINATION = "CREATE TABLE $TABLE_VACCINATION" +
                " (${DatabaseContract.VaccinationColumns.ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DatabaseContract.VaccinationColumns.OUTLET} TEXT NOT NULL," +
                " ${DatabaseContract.VaccinationColumns.BOOKING_DATE} TEXT NOT NULL," +
                " ${DatabaseContract.VaccinationColumns.BOOKING_TIME} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_DOCTOR)
        db.execSQL(SQL_CREATE_TABLE_CARE)
        db.execSQL(SQL_CREATE_TABLE_GROOMING)
        db.execSQL(SQL_CREATE_TABLE_VACCINATION)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_DOCTOR")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CARE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_GROOMING")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_VACCINATION")
        onCreate(db)
    }
}