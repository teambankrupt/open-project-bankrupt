package com.example.application.domains.address.models.entities

import com.example.application.domains.common.base.models.entities.BaseEntity
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "p_addresses")
class Address : BaseEntity() {

    @ManyToOne
    var division: Division? = null

    @ManyToOne
    var district: District? = null

    @ManyToOne
    var upazila: Upazila? = null

    @ManyToOne
    var union: Union? = null

    @ManyToOne
    var village: Village? = null

    @Embedded
    var latLng: LatLng? = null

}
