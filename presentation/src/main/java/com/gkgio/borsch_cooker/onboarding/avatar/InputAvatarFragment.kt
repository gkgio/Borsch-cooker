package com.gkgio.borsch_cooker.onboarding.avatar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import com.gkgio.borsch_cooker.utils.DialogUtils
import kotlinx.android.synthetic.main.fragment_input_avatar.*
import pl.aprilapps.easyphotopicker.*


class InputAvatarFragment : BaseFragment<InputAvatarViewModel>() {

    companion object {
        const val TAG = "InputAvatarFragment"
        const val PHOTO_GALLERY_REQUEST_CODE = 1
        private const val IMAGE_QUALITY = 40
        private const val TEMP_FILE_PREFIX = "image"
    }

    lateinit var easyImage: EasyImage

    override fun getLayoutId(): Int = R.layout.fragment_input_avatar

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.inputAvatarViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        easyImage = EasyImage.Builder(requireContext())
            .setChooserTitle("Выберите фотографию")
            .setCopyImagesToPublicGalleryFolder(true)
            .setChooserType(ChooserType.CAMERA_AND_GALLERY)
            .allowMultiple(false)
            .build()

        viewModel.photoUri.observeValue(viewLifecycleOwner) {
            Glide.with(cookerAvatarIv)
                .load(it)
                .withFade()
                .withCenterCropOval()
                .placeholderByDrawable(R.drawable.ic_photo)
                .apply(RequestOptions.circleCropTransform())
                .into(cookerAvatarIv)
        }

        iconBack.setDebounceOnClickListener {
            viewModel.onBackClick()
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
        if (requireContext().checkWriteStorageGranted()) {
            openGallery()
        } else {
            requestWriteStoragePermission()
                .subscribe {
                    openGallery()
                }.addDisposable()
        }
    }

    private fun openGallery() {
        easyImage.openGallery(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val unmaskedRequestCode = requestCode.and(0x0000ffff)

        easyImage.handleActivityResult(
            unmaskedRequestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {

                override fun onMediaFilesPicked(imageFiles: Array<MediaFile>, source: MediaSource) {
                    if (imageFiles.isNotEmpty()) {
                        viewModel.onPhotoLoaded(imageFiles[0].file)
                    }
                }

                override fun onImagePickerError(error: Throwable, source: MediaSource) {
                    DialogUtils.showError(
                        view,
                        getString(R.string.file_selection_failed)
                    )
                }

                override fun onCanceled(source: MediaSource) {

                }
            })
    }

}