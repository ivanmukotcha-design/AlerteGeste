package com.butembo.alertgeste.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.butembo.alertgeste.data.local.entity.Alerte
import com.butembo.alertgeste.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : ListAdapter<Alerte, HistoryAdapter.HistoryViewHolder>(AlerteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        private val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        fun bind(alerte: Alerte) {
            binding.tvDate.text = sdf.format(Date(alerte.horodatage))
            binding.tvStatus.text = alerte.statut
            binding.tvContacts.text = "Notifiés : ${alerte.contactsNotifies}"
            
            // Couleur selon statut
            val color = when(alerte.statut) {
                "ENVOYEE" -> 0xFF27AE60.toInt()
                "ANNULEE" -> 0xFFF39C12.toInt()
                else -> 0xFFE74C3C.toInt()
            }
            binding.tvStatus.setTextColor(color)
        }
    }

    class AlerteDiffCallback : DiffUtil.ItemCallback<Alerte>() {
        override fun areItemsTheSame(oldItem: Alerte, newItem: Alerte): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Alerte, newItem: Alerte): Boolean = oldItem == newItem
    }
}
