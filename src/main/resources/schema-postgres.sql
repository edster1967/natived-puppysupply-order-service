create table if not exists customer(
    customer_id integer not null,
    first_name varchar(255),
    last_name varchar(255),
    address_1 varchar(255),
    address_2 varchar(255),
    city varchar(255),
    state varchar(255),
    zip varchar(255),
    phone varchar(255),
    email varchar(255),
    primary key(customer_id)
);

create table if not exists puppy_order(
    puppy_order_id integer not null,
    order_number integer not null,
    order_date date,
    customer_id integer,
    sub_total numeric(12,2),
    shipping_cost numeric(12,2),
    tax numeric(12,2),
    total numeric(12,2),
    constraint puppy_order_pkey primary key (puppy_order_id),
    constraint puppy_order_number_uk unique (order_number),
    constraint cust_id foreign key (customer_id) references customer (customer_id)
);
