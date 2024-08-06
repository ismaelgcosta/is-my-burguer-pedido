-- liquibase formatted sql
-- changeset ismyburguer:3

delete from item_pedido;
commit;
delete from pedido;
commit;
