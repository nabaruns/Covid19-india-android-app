package com.nabarunsarkar.safe19india.di

import com.nabarunsarkar.safe19india.ui.main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewDistrictModule = module {
    viewModel {
        DistrictViewModel(get())
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewEssentialModule = module {
    viewModel {
        EssentialViewModel(get())
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewFaqModule = module {
    viewModel {
        FaqViewModel(get())
    }
}

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
val viewAppListModule = module {
    viewModel {
        AppListViewModel(get())
    }
}