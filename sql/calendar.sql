CREATE DATABASE calendar;
\c calendar;

-- TABLES:
    DROP TABLE IF EXISTS workspace CASCADE;
    CREATE TABLE workspace(
        id SERIAL PRIMARY KEY,
        label VARCHAR(50) NOT NULL UNIQUE,
        color VARCHAR(50) NOT NULL UNIQUE
    );

    DROP TABLE IF EXISTS reservation CASCADE;
    CREATE TABLE reservation(
        id SERIAL PRIMARY KEY,
        title VARCHAR(255) NOT NULL,
        date_start TIMESTAMP NOT NULL,
        date_end TIMESTAMP NOT NULL,
        id_workspace INT NOT NULL REFERENCES workspace(id) ON DELETE CASCADE
    );

-- VIEW:
    DROP VIEW IF EXISTS v_label_reservation CASCADE;
    CREATE OR REPLACE VIEW v_label_reservation AS
        SELECT
            r.id,
            r.title,
            r.date_start,
            r.date_end,
            r.id_workspace,
            w.label AS workspace,
            w.color
        FROM
            reservation AS r
        JOIN
            workspace AS w ON r.id_workspace = w.id
    ;

-- DATA:
    INSERT INTO workspace(id, label, color) VALUES
        (1, 'Workspace 1', 'red'),
        (2, 'Workspace 2', 'green'),
        (3, 'Workspace 3', 'blue');

    INSERT INTO reservation(title, date_start, date_end, id_workspace) VALUES
        ('Test', NOW(), NOW() + INTERVAL '1 day', 1);