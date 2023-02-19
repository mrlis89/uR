package com.arnava.ur.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arnava.ur.data.model.users.AccountInfo
import com.arnava.ur.data.model.users.Friend
import com.arnava.ur.data.model.users.UserInfo
import com.arnava.ur.data.repository.AuthRepository
import com.arnava.ur.data.repository.LocalRepository
import com.arnava.ur.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: MainRepository,
    private val authRepository: AuthRepository,
    private val localRepository: LocalRepository,
) : ViewModel() {
    private val _accountFlow = MutableStateFlow<AccountInfo?>(null)
    val accountFlow = _accountFlow.asStateFlow()

    private val _friendListFlow = MutableStateFlow<List<Friend>>(emptyList())
    val friendFlow = _friendListFlow.asStateFlow()

    private val _userInfoFlow = MutableStateFlow<UserInfo?>(null)
    val userInfoFlow = _userInfoFlow.asStateFlow()

    fun loadAccountInfo() {
        viewModelScope.launch {
            _accountFlow.value = repository.getAccountInfo()
        }
    }

    fun loadFriendList() {
        viewModelScope.launch {
            _friendListFlow.value = repository.getFriendList().friendsData.friends
        }
    }

    fun loadFriendInfo(userName: String) {
        viewModelScope.launch {
            _userInfoFlow.value = repository.getUserInfo(userName)
        }
    }
    fun logout() {
        viewModelScope.launch {
            authRepository.revokeAccessToken()
            authRepository.revokeRefreshToken()
        }
        localRepository.clearTokensLocally()
    }


}

