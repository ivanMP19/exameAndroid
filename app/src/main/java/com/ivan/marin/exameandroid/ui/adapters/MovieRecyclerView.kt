package com.ivan.marin.exameandroid.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ivan.marin.exameandroid.data.entities.MovieModel
import com.ivan.marin.exameandroid.databinding.MovieItemBinding
import com.ivan.marin.exameandroid.ui.interfaces.MovieItemListener
import com.ivan.marin.exameandroid.utils.Constantes.SMALL_IMAGE_URL_PREFIX

class MovieRecyclerView(private val listener: MovieItemListener) :  RecyclerView.Adapter<MovieViewHolder>() {

    private val items = ArrayList<MovieModel>()
    private lateinit var mContext: Context

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        mContext = parent.context
        val binding: MovieItemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding,listener,mContext)
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: ArrayList<MovieModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}
class MovieViewHolder(private val itemBinding: MovieItemBinding, private val listener: MovieItemListener,private val mContext: Context)
    : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener{

    private lateinit var movieModel: MovieModel

    init {
        itemBinding.root.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: MovieModel) {
        this.movieModel = item
        itemBinding.txtName.text = movieModel.mOriginalTitle
        itemBinding.txtDescriptiontVale.text = movieModel.mOverview
        itemBinding.imgMovieImage.let {
            Glide.with(itemBinding.root)
                .load(SMALL_IMAGE_URL_PREFIX+movieModel.mPosterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(it)
        }
    }

    override fun onClick(p0: View?) {
        listener.onClickedMovie(movieModel.mId)
    }
}