package com.arstagaev.testtask1.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arstagaev.testtask1.repository.ListOfWorkersRepository

class MassiveViewModelProviderFactory(
    val app : Application,
    val repository: ListOfWorkersRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(app,repository) as T
    }
}