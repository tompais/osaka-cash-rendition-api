-- Insertar usuarios iniciales para testing
-- Tomás Pais
INSERT INTO users (id, first_name, last_name, dni)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Tomás', 'Pais', 39372050)
ON CONFLICT (dni) DO NOTHING;

-- Mariángeles Somma
INSERT INTO users (id, first_name, last_name, dni)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Mariángeles', 'Somma', 38625110)
ON CONFLICT (dni) DO NOTHING;
