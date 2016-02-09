.mode columns
.headers on
create table sailor (sname, rating);
create table boat (bname, color, rating);
create table reservation (sname, bname, day, start, finish);
create table alldays (days);

insert into sailor values ('Brutus', 1);
insert into sailor values  ('Andy', 8);
insert into sailor values ('Horatio', 7);
insert into sailor values ('Rusty', 8);
insert into sailor values ('Bob', 1);

insert into boat values ('SpeedQueen', 'white', 9);
insert into boat values ('Interlake', 'red', 8);
insert into boat values ('Marine', 'blue', 7);
insert into boat values ('Bay', 'red', 3);

insert into reservation values ('Andy', 'Interlake', 'Monday', 10, 14);
insert into reservation values ('Andy', 'Marine', 'Saturday', 14, 16);
insert into reservation values ('Andy', 'Bay', 'Wednesday', 8, 12);
insert into reservation values ('Rusty', 'Bay', 'Sunday', 9, 12);
insert into reservation values ('Rusty', 'Interlake', 'Wednesday', 13, 20);
insert into reservation values ('Rusty', 'Interlake', 'Monday', 9, 11);
insert into reservation values ('Bob', 'Bay', 'Monday', 9 , 12);
insert into reservation values ('Andy', 'Bay', 'Wednesday', 9, 10);
insert into reservation values ('Horatio', 'Marine', 'Tuesday', 15, 19);

insert into alldays ('Sunday');
insert into alldays ('Monday');
insert into alldays ('Tuesday');
insert into alldays ('Wednesday');
insert into alldays ('Thursday');
insert into alldays ('Friday');
insert into alldays ('Saturday');

