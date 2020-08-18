package com.venky.interview.viewmodel


import android.content.Context
import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import com.venky.interview.`interface`.ServerResult
import com.venky.interview.datamodel.ListModel
import com.venky.interview.repository.ListRepository
import com.venky.interview.utils.NetworkCheck


class ServerListViewModel(
    private var context: Context
) : BaseObservable() {
    var recyclerview: ObservableField<Int>? = null
    var nodatafound: ObservableField<Int>? = null
    var swipeRefreshLayout: ObservableField<Boolean>? = null
    private var repository: ListRepository? = null

    @Bindable
    public var mainlist = ArrayList<ListModel>();
    private fun setList(string: ArrayList<ListModel>) {
        this.mainlist = string
        notifyPropertyChanged(BR.mainlist)
    }

    init {
        recyclerview = ObservableField()
        nodatafound = ObservableField()
        swipeRefreshLayout = ObservableField()
        recyclerview!!.set(View.GONE)
        nodatafound!!.set(View.VISIBLE)
        swipeRefreshLayout!!.set(false)
        repository = swipeRefreshLayout?.let { ListRepository(it, context) };

    }

    fun refreshdata() {
        var result = object : ServerResult {
            override fun success(result: ArrayList<ListModel>) {
                if (result.size > 0) {
                    recyclerview!!.set(View.VISIBLE)
                    nodatafound!!.set(View.GONE)
                    setList(result)
                } else {
                    recyclerview!!.set(View.GONE)
                    nodatafound!!.set(View.VISIBLE)
                }
            }
        }
        if (NetworkCheck.isInternetAvailable(context)) {
            swipeRefreshLayout!!.set(true)
            repository!!.getOnline(result)
        } else {
            swipeRefreshLayout!!.set(true)
            repository!!.getOffline(result)
        }
    }

    fun destroyView() {
        repository!!.destroy()
    }

}