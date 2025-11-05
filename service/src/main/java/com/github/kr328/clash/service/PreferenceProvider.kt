package com.github.kr328.clash.service

import android.content.Context
import android.content.SharedPreferences
import com.github.kr328.clash.common.constants.Authorities
import rikka.preference.MultiProcessPreference
import rikka.preference.PreferenceProvider

// 用于多进程共享 SharedPreferences
// 在同一进程内使用普通SharedPreferences
// 在跨进程使用MultiProcessPreference，如UI 进程访问 Service 数据
class PreferenceProvider : PreferenceProvider() {
    override fun onCreatePreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }

    companion object {
        private const val FILE_NAME = "service"

        fun createSharedPreferencesFromContext(context: Context): SharedPreferences {
            return when (context) {
                is BaseService, is TunService ->
                    context.getSharedPreferences(
                        FILE_NAME,
                        Context.MODE_PRIVATE
                    )
                else ->
                    MultiProcessPreference(
                        context,
                        Authorities.SETTINGS_PROVIDER
                    )
            }
        }
    }
}