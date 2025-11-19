INSERT INTO catalog.bouquets(id, name, description, price, stockQuantity) VALUES (1, 'Букет "Романтичний"', 'Букет із кущових троянд', 1200.00, 15);
INSERT INTO catalog.bouquets(id, name, description, price, stockQuantity) VALUES (2, 'Букет "З троянд"', 'Букет із троянд сорту Експлорер', 1100.00, 10);
INSERT INTO catalog.bouquets(id, name, description, price, stockQuantity) VALUES (3, 'Букет "Осінній"', 'Букет з хризантем,жоржин та гербер', 950.00, 10);
INSERT INTO catalog.bouquets(id, name, description, price, stockQuantity) VALUES (4, 'Композиція "Ніжна"', 'Композиція із троянд,гортензій та орхідей', 1800.00, 10);
INSERT INTO catalog.bouquets(id, name, description, price, stockQuantity) VALUES (5, 'Композиція "Екзотична"', 'Мікс екзотичних квітів у коробці', 1500.00, 15);

ALTER SEQUENCE catalog.bouquets_seq RESTART WITH 6;