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
import com.butembo.alertgeste.domain.AlertStatus
import com.butembo.alertgeste.R
import androidx.core.content.ContextCompat

class HistoryAdapter(private val onDelete: (Alerte) -> Unit) :
    ListAdapter<Alerte, HistoryAdapter.HistoryViewHolder>(AlerteDiffCallback()) {

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
            binding.btnDelete.apply {
                isEnabled = !AlertStatus.isInProgress(alerte.statut)
                alpha = if (isEnabled) 1f else 0.35f
                contentDescription = if (isEnabled)
                    context.getString(R.string.history_delete_description, binding.tvDate.text)
                else context.getString(R.string.history_delete_in_progress)
                setOnClickListener { onDelete(alerte) }
            }
            binding.tvStatus.text = AlertStatus.label(alerte.statut)
            binding.tvContacts.text = buildString {
                if (alerte.contactsNotifies.isNotBlank()) append("SMS confirmés : ${alerte.contactsNotifies}\n")
                append(alerte.detail)
                if (alerte.latitude != null && alerte.longitude != null)
                    append("\nPosition : ${alerte.latitude}, ${alerte.longitude}")
            }
            
            // Couleur selon statut
            val color = when(alerte.statut) {
                AlertStatus.SENT -> R.color.colorSuccess
                AlertStatus.CANCELLED -> R.color.colorTextSecondary
                AlertStatus.COUNTDOWN, AlertStatus.LOCATING, AlertStatus.SENDING -> R.color.colorPrimary
                AlertStatus.PARTIAL, AlertStatus.UNKNOWN, AlertStatus.INTERRUPTED -> R.color.colorWarning
                else -> R.color.colorDanger
            }
            binding.tvStatus.setTextColor(ContextCompat.getColor(binding.root.context, color))
        }
    }

    class AlerteDiffCallback : DiffUtil.ItemCallback<Alerte>() {
        override fun areItemsTheSame(oldItem: Alerte, newItem: Alerte): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Alerte, newItem: Alerte): Boolean = oldItem == newItem
    }
}
