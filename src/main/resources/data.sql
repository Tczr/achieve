
INSERT INTO users (userEmail, userPassword, userName, createdAt)
    VALUES('email@email.com','password1234','newUser','2022-4-26');

INSERT INTO users (userEmail, userPassword, userName, createdAt)
    VALUES('email2@email.com','password1234','newUser2','2022-4-26');

INSERT INTO todoTasks(userId, taskName, taskStatus, taskReminder, createdAt)
    VALUES (2, 'new task to do', 'delayed', 0, '2022-4-26');

INSERT INTO todoTasks(userId, taskName, taskStatus, taskReminder, createdAt)
    VALUES (2, 'delayed task to do', 'delayed', 0, '2022-4-26') ;

INSERT INTO todoTasks(userId, taskName, taskReminder, scheduled_start, scheduled_end, createdAt)
    VALUES (1, 'implement the new  futures', 1, '2022-05-01', '2022-05-10', '2022-4-26');

INSERT INTO todoTasks(userId, taskName, taskStatus, taskReminder, createdAt)
    VALUES (1, 'change the decor', 'active', 0, '2022-4-26');

INSERT INTO todoTasks(userId, taskName, taskStatus, createdAt)
    VALUES (1, 'old task to do', 'finished', '2022-4-26');