package com.miklene.castlefight.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.miklene.castlefight.R
import com.miklene.castlefight.databinding.FragmentFightListBinding
import com.miklene.castlefight.model.Fight
import com.miklene.castlefight.mvvm.CheckUpdateViewModel
import com.miklene.castlefight.mvvm.FightViewModel
import com.miklene.castlefight.recycler_view.FightRecyclerAdapter
import java.lang.Exception

class FightListFragment() : Fragment(), CheckUpdateViewModel.DatabaseCheckUpdateCallback {

    private lateinit var binding: FragmentFightListBinding
    private lateinit var viewModel: FightViewModel
    private lateinit var checkUpdateViewModel: CheckUpdateViewModel
    private var fightList: List<Fight> = listOf()

    private fun setFightList(_fightList: List<Fight>) {
        fightList = _fightList
        binding.list.adapter = FightRecyclerAdapter(fightList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this)[FightViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        viewModel.getAllFights().observe(this, Observer<List<Fight>>() {
            it?.let {
                setFightList(it)
            }
        })
        checkUpdateViewModel = activity?.run {
            ViewModelProviders.of(this)[CheckUpdateViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        checkUpdateViewModel.attachCallback(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFightListBinding.inflate(inflater, container, false)
        val view = binding.root
        val context = view.context
        binding.list.layoutManager = LinearLayoutManager(activity)
        binding.list.adapter = FightRecyclerAdapter(fightList)
        //binding.list.adapter = FightRecyclerAdapter(fightList)
        return view
    }

    override fun onStart() {
        super.onStart()
        binding.floatingActionButton.setOnClickListener {
            val dialog: DialogFragment = AddFightDialogFragment()
            activity?.supportFragmentManager?.let { it1 -> dialog.show(it1, "add_fight") }
        }
        checkUpdateViewModel.checkUpdate()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun databaseIsOutOfDate(outOfDate: Boolean) {
        if (outOfDate) {
            val dialog: DialogFragment = CheckUpdateDialogFragment()
            activity?.supportFragmentManager?.let { it -> dialog.show(it, "check_update") }
        }
    }

    override fun cantFindDatabase() {
        Snackbar.make(binding.fightList, R.string.cant_find_database, Snackbar.LENGTH_SHORT)
            .show()
    }
}