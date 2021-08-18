package com.miklene.castlefight.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.miklene.castlefight.R
import com.miklene.castlefight.databinding.DialogFragmentCheckUpdateBinding
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.mvvm.AddFightViewModel
import com.miklene.castlefight.mvvm.CheckUpdateViewModel
import com.miklene.castlefight.mvvm.FightViewModel
import com.miklene.castlefight.mvvm.UpdateDatabaseViewModel
import java.lang.Exception

class CheckUpdateDialogFragment : DialogFragment(), UpdateDatabaseViewModel.DatabaseUpdateCallback, AddFightViewModel.AddFightViewModelCallback{
    private lateinit var binding: DialogFragmentCheckUpdateBinding
    private lateinit var viewModel: AddFightViewModel
    private lateinit var updateDatabaseViewModel: UpdateDatabaseViewModel
    private lateinit var fightViewModel: FightViewModel

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        updateDatabaseViewModel.loadUpdate()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        var builder = AlertDialog.Builder(activity, R.style.Theme_AppCompat_Light_Dialog)
        builder.setTitle(R.string.check_update)
        binding = DialogFragmentCheckUpdateBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[AddFightViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        viewModel.attachCallback(this)
        updateDatabaseViewModel = activity?.run {
            ViewModelProviders.of(this)[UpdateDatabaseViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        updateDatabaseViewModel.attachCallback(this)
        fightViewModel = activity?.run {
            ViewModelProviders.of(this)[FightViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        binding.checkUpdateProgressBar.visibility = ProgressBar.VISIBLE
        return builder.create()
    }

    override fun databaseLoadingComplete(fights: MutableList<Fight>) {
        //fightViewModel.deleteAll()
        viewModel.insertFights(fights)
    }

    override fun databaseIsGood() {
        binding.checkUpdateProgressBar.visibility = ProgressBar.INVISIBLE
        dialog?.dismiss()
    }

    override fun cantFindDatabase() {

    }

    override fun insetComplete() {
        updateDatabaseViewModel.updateDatabaseVersion()
        binding.checkUpdateProgressBar.visibility = ProgressBar.INVISIBLE
        dialog?.dismiss()
    }


}