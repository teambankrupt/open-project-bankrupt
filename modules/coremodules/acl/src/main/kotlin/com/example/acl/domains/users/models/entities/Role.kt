//package com.example.application.domains.users.models.entities
//
//import com.example.coreweb.domains.base.entities.BaseEntity
//import org.hibernate.annotations.Fetch
//import org.hibernate.annotations.FetchMode
//
//import javax.persistence.*
//
//@Entity
//@Table(name = "roles")
//class Role : BaseEntity() {
//    @Column(nullable = false, unique = true)
//    lateinit var name: String
//
//    @Column(name = "description")
//    var description: String? = null
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinTable(name = "roles_privileges", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
//    var privileges: List<Privilege>? = null
//
//    @Column(nullable = false)
//    var restricted: Boolean = true
//
//    enum class ERole {
//        Admin, User
//    }
//
//    fun isAdmin(): Boolean {
//        return privileges != null && this.privileges!!.stream().anyMatch { Privileges.ADMINISTRATION.name == it.name }
//    }
//
//    fun isSameAs(role: Role): Boolean {
//        return role.id == this.id
//    }
//
//    public fun hasPrivilege(privilegeId: Long): Boolean {
//        return this.privileges?.firstOrNull { it.id == privilegeId } != null
//    }
//
//}
