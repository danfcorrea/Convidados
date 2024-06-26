package com.danfcorrea.convidados.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.danfcorrea.convidados.R
import com.danfcorrea.convidados.databinding.GuestItemBinding
import com.danfcorrea.convidados.model.GuestModel
import com.danfcorrea.convidados.ui.listener.OnGuestListener

class GuestsAdapter: RecyclerView.Adapter<GuestsAdapter.GuestsViewHolder>() {

    private var guestList: List<GuestModel> = listOf()
    private lateinit var listener: OnGuestListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestsViewHolder {
        val item = GuestItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuestsViewHolder(item, listener)
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

    fun attachListener(guestListener: OnGuestListener){
        listener = guestListener
    }

    inner class GuestsViewHolder internal constructor(private val bind: GuestItemBinding, private val listener: OnGuestListener) : RecyclerView.ViewHolder(bind.root){
        fun bind(guest: GuestModel) {
            bind.textName.text = guest.name
            bind.imageEdit.setOnClickListener{
                listener.onClick(guest.id)
            }

            bind.imageDelete.setOnClickListener {
                AlertDialog.Builder(itemView.context)
                    .setMessage(itemView.context.getString(R.string.delete_confirmation))
                    .setPositiveButton(itemView.context.getString(R.string.yes)) { dialog, which ->
                        listener.onDelete(guest.id)
                    }
                    .setNegativeButton(itemView.context.getString(R.string.no), null)
                    .create().show()
            }
        }
    }
}
