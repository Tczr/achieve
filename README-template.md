# Achieve
## Table of contents

- [Overview](#overview)
- [My process](#my-process)
  - [Built with](#built-with)
  - [Component](#component)
- [Problems and solution](#problems-and-solution)


## Overview 
A simple todo app, that makes you able to see your achievements.

## My process 

### Built with
* Spring boot framework
* H2 database with JdbcTemplate


### Component 
- **User**.
- **Task associated with it**: 
  1. **Status** : to filter task status in Enum.  
  2. **Scheduler** : which is a primary object for schedule if reminder was set
- **Controller**: to manage request.
- **Handler** : to get Data from Database.
- **Dao** : the actual implementation of database query.
- **Helper** :Provide assistance services such as:
  1. sorting algorithm or searching algorithm
  2. an email service.

## Problems and solution

### Problems
1. Password is not encrypted 
also, in order to check user I was just
store as a property which is bad for security purposes.

2. the structure is too messy.
that's as a result of separated concern and making fewer calls to database,
it would be not efficient to call whenever user get tasks.
but we faced a problem here that is whenever user updated or deleted or added a task it's separated from user tasks list which is a separated object that holde tasks from database. 
3. Also, about structure there is no need for getTask or getTasks
it's useless, it's a privacy violation. what I should have done is to get user tasks or task to prevent other users from seeing other users tasks.

> it's obvious but I just wanted to mention that it's a violation and i just kept it for the sake of practicing.

### Possible solution
 **-Solution for problem #1:** we just need to encreypt it and if we want to upgrade the security we could slat the password and store it the database to check everytime we match the enterd password with the stored password.



 **-Solution for problem #2:** we could make a general repository
 and then store just user information why?? because user is already has its own list of tasks
 so, we just need to store just users and whenever user get updated its own tasks we fire a call to a(task handler) to add it to task table in the data base
 same for other crud operation. So we just need one servie to make call to tow tables or repositories and save it the user in an object and its tasks in a map to make the search much easier and return the values or the object(user) to the controller to handel request.
 
 
 **-General solution:**   simple and effective solution is to separate the list from the user model, and then we could follow the old design to search for user tasks by its id.
 and I think this is better than the above solutions because the hole idea is to make a minimum call to the database, so we store the hole tasks in a map and map it by user id to search for its own tasks.
 
  **The problem with problem #2 is that I didn't fully understand the "RESTful" API. at that time.
  there is no broblems with the apis nor the structure, simply creating an object in javascript to store the required data whenever request to an endpoint is made.
  this will do the work.**
  
