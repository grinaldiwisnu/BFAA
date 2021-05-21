package com.example.githubuserapp.views.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.example.githubuserapp.R
import com.example.githubuserapp.utils.Notification

class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var reminderPreference: SwitchPreferenceCompat
    private lateinit var notification: Notification
    private lateinit var reminder: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Settings"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey)

        notification = Notification()

        initNotification()
        initSharedPreference()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminder){
            if (sharedPreferences != null){
                reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
            }
        }

        val state: Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)

        setNotif(state)
    }

    private fun initNotification(){
        reminder = "Notifikasi Harian"
        reminderPreference = findPreference<SwitchPreferenceCompat>(reminder) as SwitchPreferenceCompat
    }

    private fun initSharedPreference(){
        val sharedPreferences = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sharedPreferences.getBoolean(reminder, false)
    }

    private fun setNotif(state: Boolean){
        if (state){
            context?.let {
                notification.setNotification(it)
            }
        } else {
            context?.let {
                notification.cancelAlarm(it)
            }
        }
    }

}