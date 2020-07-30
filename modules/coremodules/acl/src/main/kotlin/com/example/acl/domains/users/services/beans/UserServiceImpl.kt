package com.example.acl.domains.users.services.beans

import com.example.acl.domains.users.models.entities.AcValidationToken
import com.example.acl.domains.users.repositories.UserRepository
import com.example.acl.domains.users.services.AcValidationTokenService
import com.example.acl.domains.users.services.RoleService
import com.example.acl.domains.users.services.UserService
import com.example.auth.config.security.SecurityContext
import com.example.auth.entities.User
import com.example.auth.enums.Roles
import com.example.auth.utils.PasswordUtil
import com.example.common.exceptions.exists.UserAlreadyExistsException
import com.example.common.exceptions.forbidden.ForbiddenException
import com.example.common.exceptions.invalid.InvalidException
import com.example.common.exceptions.notfound.NotFoundException
import com.example.common.exceptions.notfound.UserNotFoundException
import com.example.common.exceptions.unknown.UnknownException
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.SessionIdentifierGenerator
import com.example.common.utils.Validator
import com.example.coreweb.domains.mail.services.MailService
import com.example.coreweb.domains.sms.services.SmsService
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
@PropertySource("classpath:security.properties")
open class UserServiceImpl @Autowired constructor(
        private val userRepository: UserRepository,
        private val acValidationTokenService: AcValidationTokenService,
        private val smsService: SmsService,
        private val mailService: MailService,
        private val roleService: RoleService
) : UserService {

    @Value("\${auth.method}")
    lateinit var authMethod: String

    @Value("\${applicationName}")
    lateinit var applicationName: String

    @Value("\${token.validity}")
    lateinit var tokenValidity: String

    override fun search(query: String, role: String?, page: Int, size: Int): Page<User> {
        val r = role?.let { this.roleService.find(it).orElseThrow { ExceptionUtil.notFound("Could not find role with name: $role") } }
        return this.userRepository.search(query, r, PageAttr.getPageRequest(page, size))
    }

    override fun search(query: String, page: Int, size: Int): Page<User> {
        return this.userRepository.search(query, PageAttr.getPageRequest(page, size))
    }

    override fun findAll(page: Int): Page<User> {
        return this.userRepository.findAll(PageAttr.getPageRequest(page))
    }

    override fun findByRole(role: String, page: Int): Page<User> {
        return this.userRepository.findByRolesName(role, PageAttr.getPageRequest(page))
    }

    override fun findByRole(role: String): List<User> {
        return this.userRepository.findByRolesName(role)
    }


    override fun save(entity: User): User {
        return this.userRepository.save(entity)
    }

    override fun register(token: String, user: User): User {
        if (!this.acValidationTokenService.isTokenValid(token))
            throw InvalidException("Token invalid!")
        val acValidationToken = this.acValidationTokenService.findByToken(token)

        val username = if (authMethod == "phone") user.phone else user.email
        if (username != acValidationToken.username) throw InvalidException("Token invalid!")

        val savedUser = this.save(user)
        acValidationToken.isTokenValid = false
        acValidationToken.reason = "Registration/Otp Confirmation"
        this.acValidationTokenService.save(acValidationToken)
        return savedUser
    }


    override fun requireAccountValidationByOTP(phoneOrEmail: String, tokenValidUntil: Date): Boolean {
        val isPhone = this.authMethod == "phone"
        this.validateIdentity(isPhone, phoneOrEmail)

        val user = if (isPhone) this.userRepository.findByPhone(phoneOrEmail)
        else this.userRepository.findByEmail(phoneOrEmail)
        if (user.isPresent) throw UserAlreadyExistsException("User already registered with this ${authMethod}!")

        if (!this.acValidationTokenService.canGetOTP(phoneOrEmail))
            throw ForbiddenException("Already sent an OTP. Please try agin in two minutes!")
        var acValidationToken = AcValidationToken()
        acValidationToken.token = SessionIdentifierGenerator.generateOTP().toString()
        acValidationToken.isTokenValid = true
        acValidationToken.username = phoneOrEmail
        acValidationToken.tokenValidUntil = tokenValidUntil
        acValidationToken.reason = "User Registration"
        // save acvalidationtoken
        acValidationToken = this.acValidationTokenService.save(acValidationToken)
        val finalAcValidationToken = acValidationToken
        Thread {
            try {
                Thread.sleep((2 * 60 * 1000).toLong())
                finalAcValidationToken.isTokenValid = false
                acValidationTokenService.save(finalAcValidationToken)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }.start()

        // build confirmation link
        val tokenMessage = "Your " + this.applicationName + " token is: " + acValidationToken.token
        // send link by sms
        return if (this.authMethod == "phone") this.smsService.sendSms(phoneOrEmail, tokenMessage)
        else this.mailService.sendEmail(phoneOrEmail, this.applicationName + " Registration", tokenMessage)
    }

    private fun validateIdentity(phone: Boolean, phoneOrEmail: String) {
        if (phone) {
            if (!Validator.isValidPhoneNumber(phoneOrEmail))
                throw InvalidException("Phone number: $phoneOrEmail is invalid!")
        } else {
            if (!Validator.isValidEmail(phoneOrEmail))
                throw InvalidException("Email: $phoneOrEmail is invalid!")
        }

    }


    override fun find(id: Long): Optional<User> {
        return this.userRepository.findById(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val user = this.userRepository.find(id).orElseThrow { ExceptionUtil.notFound("User", id) }
            user.isDeleted = false
            this.userRepository.save(user)
            return
        }
        return this.userRepository.deleteById(id)
    }

    override fun findByUsername(username: String): Optional<User> {
        return this.userRepository.findByUsername(username)
    }

    override fun findByPhone(phone: String): Optional<User> {
        return this.userRepository.findByPhone(phone)
    }

    override fun findByEmail(email: String): Optional<User> {
        return this.userRepository.findByEmail(email)
    }


    override fun changePassword(id: Long, currentPassword: String, newPassword: String): User {
        var user: User = this.find(id).orElseThrow { UserNotFoundException("Could not find user with id  $id") }

        if (!PasswordUtil.matches(user.password, currentPassword))
            throw ForbiddenException("Password doesn't match")

        if (newPassword.length < 6) throw InvalidException("Password must be at least 6 characters!")
        user.password = PasswordUtil.encryptPassword(newPassword, PasswordUtil.EncType.BCRYPT_ENCODER, null)
        user = this.save(user)
        return user
    }

    override fun setPassword(id: Long, newPassword: String): User {
        val currentUser = SecurityContext.getCurrentUser()
        if (currentUser == null || !currentUser.isAdmin)
            throw ForbiddenException("You are not authorised to do this action.")

        val user: User = this.find(id).orElseThrow { throw UserNotFoundException("Could not find user with id $id") }

        if (newPassword.length < 6) throw InvalidException("Password must be at least 6 characters!")
        user.password = PasswordUtil.encryptPassword(newPassword, PasswordUtil.EncType.BCRYPT_ENCODER, null)
        return this.save(user)
    }

    override fun handlePasswordResetRequest(username: String) {
        val user = this.findByUsername(username).orElseThrow { NotFoundException("Could not find user with username: $username") }
        if (this.acValidationTokenService.isLimitExceeded(user))
            throw ForbiddenException("Limit exceeded!")

        val otp = SessionIdentifierGenerator.generateOTP()
        val message = "Your " + this.applicationName + " token is: " + otp
        val success = this.smsService.sendSms(user.username, message)
        // save validation token
        if (!success) throw UnknownException("Could not send SMS")

        val resetToken = AcValidationToken()
        resetToken.user = user
        resetToken.token = otp.toString()
        resetToken.isTokenValid = true
        resetToken.reason = "Password Reset (Initiated)"

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis() + Integer.parseInt(this.tokenValidity)
        resetToken.tokenValidUntil = calendar.time

        this.acValidationTokenService.save(resetToken)
    }

    override fun setRoles(id: Long, roleIds: List<Long>): User {
        val user = this.find(id).orElseThrow { NotFoundException("Could not find user with username: $id") }
        val isAdmin = user.isAdmin // check if user is admin
        val roles = this.roleService.findByIds(roleIds)
        user.roles = roles.filter { role -> !role.isAdmin }.toMutableList()
        if (isAdmin) {// set admin role explicitly after clearing roles
            val role = this.roleService.find(Roles.Admin.name).orElseThrow { NotFoundException("Admin role couldn't be set!") }
            user.roles.add(role)
        }
        return this.save(user)
    }

    @Transactional
    override fun resetPassword(username: String, token: String, password: String): User {
        if (password.length < 6)
            throw ForbiddenException("Password length should be at least 6")
        val acValidationToken = this.acValidationTokenService.findByToken(token)
        var user = acValidationToken.user
        if (username != user.username)
            throw ForbiddenException("You are not authorized to do this action!")
        user.password = PasswordUtil.encryptPassword(password, PasswordUtil.EncType.BCRYPT_ENCODER, null)
        acValidationToken.isTokenValid = false
        acValidationToken.reason = "Password Reset"
        user = this.save(user)
        acValidationToken.user = user
        this.acValidationTokenService.save(acValidationToken)
        return user
    }

    @Transactional
    override fun toggleAccess(userId: Long, enable: Boolean) {
        this.userRepository.toggleAccess(userId, enable)
    }

}
