CREATE TABLE IF NOT EXISTS users
(
    id         uuid primary key unique     default gen_random_uuid() not null,
    username   varchar(255) unique                                   not null,
    password   varchar(255)                                          not null,
    name       varchar(255)                                          not null,
    email      varchar(255) unique                                   not null,
    role       varchar(255)
        check (role in ('ROLE_USER',
                        'ROLE_ADMIN'))                               not null,
    created_at timestamp without time zone default localtimestamp    not null,
    updated_at timestamp without time zone default null
);

CREATE TABLE IF NOT EXISTS tasks
(
    id          uuid primary key unique                          default gen_random_uuid() not null,
    name        varchar(255)                                                               not null,
    description text                                                                       not null,
    status      varchar(255)
        check (status in ('PENDING',
                          'IN_PROGRESS',
                          'DONE'))                                                         not null,
    priority    varchar(255)
        check (priority in ('HIGH',
                            'MEDIUM',
                            'LOW'))                                                        not null,
    creator     uuid references users (id) on delete cascade                               not null,
    executor    uuid references users (id) on delete set default default null,
    created_at  timestamp without time zone                      default localtimestamp    not null,
    expires_on  timestamp without time zone                                                not null,
    updated_at  timestamp without time zone                      default null
);