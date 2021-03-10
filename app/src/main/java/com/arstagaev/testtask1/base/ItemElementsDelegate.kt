package com.arstagaev.testtask1.base

import android.view.View


interface ItemElementsDelegate<T> {
    fun onElementClick(model: T, view: View, clickedPosition: Int)

}