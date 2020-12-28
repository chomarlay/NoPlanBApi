insert into User (id, username, email, password, role, created_By, created_Date, last_Modified_By, last_Modified_Date) values (30001L, 'noPlanB', 'noplanb@noplanb.com.au', 'password123', 'ROLE_USER','noPlanB', '2019-09-12', 'noPlanB', '2019-09-12' );
insert into User (id, username, email, password, role, created_By, created_Date, last_Modified_By, last_Modified_Date) values (30002L, 'theodore', 'theodore@chippymonks.com.au', 'password123', 'ROLE_USER','theodore', '2019-09-12', 'theodore', '2019-09-12' );

insert into Project (id, title, user_id, created_By,  created_Date, last_Modified_By, last_Modified_Date) values (10001L, 'Inbox', 30001L, 'noPlanB', '2019-09-12', 'noPlanB', '2019-09-12');
insert into Project (id, title, user_id, created_By, created_Date, last_Modified_By, last_Modified_Date) values (10002L, 'Running', 30002L, 'theodore', '2019-09-12', 'theodore', '2019-09-12');
insert into Project (id, title, user_id, created_By, created_Date, last_Modified_By, last_Modified_Date) values (10003L, 'Spring JPA', 30001L, 'noPlanB', '2019-09-12', 'noPlanB', '2019-09-12');
insert into Project (id, title, user_id, completed_Date, created_By, created_Date, last_Modified_By, last_Modified_Date) values (10000L, 'Music', 30002L, '2019-09-30', 'theodore', '2019-09-12', 'theodore', '2019-09-12');


insert into Task (id, title, project_id, user_id, due_date, created_By,  created_Date, last_Modified_By, last_Modified_Date) values (20001L, '5km Run',  10002L, 30002L, '2020-10-31', 'theodore', '2019-09-12', 'theodore', '2019-09-12');
insert into Task (id, title, project_id, user_id, due_date, created_By,  created_Date, last_Modified_By, last_Modified_Date) values (20002L, '10km Run', 10002L, 30002L, '2020-11-07', 'theodore', '2019-09-12',  'theodore', '2019-09-12');
insert into Task (id, title, project_id, user_id, completed_Date, created_By,  created_Date, last_Modified_By, last_Modified_Date) values (20003L, '3km Driveway Run', 10002L, 30002L,  '2019-08-12', 'theodore', '2019-09-12', 'theodore', '2019-09-12');
insert into Task (id, title, project_id, user_id, created_By,  created_Date, last_Modified_By, last_Modified_Date) values (20020L, 'Task 1', 10003L, 30001L,'noPlanB', '2019-09-12', 'noPlanB', '2019-09-12');
insert into Task (id, title, project_id, user_id, created_By,  created_Date, last_Modified_By, last_Modified_Date) values (20021L, 'Task 2', 10003L, 30001L,'noPlanB', '2019-09-12', 'noPlanB', '2019-09-12');

