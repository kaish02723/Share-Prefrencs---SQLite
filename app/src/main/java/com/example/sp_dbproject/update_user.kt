package com.example.sp_dbproject

import android.annotation.SuppressLint
import android.app.ComponentCaller
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresExtension
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream

class update_user : AppCompatActivity() {
    private var SQLiteHelperClass=SQLiteOpenHelperClass(this)
    private var uId:Int=-1
    lateinit var bottonSheetOpenClick: BottomSheetDialog
    lateinit var imagesSelected: CircleImageView


    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    @SuppressLint("MissingInflatedId", "CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_user)
        bottonSheetOpenClick = BottomSheetDialog(this)







        imagesSelected=findViewById<CircleImageView>(R.id.profile_image_open_botton_sheet_update)
        val updateNameEditText=findViewById<EditText>(R.id.editTextUpdateName)
        val updateEmailEditText=findViewById<EditText>(R.id.editTextUpdateEmail)
        val updateClickButton=findViewById<Button>(R.id.updateClickButtonData)


        val int=intent.getByteArrayExtra("image")
        if (int != null){
            var bitMap=BitmapFactory.decodeByteArray(int,0,int.size)
            imagesSelected.setImageBitmap(bitMap)
        }




        val nameSetText=intent.getStringExtra("name")
        val emailSetText=intent.getStringExtra("email")
        updateNameEditText.setText(nameSetText)
        updateEmailEditText.setText(emailSetText)


















        uId=intent.getIntExtra("id",-1)
        if (uId == -1){
            finish()
            return
        }
        updateClickButton.setOnClickListener {
           val name=updateNameEditText.text.toString()
           val email=updateEmailEditText.text.toString()

            val image = (imagesSelected.drawable as BitmapDrawable).bitmap
            val byteArray = getImageBytes(image)



            if (name.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Please Enter Your Name & Email", Toast.LENGTH_SHORT).show()
            }
            else{
                val model=UserModelClass(uId,name,email,byteArray)
                SQLiteHelperClass.updateFunction(model)

                startActivity(Intent(this,HomePage_ListView_Show::class.java))
                Toast.makeText(this, "update successFully", Toast.LENGTH_SHORT).show()
                finish()
            }


        }
















        val animationArrowClickButtonHomePage = findViewById<LottieAnimationView>(R.id.animationExitHomePageListViewUpdateData)
        animationArrowClickButtonHomePage.setOnClickListener {
            startActivity(Intent(this, HomePage_ListView_Show::class.java))
            finish()
        }

        imagesSelected = findViewById(R.id.profile_image_open_botton_sheet_update)
        imagesSelected.setOnClickListener {
            bottonSheetLayoutFunction()
        }
        val openClickBottonSheetFloting = findViewById<FloatingActionButton>(R.id.floatingActionButton2_open_botton_sheet_update)
        openClickBottonSheetFloting.setOnClickListener {
            bottonSheetLayoutFunction()
        }

    }

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    fun bottonSheetLayoutFunction() {
        var latouyBottonSheet = LayoutInflater.from(this).inflate(R.layout.botton_sheet_cemra_file_alert_layout, null, false)
        var closeBottonSheetClickImageView = latouyBottonSheet.findViewById<ImageView>(R.id.imageViewCloseBottonSheet)
        var cameraOpenBottonClickFlotingAction = latouyBottonSheet.findViewById<FloatingActionButton>(R.id.floatingActionButton2CameraOpenClick)
        var fileOpenBottonClickFlotingAction = latouyBottonSheet.findViewById<FloatingActionButton>(R.id.floatingActionButtoOpenFile)


        fileOpenBottonClickFlotingAction.setOnClickListener {
            var intent=Intent(MediaStore.ACTION_PICK_IMAGES)
            startActivityForResult(intent,999)
            bottonSheetOpenClick.dismiss()
        }
        cameraOpenBottonClickFlotingAction.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 333)
            bottonSheetOpenClick.dismiss()
        }
        closeBottonSheetClickImageView.setOnClickListener {
            bottonSheetOpenClick.dismiss()
        }

        bottonSheetOpenClick.setContentView(latouyBottonSheet)
        bottonSheetOpenClick.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 333 && resultCode == RESULT_OK){
            val bitPhoto=data?.extras?.get("data") as Bitmap
            imagesSelected.setImageBitmap(bitPhoto)
        }
        else if (requestCode==999 && resultCode== RESULT_OK){
            imagesSelected.setImageURI(data?.data)
        }
    }


















    fun getImageBytes(bitmap: Bitmap): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}