package com.example.dictionarycardswipe.ui.main

import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.dictionarycardswipe.R
import com.example.dictionarycardswipe.databinding.FragmentMainBinding
import com.example.dictionarycardswipe.ui.MainViewModel
import com.example.dictionarycardswipe.util.CheckInternet
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList


@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding

    private lateinit var flingAdapterView: SwipeFlingAdapterView



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val viewModel by viewModels<MainViewModel>()
        binding = FragmentMainBinding.bind(view)

        var text = ""
        var editV = binding.editTextV
        var internet = CheckInternet()

        binding.textView2.text = viewModel.data

        binding.SourceText.text = "Data from: "



        binding.textView2.setMovementMethod(ScrollingMovementMethod())

        flingAdapterView = binding.swipeID

        var list = mutableListOf<String>()

        viewModel.readDatabase.observe(requireActivity()) {
            for (item in it){
                list.add(item.acronymEntity[0].sf)
            }
        }

        val arrayAdapter = ArrayAdapter(requireActivity(),R.layout.card_item,R.id.item_TV,list)

        flingAdapterView.adapter = arrayAdapter

        flingAdapterView.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() {
                list.removeAt(0)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(o: Any) {
                //Toast.makeText(requireActivity(), "dislike", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(o: Any) {
                //Toast.makeText(requireActivity(), "like", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(i: Int) {

            }
            override fun onScroll(v: Float) {}
        })

        binding.button.setOnClickListener(){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                parentFragmentManager.beginTransaction().detach(this).commitNow();
                parentFragmentManager.beginTransaction().attach(this).commitNow();
            } else {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit();
            }

        }

        binding.button2.setOnClickListener(){

            viewModel.nukeData()

        }

        flingAdapterView.setOnItemClickListener { i, any ->

            Toast.makeText(requireActivity(), "item clicked " + any.toString(), Toast.LENGTH_SHORT).show()

            var bundle_data = mutableListOf<String>()

            viewModel.readDatabase.observe(this.requireActivity()) {
                if (it.isNotEmpty()) {
                    for(acronym in it){
                        if(it[0].acronymEntity[0].sf.isNotEmpty() &&
                            any.toString() == acronym.acronymEntity[0].sf){

                            bundle_data.add(acronym.acronymEntity[0].lfs[0].lf)

                        }
                    }
                } else {
                    Toast.makeText(requireActivity(), "item clicked " + any.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            //bundle_data.add("sdasddasda")
            val bundle = Bundle()
            bundle.putStringArrayList("acronym_details",bundle_data as ArrayList<String>)

            this.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
        }






        editV.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                // if the event is a key down event on the enter button
                if (event.action == KeyEvent.ACTION_DOWN &&
                    keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    // perform action on key press

                    if(checkDatabase(viewModel,editV.text.toString().uppercase())){
                        binding.SourceText.text = "Database results for: " + editV.text.toString().uppercase()

                        viewModel.readDatabase.observe(requireActivity()) {
                            if (it.isNotEmpty()) {

                                for(acronym in it){
                                    if(acronym.acronymEntity[0].sf.isNotEmpty() &&
                                        editV.text.toString().uppercase() == acronym.acronymEntity[0].sf){

                                        var tempText = ""
                                        for (i in acronym.acronymEntity[0].lfs){
                                            tempText += i.lf + "\n"
                                        }
                                        viewModel.data = tempText
                                        binding.textView2.text = viewModel.data

                                    }

                                }
                                Toast.makeText(requireActivity(),"data from Database", Toast.LENGTH_SHORT).show()
                            } else {
                                //throw Throwable("No data")
                            }
                        }
                    }
                    else{
                        binding.SourceText.text = "Online results for: " + editV.text.toString().uppercase()
                        getDataSetText(viewModel,editV)

                    }
                    return true
                }
                return false
            }
        })

    }

    private fun checkDatabase(viewModel: MainViewModel, editText: String): Boolean{

        var myBool = false

        viewModel.readDatabase.observe(requireActivity()) {
            if (it.isNotEmpty()) {

                for(acronym in it){
                    if(acronym.acronymEntity[0].sf.isNotEmpty() && editText == acronym.acronymEntity[0].sf){
                        myBool = true
                    }
                }
            } else {
                //throw Throwable("No data")
            }
        }
        return myBool
    }


    private fun getDataSetText(
        viewModel: MainViewModel,
        editV: EditText
    ) {
        var tempText = ""

            if (editV.text.toString() != "") {
                viewModel.getData(editV.text.toString().uppercase())

                viewModel._data.observe(requireActivity()) {

                    if (it.isNotEmpty()) {
                        tempText = ""
                        for (item in it[0].lfs){
                            tempText += item.lf + "\n"
                        }
                        viewModel.data = tempText
                        binding.textView2.text = viewModel.data
                        if(it[0].lfs[0].lf.isNotEmpty()){
                            Toast.makeText(requireActivity(),"data from API", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            //Toast.makeText(requireActivity(),"No result from API", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(requireActivity(),"No result from API", Toast.LENGTH_SHORT).show()
                    }
                }
            }



    }

}