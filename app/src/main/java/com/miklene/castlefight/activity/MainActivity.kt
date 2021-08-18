package com.miklene.castlefight.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.miklene.castlefight.R
import com.miklene.castlefight.databinding.ActivityMainBinding
import com.miklene.castlefight.fragments.FightListFragment
import com.miklene.castlefight.fragments.PlayerListFragment
import com.miklene.castlefight.fragments.RaceListFragment
import com.miklene.castlefight.mvvm.RoundViewModel
import com.miklene.castlefight.room.player.PlayerDao
import com.miklene.castlefight.room.player.PlayerDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var db: PlayerDatabase? = null
    private var dao: PlayerDao? = null
    private lateinit var toolbar: ActionBar

    private lateinit var roundViewModel: RoundViewModel
    //  private val host by lazy { NavHostFragment.create(R.navigation.nav_graph) }

    private val playerFragment = PlayerListFragment()
    private val fightFragment = FightListFragment()
    private val raceFragment = RaceListFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // fightViewModel = ViewModelProvider(this).get(FightViewModel::class.java)
        /*supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_tag, FightListFragment())
            .addToBackStack("list")
            .commit()*/

        //db = PlayerDatabase.getPlayerDatabase(this)
        // dao = db?.playerDao()
        /*val navController = findNavController(R.id.fragment_container)
        binding.navigationView.setupWithNavController(navController)*/
        makeCurrentFragment(fightFragment)
        binding.navigationView.selectedItemId = R.id.navigation_fights
        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_races -> makeCurrentFragment(raceFragment)
                R.id.navigation_fights -> makeCurrentFragment(fightFragment)
                R.id.navigation_players -> makeCurrentFragment(playerFragment)
            }
            true
        }

        /*navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            Log.d("NavigationActivity", "Navigated to $dest")
        }*/
        /* fightDao = db?.fightDao();
         val Vitya = Player("Витя")
         val Nikita = Player("Никита")
         val Orcs = Race("Орки")
         val Elfs = Race("Эльфы")*/
        /* binding.textView.text = Elfs.name
         val fight1 = Fight(0,Vitya.name,  Nikita.name, Orcs.name, Elfs.name)
         Observable.fromCallable ({
             fightDao?.deleteAll()
             fightDao?.insert(fight1)
             fightDao?.getAll()
         }).doOnNext({ list ->
             var str:String = ""
             list?.map { str += it.winner + " - " + it.loser + " "}
             binding.textView.text = str
         })
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe()*/
        //fightDao?.delete(fight1)
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}