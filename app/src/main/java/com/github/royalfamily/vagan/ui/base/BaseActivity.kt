package com.github.royalfamily.vagan.ui.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/*
    BaseActivity는 여러 Activity를 사용할 때 중복되는 코드를 미리 정의하여
    필요한 코드만 구현하도록 사용하는 기본 액티비티입니다.
    kotlin에서는 상속을 허용하려면 해당 클래스 앞에 open 키워드를 붙입니다.
    BaseActivity 클래스에 open 키워드를 붙임으로서 다른 클래스에서 BaseActivity를 상속받을 수 있습니다.
 */
open class BaseActivity : AppCompatActivity() {
/*
    lazy {} :
        lazy 는 나중에 초기화를 해줄 건데, 해당 키워드로 선언된 변수를 사용할 때 초기화를 진행하겠다는 의미의 키워드 입니다.
        호출 시 by lazy 정의에 의해서 초기화를 진행합니다.

    <reified T: ViewDataBinding> :
        T는 제네릭 타입으로, 해당 변수는 타입이 정해지지 않았음을 의미합니다.
        하지만 아래에는 T: ViewDataBinding으로 선언했는데 (제네릭 제약 : Generic Constraints),
        이는 super type이 ViewDataBinding인 객체만 T에 들어갈 수 있다고 선언한 것입니다.
        즉, ViewDataBinding을 상속 받은 객체만 T에 들어올 수 있다는 뜻입니다.
        아래의 변수 명은 binding인데, 추후에 binding<T>로 변수를 선언해야 하고, T에 들어온 타입은 setContentView의 T에 들어가게 됩니다.

    inline fun :
        람다식에서 inline 키워드를 사용하지 않았을 때에 발생하는 문제는 아래와 같습니다.
        컴파일 시 kotlin 코드가 java 코드로 변경되는데, 람다식이 호출 될 때마다 새로운 객체를 생성합니다.
        inline 키워드는 무의미한 객체 생성을 막아주는데 이에 대한 내부 동작은 꼭 공부가 필요합니다.
        reified 키워드는 inline 함수랑 같이 사용되는데, 같이 공부하면 좋을것 같습니다.
        요약하면, inline function은 함수 내의 코드를 inline으로 호출하는 위치에 붙여넣어주는 기능입니다.
*/

    protected inline fun <reified T : ViewDataBinding> binding(resId: Int) =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

    override fun onResume() {
        super.onResume()

        setListener()
        setObserver()
    }

    open fun setListener() {}
    open fun setObserver() {}

    /*
     toast의 경우 어느 화면에서든 자유롭게 사용할 수 있는데,
     매번 호출하는 activity의 context를 입력해줘야 한다.
     모든 activity가 BaseActivity를 상속 받으므로 BaseActivity의 context를 할당하여 편리하게 사용할 수 있도록 한다.
     */
    open fun showToast(message: String, isLong: Boolean = false) {
        Toast.makeText(this@BaseActivity, message, isLong.toInt()).show()
    }

    private fun Boolean.toInt() = if (this) 1 else 0

}