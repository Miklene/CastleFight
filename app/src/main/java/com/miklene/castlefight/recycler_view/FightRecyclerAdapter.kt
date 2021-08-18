package com.miklene.castlefight.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miklene.castlefight.R
import com.miklene.castlefight.model.Round

class FightRecyclerAdapter(private val rounds: List<Round>) :
    RecyclerView.Adapter<FightRecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvWinnerName: TextView? = null
        var tvLoserName: TextView? = null
        var tvWinnerRace: TextView? = null
        var tvLoserRace: TextView? = null


        init {
            tvWinnerName = itemView.findViewById(R.id.tvWinnerName)
            tvLoserName = itemView.findViewById(R.id.tvLoserName)
            tvWinnerRace = itemView.findViewById(R.id.tvWinnerRace)
            tvLoserRace = itemView.findViewById(R.id.tvLoserRace)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fight_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvWinnerName?.text = rounds[position].winner
        holder.tvLoserName?.text = rounds[position].loser
        holder.tvWinnerRace?.text = rounds[position].winnerRace
        holder.tvLoserRace?.text = rounds[position].loserRace
    }

    override fun getItemCount() = rounds.size
}