package com.gvfiqst.tontestapp.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment


abstract class ViewModelFragment : Fragment {

    constructor() : super()

    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    protected abstract val viewModel: ViewModel<*, *, *>

    override fun onDestroyView() {
        super.onDestroyView()

        viewModel.dispatchDestroy()
    }

}
