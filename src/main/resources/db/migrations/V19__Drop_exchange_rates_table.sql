-- V19__Drop_exchange_rates_table.sql
-- Eliminar completamente la tabla exchange_rates y sus dependencias
-- ya que ahora las tasas de cambio están en cada cash_rendition

-- Eliminar el tipo ENUM currency si no se usa en otros lugares
-- (Lo mantenemos porque se usa en reliefs.currency)

-- Eliminar la tabla exchange_rates
DROP TABLE IF EXISTS exchange_rates CASCADE;

-- Nota: La tabla ya no tiene foreign keys desde V18 donde eliminamos FK_reliefs_exchange_rate

