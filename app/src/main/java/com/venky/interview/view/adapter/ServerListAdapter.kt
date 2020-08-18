package com.venky.interview.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.venky.interview.databinding.CustomListadapterBinding
import com.venky.interview.datamodel.ListModel
import com.venky.interview.viewmodel.ItemViewModel
import java.util.*
import com.venky.interview.R
import kotlin.collections.ArrayList


class ServerListAdapter(private var arrayList: ArrayList<ListModel>, private var context: Context): RecyclerView.Adapter<ServerListAdapter.MyViewHolder>() {
    private  lateinit var search:ArrayList<ListModel>
    class MyViewHolder(var binding: CustomListadapterBinding) : RecyclerView.ViewHolder(binding.cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: CustomListadapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.custom_listadapter, parent, false)
        return MyViewHolder(binding)
    }
    fun check(searchEvent: ArrayList<ListModel>)
    {
        search= ArrayList<ListModel>()
        search.addAll(searchEvent)
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val viewModel = ItemViewModel(arrayList.get(position))
        holder.binding.itemviewModel=viewModel
    }
    fun filter(charText: String): Int {
        var charText = charText
        val i: Int
        charText = charText.toLowerCase(Locale.getDefault())
        arrayList.clear()
        if (charText.length == 0) {
            arrayList.addAll(search)
        } else {
            for (hash in search) {
                if(hash.title!=null) {
                    if ((hash.title!!.toLowerCase(Locale.getDefault()).contains(charText))) {
                        arrayList.add(hash)
                        Log.i("@@@search@@@", "" + arrayList)
                    }
                }
            }
        }
        i = arrayList.size
        notifyDataSetChanged()
        return i
    }

}