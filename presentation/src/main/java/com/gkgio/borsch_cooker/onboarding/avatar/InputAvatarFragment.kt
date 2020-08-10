package com.gkgio.borsch_cooker.onboarding.avatar

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import com.gkgio.borsch_cooker.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_input_avatar.*
import kotlinx.android.synthetic.main.fragment_input_avatar.saveBtn
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class InputAvatarFragment : BaseFragment<InputAvatarViewModel>() {

    companion object {
        const val TAG = "InputAvatarFragment"
        const val PHOTO_GALLERY_REQUEST_CODE = 1
        private const val IMAGE_QUALITY = 40
        private const val TEMP_FILE_PREFIX = "image"
    }

    override fun getLayoutId(): Int = R.layout.fragment_input_avatar

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.inputAvatarViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.photoUri.observeValue(viewLifecycleOwner) {
            Glide.with(cookerAvatarIv)
                .load(it)
                .withFade()
                .withCenterCropOval()
                .placeholderByDrawable(R.drawable.ic_photo)
                .apply(RequestOptions.circleCropTransform())
                .into(cookerAvatarIv)
        }

        viewModel.progressState.observeValue(viewLifecycleOwner) {
            progress.isVisible = it
        }

        cookerAvatarIv.setDebounceOnClickListener {
            clickOnAddPhoto()
        }

        saveBtn.setDebounceOnClickListener {
            viewModel.onSaveBtnClicked()
        }
    }

    private fun clickOnAddPhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"

        try {
            val chooserIntent = Intent.createChooser(intent, "Выберите фотографию")
            startActivityForResult(chooserIntent, PHOTO_GALLERY_REQUEST_CODE)
        } catch (e: android.content.ActivityNotFoundException) {
            DialogUtils.showDialog(
                TAG,
                childFragmentManager,
                getString(R.string.file_chooser_not_found),
                getString(R.string.btn_ok)
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            val unmaskedRequestCode = requestCode.and(0x0000ffff)
            if (unmaskedRequestCode == PHOTO_GALLERY_REQUEST_CODE) {
                var file: File? = null
                data.data?.let { uri ->
                    val mime = activity?.contentResolver?.getType(uri)
                    val extension = if (mime == null) null else
                        MimeTypeMap.getSingleton().getExtensionFromMimeType(mime)
                    try {
                        activity?.let { activity ->
                            val photoBitmap =
                                MediaStore.Images.Media.getBitmap(activity.contentResolver, uri)

                            val byteArrayOutputStream = ByteArrayOutputStream()
                            photoBitmap.compress(
                                Bitmap.CompressFormat.JPEG,
                                IMAGE_QUALITY,
                                byteArrayOutputStream
                            )
                            file = File.createTempFile(
                                TEMP_FILE_PREFIX,
                                extension,
                                activity.cacheDir
                            )
                            val bitmapData = byteArrayOutputStream.toByteArray()
                            val fileOutputStream = FileOutputStream(file!!)
                            fileOutputStream.write(bitmapData)
                            fileOutputStream.flush()
                            fileOutputStream.close()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        file?.delete()
                        file = null
                    }
                    viewModel.onPhotoLoaded(file)
                }
            }
        } else if (resultCode != Activity.RESULT_CANCELED) {
            DialogUtils.showError(
                view,
                getString(R.string.file_selection_failed)
            )
        }
        super.onActivityResult(requestCode, resultCode, data)
        return
    }

}