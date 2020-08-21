package com.example.app.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val ADMIN = "/admin"

            // Imports batch
            const val IMPORT_USERS = "$API$VERSION/admin/imports/users"

            const val SEARCH_ISSUES = "$API$VERSION/issues"
            const val CREATE_ISSUE = "$API$VERSION/issues"
            const val FIND_ISSUE = "$API$VERSION/issues/{id}"
            const val UPDATE_ISSUE = "$API$VERSION/issues/{id}"
            const val DELETE_ISSUE = "$API$VERSION/issues/{id}"
        }
    }
}
