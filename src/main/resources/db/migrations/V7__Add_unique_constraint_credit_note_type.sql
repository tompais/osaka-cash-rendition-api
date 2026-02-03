-- V7: Agregar constraint único para type + cash_rendition_id en credit_notes
-- Asegura que solo haya una nota de crédito de cada tipo por rendimiento de caja

ALTER TABLE credit_notes
    ADD CONSTRAINT UQ_credit_notes_type_per_rendition UNIQUE (cash_rendition_id, type);

