--- Find arts
SELECT
    a.art_id,
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

--Find if an user liked an art
SELECT
    COUNT(*) AS likes
FROM art a
         JOIN likeart l
              ON l."art_id" = a."art_id"
         JOIN userapp u
              ON l."user_id" = u."user_id"
                     AND a.title = 'rat'
                     And u.user_id = 'sprieto@gmail.com';

-- Find art_id
SELECT
    art_id
FROM art
WHERE image = 'BNsLro8hTv.png';

-- Find collection_id
SELECT
    a.collection_id
FROM art a
         JOIN collection c
              ON a."collection_id" = c."collection_id"
                  AND c."user_id" = 'sprieto@gmail.com'
                  AND c."title" = 'ratas';