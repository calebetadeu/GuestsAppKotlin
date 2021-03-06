package com.example.guests.view.viewholder

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guests.R
import com.example.guests.service.model.GuestModel
import com.example.guests.view.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(guest: GuestModel) {

        // Obtém os elementos de interface
        val textName = itemView.findViewById<TextView>(R.id.text_name)

        // Atribui valores
        textName.text = guest.name

        // Atribui eventos
        textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        // Atribui eventos
        textName.setOnLongClickListener {
            AlertDialog.Builder(itemView.context)
                .setTitle(R.string.remocao_convidado)
                .setMessage(R.string.deseja_remover)
                .setPositiveButton(R.string.remover) { dialog, which ->
                    listener.onDelete(guest.id)
                }
                .setNeutralButton(R.string.cancelar, null)
                .show()

            true
        }

    }
}