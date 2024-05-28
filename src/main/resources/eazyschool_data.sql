/*
INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Jan 1 ', 'New Year''s Day', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Oct 31 ', 'Halloween', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Nov 24 ', 'Thanksgiving Day', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Dec 25 ', 'Christmas', 'FESTIVAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Jan 17 ', 'Martin Luther King Jr. Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' July 4 ', 'Independence Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Sep 5 ', 'Labor Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `holidays` (`day`, `reason`, `type`, `created_at`, `created_by`)
VALUES (' Nov 11 ', 'Veterans Day', 'FEDERAL', CURDATE(), 'DBA');

INSERT INTO `roles` (`role_name`, `created_at`, `created_by`)
VALUES ('ADMIN', CURDATE(), 'DBA');

INSERT INTO `roles` (`role_name`, `created_at`, `created_by`)
VALUES ('STUDENT', CURDATE(), 'DBA');

INSERT INTO `roles` (`role_name`, `created_at`, `created_by`)
VALUES ('LECTURER', CURDATE(), 'DBA');

DELETE
FROM person
where email = 'admin@eazyschool.com';

INSERT INTO `person` (`name`, `email`, `mobile_number`, `pwd`, `role_id`, `created_at`, `created_by`)
VALUES ('Admin', 'admin@eazyschool.com', '3443434343', '$2a$10$XhU4UcSxDPb5G0I0fT/CZ.Lfj2VW2fkLkUP5cOEM.xM8EzyUQXaD2',
        1, CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Adam', '2176436587', 'zadam@gmail.com', 'Regarding a job', 'Wanted to join as teacher', 'Open', CURDATE(),
        'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Zara', '3412654387', 'zarabaig@hotmail.com', 'Course Admission', 'Wanted to join a course', 'Open', CURDATE(),
        'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Marques', '8547643673', 'kmarques@yahoo.com', 'Course Review', 'Review of Development course', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Shyam', '4365328776', 'gshyam@gmail.com', 'Admission Query', 'Need to talk about admission', 'Open', CURDATE(),
        'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('John', '5465765453', 'doejohn@gmail.com', 'Holiday Query', 'Query on upcoming holidays', 'Open', CURDATE(),
        'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Taniya Bell', '3987463827', 'belltaniya@gmail.com', 'Child Scholarship', 'Can my child get scholarship?',
        'Open', CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Willie Lara', '4568764801', '476lara@gmail.com', 'Need Admission', 'My son need an admission', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Jonathan Parsons', '4321768902', 'jonathan.parsons@gmail.com', 'Course feedback', 'Music course is good',
        'Open', CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Cloe Rubio', '9854438719', 'rubio987@gmail.com', 'Correct Date of Birth', 'My Child DOB needs to be corrected',
        'Open', CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Camilla Stein', '6545433254', 'camillas@gmail.com', 'Transport Query', 'Is Transport provided?', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Lizeth Gross', '4678783434', 'grossliz@yahoo.com', 'Progress report', 'Please send progress report', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Yael Howe', '1243563254', 'howeyael@gmail.com', 'Certificate Query', 'Need Certificate hard copy', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Ian Moreno', '2312231223', 'moreno.ian@gmail.com', 'Food feedback', 'Food quality can be improved', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Desirae Ibarra', '3445235667', 'ibarrades@gmail.com', 'Traffic Complaint',
        'Traffic around school can be controlled', 'Open', CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Oswaldo Jarvis', '4556121265', 'jarvissmile@hotmail.com', 'Study Tour', 'Study tour details needed', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Miah Perkins', '2367784512', 'perkinsmiah@hotmail.com', 'Vaccination Support',
        'Vaccination center in the school', 'Open', CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Zion Bolton', '8990678900', 'boltzion@gmail.com', 'Course fees', 'Pls share fees of music course', 'Open',
        CURDATE(), 'DBA');

INSERT INTO `contact_msg` (`name`, `mobile_num`, `email`, `subject`, `message`, `status`, `created_at`, `created_by`)
VALUES ('Dominik Tanner', '4556127834', 'tannerdominik@gmail.com', 'Games schedule', 'Provide Summer games schedule',
        'Open', CURDATE(), 'DBA');

*/

