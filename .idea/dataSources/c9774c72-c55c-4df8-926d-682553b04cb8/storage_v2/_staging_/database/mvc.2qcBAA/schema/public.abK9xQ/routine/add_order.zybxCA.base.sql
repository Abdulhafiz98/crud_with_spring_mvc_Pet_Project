create function add_order(i_user integer, i_product integer) returns void
    language plpgsql
as
$$
BEGIN
      update basket set taken=false where productid=i_product and userid=i_user;
      insert into mvc_orderTible(userId, productId) VALUES (i_user,i_product);
end
$$;

alter function add_order(integer, integer) owner to postgres;

