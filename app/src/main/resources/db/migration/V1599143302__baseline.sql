CREATE SCHEMA IF NOT EXISTS app;
ALTER schema app OWNER TO "demouser";

CREATE SCHEMA IF NOT EXISTS acl;
ALTER schema acl OWNER TO "demouser";

CREATE SCHEMA IF NOT EXISTS auth;
ALTER schema auth OWNER TO "demouser";

CREATE SCHEMA IF NOT EXISTS vfs;
ALTER schema vfs OWNER TO "demouser";

CREATE SCHEMA IF NOT EXISTS core_web;
ALTER schema core_web OWNER TO "demouser";

CREATE SCHEMA IF NOT EXISTS messaging;
ALTER schema messaging OWNER TO "demouser";

-- New Migration
create table acl.ac_validation_tokens
(
    id                bigserial    not null,
    created_at        timestamp    not null,
    created_by        varchar(255),
    deleted           boolean      not null,
    updated_at        timestamp,
    updated_by        varchar(255),
    uuid_str          varchar(255) not null,
    token             varchar(255),
    token_valid       boolean      not null,
    token_valid_until timestamp,
    reason            varchar(255),
    username          varchar(255),
    user_id           int8,
    primary key (id)
);
alter table if exists acl.ac_validation_tokens
    add constraint UK_e4b8maumvhg565vctf1au3so2 unique (uuid_str);
create table app.profiles
(
    id                 bigserial    not null,
    created_at         timestamp    not null,
    created_by         varchar(255),
    deleted            boolean      not null,
    updated_at         timestamp,
    updated_by         varchar(255),
    uuid_str           varchar(255) not null,
    age                varchar(255),
    birthday           timestamp    not null,
    blood_group        varchar(255),
    gender             varchar(255),
    name               varchar(255),
    photo              varchar(255),
    photo_identity_url varchar(255),
    address_id         int8,
    user_id            int8,
    primary key (id)
);
create table app.promotions
(
    id               bigserial    not null,
    created_at       timestamp    not null,
    created_by       varchar(255),
    deleted          boolean      not null,
    updated_at       timestamp,
    updated_by       varchar(255),
    uuid_str         varchar(255) not null,
    active           boolean      not null,
    background_color varchar(255),
    click_count      int8         not null,
    description      varchar(2000),
    priority         varchar(255),
    promo_image      varchar(255),
    text_color       varchar(255),
    title            varchar(255) not null,
    url              varchar(255),
    view_count       int8         not null,
    primary key (id)
);
alter table if exists app.profiles
    add constraint UK_m08m124r50l5jc9uoepqb3ves unique (uuid_str);
alter table if exists app.promotions
    add constraint UK_5kbg11yrptyiaukp1u9ox74eq unique (uuid_str);
create table auth.m_users
(
    id                      bigserial    not null,
    created_at              timestamp    not null,
    created_by              varchar(255),
    deleted                 boolean      not null,
    updated_at              timestamp,
    updated_by              varchar(255),
    uuid_str                varchar(255) not null,
    account_non_expired     boolean,
    account_non_locked      boolean,
    credentials_non_expired boolean,
    email                   varchar(255),
    enabled                 boolean      not null,
    gender                  varchar(255) not null,
    name                    varchar(255) not null,
    password                varchar(512) not null,
    phone                   varchar(255),
    username                varchar(255) not null,
    primary key (id)
);
create table auth.m_users_roles
(
    user_id  int8 not null,
    roles_id int8 not null
);
create table auth.privileges
(
    id          bigserial    not null,
    created_at  timestamp    not null,
    created_by  varchar(255),
    deleted     boolean      not null,
    updated_at  timestamp,
    updated_by  varchar(255),
    uuid_str    varchar(255) not null,
    description varchar(255),
    label       varchar(255) not null,
    name        varchar(255) not null,
    primary key (id)
);
create table auth.privileges_access_urls
(
    privilege_id int8 not null,
    access_urls  varchar(255)
);
create table auth.roles
(
    id          bigserial    not null,
    created_at  timestamp    not null,
    created_by  varchar(255),
    deleted     boolean      not null,
    updated_at  timestamp,
    updated_by  varchar(255),
    uuid_str    varchar(255) not null,
    description varchar(255),
    name        varchar(255) not null,
    restricted  boolean      not null,
    primary key (id)
);
create table auth.roles_privileges
(
    role_id      int8 not null,
    privilege_id int8 not null
);
alter table if exists auth.m_users
    add constraint UK_a2ao1y942v9whup5j7dhw4qve unique (uuid_str);
