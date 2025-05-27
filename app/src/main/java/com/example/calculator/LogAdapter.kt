package com.example.calculator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LogAdapter(val listOperations: List<LogData>): RecyclerView.Adapter<LogAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val textLog: TextView=itemView.findViewById(R.id.textLog)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.log_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val log=listOperations[position]
        holder.textLog.text="${log.expression} = ${log.result}"
    }

    override fun getItemCount(): Int {
        return listOperations.size
    }
}