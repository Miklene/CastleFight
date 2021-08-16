package com.miklene.castlefight.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miklene.castlefight.R
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race

class RaceRecyclerAdapter(
    private val races: List<Race>,
    private val onRaceClickListener: RaceRecyclerAdapter.OnRaceClickListener
) :
    RecyclerView.Adapter<RaceRecyclerAdapter.MyViewHolder>() {

    interface OnRaceClickListener {
        fun onRaceClick(race: Race)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPlayerName: TextView? = null
        var tvPlayerWins: TextView? = null
        var tvPlayerLoses: TextView? = null

        init {
            tvPlayerName = itemView.findViewById(R.id.tvPlayerName)
            tvPlayerWins = itemView.findViewById(R.id.tvPlayerWins)
            tvPlayerLoses = itemView.findViewById(R.id.tvPlayerLoses)
        }

        fun bind(race: Race, clickListener: RaceRecyclerAdapter.OnRaceClickListener) {
            tvPlayerName?.text = race.name
            tvPlayerLoses?.text = race.loses.toString()
            tvPlayerWins?.text = race.wins.toString()
            itemView.setOnClickListener {
                clickListener.onRaceClick(race)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val race = races[position]
        holder.bind(race, onRaceClickListener)
    }

    override fun getItemCount() = races.size
}