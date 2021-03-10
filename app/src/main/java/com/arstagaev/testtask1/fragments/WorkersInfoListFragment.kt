package com.arstagaev.testtask1.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arstagaev.testtask1.R
import com.arstagaev.testtask1.activity.MainActivity
import com.arstagaev.testtask1.adapters.ShowWorkersAdapter
import com.arstagaev.testtask1.base.BaseAdapterCallback
import com.arstagaev.testtask1.base.ItemElementsDelegate
import com.arstagaev.testtask1.models.WorkerInfo
import com.arstagaev.testtask1.storage.PreferenceMaestro
import com.arstagaev.testtask1.viewmodels.MainViewModel
import timber.log.Timber

class WorkersInfoListFragment : Fragment(R.layout.fragment_list_workers) {
    private var workerProfileFragment: WorkerProfileFragment? = null
    private var chosenSpecialtyID = 0
    private var chosenSpecialtyName = ""
    private lateinit var viewModel : MainViewModel

    private lateinit var recycler_view : RecyclerView
    private lateinit var mainScreenLayout: RelativeLayout
    private val mAdapter = ShowWorkersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context
        viewModel =(activity as MainActivity).viewModel

        recycler_view = view.findViewById(R.id.list_of_workers)

        val bundle = this.arguments
        if (bundle != null) {
            chosenSpecialtyID = bundle.getInt("id_spec", 0)
            chosenSpecialtyName = bundle.getString("name_spec", "")
            //val value2 = bundle.getInt("VALUE2", -1)
        }

        showRecyclerview()
        refreshRecyclerview()
        PreferenceMaestro.checkFragmentVisible = 2
    }

    private fun refreshRecyclerview() {
        viewModel.getWorkersBySpeciality(chosenSpecialtyName).observe(viewLifecycleOwner, Observer { listOfWorkers ->
            mAdapter.updateItems(listOfWorkers)

        })
    }

    private fun showRecyclerview() {

        mAdapter.attachDelegate(object : ItemElementsDelegate<WorkerInfo> {
            override fun onElementClick(model: WorkerInfo, view: View, clickedPosition: Int) {
                openProfileOfChosenWorker(model.idOfWorker)
                Timber.d("click" + model.idOfWorker)
            }
            //            override fun onElementClick(model: WorkerInfo, view: View, clickedPosition: Int) {
//
//            }
        })

        recycler_view.adapter = mAdapter
        recycler_view.layoutManager = LinearLayoutManager(activity)

        recycler_view.setHasFixedSize(false)
    }

    fun openProfileOfChosenWorker(idOfWorker : Int){
        workerProfileFragment = WorkerProfileFragment()
        val arguments = Bundle()
        arguments.putInt("id_worker",idOfWorker)
        //arguments.putString("name_spec",specialtyName)
        workerProfileFragment!!.arguments = arguments

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragments_container, workerProfileFragment!!)
            .commit()
    }

    companion object {
        const val FRAGMENT_TAG = "workers_list"
        fun create(): WorkersInfoListFragment {

            val fragment = WorkersInfoListFragment()
            //fragment.arguments = arguments

            return fragment
        }

    }
}