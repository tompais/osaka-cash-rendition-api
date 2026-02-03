-- Add exchange_rate_snapshot and exchange_rate_id columns to reliefs table
ALTER TABLE reliefs
    ADD COLUMN exchange_rate_snapshot DECIMAL(19, 4) NOT NULL DEFAULT 1.0,
    ADD COLUMN exchange_rate_id UUID NOT NULL;

-- Remove default value after adding the column (it was only needed for existing rows)
ALTER TABLE reliefs
    ALTER COLUMN exchange_rate_snapshot DROP DEFAULT;

-- Add foreign key constraint
ALTER TABLE reliefs
    ADD CONSTRAINT fk_reliefs_exchange_rate
        FOREIGN KEY (exchange_rate_id)
            REFERENCES exchange_rates (id);

-- Create index for foreign key
CREATE INDEX idx_reliefs_exchange_rate_id
    ON reliefs (exchange_rate_id);

-- Add comments
COMMENT
ON COLUMN reliefs.exchange_rate_snapshot IS 'Snapshot de la tasa de conversión a ARS al momento del alivio (1.0 para ARS)';
COMMENT
ON COLUMN reliefs.exchange_rate_id IS 'Referencia al tipo de cambio usado al momento del alivio';
COMMENT
ON COLUMN reliefs.exchange_rate_id IS 'Referencia al tipo de cambio usado (para histórico/auditoría, NULL para alivios en ARS)';


