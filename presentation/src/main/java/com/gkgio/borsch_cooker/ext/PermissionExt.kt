package com.gkgio.borsch_cooker.ext

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable

fun Context.checkPermissionGranted(permission: String) =
    ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED

fun Context.checkWriteStorageGranted() =
    checkPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)

fun Fragment.requestWriteStoragePermission(): Observable<Boolean> =
    RxPermissions(this)
        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)

