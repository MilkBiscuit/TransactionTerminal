package com.cheng.transactionterminal.ui

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
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
    private val focusChangeListener: View.OnFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            presenter.saveTempTransaction(
                binding.editTextCardNumber.text,
                binding.editTextExpiry.text,
                binding.editTextCvv.text,
                binding.switchCardStored.isChecked,
                currentMoToType,
                noCvvReason
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
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
        binding.editTextCardNumber.onFocusChangeListener = focusChangeListener
        binding.editTextExpiry.onFocusChangeListener = focusChangeListener
        binding.editTextCvv.onFocusChangeListener = focusChangeListener
        binding.autoCompleteTextViewMotoType.onFocusChangeListener = focusChangeListener
        binding.autoCompleteTextViewNoCvv.onFocusChangeListener = focusChangeListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_load_transaction -> {
                val pair = presenter.readTempTransaction()
                val bankCard = pair.first
                binding.editTextCardNumber.setText(bankCard.cardNumber)
                binding.editTextExpiry.setText(bankCard.expiryDate)
                binding.editTextCvv.setText(bankCard.cvv)
                // TODO: moto type, no cvv reason etc.

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
