-- INSERT INTO UserApp (email, password, role) VALUES ('hugo@gmail.com', '123456', 'owner');

insert into collection (user_id, title) VALUES ('sprieto@gmail.com','ratas');
insert into collection (user_id, title) VALUES ('lamateus@unbosque.edu.co','monkey');
insert into art (collection_id, image, title, price) VALUES ('1','BNsLro8hTv.png','rat','200');
insert into art (collection_id, image, title, price) VALUES ('2','mvjJCYzK0G.webp','Maniac Rat','52000');
insert into likeart (user_id, art_id) VALUES ('sprieto@gmail.com', '1');
insert into likeart (user_id, art_id) VALUES ('lamateus@unbosque.edu.co', '1');
insert into likeart (user_id, art_id) VALUES ('lamateus@unbosque.edu.co', '2');