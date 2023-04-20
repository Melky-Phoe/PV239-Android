package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.ItemBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemFragment : Fragment() {

    private var _binding: ItemBinding? = null

    private var _totalCount = 1
    private var _currentCount = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ItemBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editButton.setOnClickListener {
            // TODO navigate to edit
            // findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.plusButton.setOnClickListener {
            if (_currentCount < _totalCount) {
                _currentCount++
            }
        }

        binding.minusButton.setOnClickListener {
            if (_currentCount == _totalCount) {
                binding.checkBox.isEnabled = true
                binding.checkBox.isChecked = false
            }
            if (_currentCount > 0) {
                _currentCount--
            }
        }

        binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                _currentCount = _totalCount
                binding.checkBox.isEnabled = false // Disable checkbox
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}