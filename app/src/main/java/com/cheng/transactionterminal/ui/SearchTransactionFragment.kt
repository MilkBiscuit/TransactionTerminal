package com.cheng.transactionterminal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.contract.ISearchTransactionView
import com.cheng.transactionterminal.databinding.FragmentSearchTransactionBinding
import com.cheng.transactionterminal.entity.TransactionRecord
import com.cheng.transactionterminal.presenter.SearchTransactionPresenter
import com.cheng.transactionterminal.usecase.DateUtil
import java.util.*

class SearchTransactionFragment : Fragment(), ISearchTransactionView {

    private var _binding: FragmentSearchTransactionBinding? = null
    private val presenter = SearchTransactionPresenter(this)
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
            presenter.searchTransaction(binding.editTextCardNumberLast4.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showResult(transactions: List<TransactionRecord>) {
        // TODO: Use RecyclerView for pretty UIs
        val resultAsText = transactions.joinToString(separator = "\n") {
            val transactionDate = Date(it.transactionDate)
            val formattedDate = DateUtil.formatDate(transactionDate)

            "Amount: ${it.amountInCents}, Date: $formattedDate"
        }

        binding.textViewSearchResult.text = resultAsText.ifBlank {
            requireContext().getString(R.string.no_matched_transactions)
        }
    }

}
