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

