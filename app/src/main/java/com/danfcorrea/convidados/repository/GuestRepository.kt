package com.danfcorrea.convidados.repository

import android.content.Context
import com.danfcorrea.convidados.model.GuestModel

class GuestRepository(context: Context) {

    private val guestDataBase = GuestDataBase.getDataBase(context).guestDAO()

    fun insert(guest: GuestModel): Boolean {
        return guestDataBase.insert(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return guestDataBase.update(guest)> 0
    }

    fun delete(id: Int): Boolean {
        val guest = get(id)
        return guestDataBase.delete(guest) > 0
    }

    fun getAll(): List<GuestModel> {
        return guestDataBase.getAll()
    }

    fun getFilter(present: Int): List<GuestModel> {
        return guestDataBase.getFilter(present)
    }

    fun get(id: Int): GuestModel {
        return guestDataBase.get(id)
    }

}