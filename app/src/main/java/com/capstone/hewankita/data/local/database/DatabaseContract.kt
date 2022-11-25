package com.capstone.hewankita.data.local.database

import android.provider.BaseColumns

internal class DatabaseContract {

    internal class DoctorColumns : BaseColumns {
        companion object {
            const val TABLE_DOCTOR = "doctor"
            const val ID = "_id"
            const val OUTLET = "outlet"
            const val BOOKING_DATE = "booking_date"
            const val BOOKING_TIME = "booking_time"
        }
    }

    internal class CareColumns : BaseColumns {
        companion object {
            const val TABLE_CARE = "care"
            const val ID = "_id"
            const val OUTLET = "outlet"
            const val CHECK_IN = "check_in"
            const val CHECK_OUT = "check_out"
            const val TIME_OF_ARRIVAL = "time_of_arrival"
        }
    }

    internal class GroomingColumns : BaseColumns {
        companion object {
            const val TABLE_GROOMING = "grooming"
            const val ID = "_id"
            const val OUTLET = "outlet"
            const val BOOKING_DATE = "booking_date"
            const val BOOKING_TIME = "booking_time"
        }
    }

    internal class VaccinationColumns : BaseColumns {
        companion object {
            const val TABLE_VACCINATION = "vaccination"
            const val ID = "_id"
            const val OUTLET = "outlet"
            const val BOOKING_DATE = "booking_date"
            const val BOOKING_TIME = "booking_time"
        }
    }
}