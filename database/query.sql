SELECT sum(contain.quantity * contain.price) AS income,
       sum(contain.quantity * contain.commission) AS total_commission,
       sum(contain.quantity * contain.product_price) AS depense
FROM command
    INNER JOIN contain
        ON command.id = contain.id_command;

SELECT suggester.id AS id_suggester,
    suggester.firstname AS firstname,
    suggester.lastname AS lastname,
    SUM(contain.price * contain.quantity) AS restau_income,
    SUM(contain.commission * contain.quantity) AS total_commission
FROM command
    INNER JOIN contain
        ON command.id = contain.id_command
    LEFT JOIN client AS suggester
        ON command.id_suggester = suggester.id
WHERE YEAR(command.date_time) = 2023 AND
    MONTH(command.date_time) = 12
GROUP BY suggester.id, suggester.lastname, suggester.firstname;

SELECT
    COALESCE(
            (
                SELECT
                    COALESCE(
                            SUM(COALESCE(ct.price, 0) * COALESCE(ct.quantity, 0)),
                            0
                        )
                FROM
                    command AS c
                        LEFT JOIN contain AS ct
                                  ON c.id = ct.id_command
                WHERE
                        c.id_suggester = suggester.id
                  AND YEAR(c.date_time) = 2023
                  AND MONTH(c.date_time) < MONTH(command.date_time)
                GROUP BY
                    c.id_suggester  -- Ajout du GROUP BY pour agréger par suggester
            ),
            0
        ) AS cumulative_income,
    COALESCE(
            (
                SELECT
                    COALESCE(
                            SUM(COALESCE(ct.commission, 0) * COALESCE(ct.quantity, 0)),
                            0
                        )
                FROM command AS c
                         LEFT JOIN contain AS ct
                                   ON c.id = ct.id_command
                WHERE c.id_suggester = suggester.id
                  AND YEAR(c.date_time) = 2023
                  AND MONTH(c.date_time) < MONTH(command.date_time)
                GROUP BY
                    c.id_suggester  -- Ajout du GROUP BY pour agréger par suggester
            ),
            0
        ) AS cumulative_commission,
    suggester.id AS id_suggester,
    suggester.firstname AS firstname,
    suggester.lastname AS lastname,
    SUM(contain.price * contain.quantity) AS restau_income,
    SUM(contain.commission * contain.quantity) AS total_commission
FROM command
         LEFT JOIN contain
                    ON command.id = contain.id_command
         LEFT JOIN client AS suggester
                   ON command.id_suggester = suggester.id
WHERE YEAR(command.date_time) = 2023 AND
        MONTH(command.date_time) = 11
GROUP BY suggester.id, suggester.lastname, suggester.firstname;

SELECT
    COALESCE(
        SUM(COALESCE(ct.price, 0) * COALESCE(ct.quantity, 0)),
        0
    )
FROM
    command AS c
        LEFT JOIN contain AS ct
                  ON c.id = ct.id_command
WHERE
    YEAR(c.date_time) = 2023
    AND MONTH(c.date_time) < 12
GROUP BY c.id_suggester;

SELECT
    c.id_suggester AS id_suggester,
    COALESCE(SUM(ct.commission * ct.quantity), 0) AS cumul_commission
FROM
    command AS c
        LEFT JOIN contain AS ct
                  ON c.id = ct.id_command
WHERE
    YEAR(c.date_time) = 2023
    AND MONTH(c.date_time) < 12
GROUP BY c.id_suggester
;