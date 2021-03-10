package com.arstagaev.testtask1.base

import android.view.View
import com.arstagaev.testtask1.models.WorkerInfo

/**
 * Created by agladkov on 10.01.18.
 */
interface BaseAdapterCallback<T> {
    fun onItemClick(model: T, view: View)
    fun onLongClick(model: T, view: View): Boolean
}