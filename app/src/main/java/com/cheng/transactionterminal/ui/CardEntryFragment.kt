package com.cheng.transactionterminal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.contract.ICardEntryView
import com.cheng.transactionterminal.databinding.FragmentCardEntryBinding
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason
import com.cheng.transactionterminal.presenter.CardEntryPresenter
import com.cheng.transactionterminal.usecase.CardNumberUtil
import java.lang.ref.WeakReference

class CardEntryFragment : Fragment(), ICardEntryView {

    private var _binding: FragmentCardEntryBinding? = null

    private lateinit var motoTypeTextView: AutoCompleteTextView
    private lateinit var noCvvTextView: AutoCompleteTextView
    private val presenter = CardEntryPresenter(this)
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private var currentMoToType: MoToType? = null
    private var noCvvReason: NoCvvReason? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardEntryBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextCardNumber.addTextChangedListener(
            CardNumberTextWatcher(
                WeakReference(context),
                WeakReference(binding.editTextCardNumber),
                WeakReference(binding.inputLayoutCardNumber)
            )
        )

        binding.editTextExpiry.addTextChangedListener(
            ExpiryTimeTextWatcher(
                WeakReference(context),
                WeakReference(binding.editTextExpiry),
                WeakReference(binding.inputLayoutExpiry)
            )
        )

        binding.editTextCvv.addTextChangedListener { editable ->
            if (!editable.isNullOrEmpty()) {
                hideNoCvvReason()
            }
        }

        motoTypeTextView = binding.autoCompleteTextViewMotoType
        val motoTypeStrings = requireContext().resources.getStringArray(R.array.moto_type_values)
        val motoTypeAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, motoTypeStrings
        )
        motoTypeTextView.setAdapter(motoTypeAdapter)
        motoTypeTextView.setOnItemClickListener { _, _, position, _ ->
            currentMoToType = when(position) {
                0 -> MoToType.Single
                1 -> MoToType.Recurring
                else -> null
            }
            binding.inputLayoutMotoType.error = null
        }

        noCvvTextView = binding.autoCompleteTextViewNoCvv
        val noCvvReasonStrings = requireContext().resources.getStringArray(R.array.no_cvv_reason_values)
        val noCvvReasonAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, noCvvReasonStrings
        )
        binding.autoCompleteTextViewNoCvv.setAdapter(noCvvReasonAdapter)
        binding.autoCompleteTextViewNoCvv.setOnItemClickListener { _, _, position, _ ->
            noCvvReason = when(position) {
                0 -> NoCvvReason.NoCvvOnCard
                1 -> NoCvvReason.NoCardPresent
                2 -> NoCvvReason.UnableToRead
                else -> null
            }
            binding.inputLayoutNoCvv.error = null
        }

        binding.buttonContinueTransaction.setOnClickListener {
            presenter.submitTransaction(
                CardNumberUtil.removeWhiteSpace(binding.editTextCardNumber.text.toString()),
                binding.editTextExpiry.text,
                binding.editTextCvv.text,
                binding.switchCardStored.isChecked,
                currentMoToType,
                noCvvReason,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showNoCvvReason() {
        binding.inputLayoutNoCvv.visibility = View.VISIBLE
    }

    override fun hideNoCvvReason() {
        binding.inputLayoutNoCvv.visibility = View.INVISIBLE
    }

    override fun setCardNumberError(stringResId: Int) {
        binding.inputLayoutCardNumber.error = setInputLayoutError(stringResId)
    }

    override fun setExpiryError(stringResId: Int) {
        binding.inputLayoutExpiry.error = setInputLayoutError(stringResId)
    }

    override fun setNoCvvReasonError(stringResId: Int) {
        binding.inputLayoutNoCvv.error = setInputLayoutError(stringResId)
    }

    override fun setMotoTypeError(stringResId: Int) {
        binding.inputLayoutMotoType.error = setInputLayoutError(stringResId)
    }

    override fun onTransactionSaved() {
        Toast.makeText(context, R.string.toast_transaction_saved, Toast.LENGTH_SHORT).show()
        findNavController().navigateUp()
    }

    private fun setInputLayoutError(stringResId: Int) = if (stringResId == 0) {
        null
    } else {
        requireContext().getText(stringResId)
    }
}
