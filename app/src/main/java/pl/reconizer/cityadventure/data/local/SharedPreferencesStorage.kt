package pl.reconizer.cityadventure.data.local

import android.content.SharedPreferences

class SharedPreferencesStorage(private val sharedPreferences: SharedPreferences) : ILocalStorage {

    override fun save(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).commit()
    }

    override fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun remove(key: String) {
        save(key, null)
    }
}