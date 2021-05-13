USE hwcamqp;
CREATE TABLE amqp_record
(
    id           int           NOT NULL     AUTO_INCREMENT,
    user_name    varchar(255)   NOT NULL ,
    created_time datetime       NULL ,
    record       varchar(255)   NULL ,
    PRIMARY KEY (id)
);