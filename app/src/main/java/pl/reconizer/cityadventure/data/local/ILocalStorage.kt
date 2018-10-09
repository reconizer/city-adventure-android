package pl.reconizer.cityadventure.data.local

interface ILocalStorage {

    operator fun set(key: String, value: String?)

    operator fun get(key: String): String?

    fun remove(key: String)

}