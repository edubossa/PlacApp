package com.ews.placapp.ui.game

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.ews.placapp.R
import com.ews.placapp.ui.game.awayteam.AwayTeamFragment
import com.ews.placapp.ui.game.event.EventFragment
import com.ews.placapp.ui.game.hometeam.HomeTeamFragment
import com.ews.placapp.ui.score.ScoreActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    //lateinit inicializacao preguiscosa
    lateinit var model :GameViewModel; //USADO PARA NAO PERDER OS DADOS QUANDO ROTACIONA O CELULAR, E PARA NAO PERDER O CONTEXTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.model = ViewModelProviders.of(this).get(GameViewModel::class.java)

        ivBack.setOnClickListener{
            onBackPressed()
        }

        showEventFragment()
        registerBroadcastReceiver()
    }

    private fun showEventFragment() {
        // containerGame
        val ft = supportFragmentManager.beginTransaction()

        // Evita o erro quando rotacionar a tela para nao perder os dados
        if (supportFragmentManager.findFragmentByTag("EventFragment") == null) {
            ft.add(R.id.containerGame, EventFragment())
            ft.commit()
        }
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("FILTER_EVENT_NAME")
        intentFilter.addAction("FILTER_HOME_TEAM_NAME")
        intentFilter.addAction("FILTER_AWAY_TEAM_NAME")

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
    }

    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("event_name")) {
                model.eventName = intent.getStringExtra("event_name")
                next(HomeTeamFragment())
            }

            if(intent.hasExtra("home_team_name")) {
                model.homeTeamName = intent.getStringExtra("home_team_name")
                next(AwayTeamFragment())
            }

            if(intent.hasExtra("away_team_name")) {
                model.awayTeamName = intent.getStringExtra("away_team_name")
                showScore()
            }

        }

    }

    private fun showScore() {
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra("event_name", model.eventName)
        intent.putExtra("home_team_name", model.homeTeamName)
        intent.putExtra("away_team_name", model.awayTeamName)
        startActivity(intent)
        finish()
    }

    private fun next(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.containerGame, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }

}
