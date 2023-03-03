CREATE TYPE user_role AS ENUM (
    'admin',
    'user'
)
--;;

CREATE CAST (varchar AS user_role) WITH INOUT AS ASSIGNMENT;
--;;

CREATE TABLE users(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    email varchar(200) NOT NULL UNIQUE,
    password varchar(60) NOT NULL,
    enabled bool NOT NULL DEFAULT true,
    user_role user_role DEFAULT 'user' NOT NULL,
    api_token uuid NOT NULL DEFAULT uuid_generate_v4()
)
--;;

CREATE TABLE teams(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    name varchar(512) NOT NULL,
    enabled bool NOT NULL DEFAULT true
)
--;;

CREATE TYPE team_role AS ENUM (
    'team-admin',
    'team-editor',
    'team-viewer'
)
--;;

CREATE CAST (varchar AS team_role) WITH INOUT AS ASSIGNMENT;
--;;

CREATE TABLE team_users(
    team_id uuid NOT NULL,
    user_id uuid NOT NULL,
    team_role team_role NOT NULL,
    CONSTRAINT team_users_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id),
    CONSTRAINT team_users_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
)
--;;

CREATE TABLE notes(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    team_id uuid NOT NULL,
    is_public bool NOT NULL DEFAULT false,
    name varchar(200) NOT NULL,
    content text,
    CONSTRAINT notes_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id)
)
--;;

CREATE TABLE tickets(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    team_id uuid NOT NULL,
    name varchar(200) NOT NULL,
    resolved bool NOT NULL DEFAULT false,
    CONSTRAINT tickets_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id)
)
--;;

CREATE TABLE ticket_messages(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    ticket_id uuid NOT NULL,
    user_id uuid NOT NULL,
    message varchar(1000) NOT NULL,
    CONSTRAINT ticket_messages_ticket_id_fk FOREIGN KEY (ticket_id) REFERENCES tickets(id),
    CONSTRAINT ticket_messages_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id)
)
--;;

CREATE TABLE content_pages(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    path varchar(2048) NOT NULL UNIQUE,
    content text
)
--;;

CREATE TABLE audit_log(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    user_id uuid NOT NULL,
    team_id uuid NOT NULL,
    action text NOT NULL,
    note text,
    CONSTRAINT audit_log_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT audit_log_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id)
)
--;;

CREATE TABLE billing_details(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    team_id uuid NOT NULL,
    company_name varchar(200),
    address varchar(1024),
    vat varchar(100),
    CONSTRAINT billing_details_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id)
)
--;;

CREATE TABLE plans(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    name varchar(200) NOT NULL,
    price_usd numeric(18,2) NOT NULL,
    price_per_user bool NOT NULL DEFAULT false,
    public bool NOT NULL DEFAULT false,
    team_id uuid NOT NULL,
    max_notes integer NOT NULL,
    max_chars integer NOT NULL,
    max_users integer NOT NULL,
    CONSTRAINT plans_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id)
)
--;;

CREATE TABLE subscriptions(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    team_id uuid NOT NULL,
    plan_id uuid NOT NULL,
    canceled bool NOT NULL DEFAULT false,
    CONSTRAINT subscriptions_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id),
    CONSTRAINT subscriptions_plan_id_fk FOREIGN KEY (plan_id) REFERENCES plans(id)
)
--;;


CREATE TABLE invoices(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    team_id uuid NOT NULL,
    amount_usd numeric(18,2) NOT NULL,
    subscription_id uuid NOT NULL,
    CONSTRAINT invoices_team_id_fk FOREIGN KEY (team_id) REFERENCES teams(id),
    CONSTRAINT invoices_subscription_id_fk FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
)
