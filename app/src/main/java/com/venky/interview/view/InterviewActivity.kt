package com.venky.interview.view

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.IntentFilter
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.venky.interview.R
import com.venky.interview.databinding.ActivityInterviewBinding
import com.venky.interview.`interface`.Connectivity
import com.venky.interview.view.fragment.ServerListFragment
import com.example.interview.task.view.reciever.NetworkChangeReceiver


class InterviewActivity : AppCompatActivity() {
    private var connectivity: Connectivity? = null;
    private var connectivityReceiver: NetworkChangeReceiver? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityInterviewBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_interview
        )
        binding.executePendingBindings();
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Home"
        showFragment(ServerListFragment.newInstance("List"))


    }

    public fun setConnect(listener: Connectivity) {
        this.connectivity = listener
        val intentFilter = IntentFilter(
            "android.net.conn.CONNECTIVITY_CHANGE"
        )

        connectivityReceiver = NetworkChangeReceiver(connectivity!!);
        registerReceiver(connectivityReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver)
        }
    }

    override fun onBackPressed() {
        checkback()
    }

    private fun showFragment(fragment: Fragment) {
        val fm: FragmentManager = supportFragmentManager

        @SuppressLint("CommitTransaction")
        val ft: FragmentTransaction = fm.beginTransaction().replace(R.id.container, fragment)
        ft.addToBackStack(null)
        ft.commit()
        fm.executePendingTransactions()
    }

    private fun checkback() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id -> finish() })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id -> //  Action for 'NO' Button
                        dialog.cancel()
                    })
            val alert: AlertDialog = builder.create()
            alert.setTitle("Really Exit?")
            alert.show()
            alert.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.colorPrimary))
            alert.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.colorPrimary))
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                // app icon in action bar clicked; go home
                checkback()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}