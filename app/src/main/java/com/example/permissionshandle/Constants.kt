package com.example.permissionshandle

import android.Manifest

class Constants {

    companion object{
        val REQUEST_CODE_FOR_WRITE_PERMISSION = 4
        val REQUEST_CODE_FOR_READ_PERMISSION = 5


        val WRITE_PERMISSIONS = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val READ_PERMISSIONS = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

}