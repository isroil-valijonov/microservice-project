INSERT INTO users (first_name, last_name, email, phone_number)
values ('Ali', 'Bek', 'jon@gmail.com', '944906677') ON CONFLICT (email) DO NOTHING;