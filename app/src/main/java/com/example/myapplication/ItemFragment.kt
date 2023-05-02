package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.ItemBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ItemFragment : Fragment() {

  /*  private lateinit var binding: ItemBinding

    private var _totalCount = 3
    private var _currentCount: Int = 0
        set(value) {
            field = value
            onCurrentCountUpdate()
        }

    //private var _currentCount = MutableLiveData<Int>()


    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding

//    private val adapter: ItemAdapter by lazy {
//        ItemAdapter()
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemCounterText.text = "$_currentCount/$_totalCount"
        *//*
        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_itemFragment_to_itemAddEditFragment)
        }

         *//*

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

    private fun onCurrentCountUpdate() {
        binding.itemCounterText.text = "$_currentCount/$_totalCount"
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }*/
}