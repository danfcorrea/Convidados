package com.danfcorrea.convidados.ui.guestform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {
    fun insert(guest: GuestModel) {
        repository.insert(guest)
    }

    private val repository = GuestRepository.getInstance(application)

}