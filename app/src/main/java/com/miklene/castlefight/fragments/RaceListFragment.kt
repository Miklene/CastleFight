package com.miklene.castlefight.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.miklene.castlefight.activity.PlayerStatisticsActivity
import com.miklene.castlefight.databinding.FragmentPlayerListBinding
import com.miklene.castlefight.model.Race
import com.miklene.castlefight.mvvm.RaceViewModel
import com.miklene.castlefight.recycler_view.RaceRecyclerAdapter
import java.lang.Exception

class RaceListFragment : Fragment(), RaceRecyclerAdapter.OnRaceClickListener {

    private lateinit var binding: FragmentPlayerListBinding
    private lateinit var viewModel: RaceViewModel
    private var raceList: List<Race> = listOf()

    private fun setRaceList(_raceList: List<Race>) {
        raceList = _raceList
        binding.list.adapter = RaceRecyclerAdapter(raceList, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[RaceViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        viewModel.getAllRaces().observe(this, Observer<List<Race>>() {
            it?.let {
                setRaceList(it)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerListBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.list.layoutManager = LinearLayoutManager(activity)
        binding.list.adapter = RaceRecyclerAdapter(raceList, this)
        binding.tvPlayerNameHeader.text = "Название расы"
        return view
    }

    override fun onRaceClick(race: Race) {
        val intent = Intent(activity, PlayerStatisticsActivity::class.java)
        intent.putExtra("RaceName", race.name)
        startActivity(intent)
    }
}