USE agenhotel;

-- Execute este arquivo apenas uma vez quando o banco ja existir.
ALTER TABLE reservas
    ADD COLUMN hora_checkin TIME NOT NULL DEFAULT '14:00:00' AFTER data_checkin,
    ADD COLUMN hora_checkout TIME NOT NULL DEFAULT '12:00:00' AFTER data_checkout;
