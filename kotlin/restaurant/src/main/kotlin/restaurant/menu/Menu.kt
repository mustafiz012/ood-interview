package restaurant.menu

import java.util.Collections

class Menu {
    private val menuItems: MutableMap<String, MenuItem> = mutableMapOf()

    fun addItem(item: MenuItem) {
        menuItems[item.name] = item
    }

    fun getItem(name: String): MenuItem? = menuItems[name]

    val items: Map<String, MenuItem>
        get() = Collections.unmodifiableMap(menuItems)
}
