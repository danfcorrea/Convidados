package com.danfcorrea.convidados.ui.allguests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danfcorrea.convidados.databinding.GuestItemBinding
import com.danfcorrea.convidados.model.GuestModel

class GuestsAdapter: RecyclerView.Adapter<GuestsAdapter.GuestsViewHolder>() {

    private var guestList: List<GuestModel> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestsViewHolder {
        val item = GuestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestsViewHolder(item)
    }

    override fun getItemCount(): Int {
        return guestList.count()
    }

    override fun onBindViewHolder(holder: GuestsViewHolder, position: Int) {
        holder.bind(guestList[position])
    }

    fun updateGuests(list: List<GuestModel>){
        guestList = list
        notifyDataSetChanged()
    }

    inner class GuestsViewHolder internal constructor(private val item: GuestItemBinding) : RecyclerView.ViewHolder(item.root){
        fun bind(guest: GuestModel) {
            item.textName.text = guest.name
        }
    }
}
