package com.cheng.transactionterminal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.databinding.FragmentCardEntryBinding
import com.cheng.transactionterminal.presenter.CardEntryPresenter
import java.lang.ref.WeakReference

class CardEntryFragment : Fragment() {

    private var _binding: FragmentCardEntryBinding? = null

    private val presenter = CardEntryPresenter()
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardEntryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextExpiry.addTextChangedListener(
            ExpiryTimeTextWatcher(WeakReference(binding.editTextExpiry))
        )
        binding.buttonContinueTransaction.setOnClickListener {
            findNavController().navigate(R.id.action_to_first_fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
