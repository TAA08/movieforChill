package com.example.movieforchill.presentation.login

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movieforchill.R
import com.example.movieforchill.databinding.FragmentLoginBinding
import com.example.movieforchill.domain.models.session.LoginApprove
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class LoginFragment : Fragment(), CoroutineScope {


    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModel<LoginViewModel>()

    override val coroutineContext: CoroutineContext = Dispatchers.Main

    private lateinit var prefSettings: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        prefSettings =
            context?.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE) as SharedPreferences
        if (prefSettings.getString(SESSION_ID_KEY, null) != null) {
            findNavController().navigate(R.id.action_loginFragment_to_navigation_first_fragment)
        }
        editor = prefSettings.edit()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoginClick()
    }


    private fun onLoginClick() {
        binding.btnLogin.setOnClickListener {
            hideKeyboard(requireActivity())
            if (!binding.etUsername.text.isNullOrBlank() && !binding.etPassword.text.isNullOrBlank()) {
                val data = LoginApprove(
                    username = binding.etUsername.text.toString().trim(),
                    password = binding.etPassword.text.toString().trim(),
                    request_token = ""
                )
                viewModel.login(data)
                observeLoadingState()
            } else {
                Toast.makeText(requireContext(), "Введите данные", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun observeLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner) {
            when (it) {
                LoginViewModel.LoadingState.ShowLoading -> binding.pbLoading.visibility =
                    View.VISIBLE
                LoginViewModel.LoadingState.HideLoading -> binding.pbLoading.visibility = View.GONE
                LoginViewModel.LoadingState.Finish -> {
                    viewModel.sessionId.observe(viewLifecycleOwner) {
                        sessionId = it
                        putDataIntoPref(sessionId)
                        try {
                            findNavController().navigate(
                                R.id.action_loginFragment_to_navigation_first_fragment
                            )
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else -> Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun putDataIntoPref(string: String) {
        editor.putString(SESSION_ID_KEY, string)
        editor.commit()
        binding.etUsername.text = null
        binding.etPassword.text = null
    }

    //скрыть клавиатуру
    private fun hideKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    companion object {

        private var sessionId: String = ""
        const val APP_SETTINGS = "Settings"
        const val SESSION_ID_KEY = "SESSION_ID"
    }
}

