package com.venky.interview.viewmodel

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.venky.interview.datamodel.ListModel

class ItemViewModel(private var model: ListModel): BaseObservable() {
    init {
        if(model.imageHref==null)imageUrl("https://web.whatsapp.com") else model.imageHref?.let { imageUrl(it) }
    }
    @Bindable
    public var imageUrl:String?=null;
    private  fun imageUrl(imageUrl:String){
        this.imageUrl=imageUrl
        notifyPropertyChanged(BR.imageUrl)
    }
    fun getTitle(): SpannableStringBuilder? {
        val title = SpannableStringBuilder("Title: "+if (model.title == null) "No Title" else model.title)
        title.setSpan( StyleSpan(Typeface.BOLD), 0, 6, 0)
        return title
    }

    fun getDescription(): SpannableStringBuilder? {
        val description = SpannableStringBuilder("Description: "+if (model.description == null) "No Description" else model.description)
        description.setSpan( StyleSpan(Typeface.BOLD), 0, 12, 0)
        return description
    }
}