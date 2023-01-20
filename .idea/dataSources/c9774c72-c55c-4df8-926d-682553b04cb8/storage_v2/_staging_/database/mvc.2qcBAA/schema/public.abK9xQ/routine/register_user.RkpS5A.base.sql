create function register_user(i_chat_id bigint, i_phone_number character varying) returns boolean
    language plpgsql
as
$$
    declare
        v_user_id bigint;
    begin
        select id into v_user_id from mvc_user where phone_number = i_phone_number;
        raise notice 'son%',v_user_id;
        update mvc_user set chat_id = i_chat_id where id = v_user_id ;
        return true;
    end
    $$;

alter function register_user(bigint, varchar) owner to postgres;

