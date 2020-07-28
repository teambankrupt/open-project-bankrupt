package com.example.coreweb.domains.address.models.entities

import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "addr_addresses")
class Address : BaseEntity() {

    @ManyToOne
    @JoinColumn(name = "division_id")
    var division: Division? = null

    @ManyToOne
    @JoinColumn(name = "district_id")
    var district: District? = null

    @ManyToOne
    @JoinColumn(name = "upazila_id")
    var upazila: Upazila? = null

    @ManyToOne
    @JoinColumn(name = "union_id")
    var union: Union? = null

    @ManyToOne
    @JoinColumn(name = "village_id")
    var village: Village? = null

    @Embedded
    var latLng: LatLng? = null

}
