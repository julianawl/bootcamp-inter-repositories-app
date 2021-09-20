package com.bootcamp.apprepo.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.bootcamp.apprepo.R
import com.bootcamp.apprepo.core.createDialog
import com.bootcamp.apprepo.core.createProgressDialog
import com.bootcamp.apprepo.core.hideSoftKeyboard
import com.bootcamp.apprepo.databinding.ActivityMainBinding
import com.bootcamp.apprepo.presentation.MainViewModel
import com.bootcamp.apprepo.ui.adapter.RepoListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val dialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { RepoListAdapter() }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.rvRepositories.adapter = adapter

        viewModel.repo.observe(this){
           when(it){
               MainViewModel.State.Loading -> dialog.show()
               is MainViewModel.State.Error -> {
                   createDialog{
                       setMessage(it.error.message)
                   }.show()
                   dialog.dismiss()
               }
               is MainViewModel.State.Success -> {
                   dialog.dismiss()
                   adapter.submitList(it.list)
               }
           }
        }

        adapter.onRepoClickListener = {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.htmlURL)
            startActivity(intent)
        }

        adapter.onAvatarClickListener = {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(it.htmlUrl)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepoList(it) }
        binding.root.hideSoftKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.e(TAG, "onQueryTextSubmit: $newText")
        return false
    }

    companion object {
        const val TAG = "TAG"
    }
}