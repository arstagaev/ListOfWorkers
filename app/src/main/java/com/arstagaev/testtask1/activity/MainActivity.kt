package com.arstagaev.testtask1.activity


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arstagaev.testtask1.R
import com.arstagaev.testtask1.fragments.ListOfSpecialtyFragment
import com.arstagaev.testtask1.repository.ListOfWorkersRepository
import com.arstagaev.testtask1.storage.PreferenceMaestro
import com.arstagaev.testtask1.viewmodels.MainViewModel
import com.arstagaev.testtask1.viewmodels.MassiveViewModelProviderFactory
import timber.log.Timber


class MainActivity : AppCompatActivity() {
    private var fragmentA: ListOfSpecialtyFragment? = null
    //private var fragmentB: FragmentB? = null

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("Get Started!")
        //Log.d(TAGZ,"Get Started!, not Timber")

        val repository = ListOfWorkersRepository(application)


        val viewModelProviderFactory = MassiveViewModelProviderFactory(application, repository)
        viewModel =ViewModelProvider(this, viewModelProviderFactory).get(MainViewModel::class.java)

        fragmentA = ListOfSpecialtyFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragments_container, fragmentA!!)
                .commit()


        viewModel.getAllSpec().observe(this, Observer { listS ->
            for (i in listS) {
                Timber.d("xxx " + i.name + " id=" + i.specialty_id)
            }

        })

    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext," не доработал onBackPressed() ",Toast.LENGTH_SHORT).show()

        //super.onBackPressed()
    }

    override fun onDestroy() {
        PreferenceMaestro.checkFragmentVisible = 1
        super.onDestroy()
    }

//    override fun onBackPressed() {
//        val firstScreenFragment = supportFragmentManager.findFragmentByTag(ListOfSpecialtyFragment.FRAGMENT_TAG) as ListOfSpecialtyFragment
//        val secondScreenFragment = supportFragmentManager.findFragmentByTag(WorkersInfoListFragment.FRAGMENT_TAG) as WorkersInfoListFragment
//        val thirdScreenFragment = supportFragmentManager.findFragmentByTag(WorkerProfileFragment.FRAGMENT_TAG) as WorkerProfileFragment
//        Toast.makeText(applicationContext,"000 "+firstScreenFragment.isResumed +secondScreenFragment.isResumed, Toast.LENGTH_SHORT).show()
//
//        if (firstScreenFragment != null &&
//            firstScreenFragment.isResumed) {
//                Toast.makeText(applicationContext,"111", Toast.LENGTH_LONG).show()
//
//            return
//        }
//
//
//        if (secondScreenFragment != null &&
//            secondScreenFragment.isResumed ) {
//            Toast.makeText(applicationContext,"222",Toast.LENGTH_LONG).show()
//
//            switchFragment(1)
//            return
//        }
//
//
//        if (thirdScreenFragment != null &&
//            thirdScreenFragment.isResumed) {
//                switchFragment(2)
//            Toast.makeText(applicationContext,"333",Toast.LENGTH_LONG).show()
//
//            return
//        }
//
//        //super.onBackPressed()
//    }
}