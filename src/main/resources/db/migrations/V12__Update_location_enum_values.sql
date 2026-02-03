-- V12__Update_location_enum_values.sql
-- Actualizar el tipo ENUM location para tener los valores correctos: COLEGIALES y PUERTO_MADERO

-- Paso 1: Crear un nuevo tipo ENUM con los valores correctos
CREATE TYPE location_new AS ENUM ('COLEGIALES', 'PUERTO_MADERO');

-- Paso 2: Alterar la columna location en cash_renditions para usar el nuevo tipo
-- Primero convertimos a text, luego al nuevo tipo
ALTER TABLE cash_renditions
    ALTER COLUMN location TYPE location_new
        USING CASE
                  WHEN location::text = 'PALERMO' THEN 'COLEGIALES'::location_new
                  WHEN location::text = 'DEVOTO' THEN 'PUERTO_MADERO'::location_new
                  WHEN location::text = 'ALMAGRO' THEN 'COLEGIALES'::location_new
                  ELSE 'COLEGIALES'::location_new
        END;

-- Paso 3: Eliminar el tipo ENUM antiguo
DROP TYPE location;

-- Paso 4: Renombrar el nuevo tipo al nombre original
ALTER TYPE location_new RENAME TO location;
