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
## Challenges
### Get all challenges
**Url:** /challenges  
**Method:** ´GET´  
**Success Response:** [200 OK]  
**Example Response Content:**  
{  
    "id": 1,  
    "topic": "Hej hej",  
    "description": "Att skapa ny användare för att se om loggen fungerar som den ska",  
    "youtubeURL": null,  
    "creationDate": "2016-05-22 11:58:56",  
    "upvotes": 2,  
    "points": null,  
    "challengeUpvoters": [  
      2,  
      3  
    ],  
    "challengeClaimer": null,  
    "challengeCreator": {  
      "id": 1,  
      "firstName": "Kalle",  
      "lastName": "Brallsson",  
      "email": "kallebrallsson1@gmail.com",  
      "password": null,  
      "yubiKeyID": null,  
      "completedChallengePoints": 13,  
      "createdChallengePoints": null  
    },  
    "challengeCompleted": false,  
    "challengeClaimed": false,  
    "youtubeVideoUploaded": false,  
    "youtubeUrlProvided": null,  
    "challengeDisapproved": false  
  },  
  {  
    "id": 2,  
    "topic": "Testar igen",  
    "description": "Måste försöka få den att fånga en ny level",  
    "youtubeURL": null,  
    "creationDate": "2016-05-22 12:28:15",  
    "upvotes": 2,  
    "points": null,  
    "challengeUpvoters": [  
      2,  
      3  
    ],  
    "challengeClaimer": null,  
    "challengeCreator": {  
      "id": 1,  
      "firstName": "Kalle",  
      "lastName": "Brallsson",  
      "email": "kallebrallsson1@gmail.com",  
      "password": null,  
      "yubiKeyID": null,  
      "completedChallengePoints": 13,  
      "createdChallengePoints": null  
    },  
    "challengeCompleted": false,  
    "challengeClaimed": false,  
    "youtubeVideoUploaded": false,  
    "youtubeUrlProvided": null,  
    "challengeDisapproved": false  
}  