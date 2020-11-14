package com.gkgio.borsch_cooker.profile

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.profileViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.nonNullObserve(viewLifecycleOwner) { state ->
            state.cooker?.let { cooker ->
                with(cooker) {

                    avatarUrl?.let {
                        Glide.with(avatarIv)
                            .load(it)
                            .withFade()
                            .withCenterCropOval()
                            .placeholderByDrawable(R.drawable.ic_photo)
                            .apply(RequestOptions.circleCropTransform())
                            .into(avatarIv)
                    }

                    val name = "$firstName $lastName"
                    nameTv.text = name

                    phoneTv.text = phone
                }
            }
        }

        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        avatarIv.setDebounceOnClickListener {
            viewModel.onChangeAvatarClicked()
        }

        changeAvatarTv.setDebounceOnClickListener {
            viewModel.onChangeAvatarClicked()
        }

        phoneTv.setDebounceOnClickListener {
            viewModel.onChangePhoneClicked()
        }

        editPhoneTv.setDebounceOnClickListener {
            viewModel.onChangePhoneClicked()
        }

        nameTv.setDebounceOnClickListener {
            viewModel.onChangeNameClicked()
        }

        editNameTv.setDebounceOnClickListener {
            viewModel.onChangeNameClicked()
        }

        addressTv.setDebounceOnClickListener {
            viewModel.onChangeAddressClicked()
        }

        editAddressTv.setDebounceOnClickListener {
            viewModel.onChangeAddressClicked()
        }
    }
}