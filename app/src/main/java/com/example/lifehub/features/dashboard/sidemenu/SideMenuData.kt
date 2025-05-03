package com.example.lifehub.features.dashboard.sidemenu

data class SideMenuData(
    val profilePicture: ByteArray?,
    val backGroundPicture: ByteArray?,
    val items: List<SideMenuItem>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SideMenuData

        if (!profilePicture.contentEquals(other.profilePicture)) return false
        if (!backGroundPicture.contentEquals(other.backGroundPicture)) return false
        if (items != other.items) return false

        return true
    }

    override fun hashCode(): Int {
        var result = profilePicture?.contentHashCode() ?: 0
        result = 31 * result + (backGroundPicture?.contentHashCode() ?: 0)
        result = 31 * result + items.hashCode()
        return result
    }
}