package com.capstone.hewankita.helper

import android.database.Cursor
import com.capstone.hewankita.data.local.database.DatabaseContract
import com.capstone.hewankita.data.local.entity.ScheduleAll
import com.capstone.hewankita.data.local.entity.ScheduleCare

object MappingHelper {

    fun mapCursorDoctorToArrayList(notesCursor: Cursor?): ArrayList<ScheduleAll> {
        val scheduleAllList = ArrayList<ScheduleAll>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.DoctorColumns.ID))
                val outlet = getString(getColumnIndexOrThrow(DatabaseContract.DoctorColumns.OUTLET))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.DoctorColumns.BOOKING_DATE))
                val time = getString(getColumnIndexOrThrow(DatabaseContract.DoctorColumns.BOOKING_TIME))
                scheduleAllList.add(ScheduleAll(id, outlet, date, time))
            }
        }
        return scheduleAllList
    }

    fun mapCursorCareToArrayList(notesCursor: Cursor?): ArrayList<ScheduleCare> {
        val scheduleCareList = ArrayList<ScheduleCare>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.CareColumns.ID))
                val outlet = getString(getColumnIndexOrThrow(DatabaseContract.CareColumns.OUTLET))
                val checkIn = getString(getColumnIndexOrThrow(DatabaseContract.CareColumns.CHECK_IN))
                val checkOut = getString(getColumnIndexOrThrow(DatabaseContract.CareColumns.CHECK_OUT))
                val timeOfArrival = getString(getColumnIndexOrThrow(DatabaseContract.CareColumns.TIME_OF_ARRIVAL))
                scheduleCareList.add(ScheduleCare(id, outlet, checkIn, checkOut, timeOfArrival))
            }
        }
        return scheduleCareList
    }

    fun mapCursorGroomingToArrayList(notesCursor: Cursor?): ArrayList<ScheduleAll> {
        val scheduleAllList = ArrayList<ScheduleAll>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.GroomingColumns.ID))
                val outlet = getString(getColumnIndexOrThrow(DatabaseContract.GroomingColumns.OUTLET))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.GroomingColumns.BOOKING_DATE))
                val time = getString(getColumnIndexOrThrow(DatabaseContract.GroomingColumns.BOOKING_TIME))
                scheduleAllList.add(ScheduleAll(id, outlet, date, time))
            }
        }
        return scheduleAllList
    }

    fun mapCursorVaccinationToArrayList(notesCursor: Cursor?): ArrayList<ScheduleAll> {
        val scheduleAllList = ArrayList<ScheduleAll>()
        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.VaccinationColumns.ID))
                val outlet = getString(getColumnIndexOrThrow(DatabaseContract.VaccinationColumns.OUTLET))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.VaccinationColumns.BOOKING_DATE))
                val time = getString(getColumnIndexOrThrow(DatabaseContract.VaccinationColumns.BOOKING_TIME))
                scheduleAllList.add(ScheduleAll(id, outlet, date, time))
            }
        }
        return scheduleAllList
    }
}