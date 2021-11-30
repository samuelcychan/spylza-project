package com.splyza.team.data

import android.view.View
import android.widget.AdapterView
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel(), Observable {
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team
    var teamId = ""

    private var _role = "readonly"  // default role value

    @Suppress("UNUSED_PARAMETER")
    fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val role = Roles.values()[pos].title.lowercase()
        if (_role != role) {
            _role = role
            getInviteLink()
        }
    }

    private var _link = MutableLiveData("")
    val inviteLink: String
        get() = _link.value ?: ""

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) = callbacks.add(callback)

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) = callbacks.remove(callback)

    @Bindable
    fun getMemberCount() =
        (_team.value?.members?.total ?: 0) - (_team.value?.members?.supporters ?: 0)

    @Bindable
    fun getMemberLimit() = _team.value?.plan?.memberLimit ?: 0

    @Bindable
    fun getSupporterCount() = _team.value?.members?.supporters ?: 0

    @Bindable
    fun getSupporterLimit() = _team.value?.plan?.supporterLimit ?: 0

    init {
        viewModelScope.launch {
            getTeamData()
        }
    }

    private fun getTeamData() {
        //ToDo: replace the test data with REST body.
        val id = "57994f271ca5dd20847b910c"
        val members = Members(
            total = 89,
            administrators = 1,
            managers = 18,
            editors = 6,
            members = 58,
            supporters = 6
        )
        val plan = Plan(
            memberLimit = 100,
            supporterLimit = 20
        )
        _team.value = Team(id = id, members = members, plan = plan)
    }

    private fun getInviteLink() {
        //ToDo: replace the test data with REST body.
        _link.value = "https://example.com/ti/eyJpbnZpdGVJZ"
    }
}