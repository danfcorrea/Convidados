package com.danfcorrea.convidados.ui.allguests

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.repository.GuestRepository

class AllGuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val _listAllGuests = MutableLiveData<List<GuestModel>>().apply {
    }

    val allGuests: LiveData<List<GuestModel>> = _listAllGuests

    fun getAll() {
        _listAllGuests.value = repository.getAll()
    }
}