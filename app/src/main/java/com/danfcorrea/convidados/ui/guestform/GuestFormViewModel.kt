package com.danfcorrea.convidados.ui.guestform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val _guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = _guestModel

    fun save(guest: GuestModel) {
        if (guest.id == 0)
            repository.insert(guest)
        else
            repository.update(guest)
    }

    fun get(id: Int){
        _guestModel.value = repository.get(id)
    }
}