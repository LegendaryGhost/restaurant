SELECT sum(contain.quantity * contain.price) AS income,
       sum(contain.quantity * contain.commission) AS total_commission,
       sum(contain.quantity * contain.product_price) AS depense
FROM command
    INNER JOIN contain
        ON command.id = contain.id_command;