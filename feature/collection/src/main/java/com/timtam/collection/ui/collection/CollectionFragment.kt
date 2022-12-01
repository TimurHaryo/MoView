package com.timtam.collection.ui.collection

import android.os.Bundle
import android.view.View
import com.timtam.collection.R
import com.timtam.collection.databinding.FragmentCollectionBinding
import com.timtam.common.abstraction.LifecycleFragment
import com.timtam.common.extension.i
import com.timtam.common.util.viewLifecycleLazy

class CollectionFragment : LifecycleFragment(R.layout.fragment_collection) {

    private val binding: FragmentCollectionBinding by viewLifecycleLazy(FragmentCollectionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        i { "HELLO COLLECTION!" }
    }
}
