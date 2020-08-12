package com.example.coreweb.domains.address.models.mappers

import com.example.coreweb.domains.address.models.dto.AddressDto
import com.example.coreweb.domains.address.models.entities.Address
import com.example.coreweb.domains.address.models.entities.LatLng
import com.example.coreweb.domains.address.services.*
import com.example.common.exceptions.notfound.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AddressMapper @Autowired constructor(
        private val divisionService: DivisionService,
        private val districtService: DistrictService,
        private val upazilaService: UpazilaService,
        private val unionService: UnionService,
        private val villageService: VillageService
) {

    fun map(address: Address): AddressDto {
        val dto = AddressDto()
        dto.id = address.id
        dto.divisionId = address.division?.id
        dto.districtId = address.district?.id
        dto.upazilaId = address.upazila?.id
        dto.unionId = address.union?.id
        dto.villageId = address.village?.id
        dto.lat = address.latLng?.latitude
        dto.lng = address.latLng?.longitude
        dto.createdAt = address.createdAt
        dto.updatedAt = address.updatedAt
        return dto
    }

    fun map(addressDto: AddressDto, address: Address?): Address {
        var addr = address
        if (addr == null) addr = Address()
        addr.division = addressDto.divisionId?.let { this.divisionService.find(it).orElseThrow { NotFoundException("Could not find division with id: " + addr.division?.id) } }
        addr.district = addressDto.districtId?.let { this.districtService.find(it).orElseThrow { NotFoundException("Could not find district with id: " + addr.district?.id) } }
        addr.upazila = addressDto.upazilaId?.let { this.upazilaService.find(it).orElseThrow { NotFoundException("Could not find upazila with id: " + addr.upazila?.id) } }
        addr.union = addressDto.unionId?.let { this.unionService.find(it).orElseThrow { NotFoundException("Could not find union with id: " + addr.union?.id) } }
        addr.village = addressDto.villageId?.let { this.villageService.find(it).orElseThrow { NotFoundException("Could not find village with id: " + addr.village?.id) } }
        if (addr.latLng == null) addr.latLng = LatLng()
        addr.latLng!!.latitude = addressDto.lat ?: addr.latLng!!.latitude
        addr.latLng!!.longitude = addressDto.lng ?: addr.latLng!!.longitude
        return addr
    }

}
