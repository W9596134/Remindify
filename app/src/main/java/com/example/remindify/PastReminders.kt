package com.example.remindify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.childEvents
import com.google.firebase.database.getValue
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PastReminders.newInstance] factory method to
 * create an instance of this fragment.
 */
class PastReminders : Fragment(R.layout.fragment_past_reminders) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var rv=view.findViewById<RecyclerView>(R.id.rv)
        var ls=ArrayList<Reminder>()
        var adapter= MyAdapter(ls,context)
        rv.adapter=adapter
        var lm=LinearLayoutManager(context);
        rv.layoutManager=lm

        var db=FirebaseDatabase.getInstance()
        var ref=db.getReference("reminders")
            .child(FirebaseAuth.getInstance().currentUser?.uid?:"")
            .child("Reminders")
        ref.keepSynced(true)
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var reminderw=snapshot.getValue<Reminderw>()
                var cal = Calendar.getInstance()

                if (reminderw != null) {
                    cal.set(Calendar.DAY_OF_MONTH, reminderw.date!!.split("/")[0].toInt())
                    cal.set(Calendar.MONTH,reminderw.date!!.split("/")[1].toInt()-1)
                    cal.set(Calendar.YEAR,reminderw.date!!.split("/")[2].toInt())
                }

                if (reminderw != null) {
                    cal.set(Calendar.HOUR_OF_DAY,reminderw.time!!.split(":")[0].toInt())
                    cal.set(Calendar.MINUTE,reminderw.time!!.split(":")[1].toInt())
                }
                val reminder=Reminder(snapshot.key.toString(),reminderw!!,cal.timeInMillis)
                if (reminder != null && System.currentTimeMillis()>reminder.timeinmilli) {

                    ls.add(reminder)
                    ls.sortBy { it.timeinmilli }
                    ls.reverse()
                    adapter.notifyDataSetChanged()
                }
                adapter.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }

        })


    }

}