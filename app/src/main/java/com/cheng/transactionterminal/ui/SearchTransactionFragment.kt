package com.cheng.transactionterminal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cheng.transactionterminal.databinding.FragmentSearchTransactionBinding

class SearchTransactionFragment : Fragment() {

    private var _binding: FragmentSearchTransactionBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchTransactionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSearch.setOnClickListener {
            // TODO: search

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
