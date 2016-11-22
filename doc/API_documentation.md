# Challenge Accepted API Documentation
**Base Url:** /challengeaccepted/api
## Users
### Get all users
**Url:** /users  
**Method:** ´GET´  
**Success Response:** [200 OK]  
**Example Response Content:**  
{  
    "id": 1,  
    "firstName": "Kalle",  
    "lastName": "Brallsson",  
    "email": "kallebrallsson1@gmail.com",  
    "password": null,  
    "yubiKeyID": null,  
    "completedChallengePoints": null,  
    "createdChallengePoints": null  
  },  
  {  
    "id": 2,  
    "firstName": "Rigmor",  
    "lastName": "Osvaldsson",  
    "email": "rigmorosvaldsson1@gmail.com",  
    "password": null,  
    "yubiKeyID": null,  
    "completedChallengePoints": null,  
    "createdChallengePoints": null  
}  
### Get user by ID
**Url:** /users/:id
**Method:** ´GET´
**Url Params:** id=[Long]
**Success Response:** [200 OK]
**Example Response Content:**
{
  "id": 1,
  "firstName": "Kalle",
  "lastName": "Brallsson",
  "email": "kallebrallsson1@gmail.com",
  "password": null,
  "yubiKeyID": null,
  "completedChallengePoints": null,
  "createdChallengePoints": null
}
### Update user
**Url:** /users
**Method:** ´PUT´
**Content Type:** Json
**Success Response:** [200 OK]
**Example Request Content:** 
{
    "id": 1,
    "firstName": "Kalle",
    "lastName": "Brallsson",
    "email": "kallebrallsson1@gmail.com",
    "password": null,
    "yubiKeyID": null,
    "completedChallengePoints": 13,
    "createdChallengePoints": null
}
