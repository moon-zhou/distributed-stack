CREATE TABLE article (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         content TEXT NOT NULL,
                         FULLTEXT (title, content),
													FULLTEXT (title),
													FULLTEXT (content)
    ) ENGINE = InnoDB;


DROP TABLE article;
DROP TABLE IF EXISTS article;

SELECT count(id) FROM article;
SELECT * FROM article WHERE id=99999;