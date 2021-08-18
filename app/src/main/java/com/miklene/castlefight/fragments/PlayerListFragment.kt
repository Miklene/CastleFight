package com.miklene.castlefight.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.miklene.castlefight.activity.PlayerStatisticsActivity
import com.miklene.castlefight.databinding.FragmentPlayerListBinding
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.mvvm.PlayerViewModel
import com.miklene.castlefight.recycler_view.PlayerRecyclerAdapter
import java.lang.Exception

class PlayerListFragment:Fragment(), PlayerRecyclerAdapter.OnPlayerClickListener {

    private lateinit var binding: FragmentPlayerListBinding
    private lateinit var viewModel: PlayerViewModel
    private var playerList: List<Player> = listOf()

    fun setPlayerList(_playerList: List<Player>) {
        playerList = _playerList
        binding.list.adapter = PlayerRecyclerAdapter(playerList,this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[PlayerViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        viewModel.getAllPlayers().observe(this, Observer<List<Player>>() {
            it?.let {
                setPlayerList(it)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayerListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.list.layoutManager = LinearLayoutManager(activity)
        binding.list.adapter = PlayerRecyclerAdapter(playerList, this)
       /* binding.list.isClickable = true
        binding.list.isFocusable = true*/
        return view
    }

    override fun onPlayerClick(player: Player) {
       val intent = Intent(activity, PlayerStatisticsActivity::class.java)
        intent.putExtra("PlayerName", player.name)
        startActivity(intent)
    }
}