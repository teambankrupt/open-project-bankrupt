# Spring Boot Featured Skeleton

It's a skeleton project with lots of features implemented by default.

## Features At a Glance

### For Admin Panel
* Login
* Template configuration (Thymeleaf engine with layout dialect)
* Integrated (with thymeleaf engine, layout dialect) Opensource AdminBSBMaterialDesign theme for admin panel
* Spring Security Configuration (WebSecurity)

### For API's
* User Registration/Authentication
* User Role Management
* Profile CRUD
* OAuth2 for securing API's
* Phone verification with OTP when registration
* Swagger configuration
* CORS configuration
* Promotion Management

### Common
* Email configuration for sending Emails
* SMS service configuration for sending SMS
* Exception Handling
* Activity Logging
* Flood Control (Auto Block IP for multiple failed attemps)
* Firebase Push server configuration (Implemented on NotificationService)
* File Upload Configuration for storing files on FileSystem
* And lots of utility classes


# Authentication Flow (Some basic implemented api's)

## Registration
Before you can register a user with a phone number you need to verify phone.

```$xslt
POST /api/v1/register/verifyPhone?phone=01610226163 HTTP/1.1
```
That phone number will get a token (validity for 2 minutes, can send another token request after two minutes)

Then you can send a request for registering user with that token

```$xslt
POST /api/v1/register?otp=449183 HTTP/1.1
Host: localhost:8080
Content-Type: application/json
cache-control: no-cache
{
	"name": "Sayem Hossain",
	"email" : "optional@whatever.com",
	"phone" : "01610226163",
	"password": "123456"
}
```
If registration is successful it will give you a response like below, so that you can instantly log user in.

```$xslt
{
    "access_token": "eea1a168-36d3-41ae-963b-e5156cb678ed",
    "token_type": "bearer",
    "refresh_token": "cc936718-e938-4143-b742-b7d0575c49a2",
    "expires_in": 19999,
    "scope": "read trust write",
    "phone": "01610226163",
    "name": "Sayem Hossain",
    "id": 10,
    "authorities": [
        {
            "authority": "ROLE_USER"
        }
    ],
    "username": "01610226163"
}
```

Bind this response to your defined UserAuth object and this object should be your authentication

## Login

```$xslt
GET /api/v1/login?username=01610226163&amp; password=123456&amp; client_id=client_id&amp; client_secret=client_secret&amp; grant_type=password HTTP/1.1
Host: localhost:8080
cache-control: no-cache

```  
Response of login url will be same as registration response above (UserAuth) if login succeed.

## Refresh Token
The validity duration of access token is 5000s. So if you find 401 response code in any request, refresh your access token
```$xslt
POST /api/v1/login?client_id=client_id&amp; client_secret=client_secret&amp; grant_type=refresh_token&amp; refresh_token=cc936718-e938-4143-b742-b7d0575c49a2 HTTP/1.1
Host: localhost:8080
cache-control: no-cache
Postman-Token: 4826c898-6b31-4ccc-9551-f2c3d5c5bb01

```

---------------------

# User profile

## Create Profile 

```$xslt
PUT /api/v1/profiles/user/{username} HTTP/1.1
Host: localhost:8080
Authorization: Bearer 810108a4-e375-4914-9186-d11512dbcf36
Content-Type: application/json
{
	"name":"Sayem Hossain",
	"birthday": "2018-01-01",
    "gender": "male",
    "photo": "/images/c7e1c247-ef23-4b21-9243-927568bc7e45",
    "bloodGroup" : "O_POSITIVE",
    "lastDonated" : "2018-07-21",
	"address":{
		"flat": "23",
		"floor": 2,
		"house": "34/3",
		"road": "Road 42",
		"village": "",
		"area" : "dhanmondi",
		"postOffice": "dhanmondi",
		"postCode": "1209",
		"policeStation": "dhanmondi",
		"upazila": "dhanmondi",
		"district": "dhaka",
		"division": "Dhaka",
		"country": "Bangladesh"
	}
}
```

## Get own profile
Get own profile information.
```$xslt
GET /api/v1/profiles/user/{username} HTTP/1.1
Host: localhost:8080
Authorization: Bearer 810108a4-e375-4914-9186-d11512dbcf36

```

Response: Profile object, Json sample is exactly like above



### Customize Basic Configurations

For General configurations, open `application.properties` file and change the values.

File Storage path
```files.upload.path=/var/appFiles```
App Config
```$xslt
#BASE URL
app.domain=example.com
baseUrl=https://example.com
baseUrlApi=https://api.example.com
applicationName=ExampleApp
contactEmail=info@example.com
```
Admin accounts (these two phone number will automatically be admin after registration)
```$xslt
#Admin emails
admin.phone1=adminphone1
admin.phone2=adminphone2
```

Client ID/Secret for your client app (web frontend, android,ios etc)
```$xslt
app.client.id=client_id
app.client.secret=client_secret
```

Token Endpoint:
```
app.loginEndpoint=/api/v1/login
```
Firebase Server Key (for firebase push):
```$xslt
app.fcm.serverkey=SERVER_KEY
```

### Email Configuration

Open `mail.properties` file and change property values according to your email provider

### SMS configuration

This project is configured to use mimsms by default. If you have a mimsms account then provide your senderId and apiKey.

If you want to use other provider implement your api in `SmsServiceImpl` class

```$xslt
mimsms.apiKey=API_KEY
mimsms.senderId=SENDER_ID
```

Deployment:

Packaging
```mvn clean package -DskipTests -Denv=prod```
Here -Denv=prod comes from maven profiling on pom.xml file. This argument activates production profile and loads config from that profile.
use -Denv=dev or -Denv=local or create a new profile.

To deploy app on a cloud instance please look at my blog here.

[Deploy Spring Boot app in a cloud instance](https://www.rimon.xyz/2017/09/deploy-spring-boot-app-in-digitalocean-html/)