alter table if exists auth.m_users
    add constraint UK_5tyf96frxy09iqb90fag74jl5 unique (email);
alter table if exists auth.m_users
    add constraint UK_7aoads8ubmh10m9uo1kxwpvud unique (phone);
alter table if exists auth.m_users
    add constraint UK_lxevwjnma30s6actvjsblbp6a unique (username);
alter table if exists auth.privileges
    add constraint UK_ivxrkr76qqn0f7agxlv341nmr unique (uuid_str);
alter table if exists auth.privileges
    add constraint UK_duexhdry3ikhubr339uo893c0 unique (label);
alter table if exists auth.privileges
    add constraint UK_m2tnonbcaquofx1ccy060ejyc unique (name);
alter table if exists auth.roles
    add constraint UK_k1ercudabcvs864c1vpq5k973 unique (uuid_str);
alter table if exists auth.roles
    add constraint UK_ofx66keruapi6vyqpv6f2or37 unique (name);
create table core_web.activity_logs
(
    id             bigserial    not null,
    created_at     timestamp    not null,
    created_by     varchar(255),
    deleted        boolean      not null,
    updated_at     timestamp,
    updated_by     varchar(255),
    uuid_str       varchar(255) not null,
    expires        varchar(255),
    ip             varchar(255),
    request_method varchar(255),
    total_visitors int8,
    url            varchar(255),
    user_agent     varchar(255),
    user_id        int8,
    primary key (id)
);
create table core_web.addr_addresses
(
    id          bigserial    not null,
    created_at  timestamp    not null,
    created_by  varchar(255),
    deleted     boolean      not null,
    updated_at  timestamp,
    updated_by  varchar(255),
    uuid_str    varchar(255) not null,
    altitude    float8       not null,
    latitude    float8       not null,
    longitude   float8       not null,
    district_id int8,
    division_id int8,
    union_id    int8,
    upazila_id  int8,
    village_id  int8,
    primary key (id)
);
create table core_web.addr_districts
(
    id          bigserial    not null,
    created_at  timestamp    not null,
    created_by  varchar(255),
    deleted     boolean      not null,
    updated_at  timestamp,
    updated_by  varchar(255),
    uuid_str    varchar(255) not null,
    name_bn     varchar(255),
    name_en     varchar(255),
    division_id int8,
    primary key (id)
);
create table core_web.addr_divisions
(
    id         bigserial    not null,
    created_at timestamp    not null,
    created_by varchar(255),
    deleted    boolean      not null,
    updated_at timestamp,
    updated_by varchar(255),
    uuid_str   varchar(255) not null,
    name_bn    varchar(255),
    name_en    varchar(255),
    primary key (id)
);
create table core_web.addr_unions
(
    id         bigserial    not null,
    created_at timestamp    not null,
    created_by varchar(255),
    deleted    boolean      not null,
    updated_at timestamp,
    updated_by varchar(255),
    uuid_str   varchar(255) not null,
    name_bn    varchar(255),
    name_en    varchar(255),
    upazila_id int8,
    primary key (id)
);
create table core_web.addr_upazilas
(
    id          bigserial    not null,
    created_at  timestamp    not null,
    created_by  varchar(255),
    deleted     boolean      not null,
    updated_at  timestamp,
    updated_by  varchar(255),
    uuid_str    varchar(255) not null,
    name_bn     varchar(255),
    name_en     varchar(255),
    thana       boolean,
    district_id int8,
    primary key (id)
);
create table core_web.addr_villages
(
    id         bigserial    not null,
    created_at timestamp    not null,
    created_by varchar(255),
    deleted    boolean      not null,
    updated_at timestamp,
    updated_by varchar(255),
    uuid_str   varchar(255) not null,
    name_bn    varchar(255),
    name_en    varchar(255),
    union_id   int8,
    primary key (id)
);
create table core_web.email_logs
(
    id                bigserial    not null,
    created_at        timestamp    not null,
    created_by        varchar(255),
    deleted           boolean      not null,
    updated_at        timestamp,
    updated_by        varchar(255),
    uuid_str          varchar(255) not null,
    mail_bcc          VARCHAR(511),
    mail_cc           VARCHAR(511),
    mail_from         varchar(255) not null,
    message           TEXT,
    no_of_attachments int4,
    subject           VARCHAR(511),
    mail_to           varchar(255) not null,
    primary key (id)
);
alter table if exists core_web.activity_logs
    add constraint UK_ifu41tx2iak05imsolm84dk6a unique (uuid_str);
