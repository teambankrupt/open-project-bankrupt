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

---------------------

There are Admin API's for authority management (User Roles, Privileges/Permissions)

# Architecture:

#### Privileges
A Privilege/Permission is the smallest piece of element which is responsible for accessing certain API or collection of API's.<br/>
It has two Main property and a `Label` for human readability.

- label (Hoomans..! it's for you)
- name (A Unique String all uppercase sperated with underscore. Ex. CREATE_POST, VIEW_STATISTICS)
- access_urls (A role containing this privilege will have access to these urls)

A privilege can be created and maintained by admin users.

#### Roles
A Role contains a collection of privileges. It can also be consider as a group of privileges. <br/>
A User with certain role can have all the privileges inside that role, thus access all endpoints defined for those privileges.

- name (Any Unique String, Ex. Admin, Financial Advisor, Monitor)
- restricted (A boolean value)
- privileges

Here, set `restricted` to false when creating/updating a role, if you don't want your user to register for that certain role. </br>
For example, for  a ride sharing app, you may want your user to register for both `Drive` and `User` role, but you don't want them to register for `admin` role.


# Implemented Admin API's

## Privileges

### Get all privileges
```GET /api/v1/admin/privileges HTTP/1.1```

### Create a privilege

```$xslt
POST /api/v1/admin/privileges HTTP/1.1
Content-Type: application/json

{
	"name": "update post",
	"label": "update post",
	"access_urls": [
		"/api/v1/posts/update"
		]
}
```

### Update a privilege

```
PATCH /api/v1/admin/privileges/{privilege_id} HTTP/1.1
Content-Type: application/json

{
	"name": "UPDATE_POST",
	"label": "Update Post",
	"access_urls": [
		"/api/v1/posts/update"
		]
}
```

### Delete a privilege

``` 
DELETE /api/v1/admin/privileges/{privilege_id} HTTP/1.1
```

## Roles

### Get list of Roles

``` 
GET /api/v1/admin/roles HTTP/1.1
```

### Create a role with privileges

``` 
POST /api/v1/admin/roles HTTP/1.1
Content-Type: application/json

{
	"name": "Editor",
	"restricted": false,
	"privilege_ids": [1,3]
}
```

### Update a role

``` 
PATCH /api/v1/admin/roles/{role_id} HTTP/1.1
Content-Type: application/json

{
	"name": "Editor",
	"restricted": false,
	"privilege_ids": [3,5]
}
```

## Delete a role

``` 
DELETE /api/v1/admin/roles/{role_id} HTTP/1.1

```



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
