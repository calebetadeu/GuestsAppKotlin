package com.example.guests.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.guests.service.model.GuestModel
import com.example.guests.service.repository.GuestRepository

class GuestFormViewModel(application: Application) : AndroidViewModel(application) {

    // Contexto e acesso a dados
    private val mContext = application.applicationContext
    private val mGuestRepository: GuestRepository = GuestRepository.getInstance(mContext)

    private var mSaveGuest = MutableLiveData<Boolean>()
    val saveGuest: LiveData<Boolean> = mSaveGuest

    private var mGuest = MutableLiveData<GuestModel>()
    val guest: LiveData<GuestModel> = mGuest

    /**
     * Salva convidado
     * */
    fun save(id: Int, name: String, presence: Boolean) {
        val guest = GuestModel(id, name, presence)

        if (id == 0) {
            mSaveGuest.value = mGuestRepository.save(guest)
        } else {
            mSaveGuest.value = mGuestRepository.update(guest)
        }
    }

    /**
     * Carrega convidado
     * */
    fun load(id: Int) {
        mGuest.value = mGuestRepository.get(id)
    }

}