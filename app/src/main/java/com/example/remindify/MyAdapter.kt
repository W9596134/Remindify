package com.example.remindify

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MyAdapter(ls: ArrayList<Reminder>, context: Context?) : RecyclerView.Adapter<MyAdapter.MyviewHolder>() {
    var ls = ls
    var context =context;
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
//        TODO("Not yet implemented")
        var view= LayoutInflater.from(parent.context).inflate(R.layout.row,parent,false)
        return MyviewHolder(view)
    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return ls.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
//        TODO("Not yet implemented")
        holder.des.text= ls.get(position).reminder.description
        holder.title.text= ls.get(position).reminder.title
        holder.date.text= ls.get(position).reminder.date
        holder.time.text= ls.get(position).reminder.time

        var db= FirebaseDatabase.getInstance()
        var ref=db.getReference("reminders")
            .child(FirebaseAuth.getInstance().currentUser?.uid?:"")
            .child("Reminders")

        holder.itemView.setOnClickListener {
            var intent=Intent(context,ViewReminder::class.java)
            intent.putExtra("title",ls.get(position).reminder.title)
            intent.putExtra("des",ls.get(position).reminder.description)
            intent.putExtra("date",ls.get(position).reminder.date)
            intent.putExtra("time",ls.get(position).reminder.time)
            intent.putExtra("image",ls.get(position).reminder.image)
            intent.putExtra("id",ls.get(position).id)
            context?.startActivity(intent)


        }

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(this.context!!)
            builder.setTitle("Remove Reminder")
            builder.setMessage("Are you sure you want to remove this reminder?")
            builder.setPositiveButton("OK"){dialogInterface, which ->
                ref.child(ls.get(position).id).removeValue()
                ls.removeAt(position)
                notifyDataSetChanged()
            }
            builder.setNegativeButton("Cancel"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            builder.show()

            true


        }
    }

    class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title=itemView.findViewById<TextView>(R.id.title)
        var des=itemView.findViewById<TextView>(R.id.des)
        var date=itemView.findViewById<TextView>(R.id.date)
        var time=itemView.findViewById<TextView>(R.id.time)


    }

}
