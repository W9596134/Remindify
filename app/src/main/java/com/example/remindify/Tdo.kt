package com.example.remindify

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.makeramen.roundedimageview.RoundedImageView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tdo.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tdo : Fragment(R.layout.fragment_tdo) {
    var img: ImageView? =null
    var imageurl:String?=null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var title = view.findViewById<TextView>(R.id.title)
        var date = view.findViewById<TextView>(R.id.date)
        var save = view.findViewById<Button>(R.id.save)
        var image = view.findViewById<TextView>(R.id.image)
        var description = view.findViewById<TextView>(R.id.description)
        img = view.findViewById<RoundedImageView>(R.id.img)
        getDate(date,requireContext())
        var time = view.findViewById<TextView>(R.id.time)
        getTime(time,requireContext())


        save.setOnClickListener {

            var titleText = title.text.toString()
            var dateText = date.text.toString()
            var timeText = time.text.toString()
            var descriptionText = description.text.toString()
            var reminder = Reminderw( titleText,descriptionText,dateText,timeText,imageurl)
            var ref=FirebaseDatabase.getInstance().getReference("reminders")
                .child(FirebaseAuth.getInstance().currentUser?.uid?:"")
                .child("Reminders")
            ref.push()
                .setValue(reminder)
                .addOnCompleteListener(){
                    title.text=""
                    date.text=""
                    time.text=""
                    description.text=""
                    img?.visibility=View.GONE
                    Toast.makeText(context,"Reminder Added",Toast.LENGTH_SHORT).show()

                }.addOnSuccessListener {
                    var cal = Calendar.getInstance()

                    cal.set(Calendar.DAY_OF_MONTH,dateText.split("/")[0].toInt())
                    cal.set(Calendar.MONTH,dateText.split("/")[1].toInt()-1)
                    cal.set(Calendar.YEAR,dateText.split("/")[2].toInt())

                    cal.set(Calendar.HOUR_OF_DAY,timeText.split(":")[0].toInt())
                    cal.set(Calendar.MINUTE,timeText.split(":")[1].toInt())

                    val delay = cal.timeInMillis - System.currentTimeMillis()
                    Log.e("timessdsd",delay.toString())

                    notificationDialog(requireContext(),cal.timeInMillis,titleText,descriptionText,0)

                    }

            Toast.makeText(context,"Reminder Added",Toast.LENGTH_SHORT).show()
        }

        image.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            changeImage.launch(pickImg)
        }
    }

    private val changeImage =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                Toast.makeText(context,it.data.toString(),Toast.LENGTH_SHORT).show()
                val data = it.data
                val imgUri = data?.data
                img?.setImageURI(imgUri)
                var auth = FirebaseAuth.getInstance()
                var storageRef=FirebaseStorage.getInstance().reference
                var imageRef = storageRef.child("images/${auth.currentUser?.uid}")
                var uploadTask = imageRef.putFile(imgUri!!)
                uploadTask.addOnFailureListener {
                    Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show()
                }.addOnSuccessListener {
                    it.storage.downloadUrl.addOnSuccessListener {
                        img?.visibility=View.VISIBLE
                        imageurl=it.toString();
                    }
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                }
            }
        }


    fun getTime(textView: TextView, context: Context){

        val cal = Calendar.getInstance()

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            textView.text = SimpleDateFormat("HH:mm").format(cal.time)
        }

        textView.setOnClickListener {
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }
    }
    fun getDate(textView: TextView, context: Context){

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { timePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            textView.text = SimpleDateFormat("dd/MM/yyyy").format(cal.time)
        }

        textView.setOnClickListener {
            DatePickerDialog(context, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }



    }
    fun notificationDialog(context: Context, time:Long, title:String, description:String, id:Int) {
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val NOTIFICATION_CHANNEL_ID = "C_id"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "My Notifications",
                NotificationManager.IMPORTANCE_MAX
            )
            // Configure the notification channel.
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(time)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(description)
            .setContentInfo("")
        notificationManager.notify(id, notificationBuilder.build())
    }

}