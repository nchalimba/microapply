ALTER TABLE `applications`
MODIFY COLUMN `resume_url` VARCHAR(255) NOT NULL DEFAULT '',
ADD COLUMN `first_name` VARCHAR(255) NOT NULL,
ADD COLUMN `last_name` VARCHAR(255) NOT NULL,
ADD COLUMN `phone_number` VARCHAR(255) NOT NULL;