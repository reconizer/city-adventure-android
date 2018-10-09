package pl.reconizer.cityadventure.data.local

import android.content.Context

class SharedPreferencesStorage(context: Context) : ILocalStorage {

    private val sharedPreferences = context.getSharedPreferences("LOCAL_STORAGE", Context.MODE_PRIVATE)

    override fun set(key: String, value: String?) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun get(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun remove(key: String) {
        set(key, null)
    }
}