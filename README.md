# coding-challenge
Fanatics Gaming Coding Challenge

### Prerequisites
* IntelliJ : To run code
* Postman : To test out api calls
* Bearer token : Needed to authenticate with remote calls like update and delete. See https://gorest.co.in/ for more details

### Steps
* Clone the master branch
* Import the project in IntelliJ
* Add Program arguments : --gorest.bearertoken={value of Bearer token}
* Build the project and run it. It should start local service on default port 8080
* Make GET call : http://localhost:8080/playGround
   * If all calls succeed you will get message : **All Operations successful** otherwise it will output **One or more api endpoints dint work**
