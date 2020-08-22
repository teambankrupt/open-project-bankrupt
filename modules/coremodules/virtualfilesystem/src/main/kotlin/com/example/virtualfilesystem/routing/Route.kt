package com.example.virtualfilesystem.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val ADMIN = "/admin"


            // Virtual Folders
            const val ADMIN_SEARCH_VFOLDERS = "$ADMIN/vfolders"
            const val ADMIN_CREATE_VFOLDER_PAGE = "$ADMIN/vfolders/create"
            const val ADMIN_CREATE_VFOLDER = "$ADMIN/vfolders"
            const val ADMIN_FIND_VFOLDER = "$ADMIN/vfolders/{id}"
            const val ADMIN_UPDATE_VFOLDER_PAGE = "$ADMIN/vfolders/{id}/update"
            const val ADMIN_UPDATE_VFOLDER = "$ADMIN/vfolders/{id}"
            const val ADMIN_DELETE_VFOLDER = "$ADMIN/vfolders/{id}/delete"

            const val SEARCH_VFOLDERS = "$API$VERSION/vfolders"
            const val CREATE_VFOLDER = "$API$VERSION/vfolders"
            const val FIND_VFOLDER = "$API$VERSION/vfolders/{id}"
            const val FIND_FILES_IN_VFOLDER = "$API$VERSION/vfolders/{folder_id}/files"
            const val UPDATE_VFOLDER = "$API$VERSION/vfolders/{id}"
            const val DELETE_VFOLDER = "$API$VERSION/vfolders/{id}"

            // File Extensions
            const val SEARCH_VEXTENSIONS = "$API$VERSION/vextensions"
            const val CREATE_VEXTENSION = "$API$VERSION/vextensions"
            const val FIND_VEXTENSION = "$API$VERSION/vextensions/{id}"
            const val UPDATE_VEXTENSION = "$API$VERSION/vextensions/{id}"
            const val DELETE_VEXTENSION = "$API$VERSION/vextensions/{id}"

        }
    }
}
