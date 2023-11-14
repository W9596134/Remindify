package com.example.remindify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        holder.des.text= ls.get(position).description
        holder.title.text= ls.get(position).title
        holder.date.text= ls.get(position).date
        holder.time.text= ls.get(position).time


    }

    class MyviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var title=itemView.findViewById<TextView>(R.id.title)
        var des=itemView.findViewById<TextView>(R.id.des)
        var date=itemView.findViewById<TextView>(R.id.date)
        var time=itemView.findViewById<TextView>(R.id.time)


    }

}
