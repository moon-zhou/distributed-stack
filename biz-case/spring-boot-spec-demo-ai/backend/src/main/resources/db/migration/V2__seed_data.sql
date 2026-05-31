insert into users (username, password_hash, email, created_at)
values ('admin', '$2a$10$7EqJtq98hPqEX7fNZaFWoOHi6O2vY6R4HIX66D.E8L7L8f7xGTFe.', 'admin@example.com', current_timestamp());

insert into products (name, price)
values ('Keyboard', 199.00),
       ('Mouse', 99.00),
       ('Monitor', 1299.00);
