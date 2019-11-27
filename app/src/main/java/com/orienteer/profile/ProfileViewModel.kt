package com.orienteer.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.orienteer.models.User
import com.orienteer.network.UserApi
import com.orienteer.util.TEST_USER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class ProfileViewModel : ViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User>
        get() = _currentUser

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    init {
        val user = FirebaseAuth.getInstance().currentUser
        // TODO: make this use the actual userId
        getUser("testString")
    }


    private fun getUser(userId: String) {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            val getUser = UserApi.userService.getUser(userId)
            try {
                // this will run on a thread managed by Retrofit
                val result = getUser.await()
                _currentUser.value = result
            } catch (e: Exception) {
                //_status.value = ApiStatus.ERROR
                Timber.e("Network request failed with exception $e")
                _currentUser.value = TEST_USER
            }
        }
    }
}