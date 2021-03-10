package com.arstagaev.testtask1.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.arstagaev.testtask1.R
import com.arstagaev.testtask1.activity.MainActivity
import com.arstagaev.testtask1.models.Specialty
import com.arstagaev.testtask1.storage.PreferenceMaestro
import com.arstagaev.testtask1.viewmodels.MainViewModel
import timber.log.Timber


class ListOfSpecialtyFragment : Fragment(R.layout.fragment_list_of_specialty) {
    private var fragmentx: WorkersInfoListFragment? = null
    private var choosenSpecialty = 0
    private lateinit var viewModel : MainViewModel
    private var arrayOfSpecialtys= hashSetOf<String>()
    private var specialtyList : List<Specialty> = listOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as Context
        viewModel =(activity as MainActivity).viewModel



        viewModel.getAllSpec().observe(viewLifecycleOwner, Observer { specialty ->
            for (i in specialty){
                arrayOfSpecialtys.add("Name: "+i.name +"  id: "+i.specialty_id)
            }
            specialtyList = specialty

            showListView(view,arrayOfSpecialtys.toTypedArray())
            Timber.i("size of  spec" + specialty.size+" "+arrayOfSpecialtys.toString())
        })
        PreferenceMaestro.checkFragmentVisible = 1

    }

    private fun showListView(view: View, arrayOfSpecialtys: Array<String>){

        val adapter: ArrayAdapter<*> = ArrayAdapter<String>(
            requireActivity(),
            R.layout.item_specialty,arrayOfSpecialtys
        )
        val listView: ListView = view.findViewById(R.id.list_of_specialty) as ListView
        listView.setAdapter(adapter)

        listView.setOnItemClickListener { parent, view, position, id ->
            //Toast.makeText(activity,"pos $position",Toast.LENGTH_SHORT).show()
            openAnotherFragment(
                specialtyList.get(position).specialty_id,
                specialtyList.get(position).name
            )

        }

    }
    fun openAnotherFragment(id : Int, specialtyName: String){
        fragmentx = WorkersInfoListFragment()
        val arguments = Bundle()
        arguments.putInt("id_spec",id)
        arguments.putString("name_spec",specialtyName)
        fragmentx!!.arguments = arguments

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragments_container, fragmentx!!)
            .commit()
    }



    companion object {
        const val FRAGMENT_TAG = "list_of_specialty"

        fun create(): ListOfSpecialtyFragment {

            val fragment = ListOfSpecialtyFragment()
            //fragment.arguments = arguments

            return fragment
        }

    }
}