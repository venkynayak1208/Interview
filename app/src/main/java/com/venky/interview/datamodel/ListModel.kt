package com.venky.interview.datamodel


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName




class ListModel() :Parcelable{
    @SerializedName("title")
   public var title: String? = null

    @SerializedName("description")
    public  var description: String? = null

    @SerializedName("imageHref")
    public var imageHref: String? = null

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        description = parcel.readString()
        imageHref = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(imageHref)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListModel> {
        override fun createFromParcel(parcel: Parcel): ListModel {
            return ListModel(parcel)
        }

        override fun newArray(size: Int): Array<ListModel?> {
            return arrayOfNulls(size)
        }
    }


}