package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class ListViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchData(): LiveData<MutableList<Brand>> {
        val mutableData = MutableLiveData<MutableList<Brand>>()
        repo.getData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}