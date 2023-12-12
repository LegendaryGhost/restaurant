SELECT sum(contain.quantity * contain.price) AS income,
       sum(contain.quantity * contain.commission) AS total_commission,
       sum(contain.quantity * contain.product_price) AS depense
FROM command
    INNER JOIN contain
        ON command.id = contain.id_command;

SELECT suggester.id AS id_suggester,
    suggester.firstname AS firstname,
    suggester.lastname AS lastname,
    SUM(contain.commission * contain.quantity) AS total_commission
FROM command
    INNER JOIN contain
        ON command.id = contain.id_command
    INNER JOIN client AS suggester
        ON command.id_suggester = suggester.id
WHERE YEAR(command.date_time) = 2023 AND
    MONTH(command.date_time) = 12
GROUP BY suggester.id, suggester.lastname, suggester.firstname;