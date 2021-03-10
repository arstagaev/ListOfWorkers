package com.arstagaev.testtask1.utils

import timber.log.Timber

//https://2.cdn.echo.msk.ru/i/empty_s2.jpg
fun validAvatar(avatar : String?) : String{

    if (avatar?.isEmpty() == true || avatar == "" || avatar == null){
        return "https://2.cdn.echo.msk.ru/i/empty_s2.jpg"
        Timber.d("ava " +avatar)
    }else{
        return avatar
    }
}