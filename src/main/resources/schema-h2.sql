create table CUSTOMER(
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

CREATE TABLE puppy_order
(
    puppy_order_id bigint NOT NULL,
    order_number numeric NOT NULL,
    order_date date,
    customer_id bigint,
    sub_total numeric,
    shipping_cost numeric,
    tax numeric,
    total numeric,
    CONSTRAINT puppy_order_pkey PRIMARY KEY (puppy_order_id),
    CONSTRAINT cust_id FOREIGN KEY (customer_id)
        REFERENCES customer (customer_id)
)

