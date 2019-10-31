package pl.reconizer.unfold.common.extensions

import com.google.gson.Gson

inline fun <reified T> Gson.fromJson(json: String?) = this.fromJson(json, T::class.java)