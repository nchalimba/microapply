CREATE TABLE `jobs`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `title`         varchar(255) DEFAULT NULL,
    `description`   varchar(255) DEFAULT NULL,
    `company`       varchar(255) DEFAULT NULL,
    `location`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
