CREATE TABLE receipts (
  id INT UNSIGNED AUTO_INCREMENT,
  uploaded DATETIME DEFAULT CURRENT_TIME(),
  merchant VARCHAR(255),
  amount DECIMAL(12,2),
  receipt_type INT UNSIGNED,

  PRIMARY KEY (id)
);

CREATE TABLE tags (
    id INT UNSIGNED,
    tag VARCHAR(255)
);