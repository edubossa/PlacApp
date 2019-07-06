package com.ews.placapp.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ews.placapp.R
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    private lateinit var  scoreViewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)

        tvEventName.text = intent.getStringExtra("event_name")
        tvHome.text = intent.getStringExtra("home_team_name")
        tvAway.text = intent.getStringExtra("away_team_name")

        btGolHome.setOnClickListener{
            scoreViewModel.markGoalHome()
        }

        btGolWay.setOnClickListener{
            scoreViewModel.markGoalAway()
        }

        btRestart.setOnClickListener {
            scoreViewModel.restart()
        }

        btFinish.setOnClickListener{
            finish()
        }


        scoreViewModel.goalHome.observe(this, Observer {
            tvScoreHome.text = it.toString()
        })

        scoreViewModel.goalAway.observe(this, Observer {
            tvScoreAway.text = it.toString()
        })

    }
}
