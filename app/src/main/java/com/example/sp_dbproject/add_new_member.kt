package com.example.sp_dbproject

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class add_new_member : AppCompatActivity() {
    private var SQLiteDataHelper = SQLiteOpenHelperClass(this)
    lateinit var bottonSheetOpenClick: BottomSheetDialog
    lateinit var imagesSelected: CircleImageView
    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_new_member)
        bottonSheetOpenClick = BottomSheetDialog(this)







        val nameInsertEditText = findViewById<EditText>(R.id.editTextName)
        val emailInsertEditText = findViewById<EditText>(R.id.editTextEmail)
        imagesSelected= findViewById<CircleImageView>(R.id.profile_image_open_botton_sheet)
        val inserButtonClick = findViewById<AppCompatButton>(R.id.insertButtonData)
        inserButtonClick.setOnClickListener {
            val name = nameInsertEditText.text.toString()
            val email = emailInsertEditText.text.toString()


            val image = (imagesSelected.drawable as BitmapDrawable).bitmap
            val byteArray = getImageBytesFunction(image)


            if (name.isEmpty() || email.isEmpty()){
                nameInsertEditText.setError("please Enter Name")
                emailInsertEditText.setError("Please Enter Email")
                Toast.makeText(this, "Please Enter Your Name & Email Add", Toast.LENGTH_SHORT).show()
            }
            else {
                var model = UserModelClass(0, name, email,byteArray)
                SQLiteDataHelper.insertData(model)
                startActivity(Intent(this, HomePage_ListView_Show::class.java))
            }
        }











        val homePageButtonClick = findViewById<LottieAnimationView>(R.id.animationExitHomePageListViewShowData)
        homePageButtonClick.setOnClickListener {
            startActivity(Intent(this, HomePage_ListView_Show::class.java))
            finish()
        }

        imagesSelected = findViewById(R.id.profile_image_open_botton_sheet)
        imagesSelected.setOnClickListener {
            bottonSheetLayoutFunction()
        }
        val openClickBottonSheetFloting = findViewById<FloatingActionButton>(R.id.floatingActionButton2_open_botton_sheet)
        openClickBottonSheetFloting.setOnClickListener {
            bottonSheetLayoutFunction()
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    @SuppressLint("InflateParams", "MissingInflatedId")
    fun bottonSheetLayoutFunction() {
        var latouyBottonSheet = LayoutInflater.from(this).inflate(R.layout.botton_sheet_cemra_file_alert_layout, null, false)
        var closeBottonSheetClickImageView = latouyBottonSheet.findViewById<ImageView>(R.id.imageViewCloseBottonSheet)
        var cameraOpenBottonClickFlotingAction = latouyBottonSheet.findViewById<FloatingActionButton>(R.id.floatingActionButton2CameraOpenClick)
        var fileOpenBottonClickFlotingAction = latouyBottonSheet.findViewById<FloatingActionButton>(R.id.floatingActionButtoOpenFile)


        fileOpenBottonClickFlotingAction.setOnClickListener {
            var intent=Intent(MediaStore.ACTION_PICK_IMAGES)
            startActivityForResult(intent,1333)
        }
        cameraOpenBottonClickFlotingAction.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }
        closeBottonSheetClickImageView.setOnClickListener {
            bottonSheetOpenClick.dismiss()
        }

        bottonSheetOpenClick.setContentView(latouyBottonSheet)
        bottonSheetOpenClick.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==123 && resultCode== RESULT_OK){
            val photoBmp=data?.extras?.get("data") as Bitmap
            imagesSelected.setImageBitmap(photoBmp)
            bottonSheetOpenClick.dismiss()
        }
        else if (requestCode == 1333 && resultCode == RESULT_OK){
            imagesSelected.setImageURI(data?.data)
            bottonSheetOpenClick.dismiss()
        }
    }






















    fun getImageBytesFunction(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}