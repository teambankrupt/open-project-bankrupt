package com.example.messaging.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val ADMIN = "/admin"

            /**
             * WEBSOCKET - CHAT
             */
            // Chat Rooms
            const val SEARCH_CHATROOMS = "$API$VERSION/chat/chatrooms"
            const val CREATE_CHATROOM = "$API$VERSION/chat/chatrooms"
            const val FIND_CHATROOM = "$API$VERSION/chat/chatrooms/{id}"
            const val UPDATE_CHATROOM = "$API$VERSION/chat/chatrooms/{id}"
            const val DELETE_CHATROOM = "$API$VERSION/chat/chatrooms/{id}"

            // Chat
            const val CHAT = "/chat"
        }
    }
}