--- Find arts
SELECT
    image,
    a.title,
    price,
    c.user_id,
    c.title,
    u.name,
    u.lastname
FROM collection c
         JOIN art a
              ON a."collection_id" = c."collection_id"
         JOIN userapp u
              ON u."user_id" = c."user_id";

-- Find User
SELECT *
FROM userapp
WHERE user_id = "?";

--Likes of an art
SELECT
    COUNT (*) AS likes
FROM art a
         JOIN likeart l
              ON a.image = l.image
                  AND a.image = 'BNsLro8hTv.png';

--Find if an user liked an art
SELECT
    COUNT(*) AS likes
FROM art a
         JOIN likeart l
              ON l."image" = a."image"
         JOIN userapp u
              ON l."user_id" = u."user_id"
                  AND l.image = 'BNsLro8hTv.png'
                  And u.user_id = 'sprieto@gmail.com';

-- Find art_id
SELECT
    art_id
FROM art
WHERE image = 'BNsLro8hTv.png';

--Most liked arts
SELECT
    l.image,
    COUNT (*) AS likes
FROM likeart l
         JOIN art a
              ON a.image = l.image
GROUP BY l.image
ORDER BY COUNT(*) DESC
    limit 3;

--Buy function
UPDATE ownership
SET user_id = 'sprieto@gmail.com'
WHERE user_id = 'jdramoss@gmail.com'
  AND image = 'UjJ2N60ubZ.gif';

--Find Arts by filter
SELECT
    image,
    a.title,
    price,
    c.user_id,
    c.title,
    u.name,
    u.lastname
FROM collection c
         JOIN art a
              ON a."collection_id" = c."collection_id"
         JOIN userapp u
              ON u."user_id" = c."user_id"
                  AND a.title LIKE ?;
--Find Likes By Specific User
SELECT
    l.image
FROM likeart l
         JOIN art a
              ON a.image = l.image
                  AND l.user_id = '?';

-- Find arts bought/create by someone
SELECT
    a.image,
    a.title,
    price,
    c.user_id,
    c.title,
    u.name,
    u.lastname
FROM ownership o
         JOIN art a
              ON o."image" = a."image"
         JOIN collection c
              ON c."collection_id" = a."collection_id"
         JOIN userapp u
              ON c."user_id" = u."user_id"
                  AND o."user_id" = 'santiago1@gmail.com';

-- Find list arts likes of an user
SELECT
    a.image,
    a.title,
    price,
    c.user_id,
    c.title,
    u.name,
    u.lastname
FROM likeart l
         JOIN art a
              ON l."image" = a."image"
         JOIN collection c
              ON c."collection_id" = a."collection_id"
         JOIN userapp u
              ON c."user_id" = u."user_id"
                  AND l."user_id" = 'santiago1@gmail.com';

-- Call number collections & arts all artists
SELECT
    u.user_id,
    u.name,
    u.lastname,
    u.profileImage,
    u.description,
    COUNT (a) AS arts,
    COUNT (DISTINCT c) filter (where c.collection_id = a.collection_id) AS collections
FROM userapp u
         JOIN collection c
              ON c.user_id = u.user_id
         JOIN art a
              ON a.collection_id = c.collection_id
                  AND u.role = 'Artista'
GROUP BY u.user_id;

--call number likes arts from an artist
SELECT
    u.user_id,
    COUNT (l) AS likes
FROM userapp u
         JOIN collection c
              ON c.user_id = u.user_id
         JOIN art a
              ON a.collection_id = c.collection_id
         JOIN likeart l
              ON l.image = a.image
GROUP BY u.user_id
