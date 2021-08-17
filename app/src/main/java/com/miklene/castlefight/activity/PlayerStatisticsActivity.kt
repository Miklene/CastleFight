package com.miklene.castlefight.activity

import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.miklene.castlefight.R
import com.miklene.castlefight.databinding.ActivityPlayerStatisticsBinding
import com.miklene.castlefight.fragments.StatisticsFragment
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race
import com.miklene.castlefight.mvvm.PlayerViewModel
import com.miklene.castlefight.mvvm.RaceViewModel


class PlayerStatisticsActivity : AppCompatActivity(), PlayerViewModel.PlayerCallback,
    RaceViewModel.RaceCallback {
    private lateinit var binding: ActivityPlayerStatisticsBinding
    private lateinit var playerViewModel: PlayerViewModel
    private lateinit var raceViewModel: RaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerStatisticsBinding.inflate(layoutInflater)
        val view = binding.root
        val playerName = intent.extras?.getString("PlayerName")
        val raceName = intent.extras?.getString("RaceName")
        if (playerName != null) {
            binding.tvStatisticVersusOthers.text = "Статистика боев против игроков"
            playerViewModel = ViewModelProvider(this).get(PlayerViewModel::class.java)
            playerViewModel.attachPlayerCallBack(this)
            playerViewModel.getByName(playerName)
            val playerBundle = Bundle()
            playerBundle.putString("PlayerName", playerName)
            StatisticsFragment().let {
                it.arguments = playerBundle
                supportFragmentManager.beginTransaction()
                    .add(R.id.statisticsFragmentContainerVersusOthers, it).commit()
            }
            binding.tvStatisticRacePlaying.text = "Статистика рас"
            val playerBundle2 = Bundle()
            playerBundle2.putString("PlayerRace", playerName)
            StatisticsFragment().let{
                it.arguments = playerBundle2
                supportFragmentManager.beginTransaction()
                    .add(R.id.statisticsFragmentContainerPlayingRaces,it).commit()
            }
        }
        if (raceName != null) {
            binding.tvStatisticVersusOthers.text = "Статистика боев против рас"
            raceViewModel = ViewModelProvider(this).get(RaceViewModel::class.java)
            raceViewModel.attachRaceCallBack(this)
            raceViewModel.getByName(raceName)
            val raceBundle = Bundle()
            raceBundle.putString("RaceName", raceName)
            StatisticsFragment().let {
                it.arguments = raceBundle
                supportFragmentManager.beginTransaction()
                    .add(R.id.statisticsFragmentContainerVersusOthers, it).commit()
            }
            binding.tvStatisticRacePlaying.text = "Статистика игроков"
            val raceBundle2= Bundle()
            raceBundle2.putString("RacePlayer", raceName)
            StatisticsFragment().let {
                it.arguments = raceBundle2
                supportFragmentManager.beginTransaction()
                    .add(R.id.statisticsFragmentContainerPlayingRaces, it).commit()
            }
        }
        binding.buttonArrowStatisticsVersusOthers.setOnClickListener {
            if (binding.layoutStatisticsVersusOthers.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.scrollView, AutoTransition())
                binding.layoutStatisticsVersusOthers.visibility = View.VISIBLE
                binding.buttonArrowStatisticsVersusOthers.setBackgroundResource(R.drawable.ic_baseline_expand_less_24)
            } else {
                TransitionManager.beginDelayedTransition(binding.scrollView, AutoTransition())
                binding.layoutStatisticsVersusOthers.visibility = View.GONE
                binding.buttonArrowStatisticsVersusOthers.setBackgroundResource(R.drawable.ic_baseline_expand_more_24)
            }
        }
        binding.buttonArrowStatisticsRacePlaying.setOnClickListener {
            if (binding.layoutStatisticsPlayingRaces.visibility == View.GONE) {
                TransitionManager.beginDelayedTransition(binding.scrollView, AutoTransition())
                binding.layoutStatisticsPlayingRaces.visibility = View.VISIBLE
                binding.buttonArrowStatisticsRacePlaying.setBackgroundResource(R.drawable.ic_baseline_expand_less_24)
            } else {
                TransitionManager.beginDelayedTransition(binding.scrollView, AutoTransition())
                binding.layoutStatisticsPlayingRaces.visibility = View.GONE
                binding.buttonArrowStatisticsRacePlaying.setBackgroundResource(R.drawable.ic_baseline_expand_more_24)
            }
        }
        setContentView(view)
    }

    override suspend fun updatePlayerUi(player: Player) {
        binding.tvPlayerNameStatistics.text = player.name
        val fightNum = player.wins + player.loses
        val fightStat = "Боев - $fightNum(${player.wins}:${player.loses})"
        binding.tvPlayerFightStatistics.text = fightStat
    }

    override suspend fun updateRaceUi(race: Race) {
        binding.tvPlayerNameStatistics.text = race.name
        val fightNum = race.wins + race.loses
        val fightStat = "Боев - $fightNum(${race.wins}:${race.loses})"
        binding.tvPlayerFightStatistics.text = fightStat
    }

    /* override fun onCreateView(
         inflater: LayoutInflater,
         container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View {
         binding = FragmentPlayerStatisticsBinding.inflate(inflater, container, false)
         val view = binding.root
         return view
     }*/
}