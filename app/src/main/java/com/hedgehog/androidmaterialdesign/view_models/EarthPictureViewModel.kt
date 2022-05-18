package com.hedgehog.androidmaterialdesign.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hedgehog.androidmaterialdesign.R
import com.hedgehog.androidmaterialdesign.domain.earth.EarthPictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class EarthPictureViewModel(private val repository: EarthPictureRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: Flow<Boolean> = _loading

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String?> = _error

    fun requestEarthPicture() {
        _loading.value = true

        viewModelScope.launch {
            try {
                val url = repository.earthPicture().nasaJplURL
                _image.emit(url)
            } catch (exc: IOException) {
                _error.emit(R.string.network_error.toString())
            }
            _loading.emit(false)
        }
    }
}

class EarthPictureFactory(private val repository: EarthPictureRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T = EarthPictureViewModel(repository) as T
}