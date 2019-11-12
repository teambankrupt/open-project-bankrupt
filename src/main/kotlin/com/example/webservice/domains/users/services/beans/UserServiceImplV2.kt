package com.example.webservice.domains.users.services.beans

import com.example.webservice.commons.utils.DateUtil
import com.example.webservice.commons.utils.SessionIdentifierGenerator
import com.example.webservice.domains.common.services.MailService
import com.example.webservice.domains.common.services.SmsService
import com.example.webservice.domains.firebase.models.dto.NotificationData
import com.example.webservice.domains.firebase.models.dto.PushNotification
import com.example.webservice.domains.firebase.services.NotificationService
import com.example.webservice.domains.users.models.entities.AcValidationToken
import com.example.webservice.domains.users.models.entities.User
import com.example.webservice.domains.users.repositories.UserRepository
import com.example.webservice.domains.users.repositories.UserRepositoryV2
import com.example.webservice.domains.users.services.AcValidationTokenService
import com.example.webservice.domains.users.services.UserServiceV2
import com.example.webservice.exceptions.exists.UserAlreadyExistsException
import com.example.webservice.exceptions.forbidden.ForbiddenException
import com.example.webservice.exceptions.invalid.InvalidException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*


@Service
class UserServiceImplV2 @Autowired constructor(
        val userRepository: UserRepositoryV2,
        val acValidationTokenService: AcValidationTokenService,
        val notificationService: NotificationService,
        val smsService: SmsService,
        val mailService: MailService
) : UserServiceV2 {

    @Value("\${auth.method}")
    lateinit var authMethod: String

    @Value("\${applicationName}")
    lateinit var applicationName: String

    override fun register(token: String, user: User): User {
        if (!this.acValidationTokenService.isTokenValid(token))
            throw InvalidException("Token invalid!")
        val acValidationToken = this.acValidationTokenService.findByToken(token)

        val username = if(authMethod=="phone") user.phone else user.email
        if (username != acValidationToken.username) throw InvalidException("Token invalid!")

        val savedUser = this.userRepository.save(user)
        acValidationToken.isTokenValid = false
        acValidationToken.reason = "Registration/Otp Confirmation"
        this.acValidationTokenService.save(acValidationToken)
        this.notifyAdmin(savedUser)
        return savedUser
    }


    override fun requireAccountValidationByOTP(phoneOrEmail: String, tokenValidUntil: Date): Boolean {
        val user = if (this.authMethod == "phone") this.userRepository.findByPhone(phoneOrEmail)
        else this.userRepository.findByEmail(phoneOrEmail)
        if (user.isPresent) throw UserAlreadyExistsException("User already registered with this ${authMethod}!")

        if (!this.acValidationTokenService.canGetOTP(phoneOrEmail))
            throw ForbiddenException("Already sent an OTP. Please try agin in two minutes!")
        var acValidationToken = AcValidationToken()
        acValidationToken.token = SessionIdentifierGenerator.generateOTP().toString()
        acValidationToken.isTokenValid = true
        acValidationToken.username = phoneOrEmail
        acValidationToken.tokenValidUntil = tokenValidUntil
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


    private fun notifyAdmin(user: User) {
        val data = NotificationData()
        data.title = "New Registration -:- " + user.name
        val description = "Username: " + user.username + ", On: " + DateUtil.getReadableDateTime(Date())
        val brief = description.substring(0, Math.min(description.length, 100))
        data.message = brief
        data.type = PushNotification.Type.ADMIN_NOTIFICATIONS.value

        val notification = PushNotification(null, data)
        notification.to = "/topics/adminnotifications"
        this.notificationService.sendNotification(notification)
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

}
