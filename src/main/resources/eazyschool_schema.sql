drop database if exists eazyschool;
create database if not exists eazyschool;

use eazyschool;
/*
# new schema

CREATE TABLE `address` (
  `address_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `zip_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `class` (
  `class_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `contact_msg` (
  `contact_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `mobile_num` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `course_material` (
  `material_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`material_id`),
  KEY `FKmq2qrxp40egs2kfrecuex4vbq` (`course_id`),
  CONSTRAINT `FKmq2qrxp40egs2kfrecuex4vbq` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `courses` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `course_image_path` varchar(255) DEFAULT NULL,
  `description` text,
  `fees` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `holidays` (
  `day` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `type` enum('FESTIVAL','FEDERAL') DEFAULT NULL,
  PRIMARY KEY (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person` (
  `person_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile_number` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `profile_image_path` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `address_id` int DEFAULT NULL,
  `class_id` int DEFAULT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`person_id`),
  UNIQUE KEY `UK_o8tnkglv9n1eeqmo7de7em37n` (`address_id`),
  KEY `FKlwljuqdohcnacf80bhexwtf3j` (`class_id`),
  KEY `FKnpr2oekfnt93l20ykrj3g2n24` (`role_id`),
  CONSTRAINT `FKk7rgn6djxsv2j2bv1mvuxd4m9` FOREIGN KEY (`address_id`) REFERENCES `address` (`address_id`),
  CONSTRAINT `FKlwljuqdohcnacf80bhexwtf3j` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`),
  CONSTRAINT `FKnpr2oekfnt93l20ykrj3g2n24` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `person_courses` (
  `rating` int NOT NULL,
  `request_type` enum('REGISTER','UNREGISTER','NONE') DEFAULT NULL,
  `status` enum('REGISTERED','UNREGISTERED','PENDING_REGISTRATION','PENDING_UNREGISTRATION') DEFAULT NULL,
  `person_id` int NOT NULL,
  `course_id` int NOT NULL,
  PRIMARY KEY (`course_id`,`person_id`),
  KEY `FKjyketdhncnotxb93mh0x20nw6` (`person_id`),
  CONSTRAINT `FKg3xih8ifih6any232s4x9ady5` FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`),
  CONSTRAINT `FKjyketdhncnotxb93mh0x20nw6` FOREIGN KEY (`person_id`) REFERENCES `person` (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


###################################################################################################################


/*
CREATE TABLE IF NOT EXISTS `contact_msg` (
  `contact_id` int AUTO_INCREMENT PRIMARY KEY,
  `name`       varchar(100) NOT NULL,
  `mobile_num` varchar(10)  NOT NULL,
  `email`      varchar(100) NOT NULL,
  `subject`    varchar(100) NOT NULL,
  `message`    varchar(500) NOT NULL,
  `status`     varchar(10)  NOT NULL,
  `created_at` TIMESTAMP    NOT NULL,
  `created_by` varchar(50)  NOT NULL,
  `updated_at` TIMESTAMP   DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `holidays` (
  `day`        varchar(20)  NOT NULL,
  `reason`     varchar(100) NOT NULL,
  `type`       varchar(20)  NOT NULL,
  `created_at` TIMESTAMP    NOT NULL,
  `created_by` varchar(50)  NOT NULL,
  `updated_at` TIMESTAMP   DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `roles` (
  `role_id`    int         NOT NULL AUTO_INCREMENT,
  `role_name`  varchar(50) NOT NULL,
  `created_at` TIMESTAMP   NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP   DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
);

CREATE TABLE IF NOT EXISTS `address` (
  `address_id` int          NOT NULL AUTO_INCREMENT,
  `address1`   varchar(200) NOT NULL,
  `address2`   varchar(200) DEFAULT NULL,
  `city`       varchar(50)  NOT NULL,
  `state`      varchar(50)  NOT NULL,
  `zip_code`   int          NOT NULL,
  `created_at` TIMESTAMP    NOT NULL,
  `created_by` varchar(50)  NOT NULL,
  `updated_at` TIMESTAMP    DEFAULT NULL,
  `updated_by` varchar(50)  DEFAULT NULL,
  PRIMARY KEY (`address_id`)
);

CREATE TABLE IF NOT EXISTS `person` (
  `person_id`          int          NOT NULL AUTO_INCREMENT,
  `name`               varchar(100) NOT NULL,
  `email`              varchar(50)  NOT NULL,
  `mobile_number`      varchar(20)  NOT NULL,
  `pwd`                varchar(200) NOT NULL,
  `role_id`            int          NOT NULL,
  `address_id`         int          NULL,
  `created_at`         TIMESTAMP    NOT NULL,
  `created_by`         varchar(50)  NOT NULL,
  `updated_at`         TIMESTAMP    DEFAULT NULL,
  `updated_by`         varchar(50)  DEFAULT NULL,
  `profile_image_path` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`person_id`),
  FOREIGN KEY (role_id) REFERENCES roles (role_id),
  FOREIGN KEY (address_id) REFERENCES address (address_id)
);

CREATE TABLE IF NOT EXISTS `class` (
  `class_id`   int          NOT NULL AUTO_INCREMENT,
  `name`       varchar(100) NOT NULL,
  `created_at` TIMESTAMP    NOT NULL,
  `created_by` varchar(50)  NOT NULL,
  `updated_at` TIMESTAMP   DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`class_id`)
);

ALTER TABLE `person`
  ADD COLUMN `class_id` int NULL AFTER `address_id`,
  ADD CONSTRAINT `FK_CLASS_CLASS_ID` FOREIGN KEY (`class_id`)
    REFERENCES `class` (`class_id`);

CREATE TABLE IF NOT EXISTS `courses` (
  `course_id`  int          NOT NULL AUTO_INCREMENT,
  `name`       varchar(100) NOT NULL,
  `fees`       varchar(10)  NOT NULL,
  `created_at` TIMESTAMP    NOT NULL,
  `created_by` varchar(50)  NOT NULL,
  `updated_at` TIMESTAMP   DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
);

ALTER TABLE courses
  ADD course_image_path VARCHAR(255),
  ADD description TEXT;


CREATE TABLE IF NOT EXISTS `person_courses` (
  `person_id`  int         NOT NULL,
  `course_id`  int         NOT NULL,
  `created_at` TIMESTAMP   NOT NULL,
  `created_by` varchar(50) NOT NULL,
  `updated_at` TIMESTAMP   DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  FOREIGN KEY (person_id) REFERENCES person (person_id),
  FOREIGN KEY (course_id) REFERENCES courses (course_id),
  PRIMARY KEY (`person_id`, `course_id`)
);

DROP TABLE IF EXISTS `course_material`;
CREATE TABLE `course_material` (
  `material_id` INT AUTO_INCREMENT PRIMARY KEY,
  `name`        VARCHAR(255) NOT NULL,
  `file_path`   VARCHAR(255) NOT NULL,
  `course_id`   INT,
  `created_at`  TIMESTAMP    NOT NULL,
  `created_by`  varchar(50)  NOT NULL,
  `updated_at`  TIMESTAMP   DEFAULT NULL,
  `updated_by`  varchar(50) DEFAULT NULL,
  FOREIGN KEY (course_id) REFERENCES courses (course_id)
);
