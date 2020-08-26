# Spring Boot Featured Skeleton

It's a skeleton project with lots of features implemented by default.

## Environment Setup

[Environment Setup: First Run](https://github.com/sayemkcn/Spring-Boot-Featured-Skeleton/wiki/Environment-Setup:-First-Run)

## Module and CRUD Generator
### Example
Generate CRUD

`./scripts/gen.sh crud Issue app/src/main/kotlin/com/example/app/domains/`

Generate Module

`./scripts/gen.sh module IssueTracker modules/appmodules`

## Features At a Glance

### For Admin Panel
* Login
* Template configuration (Thymeleaf engine with layout dialect)
* Integrated (with thymeleaf engine, layout dialect) Opensource AdminBSBMaterialDesign theme for admin panel
* Spring Security Configuration (WebSecurity)

### For API's
* User Registration/Authentication
* Dynamic User Authorization Management (Roles &amp; Privileges)
* OAuth2 for securing API's
* Phone verification with OTP when registration
* Swagger configuration
* CORS configuration
* Promotion Management
* Profile CRUD

### Common
* Swagger Documentation
* Email configuration for sending Emails
* SMS service configuration for sending SMS
* Exception Handling
* Activity Logging
* Flood Control (Auto Block IP for multiple failed attemps)
* Firebase Push server configuration (Implemented on NotificationService)
* File Upload Configuration for storing files on FileSystem
* And lots of utility classes

## Extra
* Spring Batch Integration/Implementation
* Spring Websocket (STOMP) implementation (Chatroom)

<hr/>

# [See Documentation](https://github.com/sayemkcn/Spring-Boot-Featured-Skeleton/wiki)

<hr/>

Deployment:

Packaging
```mvn clean package -DskipTests -Denv=prod```
Here -Denv=prod comes from maven profiling on pom.xml file. This argument activates production profile and loads config from that profile.
use -Denv=dev or -Denv=local or create a new profile.

To deploy app on a cloud instance please look at my blog here.

[Deploy Spring Boot app in a cloud instance](https://blog.sayem.dev/2017/09/deploy-spring-boot-app-in-digitalocean-html/)
