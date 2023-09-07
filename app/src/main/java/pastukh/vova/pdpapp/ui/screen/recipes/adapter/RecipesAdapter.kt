package pastukh.vova.pdpapp.ui.screen.recipes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import pastukh.vova.baseui.R
import pastukh.vova.baseui.entity.RecipeEntity
import pastukh.vova.baseui.entity.RecipeState
import pastukh.vova.pdpapp.databinding.RecipeItemBinding
import pastukh.vova.pdpapp.ui.utils.visibility

class RecipesAdapter :
    ListAdapter<RecipeEntity, RecipesAdapter.EventViewHolder>(EventDiffCallback) {

    private var listener: ((Int) -> Unit)? = null
    private var downloadListener: ((Int) -> Unit)? = null

    fun setClickListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    fun setDownloadClickListener(listener: (Int) -> Unit) {
        downloadListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(
            RecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener,
            downloadListener,
        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class EventViewHolder(
        private val binding: RecipeItemBinding,
        private val listener: ((Int) -> Unit)?,
        private val downloadListener: ((Int) -> Unit)?,
    ) : ViewHolder(binding.root) {

        private val context: Context
            get() = itemView.context

        fun bind(item: RecipeEntity) = with(binding) {
            when (item.state) {
                RecipeState.DOWNLOADING -> {
                    downloadIv.visibility(false)
                    progress.show()
                }

                RecipeState.DOWNLOADED -> {
                    progress.hide()
                    downloadIv.visibility(false)
                }

                RecipeState.DEFAULT -> {
                    progress.hide()
                    downloadIv.visibility(true)
                    downloadIv.setOnClickListener { downloadListener?.invoke(item.id) }
                }
            }
            Picasso.get().load(item.image).into(imageIv)
            titleTv.text = item.title
            countryTv.text = context.getString(R.string.recipe_country, item.country)
            descriptionTv.text = context.getString(R.string.recipe_description, item.description)

            cardView.setOnClickListener { listener?.invoke(item.id) }
        }

    }

    private object EventDiffCallback : DiffUtil.ItemCallback<RecipeEntity>() {
        override fun areItemsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: RecipeEntity, newItem: RecipeEntity) =
            oldItem == newItem
    }
}
