
# Achieve 
is a simple todo app

## Technology used
* Spring boot framework
* H2 database with JdbcTemplate

##Component 
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
<br> it's Like a Mapper in facade pattern.
###Cons -- needs improve
1. Password is not encrypted 
also, in order to check user I was just
store as a property which is bad for security purposes.
2. the structure is too messy 😅.<br>
that's as a result of separated concern and making fewer calls to database,
it would be not efficient to call whenever user get tasks.
but we faced a problem that is whenever user updated or deleted or added a task it's separated from user tasks list.
we could do better.
3. Also, about structure there is no need for getTask or getTasks
it's useless, it's a privacy violation.

###Possible solution
 **-Solution for problem #1:** we could delete a pass property, but how can we save it as a password in database?.
I think we could use it as a separated string that based as extra and encrypted when saving "creating new user".
When user gets to login we take his pass and encrypted and check if they match
It's like md5 or hashing.
 <br><br>
 **-Solution for problem #2:** we could make a general repository
 and then store just user information why?? because user is already has its own list of tasks
 so, we just need to store users and whenever user get updated its own tasks we fire a call to task handler to add it to task table
 same for other crud operation