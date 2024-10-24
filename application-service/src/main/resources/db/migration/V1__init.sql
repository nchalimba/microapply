CREATE TABLE `applications`
(
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT,
    `job_id`            BIGINT(20) NOT NULL,
    `user_id`           BIGINT(20) NOT NULL,
    `status`            VARCHAR(255) NOT NULL,
    `application_date`  DATETIME DEFAULT NULL,
    `resume_url`        VARCHAR(255) DEFAULT NULL,
    `cover_letter_url`  VARCHAR(255) DEFAULT NULL,
    `notes`             TEXT DEFAULT NULL,
    `internal_notes`    TEXT DEFAULT NULL,
    PRIMARY KEY (`id`)
);
