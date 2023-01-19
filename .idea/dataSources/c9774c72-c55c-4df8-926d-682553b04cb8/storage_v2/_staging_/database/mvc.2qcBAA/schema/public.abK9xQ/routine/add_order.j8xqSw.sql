drop function add_order();
create or replace function add_order(i_user bigint, i_product character varying)
    returns boolean
    language plpgsql
as
$$
declare
    v_chat_id int;
    v_pro_id int;
    v_olready int;
BEGIN
    select id into v_chat_id from mvc_user where chat_id = i_user;
    select id into v_pro_id from product_1 where name =i_product;

    if v_pro_id is not null and v_chat_id is not null then
--         update basket set taken=false where productid=v_pro_id and userid=v_chat_id;
        select id into v_olready from mvc_ordertible where product=v_pro_id and userdi=v_chat_id;
        if v_olready is not null then
            return true;
        end if;
        insert into mvc_orderTible(userdi, product) VALUES (v_chat_id,v_pro_id);

        return true;
        end if;
    return false;
end
$$;

select * from add_order(i_user := 378400797, i_product := 'dd.png');
alter function add_order(bigint, varchar) owner to postgres;
select * from mvc_ordertible;
drop function add_order();

create or replace function deleteProduct_Order(i_chat_user bigint, i_product character varying)
returns boolean
language plpgsql
as
    $$
declare
    v_user_id int;
    v_pro_id int;
    BEGIN
    select id into v_user_id from mvc_user where chat_id = i_chat_user;
    select id into v_pro_id from product_1 where name =i_product;

    if v_pro_id is not null and v_user_id is not null then
        raise notice 'user=%',v_user_id;
        raise notice 'product=%',v_pro_id;
        delete  from basket  where userid= v_user_id and productid=v_pro_id;
        return true;
    end if;
    return false;
    end
$$;

select  deleteProduct_Order(378400797,'aa.png');
select * from basket;
delete  from basket  where userid= 1 and productid=2;
select id into v_user_id from mvc_user where chat_id =378400797 ;

-- create table basket(
--     id serial primary key ,
--     userid integer ,
--     productid integer,
--     taken boolean
-- )

create or replace function buy_product(i_chat_id bigint, i_product_id integer) returns boolean
    language plpgsql
as
$$
declare
    v_user_id bigint;
begin
    select id into v_user_id from mvc_user where chat_id = i_chat_id;
    insert into mvc_ordertible(userdi, product) values (v_user_id, i_product_id);
    return true;
end;
$$;

drop function buy_product();