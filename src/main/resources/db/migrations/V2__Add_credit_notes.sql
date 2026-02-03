-- V2__Add_credit_notes.sql
-- Crear tipos ENUM para CreditNoteType
CREATE TYPE credit_note_type AS ENUM ('INFOREST', 'MANUAL_A', 'MANUAL_B');

-- Tabla de notas de crédito
CREATE TABLE credit_notes
(
    id                UUID             NOT NULL DEFAULT uuid_generate_v4(),
    cash_rendition_id UUID             NOT NULL,
    type              credit_note_type NOT NULL,
    amount            NUMERIC(19, 2)   NOT NULL,
    notes             TEXT,
    CONSTRAINT PK_credit_notes PRIMARY KEY (id),
    CONSTRAINT FK_credit_notes_cash_rendition FOREIGN KEY (cash_rendition_id) REFERENCES cash_renditions (id) ON DELETE CASCADE,
    CONSTRAINT CK_credit_notes_amount_positive CHECK (amount > 0),
    CONSTRAINT CK_credit_notes_notes_length CHECK (notes IS NULL OR LENGTH(notes) <= 500)
);

-- Índice para búsquedas de notas de crédito por rendimiento
CREATE INDEX idx_credit_notes_rendition ON credit_notes (cash_rendition_id);

