package com.puntogris.posture.ui.login

import com.puntogris.posture.R
import com.puntogris.posture.databinding.FragmentLoginWithEmailBinding
import com.puntogris.posture.di.realmApp
import com.puntogris.posture.ui.base.BaseFragment
import io.realm.mongodb.Credentials


class LoginWithEmailFragment : BaseFragment<FragmentLoginWithEmailBinding>(R.layout.fragment_login_with_email) {

    override fun initializeViews() {
        realmApp.loginAsync(Credentials.emailPassword("emas", "1")){

        }
    }

}