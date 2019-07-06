package com.ews.placapp.ui.game.event


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.ews.placapp.R
import com.ews.placapp.ui.game.hometeam.HomeTeamFragment
import kotlinx.android.synthetic.main.fragment_event.*


/**
 * A simple [Fragment] subclass.
 *
 */
class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tbEventNextStep.setOnClickListener{
            /*val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.containerGame, HomeTeamFragment())
            ft?.addToBackStack(null)
            ft?.commit()*/
            val intent = Intent("FILTER_EVENT_NAME")
            intent.putExtra("event_name", inputEvent.text.toString())
            LocalBroadcastManager
                .getInstance(requireContext())
                .sendBroadcast(intent)
        }
    }


}
