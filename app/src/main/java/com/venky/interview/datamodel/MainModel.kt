package com.venky.interview.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.venky.interview.database.BaseConvertor
import com.venky.interview.datamodel.ListModel


@Entity(tableName = "datalist")
@TypeConverters(BaseConvertor::class)
class MainModel {
    @PrimaryKey()
    public var id=1
    @SerializedName("title")
    public  var title: String? = null
    @SerializedName("rows")
    public var rows: List<ListModel>? = null

}