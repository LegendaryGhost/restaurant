CREATE DATABASE restaurant;
USE restaurant;

CREATE TABLE client(
   id INT AUTO_INCREMENT,
   lastname VARCHAR(50)  NOT NULL,
   firstname VARCHAR(50) ,
   phone VARCHAR(50)  NOT NULL,
   password VARCHAR(50)  NOT NULL,
   type VARCHAR(50) ,
   PRIMARY KEY(id)
);

CREATE TABLE dish(
   id INT AUTO_INCREMENT,
   name VARCHAR(50)  NOT NULL,
   image VARCHAR(255)   NOT NULL,
   price DECIMAL(15,2)   NOT NULL,
   product_price DECIMAL(15,2)   NOT NULL,
   commission DECIMAL(15,2)   NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE command(
   id INT AUTO_INCREMENT,
   date_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   id_buyer INT NOT NULL,
   id_suggester INT,
   PRIMARY KEY(id),
   FOREIGN KEY(id_buyer) REFERENCES client(id),
   FOREIGN KEY(id_suggester) REFERENCES client(id)
);

CREATE TABLE contain(
   id_dish INT,
   id_command INT,
   quantity INT NOT NULL DEFAULT 1,
   price DECIMAL(15,2)   NOT NULL,
   product_price DECIMAL(15,2)   NOT NULL,
   commission DECIMAL(15,2)   NOT NULL,
   PRIMARY KEY(id_dish, id_command),
   FOREIGN KEY(id_dish) REFERENCES dish(id),
   FOREIGN KEY(id_command) REFERENCES command(id)
);


-- Test data --
INSERT INTO client(lastname, firstname, phone, password, type) VALUES
   ('Mbolatsiory', 'Rihantiana', '+261326906969', 'rihantiana', 'suggester'),
   ('Rabenarivo', 'Ryan', '+261336906969', 'ryan', 'normal'),
   ('Ramilison', 'Malko', '+261346906969', 'malko', 'normal'),
   ('Razafindratandra', 'Hery', '+261356906969', 'hery', 'normal'),
   ('Rasolofomanana', 'Tina', '+261366906969', 'tina', 'suggester'),
   ('Randrianarisoa', 'Lalaina', '+261376906969', 'lalaina', 'normal'),
   ('Rasolondrazaka', 'Andry', '+261386906969', 'andry', 'suggester'),
   ('Rakotondramanana', 'Mialy', '+261396906969', 'mialy', 'normal');

INSERT INTO dish(name, image, price, product_price, commission) VALUES
   ('Soupe atody', 'soupe_oeuf.jpg', 1200, 800, 200),
   ('Ravitoto sy Voanio', 'ravitoto.jpg', 1500, 1000, 250),
   ('Lasary Voatabia', 'lasary.jpg', 800, 500, 150),
   ('Ravitoto sy Hena-kisoa', 'ravitoto.jpg', 1800, 1200, 300),
   ('Romazava', 'romazava.jpg', 1000, 600, 180);

-- Additional data for the ticket and contain tables --
-- Note: Replace the buyer and suggester IDs with existing client IDs from the client table
INSERT INTO command(date_time, id_buyer, id_suggester) VALUES
   ('2023-12-08 12:30:00', 1, null),
   ('2023-12-08 12:30:00', 2, 1),
   ('2023-12-08 13:45:00', 3, 1),
   ('2023-12-09 14:15:00', 4, NULL),
   ('2023-12-09 15:30:00', 6, 5);

-- Note: Replace the dish IDs with existing dish IDs from the dish table
INSERT INTO contain(id_dish, id_command, quantity, price, product_price, commission) VALUES
   (1, 1, 2, 1200, 800, 200),
   (2, 1, 1, 1500, 1000, 250),
   (1, 2, 3, 1200, 800, 200),
   (2, 2, 2, 1500, 1000, 250),
   (3, 3, 1, 800, 500, 150),
   (4, 3, 2, 1800, 1200, 300),
   (3, 4, 1, 800, 500, 150),
   (4, 4, 3, 1800, 1200, 300),
   (1, 5, 2, 1200, 800, 200),
   (2, 5, 1, 1500, 1000, 250);
