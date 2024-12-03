package com.valvaraad.animetracker

import android.os.Parcel
import android.os.Parcelable

data class Manga(
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
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Manga> {
        override fun createFromParcel(parcel: Parcel): Manga = Manga(parcel)
        override fun newArray(size: Int): Array<Manga?> = arrayOfNulls(size)
    }
}
