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
import com.splyza.team.api.MockService
import kotlinx.coroutines.launch

class TeamViewModel : ViewModel(), Observable {
    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team
    var teamId = "57994f271ca5dd20847b910c"
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
        _team.value = MockService.INSTANCE.getTeam(teamId)
            .execute().body()!!
    }

    private fun getInviteLink() {
        _link.value =
            MockService.INSTANCE.invite(teamId, RoleBody(role = _role))
                .execute().body()?.url ?: ""
    }
}