# New data
INSERT INTO `contact_msg` VALUES ('1', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'zadam@gmail.com', 'Wanted to join as teacher', '2176436587', 'Adam', 'Open', 'Regarding a job');
INSERT INTO `contact_msg` VALUES ('2', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'zarabaig@hotmail.com', 'Wanted to join a course', '3412654387', 'Zara', 'Open', 'Course Admission');
INSERT INTO `contact_msg` VALUES ('3', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'kmarques@yahoo.com', 'Review of Development course', '8547643673', 'Marques', 'Open', 'Course Review');
INSERT INTO `contact_msg` VALUES ('4', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'gshyam@gmail.com', 'Need to talk about admission', '4365328776', 'Shyam', 'Open', 'Admission Query');
INSERT INTO `contact_msg` VALUES ('5', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'doejohn@gmail.com', 'Query on upcoming holidays', '5465765453', 'John', 'Open', 'Holiday Query');
INSERT INTO `contact_msg` VALUES ('6', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'belltaniya@gmail.com', 'Can my child get scholarship?', '3987463827', 'Taniya Bell', 'Open', 'Child Scholarship');
INSERT INTO `contact_msg` VALUES ('7', '2024-05-25 00:00:00', 'DBA', NULL, NULL, '476lara@gmail.com', 'My son need an admission', '4568764801', 'Willie Lara', 'Open', 'Need Admission');
INSERT INTO `contact_msg` VALUES ('8', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'jonathan.parsons@gmail.com', 'Music course is good', '4321768902', 'Jonathan Parsons', 'Open', 'Course feedback');
INSERT INTO `contact_msg` VALUES ('9', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'rubio987@gmail.com', 'My Child DOB needs to be corrected', '9854438719', 'Cloe Rubio', 'Open', 'Correct Date of Birth');
INSERT INTO `contact_msg` VALUES ('10', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'camillas@gmail.com', 'Is Transport provided?', '6545433254', 'Camilla Stein', 'Open', 'Transport Query');
INSERT INTO `contact_msg` VALUES ('11', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'grossliz@yahoo.com', 'Please send progress report', '4678783434', 'Lizeth Gross', 'Open', 'Progress report');
INSERT INTO `contact_msg` VALUES ('12', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'howeyael@gmail.com', 'Need Certificate hard copy', '1243563254', 'Yael Howe', 'Open', 'Certificate Query');
INSERT INTO `contact_msg` VALUES ('13', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'moreno.ian@gmail.com', 'Food quality can be improved', '2312231223', 'Ian Moreno', 'Open', 'Food feedback');
INSERT INTO `contact_msg` VALUES ('14', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'ibarrades@gmail.com', 'Traffic around school can be controlled', '3445235667', 'Desirae Ibarra', 'Open', 'Traffic Complaint');
INSERT INTO `contact_msg` VALUES ('15', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'jarvissmile@hotmail.com', 'Study tour details needed', '4556121265', 'Oswaldo Jarvis', 'Open', 'Study Tour');
INSERT INTO `contact_msg` VALUES ('16', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'perkinsmiah@hotmail.com', 'Vaccination center in the school', '2367784512', 'Miah Perkins', 'Open', 'Vaccination Support');
INSERT INTO `contact_msg` VALUES ('17', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'boltzion@gmail.com', 'Pls share fees of music course', '8990678900', 'Zion Bolton', 'Open', 'Course fees');
INSERT INTO `contact_msg` VALUES ('18', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'tannerdominik@gmail.com', 'Provide Summer games schedule', '4556127834', 'Dominik Tanner', 'Open', 'Games schedule');


INSERT INTO `roles` VALUES ('1', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'ADMIN');
INSERT INTO `roles` VALUES ('2', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'STUDENT');
INSERT INTO `roles` VALUES ('3', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'LECTURER');