alter table if exists core_web.addr_addresses
    add constraint UK_8o6n939s99o8e0dtg387afmad unique (uuid_str);
alter table if exists core_web.addr_districts
    add constraint UK_lhl330jnomp0rv4qr1nathl5s unique (uuid_str);
alter table if exists core_web.addr_divisions
    add constraint UK_kaxu9ve9o8tvast01vhuncxav unique (uuid_str);
alter table if exists core_web.addr_unions
    add constraint UK_ah86xt5p56sw6837ypv054ahb unique (uuid_str);
alter table if exists core_web.addr_upazilas
    add constraint UK_rql8pc84dnumra9v3y0pj8n26 unique (uuid_str);
alter table if exists core_web.addr_villages
    add constraint UK_eep7t985xda38lylb9d6egy87 unique (uuid_str);
alter table if exists core_web.email_logs
    add constraint UK_su3fqoxs6j6ok28py61o6o97j unique (uuid_str);
create table messaging.chat_chatroom_users
(
    chat_room_id int8 not null,
    users        varchar(255)
);
create table messaging.chat_chatrooms
(
    id         bigserial    not null,
    created_at timestamp    not null,
    created_by varchar(255),
    deleted    boolean      not null,
    updated_at timestamp,
    updated_by varchar(255),
    uuid_str   varchar(255) not null,
    title      varchar(255),
    primary key (id)
);
create table messaging.chat_messages
(
    id            bigserial    not null,
    created_at    timestamp    not null,
    created_by    varchar(255),
    deleted       boolean      not null,
    updated_at    timestamp,
    updated_by    varchar(255),
    uuid_str      varchar(255) not null,
    content       varchar(255),
    from_username varchar(255),
    chat_room_id  int8         not null,
    primary key (id)
);
alter table if exists messaging.chat_chatrooms
    add constraint UK_jk94guy6aw7vp96pnlra2es72 unique (uuid_str);
alter table if exists messaging.chat_messages
    add constraint UK_1t7eq084xgblhaukfr5m5kn6h unique (uuid_str);
create table vfs.vfs_extensions
(
    id         bigserial    not null,
    created_at timestamp    not null,
    created_by varchar(255),
    deleted    boolean      not null,
    updated_at timestamp,
    updated_by varchar(255),
    uuid_str   varchar(255) not null,
    ext        varchar(255),
    name       varchar(255),
    url_prefix varchar(255),
    primary key (id)
);
create table vfs.vfs_folders
(
    id           bigserial    not null,
    created_at   timestamp    not null,
    created_by   varchar(255),
    deleted      boolean      not null,
    updated_at   timestamp,
    updated_by   varchar(255),
    uuid_str     varchar(255) not null,
    accent_color varchar(255),
    description  TEXT,
    name         varchar(255) not null,
    path         varchar(255),
    thumbnail    varchar(255),
    parent_id    int8,
    primary key (id)
);
alter table if exists vfs.vfs_extensions
    add constraint UK_8q75as6hn4fk7lvui78a8uade unique (uuid_str);
alter table if exists vfs.vfs_folders
    add constraint UK_n8cdf21n042tmgy9s149onq7m unique (uuid_str);
create table firebase_user_token
(
    id         bigserial    not null,
    created_at timestamp    not null,
    created_by varchar(255),
    deleted    boolean      not null,
    updated_at timestamp,
    updated_by varchar(255),
    uuid_str   varchar(255) not null,
    user_token varchar(255) not null,
    user_id    int8,
    primary key (id)
);
create table remote_config
(
    id                   bigserial    not null,
    created_at           timestamp    not null,
    created_by           varchar(255),
    deleted              boolean      not null,
    updated_at           timestamp,
    updated_by           varchar(255),
    uuid_str             varchar(255) not null,
    app_name             varchar(255) not null,
    app_package          varchar(255) not null,
    app_url              varchar(255),
    current_app_version  varchar(255) not null,
    force_update         boolean,
    previous_app_version varchar(255) not null,
    turned_off           boolean,
    primary key (id)
);
create table uploaded_files
(
    id              bigserial    not null,
    created_at      timestamp    not null,
    created_by      varchar(255),
    deleted         boolean      not null,
    updated_at      timestamp,
    updated_by      varchar(255),
    uuid_str        varchar(255) not null,
    file_name       varchar(255),
    file_type       varchar(255),
    namespace       varchar(255),
    root_path       varchar(255),
    unique_property varchar(255),
    primary key (id)
);
alter table if exists firebase_user_token
    add constraint UK_i3peei40osalclyscoyka6hbu unique (uuid_str);
