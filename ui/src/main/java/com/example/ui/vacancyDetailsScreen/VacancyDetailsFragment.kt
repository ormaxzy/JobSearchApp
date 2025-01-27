package com.example.ui.vacancyDetailsScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.ui.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VacancyDetailsFragment : Fragment(R.layout.fragment_vacancy_details) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}