INSERT INTO `person` VALUES ('1', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'admin@eazyschool.com', '3443434343', 'Admin', NULL, '$2a$10$XhU4UcSxDPb5G0I0fT/CZ.Lfj2VW2fkLkUP5cOEM.xM8EzyUQXaD2', NULL, NULL, '1');
INSERT INTO `person` VALUES ('2', '2024-05-25 12:44:55.481377', 'admin@eazyschool.com', NULL, NULL, 'lec1@gmail.com', '0123456789', 'Lecturer1', '/assets/images/user-profiles/lec1_3f22a9c3-7bce-46b6-bca0-ebebc83f5733.jpeg', '$2a$10$9IdxqHsBhbg5EjpUsL2sQ.2n0DtJnMn2QuhS6/2nRvWCbjLRTRSX2', NULL, NULL, '3');
INSERT INTO `person` VALUES ('3', '2024-05-25 12:45:15.715231', 'admin@eazyschool.com', NULL, NULL, 'lec2@gmail.com', '0123456789', 'Lecturer2', '/assets/images/user-profiles/lec2_4b8d6b17-bbc2-4a45-bb2c-dd84da6e1bb5.jpeg', '$2a$10$Y.I3oW89NyWt5hu0ig2jfOYl0tspVKl6XU4sL0HGDJCYqBDkKRTEy', NULL, NULL, '3');
INSERT INTO `person` VALUES ('4', '2024-05-25 12:45:43.670749', 'admin@eazyschool.com', NULL, NULL, 'lec3@gmail.com', '0123456789', 'Lecturer3', '/assets/images/user-profiles/lec3_cc87a64e-0ab7-40c7-98e6-ea0c3747127d.jpeg', '$2a$10$AfaBmCBqftSXMRvGj0PDj.x/yUYZHF4hv8/vy7HoLAaOpDKRe898u', NULL, NULL, '3');
INSERT INTO `person` VALUES ('5', '2024-05-25 12:46:13.351823', 'admin@eazyschool.com', NULL, NULL, 'lec4@gmail.com', '0123456789', 'Lecturer4', '/assets/images/user-profiles/lec4_e1ee542a-f286-4907-ad8c-d37e32e03a39.jpeg', '$2a$10$06p3TslTvK5YWeQdPE/3OOaxe8Q0rVbPLBqbiARyxi3oamiuO0q1S', NULL, NULL, '3');
INSERT INTO `person` VALUES ('6', '2024-05-25 12:46:36.740046', 'admin@eazyschool.com', NULL, NULL, 'lec5@gmail.com', '0123456789', 'Lecturer5', '/assets/images/user-profiles/lec5_e1a4b0e1-87d8-4af7-bfb8-b993b972acc4.jpeg', '$2a$10$rFKO65otSKUDwUU4.7XxWeIeq7T9Gf2eCnwxmNfeKAFlwX7Cc207a', NULL, NULL, '3');
INSERT INTO `person` VALUES ('7', '2024-05-25 12:54:34.932502', 'anonymousUser', NULL, NULL, 'std1@gmail.com', '0123456789', 'Std1', '/assets/images/user-profiles/std1_cfa1d165-fb68-4f3d-8152-2d7d5cdc0e27.jpeg', '$2a$10$qdklnDg2ACwIOlLLmowUjOvnKiucRMqPjSyA7rIB4/lyFgwu4ISw6', NULL, NULL, '2');
INSERT INTO `person` VALUES ('8', '2024-05-25 12:54:56.381136', 'anonymousUser', NULL, NULL, 'std2@gmail.com', '0123456789', 'Std2', '/assets/images/user-profiles/std2_ea1cd835-4ee0-420c-a0e6-d7de83ab6d9e.jpeg', '$2a$10$Voil0zF/uVVEx9l6Hq5l7.ZU9SMZ9OuF1Zic9XZ3hFOgY44mHBccu', NULL, NULL, '2');
INSERT INTO `person` VALUES ('9', '2024-05-25 12:55:12.867040', 'anonymousUser', NULL, NULL, 'std3@gmail.com', '0123456789', 'Std3', '/assets/images/user-profiles/std3_e61cf1ef-266f-4ded-845f-1204587dfe2f.jpeg', '$2a$10$ILdoxc8QAbZfsgUvlvrT9ujj8O0pF5g2lsoNaOwRENrN0vXF.g2A.', NULL, NULL, '2');
INSERT INTO `person` VALUES ('10', '2024-05-25 12:55:29.392323', 'anonymousUser', NULL, NULL, 'std4@gmail.com', '0123456789', 'Std4', '/assets/images/user-profiles/std4_aa5e74d9-eb9f-4c95-8b28-50f0ac39fdae.jpeg', '$2a$10$Ghy9GY9H19wVNtHXDEjaJ.EZ0L8cUxxljifLvGv5JJ/w.IqYEbgHa', NULL, NULL, '2');
INSERT INTO `person` VALUES ('11', '2024-05-25 12:55:50.188065', 'anonymousUser', NULL, NULL, 'std5@gmail.com', '0123456789', 'Std5', '/assets/images/user-profiles/std5_f9c597e1-7c17-4491-bfcf-165e8386716a.jpeg', '$2a$10$s10jaeHvxeBzVgbN4SCnB.YIfwV8rt1La9UsIrK3CZIPiagUDlbcC', NULL, NULL, '2');
INSERT INTO `person` VALUES ('12', '2024-05-25 12:56:24.422375', 'anonymousUser', NULL, NULL, 'std6@gmail.com', '0123456789', 'Std6', '/assets/images/user-profiles/std6_972af9fc-b16a-43f9-a5eb-301cbd7494fc.jpeg', '$2a$10$a9EnQeJuH4.tRbG01vpMlO1Z92ZyACHy/9ys1sPPb5FinIpNWRhbK', NULL, NULL, '2');
INSERT INTO `person` VALUES ('13', '2024-05-25 12:56:45.656639', 'anonymousUser', NULL, NULL, 'std7@gmail.com', '0123456789', 'Std7', '/assets/images/user-profiles/std7_408a06a5-dbe9-4f15-92ff-a51179eee240.jpeg', '$2a$10$ORBlXrIdL8ev2yIU2twVsuvvNwyKtBprwOuFdw88ahcrFFw7dxEyO', NULL, NULL, '2');
INSERT INTO `person` VALUES ('14', '2024-05-25 12:57:12.062941', 'anonymousUser', NULL, NULL, 'std8@gmail.com', '0123456789', 'Std8', '/assets/images/user-profiles/std8_1864a7ae-44d2-4fbb-87fc-b53e406f44f7.jpeg', '$2a$10$diJ2x0lwHAG/jklXTYwLSOUvgvR8Qn3Hng.XEvVVrn5kIktJtXWyC', NULL, NULL, '2');
INSERT INTO `person` VALUES ('15', '2024-05-25 12:57:30.026623', 'anonymousUser', NULL, NULL, 'std9@gmail.com', '0132456789', 'Std9', '/assets/images/user-profiles/std9_b147359c-f137-4a26-afe5-719bce4e7f90.jpeg', '$2a$10$ZQ9rMSYsuYG5iB5K5r1Yku26LHzPksuGPijRpAC60VUypy9dNMWNe', NULL, NULL, '2');
INSERT INTO `person` VALUES ('16', '2024-05-25 12:57:49.037025', 'anonymousUser', NULL, NULL, 'std10@gmail.com', '0123456789', 'Std10', '/assets/images/user-profiles/std10_898bf99a-d9ba-403c-b792-3a20b805fd51.jpeg', '$2a$10$Tm1k3bgScbGJwQBR/2v.oO7aBSn.2cgEN4csQV442zJ5cnGinnxJi', NULL, NULL, '2');



