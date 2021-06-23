INSERT INTO "users" (email, first_name, last_name) SELECT 'juan@prato.com.ar', 'Juan', 'Prato' WHERE
    NOT EXISTS (
        SELECT user_id FROM "users" WHERE user_id = 1
    );
INSERT INTO "users" (email, first_name, last_name) SELECT 'otro@mail.com.ar', 'Otra', 'Persona' WHERE
    NOT EXISTS (
        SELECT user_id FROM "users" WHERE user_id = 2
    );
INSERT INTO "users" (email, first_name, last_name) SELECT 'ejemplo@example.com.ar', 'Tomas', 'Proto' WHERE
    NOT EXISTS (
        SELECT user_id FROM "users" WHERE user_id = 3
    );
INSERT INTO "users" (email, first_name, last_name) SELECT 'example@mail.com.ar', 'Salomon', 'Frederico' WHERE
    NOT EXISTS (
            SELECT user_id FROM "users" WHERE user_id = 4
        );

-- LOANS
do
'
declare
begin
    FOR i IN 1 .. 150 LOOP
        INSERT INTO "loans" (total, user_id) SELECT round(random()* (10000-1 + 1) + 1, 2), floor(random()* (3-1 + 1) + 1) WHERE NOT EXISTS(SELECT id FROM "loans" WHERE id=i);
    END LOOP;
end;
' language PLPGSQL;