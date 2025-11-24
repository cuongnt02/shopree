package com.ntc.shopree.data.app

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ntc.shopree.domain.repository.CategoryRepository
import com.ntc.shopree.ui.viewmodels.ProductsViewModelFactory

interface AppContainer {
    val categoryRepository: CategoryRepository
    val productsViewModelFactory: ViewModelProvider.Factory
}

