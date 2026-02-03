-- Create exchange_rates table
CREATE TABLE exchange_rates
(
    id          UUID           NOT NULL,
    currency    VARCHAR(10)    NOT NULL,
    rate_to_ars DECIMAL(19, 4) NOT NULL,
    valid_from  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    valid_until TIMESTAMP NULL,
    CONSTRAINT pk_exchange_rates PRIMARY KEY (id),
    CONSTRAINT chk_exchange_rates_rate CHECK (rate_to_ars > 0),
    CONSTRAINT chk_exchange_rates_dates CHECK (valid_until IS NULL OR valid_until > valid_from)
);

-- Create indexes for efficient queries
CREATE INDEX idx_exchange_rates_currency_valid
    ON exchange_rates (currency, valid_from, valid_until);

CREATE INDEX idx_exchange_rates_active
    ON exchange_rates (currency) WHERE valid_until IS NULL;

-- Add comment to table
COMMENT
ON TABLE exchange_rates IS 'Tipos de cambio de monedas a pesos argentinos (ARS tiene rate 1.0)';
COMMENT
ON COLUMN exchange_rates.rate_to_ars IS '1 unidad de moneda = X pesos argentinos (ARS = 1.0)';
COMMENT
ON COLUMN exchange_rates.valid_from IS 'Fecha desde la cual el tipo de cambio es válido';
COMMENT
ON COLUMN exchange_rates.valid_until IS 'Fecha hasta la cual el tipo de cambio es válido (NULL = actualmente vigente)';

-- Insert default exchange rate for ARS (always 1.0)
INSERT INTO exchange_rates (id, currency, rate_to_ars, valid_from, valid_until)
VALUES (gen_random_uuid(), 'ARS', 1.0, CURRENT_TIMESTAMP, NULL);

