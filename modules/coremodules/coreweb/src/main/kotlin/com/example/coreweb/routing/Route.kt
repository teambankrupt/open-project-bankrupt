package com.example.coreweb.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val ADMIN = "/admin"

            // Address API's
            const val SEARCH_ADDRESSES = "$API$VERSION/addresses"
            const val CREATE_ADDRESSES = "$API$VERSION/addresses"
            const val FIND_ADDRESSES = "$API$VERSION/addresses/{id}"
            const val UPDATE_ADDRESSES = "$API$VERSION/addresses/{id}"
            const val DELETE_ADDRESSES = "$API$VERSION/addresses/{id}"

            // Division API's
            const val SEARCH_DIVISION = "$API$VERSION/divisions"
            const val CREATE_DIVISION = "$API$VERSION/divisions"
            const val FIND_DIVISION = "$API$VERSION/divisions/{id}"
            const val UPDATE_DIVISION = "$API$VERSION/divisions/{id}"
            const val DELETE_DIVISION = "$API$VERSION/divisions/{id}"

            // District API's
            const val SEARCH_DISTRICT = "$API$VERSION/districts"
            const val CREATE_DISTRICT = "$API$VERSION/districts"
            const val FIND_DISTRICT = "$API$VERSION/districts/{id}"
            const val UPDATE_DISTRICT = "$API$VERSION/districts/{id}"
            const val DELETE_DISTRICT = "$API$VERSION/districts/{id}"

            // Upazila API's
            const val SEARCH_UPAZILA = "$API$VERSION/upazilas"
            const val CREATE_UPAZILA = "$API$VERSION/upazilas"
            const val FIND_UPAZILA = "$API$VERSION/upazilas/{id}"
            const val UPDATE_UPAZILA = "$API$VERSION/upazilas/{id}"
            const val DELETE_UPAZILA = "$API$VERSION/upazilas/{id}"

            // Union API's
            const val SEARCH_UNION = "$API$VERSION/unions"
            const val CREATE_UNION = "$API$VERSION/unions"
            const val FIND_UNION = "$API$VERSION/unions/{id}"
            const val UPDATE_UNION = "$API$VERSION/unions/{id}"
            const val DELETE_UNION = "$API$VERSION/unions/{id}"

            // Village API's
            const val SEARCH_VILLAGES = "$API$VERSION/villages"
            const val CREATE_VILLAGES = "$API$VERSION/villages"
            const val FIND_VILLAGES = "$API$VERSION/villages/{id}"
            const val UPDATE_VILLAGES = "$API$VERSION/villages/{id}"
            const val DELETE_VILLAGES = "$API$VERSION/villages/{id}"

        }
    }
}
