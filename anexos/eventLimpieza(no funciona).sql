CREATE EVENT limpiezaPujas
    ON SCHEDULE
      EVERY 1 second
    COMMENT 'Elimina todas la pujas menos la ganadora una vez finalizada la subasta'
    DO
    delete from subastas.pujas where idArticulo = (select idArticulo from articulos where fechaFin < now()) and importe not in(select importe from pujas group by idArticulo order by importe desc)