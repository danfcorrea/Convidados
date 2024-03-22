package com.danfcorrea.convidados.ui.allguests

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
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
            bind.textName.setOnClickListener{
                listener.onClick(guest.id)
            }
            bind.textName.setOnLongClickListener(View.OnLongClickListener {

                AlertDialog.Builder(itemView.context)
                    .setMessage("Deseja remover o convidado?")
                    .setPositiveButton("Sim") { dialog, which ->
                        listener.onDelete(guest.id)
                    }
                    .setNegativeButton("Não", null)
                    .create().show()

                true
            })
        }
    }
}
