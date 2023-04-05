package com.example.permissionshandle

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.permissionshandle.Constants.Companion.REQUEST_CODE_FOR_WRITE_PERMISSION
import com.example.permissionshandle.Constants.Companion.WRITE_PERMISSIONS
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {
    private var mStorageDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (allPermissionsGranted()) {
//                Toast.makeText(this, "thiss", Toast.LENGTH_SHORT).show()
//            } else {
//                ActivityCompat.requestPermissions(
//                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
//                )
//            }
//
//        }

    }
//
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(
//            baseContext, it
//        ) == PackageManager.PERMISSION_GRANTED
//    }


//    companion object {
//        private const val TAG = "CameraX-MLKit"
//        private const val REQUEST_CODE_PERMISSIONS = 10
//        private val REQUIRED_PERMISSIONS =
//            mutableListOf(
//                android.Manifest.permission.CAMERA    ).toTypedArray()
//
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionsUtils.getInstance().handleRequestPermissionsResult(
            this, grantResults,
            requestCode, REQUEST_CODE_FOR_WRITE_PERMISSION, this::permissionsAreGranted
        );

    }


    override fun onStart() {
        super.onStart()
        checkAndAskForStoragePermission()
    }

    private fun checkAndAskForStoragePermission() {
        if (Build.VERSION.SDK_INT <= 29) {
            if (!PermissionsUtils.getInstance().checkRuntimePermissions(this, WRITE_PERMISSIONS)) {
                showStorageDialogOptions(this)
            } else if (PermissionsUtils.getInstance()
                    .checkRuntimePermissions(this, WRITE_PERMISSIONS)
            ) {
                //granted
            }
        } else {
            //granted
        }
    }

    fun showStorageDialogOptions(context: Context?) {
        if (mStorageDialog != null && mStorageDialog!!.isShowing()) {
            return
        }
        mStorageDialog =
            MaterialAlertDialogBuilder(context!!).setIcon(R.drawable.ic_baseline_perm_media_24)
                .setTitle("Permission required")
                .setMessage("App needs Media Access permissions to function properly. In order to access and download or save statuses, please grant the permission to use the app.")
                .setPositiveButton("Grant", DialogInterface.OnClickListener { dialog, which ->
                    if (!PermissionsUtils.getInstance()
                            .checkRuntimePermissions(this, WRITE_PERMISSIONS)
                    ) {
                        getRuntimePermissions()
                    } else if (PermissionsUtils.getInstance()
                            .checkRuntimePermissions(this, WRITE_PERMISSIONS)
                    ) {
                        //granted
                    }
                    dialog.dismiss()
                }).setNegativeButton(
                    "Exit"
                ) { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                    finishAndRemoveTask()
                }.setCancelable(false).show()
    }

    private fun getRuntimePermissions() {
        PermissionsUtils.getInstance().requestRuntimePermissions(
            this,
            WRITE_PERMISSIONS,
            REQUEST_CODE_FOR_WRITE_PERMISSION
        )
    }


    private fun permissionsAreGranted() {
        Toast.makeText(this,"granted",Toast.LENGTH_SHORT).show()
    }
}