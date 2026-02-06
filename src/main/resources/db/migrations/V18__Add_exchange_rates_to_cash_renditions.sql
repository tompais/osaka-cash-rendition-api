-- V18__Add_exchange_rates_to_cash_renditions.sql
-- Agregar columnas de tasas de cambio específicas a cada rendimiento de caja
-- Esto reemplaza la relación de reliefs con exchange_rates

-- Agregar columnas de tasas de cambio a cash_renditions
ALTER TABLE cash_renditions
    ADD COLUMN usd_to_ars NUMERIC(19, 4) NOT NULL DEFAULT 1,
    ADD COLUMN brl_to_ars NUMERIC(19, 4) NOT NULL DEFAULT 1,
    ADD COLUMN eur_to_ars NUMERIC(19, 4) NOT NULL DEFAULT 1;

-- Agregar constraints para asegurar que las tasas sean positivas
ALTER TABLE cash_renditions
    ADD CONSTRAINT CK_cash_renditions_usd_to_ars_positive CHECK (usd_to_ars > 0),
    ADD CONSTRAINT CK_cash_renditions_brl_to_ars_positive CHECK (brl_to_ars > 0),
    ADD CONSTRAINT CK_cash_renditions_eur_to_ars_positive CHECK (eur_to_ars > 0);

-- Eliminar las columnas relacionadas a exchange_rates de reliefs
ALTER TABLE reliefs
    DROP CONSTRAINT IF EXISTS FK_reliefs_exchange_rate,
    DROP COLUMN IF EXISTS exchange_rate_id,
    DROP COLUMN IF EXISTS exchange_rate_snapshot;

-- Nota: Mantenemos la tabla exchange_rates para configuraciones globales y referencias futuras
-- pero ya no se usa directamente en los reliefs. Los reliefs ahora usan las tasas del cash_rendition.


