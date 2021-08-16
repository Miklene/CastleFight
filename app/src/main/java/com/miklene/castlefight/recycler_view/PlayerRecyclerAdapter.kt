package com.miklene.castlefight.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miklene.castlefight.R
import com.miklene.castlefight.model.Player

class PlayerRecyclerAdapter(
    private val players: List<Player>,
    private val onPlayerClickListener: OnPlayerClickListener
) :
    RecyclerView.Adapter<PlayerRecyclerAdapter.MyViewHolder>() {

    interface OnPlayerClickListener {
        fun onPlayerClick(player: Player)
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

        fun bind(player: Player, clickListener: OnPlayerClickListener) {
            tvPlayerName?.text = player.name
            tvPlayerLoses?.text = player.loses.toString()
            tvPlayerWins?.text = player.wins.toString()
            /*itemView.isClickable = true
            itemView.isFocusable = true*/
            itemView.setOnClickListener {
                clickListener.onPlayerClick(player)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player, onPlayerClickListener)
        /* holder.tvPlayerName?.text = players[position].name
         holder.tvPlayerLoses?.text = players[position].loses.toString()
         holder.tvPlayerWins?.text = players[position].wins.toString()*/

    }

    override fun getItemCount() = players.size
}

