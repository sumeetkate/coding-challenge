# Bet Fanatics Coding Challenge

### Overview
For this coding challenge, you will be interfacing with an existing REST-based API and performing 
various operations against that API.  


The API and its documentation are located here:  https://gorest.co.in/

Be sure to read the entire page, as it includes details that will be required to finish this exercise.

Fork this repo and do all of your work against that fork. When you are finished, submit a pull request and notify a member of our team.

### Instructions

Using the REST API endpoints documented in the link in the previous section:
1. Retrieve page 3 of the list of all users.
2. Using a logger, log the total number of pages from the previous request.
3. Sort the retrieved user list by name.
4. After sorting, log the name of the last user.
5. Update that user's name to a new value and use the correct http method to save it.
6. Delete that user.
7. Attempt to retrieve a nonexistent user with ID 5555.  Log the resulting http response code.
8. Write unit tests for all code, mocking out calls to the actual API service.