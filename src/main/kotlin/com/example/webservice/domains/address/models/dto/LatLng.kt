package com.example.webservice.domains.address.models.dto

import javax.persistence.Embeddable

@Embeddable
class LatLng(
        var lat: Double? = null,
        var lng: Double? = null
)
