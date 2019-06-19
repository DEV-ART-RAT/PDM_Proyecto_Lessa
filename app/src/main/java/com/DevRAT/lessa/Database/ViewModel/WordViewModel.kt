package com.DevRAT.lessa.Database.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.DevRAT.lessa.Database.Database.WordDataBase
import com.DevRAT.lessa.Database.Entities.Word
import com.DevRAT.lessa.Database.Repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel (application: Application): AndroidViewModel(application) {
    private val wordRepository: WordRepository
    val allWords: LiveData<List<Word>>
    val favoriteWords: LiveData<List<Word>>

    init {
        val wordDao = WordDataBase.getDatabase(application, viewModelScope).wordDao()
        wordRepository = WordRepository(wordDao)
        allWords = wordRepository.allWord
        favoriteWords=wordRepository.allFavorite
    }

    fun insert(word: Word) = viewModelScope.launch {
        wordRepository.insert(word)
    }
}