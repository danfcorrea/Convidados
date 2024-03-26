package com.danfcorrea.convidados.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val _listGuests = MutableLiveData<List<GuestModel>>().apply {
    }

    val guests: LiveData<List<GuestModel>> = _listGuests

    fun getAll() {
        _listGuests.value = repository.getAll()
    }

    fun get(present: Int){
        _listGuests.value = repository.getFilter(present)
    }

    fun delete(id: Int) {
        repository.delete(id)
    }
}