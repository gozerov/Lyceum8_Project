package ru.gozerov.lyceum8.screens.update_app

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.databinding.DialogUpdateAppBinding

class UpdateAppDialog: BottomSheetDialogFragment() {

    private lateinit var binding: DialogUpdateAppBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogUpdateAppBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener { this.dialog?.cancel() }

        binding.updateButton.setOnClickListener {

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_google_play)))
            startActivity(browserIntent)

            this.dialog?.cancel()


        }

        return binding.root
    }
}