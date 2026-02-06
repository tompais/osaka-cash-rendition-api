-- V20__Rename_delivery_columns_to_orders.sql
-- Renombrar columnas de delivery a orders para mayor claridad
-- Diferenciar entre delivery sales (ventas) y delivery orders (pedidos)

-- Renombrar columnas de Osaka
ALTER TABLE cash_renditions
    RENAME COLUMN dely_osk_ohashis TO osaka_ohashis;

ALTER TABLE cash_renditions
    RENAME COLUMN dely_osk_orders TO osaka_orders;

-- Renombrar columnas de Nori Taco
ALTER TABLE cash_renditions
    RENAME COLUMN dely_nt_orders TO nori_taco_orders;

ALTER TABLE cash_renditions
    RENAME COLUMN dely_nt_ohashis TO nori_taco_ohashis;

-- Renombrar constraints asociados
ALTER TABLE cash_renditions
    RENAME CONSTRAINT CK_cash_renditions_dely_osk_ohashis_non_negative TO CK_cash_renditions_osaka_ohashis_non_negative;

ALTER TABLE cash_renditions
    RENAME CONSTRAINT CK_cash_renditions_dely_osk_orders_non_negative TO CK_cash_renditions_osaka_orders_non_negative;

ALTER TABLE cash_renditions
    RENAME CONSTRAINT CK_cash_renditions_dely_nt_orders_non_negative TO CK_cash_renditions_nori_taco_orders_non_negative;

ALTER TABLE cash_renditions
    RENAME CONSTRAINT CK_cash_renditions_dely_nt_ohashis_non_negative TO CK_cash_renditions_nori_taco_ohashis_non_negative;


