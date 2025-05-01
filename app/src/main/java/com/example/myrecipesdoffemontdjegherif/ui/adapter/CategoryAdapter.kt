package com.example.myrecipesdoffemontdjegherif.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipesdoffemontdjegherif.data.model.Category
import com.example.myrecipesdoffemontdjegherif.databinding.ItemCategoryBinding
import com.squareup.picasso.Picasso

// Adapter pour afficher une liste de catégories dans un RecyclerView
class CategoryAdapter(private val categories: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    // ViewHolder = objet qui représente chaque ligne de la liste
    // On y stocke un objet binding lié au layout item_category.xml
    class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    // Appelé lorsqu'on a besoin de créer une nouvelle "ligne"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Création du binding à partir du layout XML (item_category.xml)
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    // Appelé pour afficher les données à une position donnée
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // On récupère la catégorie à afficher
        val category = categories[position]

        // On met à jour le contenu du TextView avec le nom de la catégorie
        holder.binding.textCategory.text = category.strCategory

        // Image avec Picasso
        Picasso.get()
            .load(category.strCategoryThumb)
            .into(holder.binding.imageCategory)
    }

    // Nombre total d'éléments dans la liste
    override fun getItemCount(): Int = categories.size
}
