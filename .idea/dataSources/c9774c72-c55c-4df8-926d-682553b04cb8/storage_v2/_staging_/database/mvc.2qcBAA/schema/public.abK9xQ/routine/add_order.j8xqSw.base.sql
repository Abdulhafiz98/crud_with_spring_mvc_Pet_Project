create function add_order(i_user bigint, i_product character varying) returns boolean
    language plpgsql
as
$$
declare
    v_chat_id int;
    v_pro_id int;
BEGIN
    select id into v_chat_id from mvc_user where chat_id = i_user;
    select id into v_pro_id from product_1 where name =i_product;

    if v_pro_id is not null and v_chat_id is not null then
--         update basket set taken=false where productid=v_pro_id and userid=v_chat_id;
        raise notice 'user=%',v_user_id;
        raise notice 'product=%',v_pro_id;
        insert into mvc_orderTible(userdi, product) VALUES (v_chat_id,v_pro_id);
        return true;
        end if;
    return false;
end
$$;

alter function add_order(bigint, varchar) owner to postgres;

