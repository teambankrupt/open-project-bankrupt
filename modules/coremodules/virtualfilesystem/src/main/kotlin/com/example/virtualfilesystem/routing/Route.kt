package com.example.virtualfilesystem.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val ADMIN = "/admin"

            const val SEARCH_VFOLDERS = "$API$VERSION/vfolders"
            const val CREATE_VFOLDER = "$API$VERSION/vfolders"
            const val FIND_VFOLDER = "$API$VERSION/vfolders/{id}"
            const val UPDATE_VFOLDER = "$API$VERSION/vfolders/{id}"
            const val DELETE_VFOLDER = "$API$VERSION/vfolders/{id}"

        }
    }
}