alter table if exists remote_config
    add constraint UK_8y9xfk9d08f60lr5ijmordjmi unique (uuid_str);
alter table if exists uploaded_files
    add constraint UK_f63xqbc6m55voreta9tgqwqy unique (uuid_str);
alter table if exists acl.ac_validation_tokens
    add constraint FKi746ajdn1vcbojvapsq6jsc9u foreign key (user_id) references auth.m_users;
alter table if exists app.profiles
    add constraint FKiebuwbkswan5oolakejxgsnvf foreign key (address_id) references core_web.addr_addresses;
alter table if exists app.profiles
    add constraint FKonpp2s5vianwi6kh3800cqriv foreign key (user_id) references auth.m_users;
alter table if exists auth.m_users_roles
    add constraint FK5hwrg9mw7hmdq8cn63mh7sx0j foreign key (roles_id) references auth.roles;
alter table if exists auth.m_users_roles
    add constraint FK143dfv8wd8b2w93gbvd84ps5k foreign key (user_id) references auth.m_users;
alter table if exists auth.privileges_access_urls
    add constraint FKp123f0u3yvp9ygtxbc1kmff5d foreign key (privilege_id) references auth.privileges;
alter table if exists auth.roles_privileges
    add constraint FK5duhoc7rwt8h06avv41o41cfy foreign key (privilege_id) references auth.privileges;
alter table if exists auth.roles_privileges
    add constraint FK629oqwrudgp5u7tewl07ayugj foreign key (role_id) references auth.roles;
alter table if exists core_web.activity_logs
    add constraint FKi1mahmfxp15s84l940o3nxg19 foreign key (user_id) references auth.m_users;
alter table if exists core_web.addr_addresses
    add constraint FKq7jex59jkxy7ajdkyyc9bp494 foreign key (district_id) references core_web.addr_districts;
alter table if exists core_web.addr_addresses
    add constraint FKgi9t0jl90g7yq9o4n2barr9l4 foreign key (division_id) references core_web.addr_divisions;
alter table if exists core_web.addr_addresses
    add constraint FKnv1depj8rsnftij52lxpxbq4w foreign key (union_id) references core_web.addr_unions;
alter table if exists core_web.addr_addresses
    add constraint FKnfahui7uycqc0l2gnm53d0uav foreign key (upazila_id) references core_web.addr_upazilas;
alter table if exists core_web.addr_addresses
    add constraint FKj3sq3fkq0xykyc05dbc0rorva foreign key (village_id) references core_web.addr_villages;
alter table if exists core_web.addr_districts
    add constraint FKghoqovcvly06mikeskpqxn830 foreign key (division_id) references core_web.addr_divisions;
alter table if exists core_web.addr_unions
    add constraint FKg7hdpqsb02w4otfiqs1oagmm3 foreign key (upazila_id) references core_web.addr_upazilas;
alter table if exists core_web.addr_upazilas
    add constraint FK42he4xymxht59qauu021iit9t foreign key (district_id) references core_web.addr_districts;
alter table if exists core_web.addr_villages
    add constraint FKnavqm8idv3axcj04hum8h41kj foreign key (union_id) references core_web.addr_unions;
alter table if exists messaging.chat_chatroom_users
    add constraint FKlrecp4psy3a7xgprj3a7d6wun foreign key (chat_room_id) references messaging.chat_chatrooms;
alter table if exists messaging.chat_messages
    add constraint FKdm2ves715qutd01dp26mvjglm foreign key (chat_room_id) references messaging.chat_chatrooms;
alter table if exists vfs.vfs_folders
    add constraint FK1wulxxd7mghr0vnmxv2cowyii foreign key (parent_id) references vfs.vfs_folders;
alter table if exists firebase_user_token
    add constraint FKijg4ywkf6r83uvyp905o6omwc foreign key (user_id) references auth.m_users;
