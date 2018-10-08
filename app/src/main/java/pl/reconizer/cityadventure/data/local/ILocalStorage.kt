package pl.reconizer.cityadventure.data.local

interface ILocalStorage {

    fun save(key: String, value: String?)

    fun get(key: String): String?

    fun remove(key: String)

}