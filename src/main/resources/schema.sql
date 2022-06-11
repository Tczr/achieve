CREATE TABLE users
(
    userId int IDENTITY NOT NULL  PRIMARY KEY,
    userEmail VARCHAR(90) NOT NULL UNIQUE,
    userPassword VARCHAR(50) NOT NULL,
    userName varchar(25),

    createdAt DATE NOT NULL

--    CONSTRAINT pk_userId (userId)
);

CREATE TABLE todoTasks
(
    userId int NOT NULL,
    taskId int IDENTITY NOT NULL PRIMARY KEY,
    taskName VARCHAR(90) NOT NULL,
    taskStatus VARCHAR(20) NOT NULL DEFAULT 'active',
    taskReminder binary DEFAULT 0,
    scheduled_start DATE,
    scheduled_end DATE,

    createdAt DATE NOT NULL,

    foreign key (userId) references users(userId) ON DELETE CASCADE ON UPDATE CASCADE


);