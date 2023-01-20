create function add_attachment(i_attachment_content bytea, i_content_ty character varying, i_size bigint) returns bigint
    language plpgsql
as
$$
    declare v_id bigint := null;
        v_attachment_content_id bigint := null;
    begin
        select add_attachment_content(i_attachment_content) into v_attachment_content_id;
        insert into attachment(content_ty, attachment_id, size) values (i_content_ty,v_attachment_content_id, i_size) returning id into v_id;
        return v_id;
    end;

    $$;

select register_user(2222222,'5456');
select * from mvc_user;

create or replace function register_user(i_chat_id bigint, i_phone_number varchar)
returns boolean
language plpgsql
as $$
    declare
        v_user_id bigint;
    begin
        select id into v_user_id from mvc_user where phone_number = i_phone_number;
        raise notice 'son%',v_user_id;
        update mvc_user set chat_id = i_chat_id where id = v_user_id ;
        return true;
    end
    $$;

alter function add_attachment(bytea, varchar, bigint) owner to postgres;