INSERT INTO `courses` VALUES ('1', '2024-05-25 12:47:39.528869', 'admin@eazyschool.com', NULL, NULL, '/assets/images/course-images/course1_029b14d0-7d30-40eb-b492-133297a144f3.jpeg', 'Calculus focuses on some important topics covered in math such as differentiation, integration, limits, functions, and so on. Calculus, a branch of mathematics, deals with the study of the rate of change and it was developed by Newton and Leibniz.', '999', 'Calculus ');
INSERT INTO `courses` VALUES ('2', '2024-05-25 12:48:42.144175', 'admin@eazyschool.com', NULL, NULL, '/assets/images/course-images/course2_701ce535-a65c-4127-b28d-464a3ef2f15f.jpeg', 'Mathematics simply means to learn or to study or gain knowledge. The theories and concepts given in mathematics help us understand and solve various types of problems in academic as well as in real life situations.

Mathematics is a subject of logic. Learning mathematics will help students to grow their problem-solving and logical reasoning skills. Solving mathematical problems is one of the best brain exercises.', '500', 'Math');
INSERT INTO `courses` VALUES ('3', '2024-05-25 12:49:16.003628', 'admin@eazyschool.com', NULL, NULL, '/assets/images/course-images/course3_d67733fa-6aee-4a90-9478-160b31ed5bae.jpeg', 'Physics is a natural science that deals with the structure of matter and the interactions between the fundamental constituents of the observable universe1. It involves the study of matter, its fundamental constituents, its motion and behavior through space and time, and the related entities of energy and force2. The main goal of physics is to understand how the universe behaves2. Physicists assume the existence of mass, length, time and electric current and then define all other physical quantities in terms of these basic units3.', '800', 'Physics');
INSERT INTO `courses` VALUES ('4', '2024-05-25 12:49:50.601516', 'admin@eazyschool.com', NULL, NULL, '/assets/images/course-images/course4_74e489bd-736b-4594-b582-48f7c3e4cda0.jpeg', 'Statistics is a mathematical field that involves:

Collecting, analyzing, interpreting, and presenting data.

Describing properties of data (descriptive statistics).

Drawing conclusions about a population based on information in a sample (inferential statistics).

Reliance on differential and integral calculus, linear algebra, and probability theory', '499', 'Statistics');
INSERT INTO `courses` VALUES ('5', '2024-05-25 12:51:27.561552', 'admin@eazyschool.com', NULL, NULL, '/assets/images/course-images/course5_1c8304ba-7f2d-4f62-bbe6-9a18becd4bec.jpeg', 'A General English course is a type of language course that aims to improve a learner�s overall English language skills. It covers a wide range of topics and is designed to help learners gain confidence in using English in everyday situations. Here are some key aspects of a General English course:



Everyday English: The course helps you improve your knowledge of everyday English and learn the language you need for socialising1.

Listening and Reading Practice: It provides extra listening and reading practice to help you improve your level of English.

Comprehensive Curriculum: The course explore', '399', 'English');


INSERT INTO `holidays` VALUES (' Dec 25 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Christmas', 'FESTIVAL');
INSERT INTO `holidays` VALUES (' Jan 1 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'New Year''s Day', 'FESTIVAL');
INSERT INTO `holidays` VALUES (' Jan 17 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Martin Luther King Jr. Day', 'FEDERAL');
INSERT INTO `holidays` VALUES (' July 4 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Independence Day', 'FEDERAL');
INSERT INTO `holidays` VALUES (' Nov 11 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Veterans Day', 'FEDERAL');
INSERT INTO `holidays` VALUES (' Nov 24 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Thanksgiving Day', 'FESTIVAL');
INSERT INTO `holidays` VALUES (' Oct 31 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Halloween', 'FESTIVAL');
INSERT INTO `holidays` VALUES (' Sep 5 ', '2024-05-25 00:00:00', 'DBA', NULL, NULL, 'Labor Day', 'FEDERAL');


INSERT INTO `person_courses` VALUES ('0', NULL, NULL, '2', '1');
INSERT INTO `person_courses` VALUES ('3', 'UNREGISTER', 'PENDING_UNREGISTRATION', '7', '1');
INSERT INTO `person_courses` VALUES ('5', 'UNREGISTER', 'PENDING_UNREGISTRATION', '8', '1');
INSERT INTO `person_courses` VALUES ('0', 'NONE', 'REGISTERED', '9', '1');
INSERT INTO `person_courses` VALUES ('0', NULL, NULL, '3', '2');
INSERT INTO `person_courses` VALUES ('5', 'NONE', 'REGISTERED', '8', '2');
INSERT INTO `person_courses` VALUES ('0', NULL, NULL, '4', '3');
INSERT INTO `person_courses` VALUES ('0', NULL, NULL, '5', '4');
INSERT INTO `person_courses` VALUES ('0', NULL, NULL, '2', '5');
INSERT INTO `person_courses` VALUES ('1', 'NONE', 'REGISTERED', '7', '5');

INSERT INTO `course_material` VALUES ('1', '2024-05-25 15:58:02.457140', 'lec1@gmail.com', NULL, NULL, 'course-materials/Calculus_/Java Programming Cheatsheet.pdf', 'Java Programming Cheatsheet.pdf', '1');
