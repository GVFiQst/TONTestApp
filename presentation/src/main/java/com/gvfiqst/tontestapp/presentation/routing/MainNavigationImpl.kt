package com.gvfiqst.tontestapp.presentation.routing

import com.gvfiqst.tontestapp.presentation.main.MainActivity
import com.gvfiqst.tontestapp.presentation.ui.search.SearchNavigation


class MainNavigationImpl(
    private val activity: MainActivity
) : MainNavigation, SearchNavigation
