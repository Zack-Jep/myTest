-- This is the schema file that the database is initialized with. It is specific to the H2 SQL dialect.
-- Author: Rasmus Ros, rasmus.ros@cs.lth.se


-- User roles describe what each user can do on a generic level.
CREATE TABLE user_role(role_id TINYINT,
                       role VARCHAR(10) NOT NULL UNIQUE,
                       PRIMARY KEY (role_id));

CREATE TABLE user(user_id INT AUTO_INCREMENT NOT NULL,
                  role_id TINYINT NOT NULL,
                  username VARCHAR_IGNORECASE NOT NULL UNIQUE, -- username should be unique
                  salt BIGINT NOT NULL,
                  password_hash UUID NOT NULL,
                  PRIMARY KEY (user_id),
                  FOREIGN KEY (role_id) REFERENCES user_role (role_id),
                  CHECK (LENGTH(username) >= 4)); -- ensures that username have 4 or more characters

-- Sessions are indexed by large random numbers instead of a sequence of integers, because they could otherwise
-- be guessed by a malicious user.
CREATE TABLE session(session_uuid UUID DEFAULT RANDOM_UUID(),
                     user_id INT NOT NULL,
                     last_seen TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
                     PRIMARY KEY(session_uuid),
                     FOREIGN KEY(user_id) REFERENCES user(user_id) ON DELETE CASCADE);

INSERT INTO user_role VALUES (1, 'ADMIN'), (2, 'USER');
INSERT INTO user (role_id, username, salt, password_hash)
    VALUES (1, 'Admin', -2883142073796788660, '8dc0e2ab-4bf1-7671-c0c4-d22ffb55ee59'),
           (2, 'Test', 5336889820313124494, '144141f3-c868-85e8-0243-805ca28cdabd');

CREATE TABLE trip(
    -- First the four columns are specified:

    tripId INT AUTO_INCREMENT,
    -- auto increment the trip ids so each can be uniquely identified.
    --payload VARCHAR NOT NULL,
    -- payload is the second column with type VARCHAR. This is the data that is typed in the input field
    -- on the foo tab in the front end. There is no limit to how long the string can be.
    -- NOT NULL specifies that the row must have a payload.

    driverId INT,
    -- user_id is the third column with type INT. This keeps track of who the user is, so that each user has their own
    -- foos only.

    --created TIMESTAMP DEFAULT CURRENT_TIMESTAMP() NOT NULL,
    -- created is the fourth and final column with type TIMESTAMP. This column also has a default value
    -- which is created by the function CURRENT_TIMESTAMP() if not supplied during creation.
    passengers ARRAY,

    capacity INT,

    ratingRequired INT,

    departureTime VARCHAR,

    expectedArrivalTime VARCHAR,

    coordinates ARRAY,

    destinationAddress NVARCHAR,

    departureAddress NVARCHAR,

    distance FLOAT,

    completed BOOLEAN,

    co2Saved FLOAT,

    -- Here are some additional constraints that the data must relate to:

    PRIMARY KEY(tripId),
    -- This defines trip_id as the unique identifier of the table. It adds NOT NULL to the column and
    -- enforces that the values rows all have a unique identifier.
    --

    -- Maybe we should add a foreign key to link the tables?
    -- This informs that the column user_id is a relation to another table's primary key. In combination
    -- with the NOT NULL constraint above it is not possible to enter data that is not connected to a user.
    -- Note that there can be multiple rows with the same user_id (but the trip_id is unique for each row).
);
INSERT INTO trip VALUES (1,1,(4,5,6),5,3,'2018-10-08T08:00','2018-10-08T08:45',(55.7029296,13.1929449,55.6052931,13.0001566),'Lund, Sverige','Malmö, Sverige',22.5,FALSE,0),
(2,1,(4,5,6),5,3,'2018-10-08T08:00','2018-10-08T08:45',(55.6052931,13.0001566,55.7029296,13.1929449),'Malmö, Sverige','Lund, Sverige',22.5,FALSE,0);