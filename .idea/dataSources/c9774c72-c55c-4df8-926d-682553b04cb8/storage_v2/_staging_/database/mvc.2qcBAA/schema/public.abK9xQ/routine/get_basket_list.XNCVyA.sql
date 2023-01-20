create function get_basket_list(i_user_chat_id bigint) returns SETOF product_1
    language plpgsql
as
$$
declare
    v_chat_id int;
    BEGIN
    select id into v_chat_id from mvc_user where chat_id = i_user_chat_id;
    return query select p.* from basket ot
                                     join product_1 p on ot.productid = p.id where ot.userid = v_chat_id;
        end;
$$;

select * from get_basket_list('378400797');
alter function get_basket_list(bigint) owner to postgres;

