package org.aos.shareroof.sign

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import org.aos.shareroof.MainViewModel
import org.aos.shareroof.R
import org.aos.shareroof.base.BaseFragment
import org.aos.shareroof.databinding.FragmentSignInBinding

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(R.layout.fragment_sign_in) {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.ivKakao.setOnClickListener {
            setLogin()
        }
    }
    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e(TAG, "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
            getKakaoUserInfo()
            findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
        }
    }

    private fun getKakaoUserInfo() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.d("TAG", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.d("TAG", "사용자 정보 요청 성공")
                mainViewModel.email.value = user.kakaoAccount?.email.toString()
                mainViewModel.nickName.value = user.kakaoAccount?.profile?.nickname ?: ""
                mainViewModel.profile.value = user.kakaoAccount?.profile?.profileImageUrl ?: ""

            }
        }
    }

    private fun setLogin() {
// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())) {
            UserApiClient.instance.loginWithKakaoTalk(requireContext()) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
        }
    }

    companion object {
        const val TAG = "signInFragment"
    }
}