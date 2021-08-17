package com.miklene.castlefight.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.miklene.castlefight.R
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Statistics

class StatisticsRecyclerAdapter(
    private val stat: List<Statistics>,
    private val onStatisticsItemClickListener: OnStatisticsItemClickListener
) :
    RecyclerView.Adapter<StatisticsRecyclerAdapter.MyViewHolder>() {

    interface OnStatisticsItemClickListener {
        fun onStatisticsItemClick(statistics: Statistics)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvPlayerName: TextView? = null
        var tvPlayerFights: TextView? = null
        var tvPlayerWins: TextView? = null
        var tvPlayerLoses: TextView? = null
        var tvPlayerWinRate: TextView? = null

        init {
            tvPlayerName = itemView.findViewById(R.id.tvStatisticsNameItem)
            tvPlayerFights = itemView.findViewById(R.id.tvStatisticsFightsItem)
            tvPlayerWins = itemView.findViewById(R.id.tvStatisticsWinsItem)
            tvPlayerLoses = itemView.findViewById(R.id.tvStatisticsLosesItem)
            tvPlayerWinRate = itemView.findViewById(R.id.tvStatisticsWinRateItem)
        }

        fun bind(stat: Statistics, clickListener: OnStatisticsItemClickListener) {
            tvPlayerName?.text = stat.name
            tvPlayerFights?.text = stat.fights.toString()
            tvPlayerWins?.text = stat.wins.toString()
            tvPlayerLoses?.text = stat.loses.toString()
            val winRate = stat.winRate
            val strWinRate = "$winRate%"
            tvPlayerWinRate?.text = strWinRate
            itemView.setOnClickListener {
                clickListener.onStatisticsItemClick(stat)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.statistics_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val st = stat[position]
        holder.bind(st, onStatisticsItemClickListener)
    }

    override fun getItemCount() = stat.size
}