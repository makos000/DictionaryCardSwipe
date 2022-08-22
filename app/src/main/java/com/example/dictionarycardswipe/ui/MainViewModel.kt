package com.example.dictionarycardswipe.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dictionarycardswipe.models.Model
import com.example.dictionarycardswipe.repo.RepoInterface
import com.example.dictionarycardswipe.room.AcronymDao
import com.example.dictionarycardswipe.room.AcronymEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repoInterface: RepoInterface, val acronymDao: AcronymDao): ViewModel() {
    val _data: MutableLiveData<Model> = MutableLiveData(Model())
    var data: String = ""

    fun getData(string: String){

        CoroutineScope(Dispatchers.IO).launch {

            val response = repoInterface.getSF(string)
            if(response.isSuccessful){

                if (response.body()!!.isNotEmpty()){
                    response.body().let{
                        insertIntoDatabase(it!!)
                        _data.postValue(it)
                    }
                }

            }
            else{
                Log.i("Response", "Api Response not successful")
            }
        }
    }

    val readDatabase: LiveData<List<AcronymEntity>> = acronymDao.readAcronymsFromDB().asLiveData()

    fun nukeData(){
        CoroutineScope(Dispatchers.IO).launch {
            acronymDao.nukeTable()
        }
    }

    fun insertIntoDatabase(model: Model){
        val acronymEntity = AcronymEntity(model)
        CoroutineScope(Dispatchers.IO).launch {
            acronymDao.insertAcronymToDB(acronymEntity)
        }
    }
}