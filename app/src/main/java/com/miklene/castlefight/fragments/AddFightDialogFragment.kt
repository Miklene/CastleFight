package com.miklene.castlefight.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.miklene.castlefight.R
import com.miklene.castlefight.databinding.DialogFragmentAddFightBinding
import com.miklene.castlefight.model.Fight
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
            val fight = Fight(
                0,
                binding.spWinnerName.selectedItem.toString(),
                binding.spLoserName.selectedItem.toString(),
                binding.spWinnerRace.selectedItem.toString(),
                binding.spLoserRace.selectedItem.toString()
            )
            viewModel.insertFight(fight)
            dialog?.dismiss()
        }
        binding.closeButton.setOnClickListener {
            dialog?.dismiss()
        }
        return builder.create()
    }

    private fun setPlayerList(_playerList: List<Player>) {
        var spPlayers: MutableList<String> = mutableListOf()
        for (player in _playerList)
            spPlayers.add(player.name)
        var spWinnerNameAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spPlayers) }
        binding.spWinnerName.adapter = spWinnerNameAdapter
        var spLoserNameAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spPlayers) }
        binding.spLoserName.adapter = spLoserNameAdapter
    }

    private fun setRaceList(_raceList: List<Race>) {
        var spRaces: MutableList<String> = mutableListOf()
        for (race in _raceList)
            spRaces.add(race.name)
        var spWinnerRaceAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spRaces) }
        binding.spWinnerRace.adapter = spWinnerRaceAdapter
        var spLoserRaceAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, spRaces) }
        binding.spLoserRace.adapter = spLoserRaceAdapter
    }
}