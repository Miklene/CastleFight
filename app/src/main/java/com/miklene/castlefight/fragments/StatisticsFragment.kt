package com.miklene.castlefight.fragments

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.miklene.castlefight.databinding.FragmentPlayerListBinding
import com.miklene.castlefight.databinding.FragmentStatisticsBinding
import com.miklene.castlefight.model.*
import com.miklene.castlefight.mvvm.*
import com.miklene.castlefight.recycler_view.FightRecyclerAdapter
import com.miklene.castlefight.recycler_view.PlayerRecyclerAdapter
import com.miklene.castlefight.recycler_view.StatisticsRecyclerAdapter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.math.E

class StatisticsFragment : Fragment(), FightViewModel.FightCallback,
    PlayerVsPlayerStatisticsViewModel.PlayerVsPlayerStatisticsCallback,
    RaceVsRaceStatisticsViewModel.RaceVsRaceStatisticsCallback {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var fightViewModel: FightViewModel
    private lateinit var raceViewModel: RaceViewModel
    private lateinit var playerVsPlayerStatisticsViewModel: PlayerVsPlayerStatisticsViewModel
    private lateinit var raceVsRaceStatisticsViewModel: RaceVsRaceStatisticsViewModel
    private var stat: MutableList<Statistics> = mutableListOf()
    private var secondStat: MutableList<Statistics> = mutableListOf()
    private var isSecondStat: Boolean = false
    private lateinit var fightList: List<Fight>
    private var playerList: List<Player> = listOf()
    private var raceList: List<Race> = listOf()
    private var playerName: String? = null
    private var raceName: String? = null
    private val sortingVariants = listOf(
        "имя",
        "> боев",
        "< боев",
        "> побед",
        "< побед",
        "> поражений",
        "< поражений",
        "> win rate",
        "< win rate"
    )

    val statisticsForRecycler: MutableList<Statistics> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null && requireArguments().containsKey("PlayerName"))
            playerName = arguments?.getString("PlayerName")!!
        if (arguments != null && requireArguments().containsKey("RaceName"))
            raceName = arguments?.getString("RaceName")!!
        initViewModels()
    }

    fun initViewModels() {
        fightViewModel = activity?.run {
            ViewModelProviders.of(this)[FightViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        /*fightViewModel.getAllFights().observe(this, Observer<List<Fight>>() {
            it?.let {
                setFightList(it)
            }
        })*/
        playerVsPlayerStatisticsViewModel = activity?.run {
            ViewModelProviders.of(this)[PlayerVsPlayerStatisticsViewModel::class.java]
        } ?: throw  Exception("Invalid Activity")
        raceVsRaceStatisticsViewModel = activity?.run {
            ViewModelProviders.of(this)[RaceVsRaceStatisticsViewModel::class.java]
        } ?: throw  Exception("Invalid Activity")
        /* playerViewModel = activity?.run {
             ViewModelProviders.of(this)[PlayerViewModel::class.java]
         } ?: throw Exception("Invalid Activity")
         playerViewModel.getAllPlayers().observe(this, Observer<List<Player>>() {
             it?.let {
                 setPlayerList(it)
             }
         })
         raceViewModel = activity?.run {
             ViewModelProviders.of(this)[RaceViewModel::class.java]
         } ?: throw Exception("Invalid Activity")
         raceViewModel.getAllRaces().observe(this, Observer<List<Race>>() {
             it?.let {
                 setRaceList(it)
             }
         })*/


    }

    fun setStatList(_statList: List<Statistics>) {
        stat = _statList.toMutableList()
        binding.list.adapter = StatisticsRecyclerAdapter(stat)
    }

    private fun setFightList(_fightList: List<Fight>) {
        fightList = _fightList
    }

    private fun setPlayerList(_playerList: List<Player>) {
        playerList = _playerList
        if (playerName != null) {
            createPlayerStat()
            if (raceList.isNotEmpty() && !isSecondStat)
                isSecondStat = true
            createPlayerRaceStat()
        }

    }

    private fun setRaceList(_raceList: List<Race>) {
        raceList = _raceList
        if (raceName != null) {
            createRaceStat()
        }
        if (playerName != null && !isSecondStat) {
            isSecondStat = true
            createPlayerRaceStat()
        }
    }

    private fun createPlayerStat() {
        for (player in playerList) {
            if (player.name != playerName)
                playerName?.let {
                    fightViewModel.getPlayerWinsAndLosesUnderAnotherPlayer(
                        it,
                        player.name
                    )
                }
        }
    }

    private fun createRaceStat() {
        for (race in raceList) {
            if (race.name != raceName)
                raceName?.let {
                    fightViewModel.getRaceWinsAndLosesUnderAnotherRace(
                        it,
                        race.name
                    )
                }
        }
    }

    private fun createPlayerRaceStat() {
        secondStat.clear()
        for (race in raceList) {
            playerName?.let {
                fightViewModel.getPlayerRaceWinsAndLoses(
                    race.name,
                    it
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.list.layoutManager = LinearLayoutManager(activity)
        fightViewModel.attachPlayerCallBack(this)
        playerVsPlayerStatisticsViewModel.attachCallback(this)
        raceVsRaceStatisticsViewModel.attachCallback(this)
        if (raceName != null)
            binding.tvStatisticsName.text = "Расы"
        if (playerName != null)
            binding.tvStatisticsName.text = "Игроки"
        var spWinnerNameAdapter =
            context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, sortingVariants) }
        binding.spinnerSortStatisticsBy.adapter = spWinnerNameAdapter
        binding.spinnerSortStatisticsBy.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                try {
                    binding.list.adapter = StatisticsRecyclerAdapter(sortStatisticBy(statisticsForRecycler))
                } catch (e: Exception) {
                    Log.d("Sort", "No such sorting variant")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        if (playerName != null)
            playerVsPlayerStatisticsViewModel.getByOwnerName(playerName!!)
        if (raceName != null)
            raceVsRaceStatisticsViewModel.getByOwnerName(raceName!!)
        return view
    }

    override fun onStart() {
        super.onStart()

    }

    override suspend fun updatePlayerWinsStat(
        wins: List<Fight>,
        loses: List<Fight>,
        loserName: String
    ) {
        val fights = wins.size + loses.size
        var winRate = 0
        if (fights != 0)
            winRate = ((wins.size.toDouble() / fights.toDouble()) * 100).toInt()
        stat.add(Statistics(loserName, fights, wins.size, loses.size, winRate))
        binding.list.adapter = StatisticsRecyclerAdapter(stat)
    }

    override suspend fun updateRaceWinStat(
        wins: List<Fight>,
        loses: List<Fight>,
        loserName: String
    ) {
        val fights = wins.size + loses.size
        var winRate = 0
        if (fights != 0)
            winRate = ((wins.size.toDouble() / fights.toDouble()) * 100).toInt()
        stat.add(Statistics(loserName, fights, wins.size, loses.size, winRate))
        binding.list.adapter = StatisticsRecyclerAdapter(stat)
    }

    override suspend fun updatePlayersRaceStat(
        wins: List<Fight>,
        loses: List<Fight>,
        raceName: String
    ) {
        val fights = wins.size + loses.size
        var winRate = 0
        if (fights != 0)
            winRate = ((wins.size.toDouble() / fights.toDouble()) * 100).toInt()
        secondStat.add(Statistics(raceName, fights, wins.size, loses.size, winRate))
        binding.list.adapter = StatisticsRecyclerAdapter(secondStat)
    }

    override fun getByPlayersNamesCallback(playerVsPlayerStatistics: PlayerVsPlayerStatistics) {

    }

    override fun getByOwnerPlayerNameCallback(playerVsPlayerStatistics: List<PlayerVsPlayerStatistics>) {
        statisticsForRecycler.clear()
        for (stat in playerVsPlayerStatistics) {
            val st =
                Statistics(stat.enemyName, stat.fights, stat.wins, stat.loses, stat.winRate)
            statisticsForRecycler.add(st)
        }
        try {
            binding.list.adapter = StatisticsRecyclerAdapter(sortStatisticBy(statisticsForRecycler))
        } catch (e: Exception) {
            Log.d("Sort", "No such sorting variant")
        }
    }

    override fun getByRaceNamesCallback(raceVsRaceStatistics: RaceVsRaceStatistics) {

    }

    override fun getByOwnerRaceNameCallback(raceVsRaceStatistics: List<RaceVsRaceStatistics>) {
        statisticsForRecycler.clear()
        for (stat in raceVsRaceStatistics) {
            val st =
                Statistics(stat.enemyName, stat.fights, stat.wins, stat.loses, stat.winRate)
            statisticsForRecycler.add(st)
        }
        try {
            binding.list.adapter = StatisticsRecyclerAdapter(sortStatisticBy(statisticsForRecycler))
        } catch (e: Exception) {
            Log.d("Sort", "No such sorting variant")
        }

    }

    private fun sortStatisticBy(statistics: MutableList<Statistics>) =
        when (binding.spinnerSortStatisticsBy.selectedItem.toString()) {
            sortingVariants[0] -> statistics.sortedBy { it.name }
            sortingVariants[1] -> statistics.sortedByDescending { it.fights }
            sortingVariants[2] -> statistics.sortedBy { it.fights }
            sortingVariants[3] -> statistics.sortedByDescending { it.wins }
            sortingVariants[4] -> statistics.sortedBy { it.wins }
            sortingVariants[5] -> statistics.sortedByDescending { it.loses }
            sortingVariants[6] -> statistics.sortedBy { it.loses }
            sortingVariants[7] -> statistics.sortedByDescending { it.winRate }
            sortingVariants[8] -> statistics.sortedBy { it.winRate }
            else -> throw Exception()
        }
}