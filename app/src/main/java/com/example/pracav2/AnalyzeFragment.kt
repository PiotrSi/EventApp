package com.example.pracav2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.pracav2.data.network.Resource
import com.example.pracav2.databinding.FragmentAnalyzeBinding
import com.example.pracav2.ui.handleApiError
import com.example.pracav2.ui.home.HomeViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AnalyzeFragment : Fragment(R.layout.fragment_analyze) {

    private lateinit var binding: FragmentAnalyzeBinding
    private val viewModel by viewModels<HomeViewModel>()

//    lateinit var barList: ArrayList<BarEntry>
//    lateinit var barDataSet: BarDataSet
//    lateinit var barData: BarData

     var pieList: ArrayList<PieEntry> =ArrayList()
    lateinit var pieDataSet: PieDataSet
    lateinit var pieData: PieData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnalyzeBinding.bind(view)

        var eventInMonth = intArrayOf(0,0,0,0,0,0,0,0,0,0,0,0)
//        var eventInMonth = IntArray(12)
        eventInMonth.forEach {
//            Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
        }
        val toolbar: Toolbar = binding.toolbar
        toolbar.title = "Chart"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)

        toolbar.setNavigationOnClickListener {
            val action = AnalyzeFragmentDirections.actionAnalyzeFragmentToProfileFragment()
            findNavController().navigate(action)
        }
        viewModel.getEvents()

        viewModel.event.observe(viewLifecycleOwner, Observer {event->
            when (event) {
                is Resource.Success -> {
//                    binding.progressbar.visible(false)

                    var data = false
                    var calBefore = Calendar.getInstance()
                    calBefore.add(Calendar.MONTH, -5)
                    calBefore.set(Calendar.DAY_OF_MONTH, 1)
                    var calAfter = Calendar.getInstance()
                    calAfter.add(Calendar.MONTH, +1)
                    calAfter.set(Calendar.DAY_OF_MONTH, 1)
//                    calAfter.set(Calendar.DAY_OF_MONTH, calAfter.getActualMaximum(Calendar.DAY_OF_MONTH))

                    Log.d("DATABEFORE", calAfter.get(Calendar.MONTH).toString()+calAfter.get(Calendar.YEAR).toString())
                    Log.d("DATA", Calendar.getInstance().get(Calendar.MONTH).toString())
//                    var  myEvents :ArrayList<EventResponseItem> = arrayListOf()
                    event.value.forEach{
                        if(it.czyZapisano) {
                            var cal2 = Calendar.getInstance()
                            cal2.time = it.data_start
                            if (cal2.after(calBefore) && cal2.before(calAfter)) {
                            data = true
                            when (date(it.data_start)) {
                                "0" -> eventInMonth[0] += 1
                                "1" -> eventInMonth[1] += 1
                                "2" -> eventInMonth[2] += 1
                                "3" -> eventInMonth[3] += 1
                                "4" -> eventInMonth[4] += 1
                                "5" -> eventInMonth[5] += 1
                                "6" -> eventInMonth[6] += 1
                                "7" -> eventInMonth[7] += 1
                                "8" -> eventInMonth[8] += 1
                                "9" -> eventInMonth[9] += 1
                                "10" -> eventInMonth[10] += 1
                                "11" -> eventInMonth[11] += 1
                            }
                        }

//                            myEvents.add(it)
                        }//else {it.id}
                    }


                    if(data) {
                        insertData(eventInMonth)
                        setUp()
                    }else{
                        binding.textView4.text = "In recent months you have not attended any events"
                    }
                }
                is Resource.Loading -> {
//                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(event)
                }
            }
        })

    }
    private fun setUp(){
//        binding.chart.setNoDataText("asaSAS")
//        binding.chart.invalidate()
        binding.chart.visibility =View.VISIBLE
        pieDataSet = PieDataSet(pieList, "Legend")
        pieData = PieData(pieDataSet)
        pieData.setValueFormatter(vf)
        binding.chart.data = pieData
        pieDataSet.colors = setColors()
        pieDataSet.valueTextSize = 15f
        binding.chart.setEntryLabelColor(Color.BLACK)
        binding.chart.legend.isEnabled = false
        binding.chart.centerText = "Your events in the last 6 months"
        binding.chart.setCenterTextSize(15f)


    }
    private val vf: ValueFormatter = object : ValueFormatter() {
        //value format here, here is the overridden method
        override fun getFormattedValue(value: Float): String {
            return "" + value.toInt()
        }
    }
    private fun date(date : Date):String{
        val cal = Calendar.getInstance()
        cal.time = date
        val year = cal.get(Calendar.YEAR).toString()
        val month = cal.get(Calendar.MONTH).toString()
        val day = cal.get(Calendar.DAY_OF_MONTH).toString()
        val hour = cal.get(Calendar.HOUR_OF_DAY).toString()
        val min = cal.get(Calendar.MINUTE).toString()
        return month
    }

    private fun insertData(eventInMonth : IntArray){
        eventInMonth.forEachIndexed { index, it ->
            if(it!=0){
                when(index){
                    0 -> pieList.add(PieEntry(it.toFloat(), "Jan."))
                    1 -> pieList.add(PieEntry(it.toFloat(), "Feb."))
                    2 -> pieList.add(PieEntry(it.toFloat(), "Mar."))
                    3 -> pieList.add(PieEntry(it.toFloat(), "Apr."))
                    4 -> pieList.add(PieEntry(it.toFloat(), "May"))
                    5 -> pieList.add(PieEntry(it.toFloat(), "June"))
                    6 -> pieList.add(PieEntry(it.toFloat(), "Jul."))
                    7 -> pieList.add(PieEntry(it.toFloat(), "Aug"))
                    8 -> pieList.add(PieEntry(it.toFloat(), "Sept."))
                    9 -> pieList.add(PieEntry(it.toFloat(), "Oct."))
                    10 -> pieList.add(PieEntry(it.toFloat(), "Nov."))
                    11 -> pieList.add(PieEntry(it.toFloat(), "Dec."))
                }
            }
        }
    }

    private fun setColors():ArrayList<Int>{
        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.YELLOW)
        colors.add(Color.CYAN)
        colors.add(Color.GRAY)
        colors.add(Color.parseColor("#D661B1EF"))
        colors.add(Color.MAGENTA)
        colors.add(Color.parseColor("#5ef28b"))
        return colors
    }

}
/*
Jan.
Feb.
Mar.
Apr.
May
June
Jul.
Aug
Sept.
Oct.
Nov.
Dec.
*/
//        val colors: ArrayList<Int> = ArrayList()
//        colors.add(Color.YELLOW)
//        colors.add(Color.CYAN)
//        colors.add(Color.GRAY)
//        colors.add(Color.parseColor("#D661B1EF"))
//        colors.add(Color.MAGENTA)
//        colors.add(Color.parseColor("#5ef28b"))

//        colors.add(Color.LTGRAY)
//        colors.add(Color.MAGENTA)
//        colors.add(Color.DKGRAY)
//        colors.add(Color.BLUE)