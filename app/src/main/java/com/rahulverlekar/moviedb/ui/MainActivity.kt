package com.rahulverlekar.moviedb.ui

import androidx.activity.viewModels
import com.rahulverlekar.moviedb.R
import com.rahulverlekar.moviedb.base.BaseActivity
import com.rahulverlekar.moviedb.base.BaseViewModel
import com.rahulverlekar.moviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    override fun getVM(): BaseViewModel = viewModel
}