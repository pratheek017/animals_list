package com.example.animals.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager

import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * The list fragment
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    /**
     * Observer to observe the animals list in the view model
     */
    private val animalsListDataObserver = Observer<List<Animal>>{list ->

        //This means if list is not null, then do whatever is inside let block.
        // Else don't do anything.
        // Just that question mark takes care of the null check. Kotlin is too cool huh? ;)
        list?.let {
            animalList.visibility = View.VISIBLE

            // "it" is the list of animals received from the live data in ViewModel.
            listAdapter.updateAnimalList(it)
        }
    }

    /**
     * * Observer to observe the loading data flag in the view model
     */
    private val loadingLiveDataObserver = Observer<Boolean> {isLoading ->
        loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE

        if (isLoading){
            animalList.visibility = View.GONE
            listError.visibility = View.GONE
        }
    }

    /**
     * * Observer to observe the load error flag in the view model
     */
    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        listError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This is view model initialization.
        // This will make sure that when this fragment is destroyed and recreated, the same
        // ListViewModel object is returned instead of recreating this object as well.
        // This will make sure that the live data values of ListViewModel are also the latest
        // as per what they're supposed to be. Example: When the screen is rotated from
        // portrait to landscape mode.
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        // This is what tells the local observer objects to start observing the live data.
        viewModel.animals.observe(viewLifecycleOwner, animalsListDataObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        viewModel.loadError.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.refresh()

        // This animalList is the ID of the recycler view in the fragment_list xml layout file.
        // Again, Kotlin is too cool huh? ;)
        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.hardRefresh()

            // This is to remove the default spinner that recycler view shows
            refreshLayout.isRefreshing = false
        }
    }
}
