package ru.gozerov.lyceum8.activity

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.gozerov.lyceum8.R
import ru.gozerov.lyceum8.utils.OnStatusBarUpdate
import ru.gozerov.lyceum8.utils.RestConstants.ACTION_UPDATE_APP
import ru.gozerov.lyceum8.utils.RestConstants.KEY_UPDATE_APP
import ru.gozerov.lyceum8.utils.StatusBarStyle
import ru.gozerov.lyceum8.utils.sharedPrefs

class MainActivity : AppCompatActivity(), OnStatusBarUpdate {

    private var hasNotificationPermissionGranted = false

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermissionGranted = isGranted
            if (!isGranted) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                            showNotificationPermissionRationale()
                        } else {
                            showSettingDialog()
                        }
                    }
            }
        }

    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogStyle)
            .setTitle(getString(R.string.alert))
            .setMessage(getString(R.string.please_allow_notifications))
            .setPositiveButton(getString(R.string.allow)) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showNotificationPermissionRationale() {

        MaterialAlertDialogBuilder(this, R.style.AlertDialogStyle)
            .setTitle(getString(R.string.alert))
            .setMessage(R.string.please_allow_notifications)
            .setPositiveButton(getString(R.string.allow)) { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        update(StatusBarStyle.DEFAULT)
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            hasNotificationPermissionGranted = true
        }
    }

    override fun update(statusBarStyle: StatusBarStyle) {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this,android.R.color.transparent)
        when(statusBarStyle) {
            StatusBarStyle.DEFAULT -> {
                val background = ContextCompat.getDrawable(this, R.drawable.status_bar_background)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.teal_800)
                window.setBackgroundDrawable(background)
            }
            StatusBarStyle.GALLERY -> {
                val background = ContextCompat.getDrawable(this, R.color.black)
                window.navigationBarColor = ContextCompat.getColor(this, R.color.black)
                window.setBackgroundDrawable(background)
            }
        }
    }

    override fun onResume() {
        findUpdateAppPush(applicationContext.sharedPrefs)
        super.onResume()
    }

    private fun findUpdateAppPush(sp: SharedPreferences) {
        val action = sp.getString(KEY_UPDATE_APP, "")
        if (action == ACTION_UPDATE_APP) {
            Handler(Looper.getMainLooper()).postDelayed({
                findNavController(R.id.fragmentContainer).navigate(R.id.updateAppDialog)
                sp.edit().remove(KEY_UPDATE_APP).apply()
            }, 100)
        }
    }

}