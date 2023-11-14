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

        var ref= FirebaseDatabase.getInstance().getReference("reminders")
            .child(FirebaseAuth.getInstance().currentUser?.uid?:"")
            .child("Reminders")
        ref.addChildEventListener( object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                var reminder=snapshot.getValue(Reminder::class.java)
                if(reminder!=null){
                    Toast.makeText(context,reminder.title.toString(),Toast.LENGTH_SHORT).show()
                    ls.add(reminder)
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onChildRemoved(snapshot: DataSnapshot){
            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        
    }

}