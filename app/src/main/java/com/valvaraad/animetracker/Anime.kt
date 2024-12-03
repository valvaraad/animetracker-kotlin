package com.valvaraad.animetracker

import android.os.Parcel
import android.os.Parcelable

    data class Anime(
        val id: Int,
        val title: String,
        val score: Double,
        val status: String,
        val progress: Int,
        val total: Int,
        val comment: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeDouble(score)
        parcel.writeString(status)
        parcel.writeInt(progress)
        parcel.writeInt(total)
        parcel.writeString(comment)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Anime> {
        override fun createFromParcel(parcel: Parcel): Anime = Anime(parcel)
        override fun newArray(size: Int): Array<Anime?> = arrayOfNulls(size)
    }
}
