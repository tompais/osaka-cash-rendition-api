-- V3__Add_additional_data.sql
-- Agregar columnas de datos adicionales (cuantitativos) al rendimiento de caja

-- Datos del comedor (dining room)
ALTER TABLE cash_renditions
    ADD COLUMN salon_otoshis INTEGER NOT NULL DEFAULT 0;

-- Datos de delivery Osaka
ALTER TABLE cash_renditions
    ADD COLUMN dely_osk_ohashis INTEGER NOT NULL DEFAULT 0;

ALTER TABLE cash_renditions
    ADD COLUMN dely_osk_orders INTEGER NOT NULL DEFAULT 0;

-- Datos de delivery Nori Taco
ALTER TABLE cash_renditions
    ADD COLUMN dely_nt_orders INTEGER NOT NULL DEFAULT 0;

ALTER TABLE cash_renditions
    ADD COLUMN dely_nt_ohashis INTEGER NOT NULL DEFAULT 0;

-- Constraints para asegurar que los valores sean no negativos
ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_salon_otoshis_non_negative CHECK (salon_otoshis >= 0);

ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_dely_osk_ohashis_non_negative CHECK (dely_osk_ohashis >= 0);

ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_dely_osk_orders_non_negative CHECK (dely_osk_orders >= 0);

ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_dely_nt_orders_non_negative CHECK (dely_nt_orders >= 0);

ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_dely_nt_ohashis_non_negative CHECK (dely_nt_ohashis >= 0);

