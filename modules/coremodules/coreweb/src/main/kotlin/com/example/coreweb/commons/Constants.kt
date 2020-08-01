package com.example.coreweb.commons

class Constants {

    class Swagger {
        companion object {

            /**
             * SWAGGER CONSTANTS
             */
            const val REST_API = "REST API"

            // Controller class name
            const val ADDRESS = "Addresses"
            const val DIVISION = "Divisions"
            const val DISTRICT = "Districts"
            const val UPAZILA = "Upazilas"
            const val UNION = "Union"
            const val VILLAGE = "Village"
            const val BASIC_APIS = "Basic Api's"
            const val GLOBAL_API = "Global Api's (Super Admin)"
            const val USERS_ADMIN = "Users (Super Admin)"
            const val ROLES_ADMIN = "Roles (Super Admin)"
            const val PRIVILEGES_ADMIN = "Privileges (Super Admin)"
            const val CHAT_ROOMS = "Chat Rooms"

            // API operation messages
            const val POST_MSG = "Create a new "
            const val PATCH_MSG = "Update an existing "
            const val GET_ALL_MSG = "Get all list of "
            const val SEARCH_ALL_MSG = "Search all list of "
            const val DELETE_MSG = "Delete "
            const val GET_MSG = "Get "
            const val BY_ID_MSG = " by Id"

            const val VERIFY_PHONE = "Verify phone number before registration/Obtain OTP with this endpoint"
            const val REGISTER = "Register user with otp and user information"
            const val CHANGE_PASSWORD = "Change password"
            const val VERIFY_RESET_PASSWORD = "Verify before reseting password/Obtain OTP"
            const val RESET_PASSWORD = "Reset password with OTP"
            const val BASIC_API_DETAILS = "Basic operations like Register/Change or Reset Password/Verify Phones and Email Addresses"
            const val GLOBAL_API_DETAILS = "Global operations like reloading app, destroying the universe etc."
            const val USERS_ADMIN_API_DETAILS = "Admin can manipulate users with these api's"
            const val ROLES_ADMIN_API_DETAILS = "Admin can manipulate user roles with these api's"
            const val PRIVILEGES_ADMIN_API_DETAILS = "Admin can manipulate privileges for roles with these api's"
            const val RELOAD_CONTEXT = "Reload Application Context/Restart App"
            const val CHAT_ROOMS_DETAILS = "Sample Chat Rooms for messaging feature"
        }
    }

}