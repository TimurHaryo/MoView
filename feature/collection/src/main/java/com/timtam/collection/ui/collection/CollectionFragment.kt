package com.timtam.collection.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timtam.collection.databinding.FragmentCollectionBinding
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.delegation.fragment.FragmentRetainerDelegate
import com.timtam.common.delegation.fragment.FragmentRetainerDelegateImpl
import com.timtam.common.extension.i

class CollectionFragment :
    LifecycleFragment<FragmentCollectionBinding>(),
    FragmentRetainerDelegate by FragmentRetainerDelegateImpl() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return oneTimeRenderer {
            _binding = FragmentCollectionBinding.inflate(inflater, container, false)
            _binding?.lifecycleOwner = viewLifecycleOwner
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView {
            i { "HELLO COLLECTION!" }
        }
    }
}
