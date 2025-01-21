package edu.pract5.apirestfree.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.pract5.apirestfree.model.Alimento
import edu.pract5.apirestfree.databinding.ItemAlimentoBinding
import kotlinx.coroutines.launch

class AlimentosAdapter(
    private val onAlimentoClick: (alimento: Alimento) -> Unit,
    val onAlimentoDeleteClick: (Alimento, pos: Int) -> Unit
) : ListAdapter<Alimento, AlimentosAdapter.ViewHolder>(DiffAlimentosCallback()) {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemAlimentoBinding.bind(view)
        fun bind(alimento: Alimento) {
            binding.tvAlimento.setText(alimento.name)

            itemView.setOnClickListener {
                onAlimentoClick(alimento)
            }

            binding.ivDelete.setOnClickListener {
                onAlimentoDeleteClick(alimento, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAlimentoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffAlimentosCallback : DiffUtil.ItemCallback<Alimento>() {
    override fun areItemsTheSame(oldItem: Alimento, newItem: Alimento): Boolean {
        return oldItem.name == newItem.name
    }
    override fun areContentsTheSame(oldItem: Alimento, newItem: Alimento): Boolean {
        return oldItem == newItem
    }
}