package com.github.royalfamily.vagan.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.royalfamily.vagan.data.Repository
import com.github.royalfamily.vagan.data.Resource
import com.github.royalfamily.vagan.dto.AuthToken
import com.github.royalfamily.vagan.enum.LoginType
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


/*
    ViewModel은 두가지로 분류할 수 있다.
    1. MVVM 패턴에서 언급되는 ViewModel
    2. AAC의 ViewModel (AAC : Android Architecture Components)

    MVVM 패턴에서 언급되는 ViewModel은
    View와 Model 사이의 매개체 역할을 하고 View에 보여지게 되는 데이터를 가공하는 역할은 한다.
    1:N 관계를 이룹니다. (View : Model)

    AAC의 ViewModel은
    앱의 lifeCycle을 고려하여 UI와 관련된 데이터를 저장하고 관리하는 역할을 한다.
    1:1 관계를 이룹니다. (View : Model)

    우선 ViewModel은 UI 컨트롤러와 데이터를 다루는 로직을 분리할 때, 데이터를 다루는 부분이다.
    두가지를 분리하는 여러 이유 중 하나는 데이터 손실 떄문이다 (예를들어 화면을 회전 시키면 데이터가 손실됨.).
    기존 화면의 데이터를 유지 및 복원하기 위해 onSaveInstanceState() 메서드를 사용하는데, 이것은 작은 데이터만 다룰 수 있어서
    데이터가 커지게 될 경우에는 적합하지 않습니다.

    단순히 AAC의 ViewModel을 사용한다 해서 MVVM 패턴이 되는 것은 아니다.
    MVVM 패턴의 ViewModel의 역할은 View에 필요한 데이터를 관리하여 바인딩 해주는 것이므로,
    ViewModel 내에서 ObservableField나 LiveData 등을 사용하여 데이터 바인딩을 해준다면
    AAC의 ViewModel로 MVVM 패턴의 ViewModel로써 사용이 가능하다.

 */

/*
    LoginViewModel이 상속받은 ViewModel은 Jetpack library의 AAC ViewModel이다.
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _requestToken = MutableLiveData<Resource<AuthToken.Response>>()

    val requestToken : LiveData<Resource<AuthToken.Response>>
    get() = _requestToken

    fun requestToken(type: LoginType, accessToken: String) = viewModelScope.launch {

        val body = AuthToken.Request(
            loginType = type.str,
            accessToken = accessToken
        )

        repository.requestUserAuth(body).collect {
            _requestToken.value = it
        }

    }


}