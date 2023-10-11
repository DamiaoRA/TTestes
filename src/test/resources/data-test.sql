insert into tb_user (id, email, name, password) VALUES (1, 'admin@ifpb.edu.br', 'admin', '$2a$10$cWS6hcSqL2dOdCOPOxZ1fuB7MPg7Cv4k6KSCC5CkuCa5tfc.zRX0y');
insert into tb_role (id, role) VALUES (1, 'ADMIN');
insert into tb_user_role (user_id, role_id) VALUES (1, 1);


insert into tb_user (id, email, name, password) VALUES (2, 'user@ifpb.edu.br', 'user', '$2a$10$cWS6hcSqL2dOdCOPOxZ1fuB7MPg7Cv4k6KSCC5CkuCa5tfc.zRX0y');
insert into tb_role (id, role) VALUES (2, 'USER');
insert into tb_user_role (user_id, role_id) VALUES (2, 2);


--insert into tb_user (id, email, name, password, role) VALUES (1, 'admin@ifpb.edu.br', 'admin', 'pass', 'ADMIN');
--insert into tb_user (id, email, name, password, role) VALUES (2, 'user@ifpb.edu.br', 'user', 'pass', 'USER');