# Instabug-task
### App screenshots
<p align="center">
  <img src="img/app1.png" height="400" width="200">
  <img src="img/app2.png" height="400" width="200">
  <img src="img/app3.png" height="400" width="200">
  <img src="img/app4.png" height="400" width="200">
  <img src="img/app5.png" height="400" width="200">
  <img src="img/app6.png" height="400" width="200">
</p>



## APK Download:
[Click here](https://drive.google.com/drive/u/0/folders/1qNqkgHqWy9jcexae_uSZLWZVJmhPxp_O)

### Tools & APIs:
- Kotlin
- View Binding   
- ViewModel
- Live data
- I didn't use Dagger hilt for DI as it's considered as 3rd parties.


### Some Documented functions
<p align="center">
  <img src="img/doc1.png" height="300" width="300">
  <img src="img/doc2.png" height="300" width="300">
  <img src="img/doc3.png" height="180" width="600">
</p>

### Code Architecture:
Mvvm, Mvi for Home Activity.
<p align="left">
  <img src="img/arch.png" height="400" width="350">
</p>


### Requriments:
We need you to create an application that will help in testing backed APIs.
This application must allow us to do the following:
1. Enter URL to connect to
2. Add request headers which numbers are dynamic
3. App must support GET/POST
a. For POST request type, App must provide a way to enter
request body as string
4. After request completion the app needs to display the following:
a. Response code
b. Error if any
c. Request/Response headers
d. Request body or query parameters depending on request type
/Response body
5. If the device is offline then app must show an error message and do
not make the call

### Notes:
Your app must not use any third party libraries. We consider (Retrofit,
volley, coroutines, room….etc) as 3rd parties

### Dynamic Headers / query parameters:
Without using List view or Recycler view.

### Validate data:
We validate the  following befor making any API call      
URL using -> REGEX     
Request type (GET / POST) 

### For background tasks:
used: [Executers](https://developer.android.com/reference/java/util/concurrent/Executors)

### For Network Calls:
used: [HttpURLConnection](https://developer.android.com/reference/java/net/HttpURLConnection)

### Endpoints for test:
GET request: [Daily cat facts](https://alexwohlbruck.github.io/cat-facts/docs/)     

POST request: https://reqres.in/api/users    
Request body -> `{\"name\": \"Upendra\", \"job\": \"Programmer\"}`



