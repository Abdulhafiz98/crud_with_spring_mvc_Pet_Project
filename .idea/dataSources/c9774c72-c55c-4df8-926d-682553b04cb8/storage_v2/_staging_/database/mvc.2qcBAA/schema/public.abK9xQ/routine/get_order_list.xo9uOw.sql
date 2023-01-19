create function get_order_list(i_user_chat_id bigint) returns SETOF product_1
    language plpgsql
as
$$
    declare
        v_chat_id int;
    BEGIN
        select id into v_chat_id from mvc_user where chat_id = i_user_chat_id;
        return query select p.* from mvc_orderTible ot
        join product_1 p on ot.product = p.id where ot.userdi = v_chat_id;
    end
$$;

drop function get_order_list();

alter function get_order_list(bigint) owner to postgres;

select get_order_list(378400797);

create or replace function get_basket_list(i_user_chat_id bigint)
returns setof product_1
language plpgsql
as
    $$
declare
    v_chat_id int;
    BEGIN
    select id into v_chat_id from mvc_user where chat_id = i_user_chat_id;
    return query select p.* from basket ot
                                     join product_1 p on ot.productid = p.id where ot.userid = v_chat_id;
        end
$$;

select get_basket_list(378400797);

