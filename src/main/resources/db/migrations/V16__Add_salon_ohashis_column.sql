-- V16__Add_salon_ohashis_column.sql
-- Agregar columna salon_ohashis a cash_renditions

ALTER TABLE cash_renditions
    ADD COLUMN salon_ohashis INTEGER NOT NULL DEFAULT 0;

-- Constraint para asegurar que el valor sea no negativo
ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_salon_ohashis_non_negative CHECK (salon_ohashis >= 0);

