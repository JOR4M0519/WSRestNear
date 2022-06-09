-- INSERT INTO
insert into userapp (user_id, password, name, lastname, role, fcoins) VALUES ('sprieto@gmail.com','12345','Santiago','Prieto','Artista',0)
insert into userapp (user_id, password, name, lastname, role, fcoins) VALUES ('lamateus@unbosque.edu.co','12345','Laura','Mateus','Artista',0)
insert into collection (user_id, title) VALUES ('sprieto@gmail.com','ratas');
insert into collection (user_id, title) VALUES ('lamateus@unbosque.edu.co','monkey');
insert into art (collection_id, image, title, price) VALUES ('1','BNsLro8hTv.png','rat','200');
insert into art (collection_id, image, title, price) VALUES ('2','mvjJCYzK0G.webp','Maniac Rat','52000');
insert into likeart (user_id, image) VALUES ('sprieto@gmail.com', '1');
insert into likeart (user_id, image) VALUES ('lamateus@unbosque.edu.co', '1');
insert into likeart (user_id, image) VALUES ('lamateus@unbosque.edu.co', '2');
INSERT INTO public.wallet_history(user_id, wtype, fcoins, image, registeredat) VALUES ('santiago1@gmail.com', 'Recarga', 500, null, '8/06/2022');

DELETE from likeart WHERE user_id = 'sprieto@gmail.com' AND image = 'BNsLro8hTv.png';
DELETE FROM ownership WHERE image = '8lcJl4kVQC.jpg';
DELETE from art WHERE image = '8lcJl4kVQC.jpg';
DELETE from collection WHERE collection_id = 3;
DELETE from userapp WHERE user_id = 'lalo1@gmail.com';