package com.example.sp_dbproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class CustomAdapterClass(private var context: Context,private var arrayList: ArrayList<UserModelClass>):BaseAdapter() {
private var SQLiteHelperClass=SQLiteOpenHelperClass(context)

    override fun getCount(): Int {
       return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
      return  p0.toLong()
    }

    @SuppressLint("ViewHolder", "MissingInflatedId")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val getView=arrayList[p0]
        val layoutCustomListView=LayoutInflater.from(context).inflate(R.layout.custome_list_view_item,p2,false)
        val name=layoutCustomListView.findViewById<TextView>(R.id.nameTextView)
        val email=layoutCustomListView.findViewById<TextView>(R.id.emailTextView)



        //show Images
        val imageInserted=layoutCustomListView.findViewById<CircleImageView>(R.id.profile_image)
        val updateUser=layoutCustomListView.findViewById<ImageView>(R.id.imageViewUpdateUser)


        // image insertted in list view image show data
        val bitmap =BitmapFactory.decodeByteArray(getView.image, 0, getView.image.size)
        imageInserted.setImageBitmap(bitmap)


        // update acvity me imageView me Image set
        var bitPhoto=(imageInserted.drawable as BitmapDrawable).bitmap
        var byteArray=byteArrayConvertFun(bitPhoto)

        updateUser.setOnClickListener {


            val intent=Intent(context,update_user::class.java).apply {
                putExtra("id",getView.id)
                putExtra("name",getView.name)
                putExtra("email",getView.email)
                putExtra("image",byteArray)
            }
            p2?.context?.startActivity(intent)
        }


        val deletedUser=layoutCustomListView.findViewById<ImageView>(R.id.imageViewDeletedUser)
        deletedUser.setOnClickListener {
            var alert=AlertDialog.Builder(context)
            alert.setTitle("Notic").setMessage("Are You Sure You Want To Deleted")

            alert.setPositiveButton("YES"){a,b ->
                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
                SQLiteHelperClass.deletedUser(getView.id)
                refreshData(SQLiteHelperClass.viewDataFromListView())
                notifyDataSetChanged()
                a.dismiss()
            }
            alert.setNegativeButton("NO"){a,b ->
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                a.dismiss()
            }
            alert.show()
        }






        name.text=getView.name
        email.text=getView.email
        return layoutCustomListView
    }

 fun byteArrayConvertFun(bit: Bitmap): ByteArray {
            val byteArrayOutputStream=ByteArrayOutputStream()
            bit.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
    }

    fun refreshData(arrayData: ArrayList<UserModelClass>){
        arrayList=arrayData
        notifyDataSetChanged()
    }

}