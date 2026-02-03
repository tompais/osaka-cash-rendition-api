-- V1__Initial_schema.sql
-- Crear extensiones necesarias
CREATE
EXTENSION IF NOT EXISTS "uuid-ossp";

-- Crear tipos ENUM para Location
CREATE TYPE location AS ENUM ('PALERMO', 'DEVOTO', 'ALMAGRO');

-- Crear tipos ENUM para Shift
CREATE TYPE shift AS ENUM ('AM', 'PM');

-- Crear tipos ENUM para Currency
CREATE TYPE currency AS ENUM ('ARS', 'USD', 'BRL', 'EUR');

-- Crear tipos ENUM para BigBox ExperienceType
CREATE TYPE experience_type AS ENUM ('GRAND_CUISINE', 'PREMIADOS', 'XPS_BENTO_BOX', 'XS_TO_GO');

-- Crear tipos ENUM para InvoiceType
CREATE TYPE invoice_type AS ENUM ('A', 'B');

-- Crear tipos ENUM para DeliveryPlatform
CREATE TYPE delivery_platform AS ENUM ('RAPPI', 'PEDIDOS_YA', 'NORI_TACO');

-- Tabla de usuarios
CREATE TABLE users
(
    id         UUID         NOT NULL DEFAULT uuid_generate_v4(),
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    dni        INTEGER      NOT NULL,
    CONSTRAINT PK_users PRIMARY KEY (id),
    CONSTRAINT UQ_users_dni UNIQUE (dni),
    CONSTRAINT CK_users_dni_range CHECK (dni >= 1000000 AND dni <= 99999999)
);

-- Índice en DNI para búsquedas rápidas
CREATE INDEX idx_users_dni ON users (dni);

-- Tabla de BigBox (configuración de precios)
CREATE TABLE big_boxes
(
    id              UUID            NOT NULL DEFAULT uuid_generate_v4(),
    experience_type experience_type NOT NULL,
    unit_price      NUMERIC(19, 2)  NOT NULL,
    valid_from      TIMESTAMP       NOT NULL,
    valid_until     TIMESTAMP,
    CONSTRAINT PK_big_boxes PRIMARY KEY (id),
    CONSTRAINT UQ_big_boxes_experience_type UNIQUE (experience_type)
);

-- Índice para búsquedas por tipo de experiencia y validez
CREATE INDEX idx_big_boxes_active ON big_boxes (experience_type, valid_until);

-- Tabla de rendimientos de caja
CREATE TABLE cash_renditions
(
    id                 UUID           NOT NULL DEFAULT uuid_generate_v4(),
    shift              shift          NOT NULL,
    location           location       NOT NULL,
    shift_date         DATE           NOT NULL DEFAULT CURRENT_DATE,
    created_by_user_id UUID           NOT NULL,
    initial_balance    NUMERIC(19, 2) NOT NULL DEFAULT 0,
    marketing          NUMERIC(19, 2) NOT NULL DEFAULT 0,
    current_account    NUMERIC(19, 2) NOT NULL DEFAULT 0,
    CONSTRAINT PK_cash_renditions PRIMARY KEY (id),
    CONSTRAINT FK_cash_renditions_created_by_user FOREIGN KEY (created_by_user_id) REFERENCES users (id),
    CONSTRAINT UQ_cash_renditions_shift_date_location_shift UNIQUE (shift_date, location, shift)
);

-- Índice compuesto para rendimientos de caja (búsquedas comunes por usuario, fecha y local)
CREATE INDEX idx_cash_renditions_user_date_location ON cash_renditions (created_by_user_id, shift_date, location);

-- Tabla de alivios
CREATE TABLE reliefs
(
    id                UUID           NOT NULL DEFAULT uuid_generate_v4(),
    cash_rendition_id UUID           NOT NULL,
    envelope_number   VARCHAR(255)   NOT NULL,
    amount            NUMERIC(19, 2) NOT NULL,
    currency          currency       NOT NULL DEFAULT 'ARS',
    CONSTRAINT PK_reliefs PRIMARY KEY (id),
    CONSTRAINT FK_reliefs_cash_rendition FOREIGN KEY (cash_rendition_id) REFERENCES cash_renditions (id) ON DELETE CASCADE,
    CONSTRAINT UQ_reliefs_envelope_number UNIQUE (envelope_number),
    CONSTRAINT CK_reliefs_amount_positive CHECK (amount > 0)
);

-- Índice para búsquedas de alivios por rendimiento
CREATE INDEX idx_reliefs_rendition ON reliefs (cash_rendition_id);

-- Tabla de ventas con factura
CREATE TABLE invoice_sales
(
    id                UUID           NOT NULL DEFAULT uuid_generate_v4(),
    cash_rendition_id UUID           NOT NULL,
    type              invoice_type   NOT NULL,
    amount            NUMERIC(19, 2) NOT NULL,
    CONSTRAINT PK_invoice_sales PRIMARY KEY (id),
    CONSTRAINT FK_invoice_sales_cash_rendition FOREIGN KEY (cash_rendition_id) REFERENCES cash_renditions (id) ON DELETE CASCADE,
    CONSTRAINT CK_invoice_sales_amount_non_negative CHECK (amount >= 0)
);

-- Índice para búsquedas de ventas con factura por rendimiento
CREATE INDEX idx_invoice_sales_rendition ON invoice_sales (cash_rendition_id);

-- Tabla de ventas por delivery
CREATE TABLE delivery_sales
(
    id                UUID              NOT NULL DEFAULT uuid_generate_v4(),
    cash_rendition_id UUID              NOT NULL,
    platform          delivery_platform NOT NULL,
    amount            NUMERIC(19, 2)    NOT NULL,
    CONSTRAINT PK_delivery_sales PRIMARY KEY (id),
    CONSTRAINT FK_delivery_sales_cash_rendition FOREIGN KEY (cash_rendition_id) REFERENCES cash_renditions (id) ON DELETE CASCADE,
    CONSTRAINT CK_delivery_sales_amount_non_negative CHECK (amount >= 0)
);

-- Índice para búsquedas de ventas por delivery por rendimiento
CREATE INDEX idx_delivery_sales_rendition ON delivery_sales (cash_rendition_id);

-- Tabla de ventas de BigBox
CREATE TABLE big_box_sales
(
    id                  UUID           NOT NULL DEFAULT uuid_generate_v4(),
    cash_rendition_id   UUID           NOT NULL,
    big_box_id          UUID           NOT NULL,
    quantity            INTEGER        NOT NULL,
    unit_price_snapshot NUMERIC(19, 2) NOT NULL,
    CONSTRAINT PK_big_box_sales PRIMARY KEY (id),
    CONSTRAINT FK_big_box_sales_cash_rendition FOREIGN KEY (cash_rendition_id) REFERENCES cash_renditions (id) ON DELETE CASCADE,
    CONSTRAINT FK_big_box_sales_big_box FOREIGN KEY (big_box_id) REFERENCES big_boxes (id),
    CONSTRAINT CK_big_box_sales_quantity_positive CHECK (quantity > 0)
);

-- Índices para ventas de BigBox
CREATE INDEX idx_big_box_sales_rendition ON big_box_sales (cash_rendition_id);
CREATE INDEX idx_big_box_sales_big_box ON big_box_sales (big_box_id);

