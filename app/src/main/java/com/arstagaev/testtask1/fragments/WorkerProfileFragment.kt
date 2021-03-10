package com.arstagaev.testtask1.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arstagaev.testtask1.R
import com.arstagaev.testtask1.activity.MainActivity
import com.arstagaev.testtask1.storage.PreferenceMaestro
import com.arstagaev.testtask1.viewmodels.MainViewModel
import com.bumptech.glide.Glide
import timber.log.Timber

class WorkerProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var viewModel : MainViewModel

    lateinit var avatar : ImageView
    lateinit var txtFName : TextView
    lateinit var txtSName : TextView
    lateinit var txtBDayDate : TextView
    lateinit var txtSpecialty : TextView

    var chosenWorkerID = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context
        viewModel =(activity as MainActivity).viewModel

        avatar = view.findViewById(R.id.avatar)
        txtFName = view.findViewById(R.id.fname_tv)
        txtSName = view.findViewById(R.id.sname_tv)
        txtBDayDate = view.findViewById(R.id.age_and_bday_date)
        //txtAge = view.findViewById(R.id.age_and_bday_date)
        txtSpecialty = view.findViewById(R.id.specialty_of_worker_profile)

        val bundle = this.arguments
        if (bundle != null) {
            chosenWorkerID = bundle.getInt("id_worker", 0)
            //val value2 = bundle.getInt("VALUE2", -1)
        }
        PreferenceMaestro.checkFragmentVisible = 3

        refreshDataOfProfile()
    }

    private fun refreshDataOfProfile() {
        viewModel.getWorkersByID(chosenWorkerID).observe(viewLifecycleOwner, Observer { workerInfo ->
            try {
                Glide.with(avatar)
                    .load(workerInfo.avatrUrl)
                    .into(avatar)
            }catch (e : Exception){
                Timber.e("error ${e.message}")
            }


            txtFName.text = workerInfo.fName
            txtSName.text = workerInfo.lName
            txtBDayDate.text = workerInfo.birthday

        })
    }

    companion object {
        const val FRAGMENT_TAG = "worker_profile"

        fun create(): WorkerProfileFragment {

            val fragment = WorkerProfileFragment()
            //fragment.arguments = arguments

            return fragment
        }

    }
}