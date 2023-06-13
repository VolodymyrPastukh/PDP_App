package pastukh.vova.data.dataSource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesDataSource(val _prefs: SharedPreferences, val gson: Gson) {
    companion object {
        const val SELECTED_EVENTS = "selected_events"
    }

    fun <T> putData(key: String, value: T){
        _prefs.edit { putString(key, gson.toJson(value)) }
    }


    inline fun <reified T> getData(key: String): T?  {
        val value = _prefs.getString(key, "")
        if(value.isNullOrEmpty()) return null
        return gson.fromJsonString<T>(value)
    }
}

inline fun <reified T> Gson.fromJsonString(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)