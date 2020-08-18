package com.venky.interview.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.venky.interview.`interface`.ServerResult
import com.venky.interview.datamodel.ListDataBase
import com.venky.interview.datamodel.ListModel
import com.venky.interview.datamodel.MainModel
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ListRepository(private var swipeRefreshLayout: ObservableField<Boolean>, private var context: Context){
  var compositeDisposable=CompositeDisposable();
    var db= ListDataBase.getDatabase(context)
    fun getOnline(result: ServerResult) {
        // This isn't an optimal implementation. We'll fix it later.
        val data = ArrayList<ListModel>()
        compositeDisposable.add(fetch()!!.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe(Consumer {string->
                swipeRefreshLayout.set(false)
                Log.d("Success", string)
                val gson = Gson()
                val mainModel: MainModel =gson.fromJson(string, MainModel::class.java)
                (context as AppCompatActivity).supportActionBar!!.setTitle(mainModel.title)
                mainModel.rows?.let { data.addAll(it) }
                result.success(data)
               insertDB(mainModel)
            }, Consumer {error->
                swipeRefreshLayout.set(false)
                Toast.makeText(context, "Error - $error", Toast.LENGTH_SHORT).show()
                Log.d("Error", "onErrorResponse: ERROR - $error")
            }))
    }
    fun getOffline(result: ServerResult){
        // This isn't an optimal implementation. We'll fix it later.
        val data = ArrayList<ListModel>()
        compositeDisposable.add(
            db!!.mainDao().getAll()!!.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(Consumer{datamodel ->
                    Log.d("Success", ""+datamodel)
                    swipeRefreshLayout.set(false)
                    datamodel.rows?.let { data.addAll(it) }
                    result.success(data)
                    (context as AppCompatActivity).supportActionBar!!.setTitle(datamodel.title)
                }, Consumer { error: Throwable ->
                    swipeRefreshLayout.set(false)
                    Log.d("Error", "onErrorResponse: ERROR - $error")
                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()

                }))

    }
    private fun fetch(): Observable<String>? {
        val future = RequestFuture.newFuture<String>()
        val req: StringRequest = object : StringRequest("https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json", future, future)
        {

        }
        req.retryPolicy = DefaultRetryPolicy(
            600000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        Volley.newRequestQueue(context).add(req)
        return Observable.fromFuture(future, 600000, TimeUnit.SECONDS, Schedulers.io())
    }
    private fun insertDB(mainModel: MainModel) {
        compositeDisposable.add(
            db!!.mainDao().insertFile(mainModel)!!.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(Action{ ->
                Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show()
            }, Consumer { error: Throwable ->

                    Toast.makeText(context, error.localizedMessage, Toast.LENGTH_SHORT).show()

            }))
    }
    fun destroy()
    {
        if(compositeDisposable!=null&&!compositeDisposable.isDisposed)
        {
            compositeDisposable.dispose()
            db=null
        }
    }
}