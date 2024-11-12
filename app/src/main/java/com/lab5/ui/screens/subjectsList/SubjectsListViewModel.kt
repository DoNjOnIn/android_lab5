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
            SubjectEntity(id = 1, title = "Мережева безпека"),
            SubjectEntity(id = 2, title = "Програмування мобільних додатків"),
        )
        // List of labs
        val listOfSubjectLabs = listOf(
            SubjectLabEntity(
                id = 1,
                subjectId = 1,
                title = "Налаштування механізмів безпеки комутаторів ethernet\n",
                description = "Дослідити принципи налаштування механізмів безпеки на комутаторах Cisco Catalyst",
                comment = "ноу",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 2,
                subjectId = 1,
                title = "Оновлення ios, резервне копіювання \n" +
                        "та відновлення налаштувань комутатора \n" +
                        "з використанням протоколу tftp\n",
                description = "Оновлення IOS, резервне копіювання та відновлення налаштувань комутатора з використанням протоколу TFTP",
                comment = "",
                inProgress = true,
            ),
            SubjectLabEntity(
                id = 3,
                subjectId = 1,
                title = "Використання списків контролю доступу acl для управління мережевим трафіком\n",
                description = "Використання списків контролю доступу ACL для управління мережевим трафіком",
                comment = "",
                inProgress = true,
            ),
            SubjectLabEntity(
                id = 4,
                subjectId = 1,
                title = "Відновлення паролів на комутаторах \n" +
                        "та маршрутизаторах cisco\n",
                description = "Відновлення паролів на комутаторах та маршрутизаторах Cisco.",
                comment = "",
                inProgress = true,
            ),
            SubjectLabEntity(
                id = 5,
                subjectId = 1,
                title = "Конфігурація і перевірка IPsec VPN між двома пунктами (site-to-site) за допомогою інтерфейсу командного рядка\n",
                description = "Конфігурація і перевірка IPsec VPN між двома пунктами (site-to-site) за допомогою інтерфейсу командного рядка.",
                comment = "",
                inProgress = true,
            ),
            SubjectLabEntity(
                id = 6,
                subjectId = 1,
                title = "Забезпечення безпеки на 2-му рівні\n",
                description = "Налаштування безпеку на 2 рівні моделі OSI.",
                comment = "",
                inProgress = true,
            ),

            SubjectLabEntity(
                id = 7,
                subjectId = 2,
                title = "Інсталяція середовища Android Studio\n",
                description = "Інсталювати середовище розробки мобільних додатків Android Studio",
                comment = "ноу",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 8,
                subjectId = 2,
                title = "Вивчення основ Kotlin та Compose UI. Створення простого ToDo\n",
                description = "Включає розроблений базовий варіант додатку із завдання лабораторної. Можете дивитись і копіювати у свій додаток, але доробіть його зі своїм стилем і фічами, простими словами зробіть красивіше і щось більш схоже на реальний додаток. \n",
                comment = "ноу",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 9,
                subjectId = 2,
                title = "Вивчення навігаційного компонента (Navigation Component) в Android\n",
                description = "Написати простий додаток, який покаже популярні туристичні напрямки в країні\n" +
                        "чи місті на ваш вибір. Користувач може переміщатися між різними екранами,\n" +
                        "щоб переглянути деталі вибраного місця.",
                comment = "ноу",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 10,
                subjectId = 2,
                title = "Робота з базами даних.\n",
                description = "У цій роботі в завданні запропоновано базу даних \"DataStore\", але у моєму прикладі реалізовано збереження даних через іншу, більш популярну і простішу, базу даних \"Room\".",
                comment = "ноу",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 11,
                subjectId = 2,
                title = "Вивчення архітектури Android додатків та асинхронної роботи\n",
                description = "Дослідження Koin DI для побудови архітектури дослідити Koin DI інструмент для побудови архітектури в Android",
                comment = "ноу",
                isCompleted = true,
            ),
            SubjectLabEntity(
                id = 12,
                subjectId = 2,
                title = "Підключення зовнішніх сервісів за допомогою HTTP протоколу\n",
                description = "Написати простий додаток який буде відображати погоду для певного міста",
                comment = "ноу",
                isCompleted = true,
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