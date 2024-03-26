package com.danfcorrea.convidados.constants

class DataBaseConstants private constructor(){

    object GUEST{
        const val TABLE_NAME = "Guest"
        const val ID = "guestid"
        const val PRESENT = 1
        const val ABSENT = 0

        object COLUMNS{
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}