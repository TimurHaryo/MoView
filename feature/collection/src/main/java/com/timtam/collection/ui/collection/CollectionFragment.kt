package com.timtam.collection.ui.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.timtam.collection.databinding.FragmentCollectionBinding
import com.timtam.common_android.abstraction.LifecycleFragment
import com.timtam.common_android.delegation.fragment.FragmentRetainable
import com.timtam.common_android.delegation.fragment.FragmentRetainer
import com.timtam.common_android.extension.i

class CollectionFragment :
    LifecycleFragment<FragmentCollectionBinding>(),
    FragmentRetainable by FragmentRetainer() {

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
        oneTimeInitView {
            i { "HELLO COLLECTION!" }
        }
    }
}
