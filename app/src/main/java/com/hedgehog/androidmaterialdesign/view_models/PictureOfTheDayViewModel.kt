package com.hedgehog.androidmaterialdesign.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class PictureOfTheDayModel(private val repository: NasaRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    private val _explanation: MutableStateFlow<String?> = MutableStateFlow(null)
    val explanation: Flow<String?> = _explanation

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String?> = _error

    fun requestPictureOfTheDay() {
        _loading.value = true

        viewModelScope.launch {
            try {
                val url = repository.pictureOfTheDay().url
                val title = repository.pictureOfTheDay().title
                val explanation = repository.pictureOfTheDay().explanation
                _image.emit(url)
                _title.emit(title)
                _explanation.emit(explanation)
            } catch (exception: IOException) {
                _error.emit(R.string.network_error.toString())
            }
            _loading.emit(false)
        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PictureOfTheDayModel(repository) as T
}