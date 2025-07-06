package com.example.readease.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.readease.data.model.MyCollection
import com.example.readease.repository.MyCollectionRepository
import kotlinx.coroutines.launch

class MyCollectionViewModel(private val repository: MyCollectionRepository) : ViewModel() {

    private val _collection = MutableLiveData<List<MyCollection>>()
    val collection: LiveData<List<MyCollection>> get() = _collection

    fun loadCollection() = viewModelScope.launch {
        _collection.value = repository.getAll()
    }

    fun addToCollection(item: MyCollection) = viewModelScope.launch {
        repository.addToCollection(item)
        loadCollection()
    }

    fun removeFromCollection(item: MyCollection) = viewModelScope.launch {
        repository.removeFromCollection(item)
        loadCollection()
    }

    fun isBookInCollection(bookId: Int): LiveData<Boolean> = liveData {
        val item = repository.findByBookId(bookId)
        emit(item != null)
    }
}