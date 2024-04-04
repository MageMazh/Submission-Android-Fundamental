package com.d121211069.submissionandroidfundamental

import com.d121211069.submissionandroidfundamental.viewModel.MainViewModel
import junit.framework.TestCase.assertNotNull
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        mainViewModel = MainViewModel()
    }

    @Test
    fun searchUser() {
        val search = mainViewModel.searchUsers("a")
        assertNotNull(search)
    }
}