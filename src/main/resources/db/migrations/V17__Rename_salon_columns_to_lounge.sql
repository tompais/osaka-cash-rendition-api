-- V17__Rename_salon_columns_to_lounge.sql
-- Renombrar columnas de salon_* a lounge_* para estandarizar nomenclatura en inglés

-- Renombrar columna salon_otoshis a lounge_otoshis
ALTER TABLE cash_renditions
    RENAME COLUMN salon_otoshis TO lounge_otoshis;

-- Renombrar columna salon_ohashis a lounge_ohashis
ALTER TABLE cash_renditions
    RENAME COLUMN salon_ohashis TO lounge_ohashis;

-- Renombrar constraint de salon_otoshis a lounge_otoshis
ALTER TABLE cash_renditions
    RENAME CONSTRAINT CK_cash_renditions_salon_otoshis_non_negative TO CK_cash_renditions_lounge_otoshis_non_negative;

-- Renombrar constraint de salon_ohashis a lounge_ohashis
ALTER TABLE cash_renditions
    RENAME CONSTRAINT CK_cash_renditions_salon_ohashis_non_negative TO CK_cash_renditions_lounge_ohashis_non_negative;

