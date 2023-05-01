package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentItemListBinding
import com.example.myapplication.databinding.FragmentListListBinding

/**
 * A simple [Fragment] subclass.
 */
class ListListFragment : Fragment() {
    private lateinit var binding: FragmentListListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}