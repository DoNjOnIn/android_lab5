package com.lab5.ui.screens.subjectDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.data.db.Lab5Database
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * SubjectDetailsViewModel - viewModel class for SubjectDetailsScreen
 * [database] - has property of database in the main constructor
 */
class SubjectDetailsViewModel(
    private val database: Lab5Database
) : ViewModel() {

    /**
     * States for SubjectEntity? - contain `null` by default
     *  _subjectStateFlow - private MutableStateFlow - ViewModel (add new data here)
     *  subjectStateFlow - public StateFlow - ComposeScreen (read only data on screen)
     *
     * States for List<SubjectLabEntity>? - contain `emptyList()` by default
     *  _subjectLabsListStateFlow - private MutableStateFlow - ViewModel (add new data here)
     *  subjectLabsListStateFlow - public StateFlow - ComposeScreen (read only data on screen)
     */
    private val _subjectStateFlow = MutableStateFlow<SubjectEntity?>(null)
    val subjectStateFlow: StateFlow<SubjectEntity?>
        get() = _subjectStateFlow

    private val _subjectLabsListStateFlow = MutableStateFlow<List<SubjectLabEntity>>(emptyList())
    val subjectLabsListStateFlow: StateFlow<List<SubjectLabEntity>>
        get() = _subjectLabsListStateFlow

    /**
     * Function of fetching Subjects and Subject Labs info on screen by subject ID
     */
    fun initData(id: Int) {
        viewModelScope.launch {
            _subjectStateFlow.value = database.subjectsDao.getSubjectById(id)
            _subjectLabsListStateFlow.value = database.subjectLabsDao.getSubjectLabsBySubjectId(id)
        }
    }
    fun updateSubjectLab(updatedLab: SubjectLabEntity) {
        viewModelScope.launch {
            database.subjectLabsDao.updateSubjectLab(updatedLab)
        }
    }
}