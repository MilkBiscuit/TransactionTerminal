package com.cheng.transactionterminal.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.databinding.FragmentFirstBinding
import com.cheng.transactionterminal.db.AppDatabase
import com.cheng.transactionterminal.entity.BankCard
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason
import com.cheng.transactionterminal.entity.TransactionRecord
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_to_card_entry_fragment)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val cardIds = AppDatabase.getDatabase(requireContext()).bankCardDao().insert(
                BankCard("1111222233334444", "11/20", "456"),
                BankCard("33333", "11/44", "456"),
            )
            val transactionId = AppDatabase.getDatabase(requireContext()).transactionDao().insert(
                TransactionRecord(100, 1, MoToType.Single)
            )

            val i = 0;
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
