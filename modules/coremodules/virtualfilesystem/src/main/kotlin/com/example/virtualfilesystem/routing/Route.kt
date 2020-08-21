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
            const val FIND_FILES_IN_VFOLDER = "$API$VERSION/vfolders/{folder_id}/files"
            const val UPDATE_VFOLDER = "$API$VERSION/vfolders/{id}"
            const val DELETE_VFOLDER = "$API$VERSION/vfolders/{id}"

            const val SEARCH_VEXTENSIONS = "$API$VERSION/vextensions"
            const val CREATE_VEXTENSION = "$API$VERSION/vextensions"
            const val FIND_VEXTENSION = "$API$VERSION/vextensions/{id}"
            const val UPDATE_VEXTENSION = "$API$VERSION/vextensions/{id}"
            const val DELETE_VEXTENSION = "$API$VERSION/vextensions/{id}"
        }
    }
}
