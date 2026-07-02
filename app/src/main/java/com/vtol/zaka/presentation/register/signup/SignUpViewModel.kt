package com.vtol.zaka.presentation.register.signup

import androidx.lifecycle.ViewModel
import com.vtol.zaka.domain.usecases.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel() {


}