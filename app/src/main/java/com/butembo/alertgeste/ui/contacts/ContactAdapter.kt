package com.butembo.alertgeste.ui.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.butembo.alertgeste.data.local.entity.Contact
import com.butembo.alertgeste.databinding.ItemContactBinding

class ContactAdapter(private val onEditClick: (Contact) -> Unit, private val onDeleteClick: (Contact) -> Unit) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.tvNom.text = contact.nom
            binding.tvTel.text = contact.telephone
            binding.root.setOnClickListener { onEditClick(contact) }
            binding.root.contentDescription = "Modifier ${contact.nom}"
            binding.btnDelete.contentDescription = "Supprimer ${contact.nom}"
            binding.btnDelete.setOnClickListener { onDeleteClick(contact) }
        }
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean = oldItem == newItem
    }
}
