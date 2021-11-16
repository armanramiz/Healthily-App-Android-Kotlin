package com.cs506.healthily.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.cs506.healthily.R
import com.cs506.healthily.data.model.DaySteps
import com.cs506.healthily.data.repository.GoalsRepository
import com.cs506.healthily.view.adapter.DayStepAdapter
import com.cs506.healthily.viewModel.DayStepsViewModel
import com.cs506.healthily.viewModel.goalViewModel
import com.google.firebase.auth.FirebaseAuth
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint

//import android.R

import com.jjoe64.graphview.GraphView




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StepsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StepsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var gv: GraphView? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        gv = view?.findViewById(R.id.graph)
        bindData()
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_steps, container, false)
        val progressBar: ProgressBar = view.findViewById(R.id.progress_bar)
        val progressText: TextView = view.findViewById(R.id.progress_text)
        val currentProgress = 6923
        val stepGoal = 10000
        progressText.text = "" + currentProgress + " / " + stepGoal
        val progressPercentage = 100 * currentProgress / stepGoal
        progressBar.setProgress(progressPercentage)


        val graph = view.findViewById(R.id.graph) as GraphView


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StepsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StepsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun setupGraphView(days: List<DaySteps>) {


        val series: BarGraphSeries<DataPoint> = BarGraphSeries(
            arrayOf(
                days[0].steps?.let { DataPoint(1.0, it.toDouble()) },
                days[1].steps?.let { DataPoint(2.0, it.toDouble()) },
                days[2].steps?.let { DataPoint(3.0, it.toDouble()) },
                days[3].steps?.let { DataPoint(4.0, it.toDouble()) },
                days[4].steps?.let { DataPoint(5.0, it.toDouble()) },
                days[5].steps?.let { DataPoint(6.0, it.toDouble()) },
                days[6].steps?.let { DataPoint(7.0, it.toDouble()) },

            )
        )
        val graph = view?.findViewById(R.id.graph) as GraphView
        graph.addSeries(series)
        series.setSpacing(50)


    }


    private fun bindData(){
        val viewModel: DayStepsViewModel =
            ViewModelProviders.of(this).get(DayStepsViewModel::class.java)

        viewModel.getAllDays()?.observe(viewLifecycleOwner) { mDays ->

            setupGraphView(mDays)

        }
    }
}