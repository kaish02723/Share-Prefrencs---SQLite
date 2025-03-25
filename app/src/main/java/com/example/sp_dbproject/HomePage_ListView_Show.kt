package com.example.sp_dbproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomePage_ListView_Show : AppCompatActivity() {
    lateinit  var  array:ArrayList<UserModelClass>
    private var SQLiteDataHelper=SQLiteOpenHelperClass(this)
    lateinit var BaseAdapter:CustomAdapterClass


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page_list_view_show)

        val exitHomePage=findViewById<LottieAnimationView>(R.id.animationExitHomePage)
        exitHomePage.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        val listViewItem=findViewById<ListView>(R.id.listView)
        BaseAdapter=CustomAdapterClass(this,SQLiteDataHelper.viewDataFromListView())
        listViewItem.adapter=BaseAdapter



        listViewItem.setOnItemLongClickListener { adapterView, view, i, l ->
            var alertDialog=AlertDialog.Builder(this)
            alertDialog.setTitle("Notics").setMessage("Are You Sure You Want To Delete")
            alertDialog.setPositiveButton("YES"){a,b ->
                var inn=array[i].id
                SQLiteDataHelper.deletedUser(inn)
                BaseAdapter=CustomAdapterClass(this,SQLiteDataHelper.viewDataFromListView())
                BaseAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Delete SuccessFully", Toast.LENGTH_SHORT).show()
            }
            alertDialog.setNegativeButton("NO"){a,b ->
                Toast.makeText(this, "Dismiss", Toast.LENGTH_SHORT).show()
                a.dismiss()
            }
            alertDialog.show()

            true
        }


        val flotingActionButtonOpenInsertAlertDialog=findViewById<FloatingActionButton>(R.id.floatingActionButton)
        flotingActionButtonOpenInsertAlertDialog.setOnClickListener {
            startActivity(Intent(this,add_new_member::class.java))
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        BaseAdapter.refreshData(SQLiteDataHelper.viewDataFromListView())
    }
}