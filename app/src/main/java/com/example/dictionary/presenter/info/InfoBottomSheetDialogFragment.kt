//package com.example.dictionary.presenter.info
//import android.content.DialogInterface
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import by.kirich1409.viewbindingdelegate.viewBinding
//import com.example.dictionary.R
//import com.example.dictionary.data.model.Dictionary
//import com.example.dictionary.data.model.Stared
//import com.example.dictionary.databinding.ScreenInfoBinding
//import com.example.dictionary.domain.AppRepositoryImpl
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//
//class InfoBottomSheetDialogFragment(val data: Dictionary,val onChange: (()->Unit)) : BottomSheetDialogFragment() {
//    val binding : ScreenInfoBinding by viewBinding(ScreenInfoBinding::bind)
//    val appRepository = AppRepositoryImpl.getAppRepository()
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.screen_info, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        appRepository.getStared(data.id) {
//            binding.apply {
//                englishWordValue.text = data.english
//                textViewTypeValue.text = data.type
//                textViewTranscriptValue.text = data.transcript
//                textViewUzbekTranslationValue.text = data.uzbek
//                textViewCountableValue.text = data.countable
//                if (it != null && it.isStared != null) {
//                    textViewIsFavourite.isChecked = it.isStared == 1
//                }
//                textViewIsFavourite.setOnCheckedChangeListener { buttonView, isChecked ->
//                    appRepository.insertStared(Stared(data.id, if (isChecked) 1 else 0),{})
//
//                }
//            }
//
//        }
//    }
//    override fun onDismiss(dialog: DialogInterface) {
//        super.onDismiss(dialog)
//        onChange.invoke()
//    }
//}
