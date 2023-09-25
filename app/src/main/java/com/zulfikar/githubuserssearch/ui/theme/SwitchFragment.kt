package com.zulfikar.githubuserssearch.ui.theme

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.zulfikar.githubuserssearch.R
import com.zulfikar.githubuserssearch.databinding.FragmentSwitchBinding

class SwitchFragment : Fragment() {
    private var _binding: FragmentSwitchBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSwitchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = SettingPreferences.getInstance(requireActivity().application.dataStore)
        val switchViewModel = ViewModelProvider(this, SwitchViewModelFactory(pref)).get(
            SwitchViewModel::class.java
        )

        with(binding) {
            switchViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                }
            }

            toolbar.setupWithNavController(findNavController())
            toolbar.setTitle(R.string.switch_title)

            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                switchViewModel.saveThemeSetting(isChecked)
            }
        }
    }
}