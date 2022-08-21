package org.aos.shareroof

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var changeView = MutableLiveData(false)
    var nickName = MutableLiveData("")
    var email = MutableLiveData("")
    var profile = MutableLiveData("")
}