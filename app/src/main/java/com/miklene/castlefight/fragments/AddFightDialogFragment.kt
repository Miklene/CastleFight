package com.miklene.castlefight.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklene.castlefight.R
import com.miklene.castlefight.databinding.DialogFragmentAddFightBinding
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.model.Round
import com.miklene.castlefight.model.Player
import com.miklene.castlefight.model.Race
import com.miklene.castlefight.mvvm.AddFightViewModel
import java.lang.Exception

class AddFightDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentAddFightBinding
    private lateinit var viewModel: AddFightViewModel
    private var playerList: List<Player> = listOf()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
        builder.setTitle(R.string.add_fight)
        binding = DialogFragmentAddFightBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[AddFightViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        viewModel.getAllPlayers().observe(this, Observer<List<Player>>() {
            it?.let {
                setPlayerList(it)
            }
        })
        viewModel.getAllRaces().observe(this, Observer<List<Race>>() {
            it?.let {
                setRaceList(it)
            }
        })
        binding.okButton.setOnClickListener {

            val player1 = binding.spPlayer1.selectedItem.toString()
            val player2 = binding.spPlayer2.selectedItem.toString()
            val fight = Fight(0, 2021, player1,player2)
            val round1 = createRound(
                player1,
                player2,
                binding.chbRound1Winner1,
                binding.spRound1Player1,
                binding.spRound1Player2,
                2
            )
            val round2 = createRound(
                player1,
                player2,
                binding.chbRound2Winner1,
                binding.spRound2Player1,
                binding.spRound2Player2,
                2
            )
            val round3 = createRound(
                player1,
                player2,
                binding.chbRound3Winner1,
                binding.spRound3Player1,
                binding.spRound3Player2,
                2
            )
            viewModel.insertFight(fight)
            viewModel.insertRound(round1)
            viewModel.insertRound(round2)
            viewModel.insertRound(round3)
            viewModel.getFight()
            dialog?.dismiss()
        }
        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }
        return builder.create()
    }

    private fun createRound(
        player1: String,
        player2: String,
        checkBox: CheckBox,
        spinner1: Spinner,
        spinner2: Spinner,
        fightId: Long
    ): Round {
        var winner: String
        var winnerRace: String
        var loser: String
        var loserRace: String
        if (checkBox.isChecked) {
            winner = player1
            loser = player2
            winnerRace = spinner1.selectedItem.toString()
            loserRace = spinner2.selectedItem.toString()
        } else {
            winner = player2
            loser = player1
            winnerRace = spinner2.selectedItem.toString()
            loserRace = spinner1.selectedItem.toString()
        }
        val round = Round(
            0, fightId,
            winner,
            loser,
            winnerRace,
            loserRace
        )
        return round
    }

    private fun setPlayerList(_playerList: List<Player>) {
        var spPlayers: MutableList<String> = mutableListOf()
        for (player in _playerList)
            spPlayers.add(player.name)
        var spPlayer1Adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spPlayers) }
        binding.spPlayer1.adapter = spPlayer1Adapter
        var spPlayer2Adapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spPlayers) }
        binding.spPlayer2.adapter = spPlayer2Adapter
    }

    private fun setRaceList(_raceList: List<Race>) {
        var spRaces: MutableList<String> = mutableListOf()
        for (race in _raceList)
            spRaces.add(race.name)
        var spWinnerRaceAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spRaces) }
        binding.spBan1Player1.adapter = spWinnerRaceAdapter
        var spLoserRaceAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spRaces) }
        binding.spBan1Player2.adapter = spLoserRaceAdapter
        binding.spBan2Player1.adapter = spLoserRaceAdapter
        binding.spBan2Player2.adapter = spLoserRaceAdapter
        binding.spRound1Player1.adapter = spLoserRaceAdapter
        binding.spRound1Player2.adapter = spLoserRaceAdapter
        binding.spRound2Player1.adapter = spLoserRaceAdapter
        binding.spRound2Player2.adapter = spLoserRaceAdapter
        binding.spRound3Player1.adapter = spLoserRaceAdapter
        binding.spRound3Player2.adapter = spLoserRaceAdapter
    }
}