package com.ntc.auth.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    var email: String? = null,

    @JvmField
    @field:Column(length = 255, nullable = false)
    final var password: String,

    @field:Column(length = 30, nullable = false)
    @field:Enumerated(value = EnumType.STRING)
    var role: Role = Role.BUYER
) : UserDetails {


    override fun getAuthorities(): Collection<out GrantedAuthority> {
        return listOf(role)
    }

    override fun getPassword(): String? {
        return password
    }


    override fun getUsername(): String {
        return email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    enum class Role : GrantedAuthority {
        BUYER, VENDOR_USER, ADMIN;


        override fun getAuthority(): String {
            return name
        }
    }


}