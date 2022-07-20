
# Achieve 
it is a simple todo app

### Technology used
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
- **Helper** : to give helper services like:
  1. sorting algorithm or searching algorithm
  2. an email service.

### Cons -- needs improve
1. Password is not encrypted 
also, in order to check user I was just
store as a property which is bad for security purposes.
2. the structure is too messy 😅.<br>
that's as a result of separated concern and making fewer calls to database,
it would be not efficient to call whenever user get tasks.
but we faced a problem here that is whenever user updated or deleted or added a task it's separated from user tasks list which is a separated object that holde tasks from database. 
3. Also, about structure there is no need for getTask or getTasks
it's useless, it's a privacy violation. what I should done is to get user tasks or task to prevent user from seeing othrs tasks.

### Possible solution
 **-Solution for problem #1:** we just need to encreypt it and if we want to upgrade the security we could slat the password and store it the database to check everytime we match the enterd password with the stored password.
 <br><br>
 **-Solution for problem #2:** we could make a general repository
 and then store just user information why?? because user is already has its own list of tasks
 so, we just need to store just users and whenever user get updated its own tasks we fire a call to a(task handler) to add it to task table in the data base
 same for other crud operation. So we just need one servie to make call to tow tables or repositories and save it the user in an object and its tasks in a map to make the search much easier and return the values or the object(user) to the controller to handel request.
 
 
 or simple and effective solution is to separate the list from the user model, and then we could follow the old design to search for user tasks by its id.
 and I think this is better than the above solution because the hole idea is to make a minimum call to the database, so we store the hole tasks in a map and map it by user id to search for its own tasks.
