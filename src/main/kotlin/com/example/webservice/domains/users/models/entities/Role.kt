package com.example.webservice.domains.users.models.entities

import com.example.webservice.domains.common.models.entities.base.BaseEntity
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role : BaseEntity() {
    @Column(nullable = false, unique = true)
    var name: String? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "roles_privileges", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
    var privileges: List<Privilege>? = null

    enum class ERole{
        Admin, User
    }

    fun isAdmin(): Boolean {
        return privileges != null && this.privileges!!.stream().anyMatch { Privilege.Privileges.ADMINISTRATION.name.equals(it.name) }
    }

    fun isSameAs(role: Role): Boolean {
        return role.id == this.id
    }


}
