package com.danfcorrea.convidados.ui.guestform

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.danfcorrea.convidados.R
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.model.SuccessOrFailure
import com.danfcorrea.convidados.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = GuestRepository.getInstance(application)

    private val _guestModel = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = _guestModel

    private val _saveGuest = MutableLiveData<SuccessOrFailure>()
    val saveGuest: LiveData<SuccessOrFailure> = _saveGuest

    fun save(guest: GuestModel) {
        val successOrFailure = SuccessOrFailure(false, "Erro!")
        if (guestValidation(guest)) {
            if (guest.id == 0) {
                successOrFailure.success = repository.insert(guest)
                if (successOrFailure.success) {
                    successOrFailure.message = getApplication<Application>().getString(R.string.insert_success)
                } else {
                    successOrFailure.message = getApplication<Application>().getString(R.string.insert_failure)
                }
            } else {
                successOrFailure.success = repository.update(guest)
                if (successOrFailure.success) {
                    successOrFailure.message = getApplication<Application>().getString(R.string.update_success)
                } else {
                    successOrFailure.message = getApplication<Application>().getString(R.string.update_failure)
                }
            }
        }else{
            successOrFailure.message = getApplication<Application>().getString(R.string.fields_error)
        }
        _saveGuest.value = successOrFailure
    }

    fun get(id: Int) {
        _guestModel.value = repository.get(id)
    }

    private fun guestValidation(guest: GuestModel): Boolean {
        return guest.name.isNotBlank()
    }
}