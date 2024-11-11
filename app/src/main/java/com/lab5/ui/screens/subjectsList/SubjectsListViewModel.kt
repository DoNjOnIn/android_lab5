package com.lab5.ui.screens.subjectsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lab5.data.db.Lab5Database
import com.lab5.data.entity.SubjectEntity
import com.lab5.data.entity.SubjectLabEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubjectsListViewModel(
    private val database: Lab5Database
) : ViewModel() {

    /**
    Flow - the (container, channel, observer), can accept and move data from producer to consumers
    StateFlow - the flow which also store data.
    MutableStateFlow - the stateFlow which can accept data (which you can fill)

    _subjectListStateFlow - private MutableStateFlow - ViewModel (add new data here)
    subjectListStateFlow - public StateFlow - ComposeScreen (read only data on screen)
     */
    private val _subjectListStateFlow = MutableStateFlow<List<SubjectEntity>>(emptyList())
    val subjectListStateFlow: StateFlow<List<SubjectEntity>>
        get() = _subjectListStateFlow


    /**
    Init block of ViewModel - invokes once the ViewModel is created
     */
    init {
        viewModelScope.launch {
            // for now, we can preload some data to DB here, on the first screen
            val subjects = database.subjectsDao.getAllSubjects()
            if (subjects.isEmpty()) preloadData()
            _subjectListStateFlow.value = database.subjectsDao.getAllSubjects()
        }
    }

    private suspend fun preloadData() {
        val listOfSubject = listOf(
            SubjectEntity(id = 1, title = "Subject 1"),
            SubjectEntity(id = 2, title = "Subject 2"),
            SubjectEntity(id = 3, title = "Subject 3"),
        )
        val listOfSubjectLabs = listOf(
            SubjectLabEntity(
                id = 1,
                subjectId = 1,
                title = "Lab[1] title",
                description = "Lab[1] description",
                comment = "Lab[1] comment",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 2,
                subjectId = 1,
                title = "Lab[2] title",
                description = "Lab[2] description",
                inProgress = true,
            ),
            SubjectLabEntity(
                id = 3,
                subjectId = 1,
                title = "Lab[3] title",
                description = "Lab[3] description",
            ),
            SubjectLabEntity(
                id = 1,
                subjectId = 2,
                title = "Lab[1] title",
                description = "Lab[1] description",
                comment = "Lab[1] comment"
            ),
        )

        listOfSubject.forEach { subject ->
            database.subjectsDao.addSubject(subject)
        }
        listOfSubjectLabs.forEach { lab ->
            database.subjectLabsDao.addSubjectLab(lab)
        }
    }
}