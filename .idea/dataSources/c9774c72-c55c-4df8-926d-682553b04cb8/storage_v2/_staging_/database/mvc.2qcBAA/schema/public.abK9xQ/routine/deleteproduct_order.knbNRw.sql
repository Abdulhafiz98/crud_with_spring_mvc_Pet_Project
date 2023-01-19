create function deleteproduct_order(i_chat_user bigint, i_product character varying) returns boolean
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

select * from deleteproduct_order(378400797, 'aa.png');

alter function deleteproduct_order(bigint, varchar) owner to postgres;

