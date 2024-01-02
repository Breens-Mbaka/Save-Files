package com.fourthprofile.savefiles

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveFilesViewModel @Inject constructor(): ViewModel() {
    private val _imageAbsolutePath = mutableStateOf("")
    val imageAbsolutePath = _imageAbsolutePath

    fun setImageAbsolutePath(imageAbsolutePath: String) {
        _imageAbsolutePath.value = imageAbsolutePath
    }
}