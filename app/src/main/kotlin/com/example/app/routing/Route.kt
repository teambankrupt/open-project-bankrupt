package com.example.app.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val ADMIN = "/admin"

            // Imports batch
            const val IMPORT_USERS = "$API$VERSION$ADMIN/imports/users"
            const val WEB_RELOAD_APPLICATION_CONTEXT = "$ADMIN/reload-application-context"
            const val WEB_SHUTDOWN_APPLICATION_CONTEXT = "$ADMIN/shutdown-application-context"
        }
    }
}
