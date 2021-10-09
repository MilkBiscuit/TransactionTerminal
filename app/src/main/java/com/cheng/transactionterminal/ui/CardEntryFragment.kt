package com.cheng.transactionterminal.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cheng.transactionterminal.R
import com.cheng.transactionterminal.databinding.FragmentCardEntryBinding
import com.cheng.transactionterminal.entity.MoToType
import com.cheng.transactionterminal.entity.NoCvvReason
import com.cheng.transactionterminal.presenter.CardEntryPresenter
import java.lang.ref.WeakReference

class CardEntryFragment : Fragment() {

    private var _binding: FragmentCardEntryBinding? = null

    private var motoTypeTextView: AutoCompleteTextView? = null
    private var noCvvTextView: AutoCompleteTextView? = null
    private val presenter = CardEntryPresenter()
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val currentMoToType: MoToType?
        get() {
            motoTypeTextView?.apply {
                return when (listSelection) {
                    0 -> MoToType.Single
                    1 -> MoToType.Recurring
                    else -> null
                }
            }
            return null
        }
    private val noCvvReason: NoCvvReason?
        get() {
            noCvvTextView?.apply {
                return when (listSelection) {
                    0 -> NoCvvReason.NoCvvOnCard
                    1 -> NoCvvReason.NoCardPresent
                    2 -> NoCvvReason.UnableToRead
                    else -> null
                }
            }
            return null
        }

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

        motoTypeTextView = binding.autoCompleteTextViewMotoType
        val motoTypeStrings = requireContext().resources.getStringArray(R.array.moto_type_values)
        val motoTypeAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, motoTypeStrings
        )
        binding.autoCompleteTextViewMotoType.setAdapter(motoTypeAdapter)

        noCvvTextView = binding.autoCompleteTextViewNoCvv
        val noCvvReasonStrings = requireContext().resources.getStringArray(R.array.no_cvv_reason_values)
        val noCvvReasonAdapter = ArrayAdapter(
            requireContext(), android.R.layout.simple_dropdown_item_1line, noCvvReasonStrings
        )
        binding.autoCompleteTextViewNoCvv.setAdapter(noCvvReasonAdapter)

        binding.buttonContinueTransaction.setOnClickListener {
            if (binding.editTextCvv.text?.isBlank() == true) {
                binding.inputLayoutNoCvv.visibility = View.VISIBLE

                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_to_first_fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
