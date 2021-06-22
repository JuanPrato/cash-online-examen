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

INSERT INTO "loans" (total, user_id) SELECT 150.00, 1 WHERE
    NOT EXISTS (
        SELECT id FROM "loans" WHERE id = 1
    );
INSERT INTO "loans" (total, user_id) SELECT 75.32, 1 WHERE
    NOT EXISTS (
        SELECT id FROM "loans" WHERE id = 2
    );
INSERT INTO "loans" (total, user_id) SELECT 75.32, 2 WHERE
    NOT EXISTS (
        SELECT id FROM "loans" WHERE id = 3
    );