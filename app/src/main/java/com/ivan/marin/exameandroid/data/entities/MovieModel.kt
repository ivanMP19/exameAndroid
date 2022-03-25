package com.ivan.marin.exameandroid.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName ="movies")
data class MovieModel(
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName(value = "id")  val mId: Int,
    @ColumnInfo(name = "vote_count") @SerializedName(value="vote_count")  val mVoteCount: Int,
    @ColumnInfo(name = "video") @SerializedName(value="video")  val mVideo: Boolean,
    @ColumnInfo(name = "vote_average") @SerializedName(value="vote_average")  val mVoteAverage: Float,
    @ColumnInfo(name = "title") @SerializedName(value="title")  val mTitle: String,
    @ColumnInfo(name = "popularity") @SerializedName(value="popularity")  val mPopularity: Float,
    @ColumnInfo(name = "poster_path") @SerializedName(value="poster_path")  val mPosterPath: String,
    @ColumnInfo(name = "original_language") @SerializedName(value="original_language")  val mOriginalLanguage: String,
    @ColumnInfo(name = "original_title") @SerializedName(value="original_title")  val mOriginalTitle: String,
    @ColumnInfo(name = "backdrop_path") @SerializedName(value="backdrop_path") val mBackdropPath: String,
    @ColumnInfo(name = "adult") @SerializedName(value="adult")  val mAdult: Boolean,
    @ColumnInfo(name = "overview") @SerializedName(value="overview")  val mOverview: String,
    @ColumnInfo(name = "release_date") @SerializedName(value="release_date")  val mReleaseDate: String
    )/*: BaseObservable() {


    var DIFF_CALLBACK: DiffUtil.ItemCallback<MovieModel> = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.mId == newItem.mId
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem.mId == newItem.mId
        }
    }


}*/