create function get_order_list(i_user_chat_id bigint) returns SETOF product_1
    language plpgsql
as
$$
    declare
        v_chat_id int;
    BEGIN
        select id into v_chat_id from mvc_user where chat_id = i_user_chat_id;
        return query select p.* from mvc_orderTible ot
        join product_1 p on ot.productId = p.id where ot.userId = v_chat_id;
    end
$$;

alter function get_order_list(bigint) owner to postgres;

