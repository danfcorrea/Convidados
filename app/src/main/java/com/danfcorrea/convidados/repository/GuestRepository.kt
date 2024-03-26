package com.danfcorrea.convidados.repository

import android.content.ContentValues
import android.content.Context
import com.danfcorrea.convidados.constants.DataBaseConstants
import com.danfcorrea.convidados.model.GuestModel

class GuestRepository private constructor(context: Context) {

    private val guestDataBase = GuestDataBase(context)

    companion object {
        private lateinit var repository: GuestRepository

        fun getInstance(context: Context): GuestRepository {
            if (!::repository.isInitialized)
                repository = GuestRepository(context)
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
            true
        } catch (_: Exception) {
            false
        }

    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestDataBase.writableDatabase
            val presence = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presence)

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestDataBase.writableDatabase

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (_: Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID) })
                    val name =
                        cursor.getString(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME) })
                    val presence =
                        cursor.getInt(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE) })

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()

        } catch (_: Exception) {
            return list
        }
        return list
    }

    fun getFilter(present: Int): List<GuestModel> {
        val list = mutableListOf<GuestModel>()
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.PRESENCE + " = ?"
            val args = arrayOf(present.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id =
                        cursor.getInt(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID) })
                    val name =
                        cursor.getString(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME) })
                    val presence =
                        cursor.getInt(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE) })

                    list.add(GuestModel(id, name, presence == 1))
                }
            }
            cursor.close()

        } catch (_: Exception) {
            return list
        }
        return list
    }

    fun get(id: Int): GuestModel? {
        var guest: GuestModel? = null
        try {
            val db = guestDataBase.readableDatabase
            val projection = arrayOf(
                DataBaseConstants.GUEST.COLUMNS.ID,
                DataBaseConstants.GUEST.COLUMNS.NAME,
                DataBaseConstants.GUEST.COLUMNS.PRESENCE
            )

            val selection = DataBaseConstants.GUEST.COLUMNS.ID + " = ?"
            val args = arrayOf(id.toString())

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME) })
                    val presence =
                        cursor.getInt(cursor.run { getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE) })

                    guest = GuestModel(id, name, presence == 1)
                }
            }
            cursor.close()

        } catch (_: Exception) {
            return guest
        }
        return guest

    }

}