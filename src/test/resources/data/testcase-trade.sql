insert into item (id, name, price, comment) values (1, 'jacket1', 10000, 'nice man shirt1');
insert into item (id, name, price, comment) values (2, 'jacket2', 5000, 'nice man shirt2');
insert into item (id, name, price, comment) values (3, 'jacket3', 5000, 'nice woman shirt3');
insert into item (id, name, price, comment) values (4, 'jacket4', 10000, 'nice woman shirt4');
insert into item (id, name, price, comment) values (5, 'jean1', 10000, 'nice man pants1');
insert into item (id, name, price, comment) values (6, 'jean2', 5000, 'nice man pants2');
insert into item (id, name, price, comment) values (7, 'jean3', 10000, 'nice woman pants3');
insert into item (id, name, price, comment) values (8, 'jean4', 5000, 'nice woman pants4');

insert into member (id, nickname) values (1, 'iksadnorth');
insert into member (id, nickname) values (2, 'iksadsouth');
insert into member (id, nickname) values (3, 'iksadeast');
insert into member (id, nickname) values (4, 'iksadwest');

insert into trade (item_id, seller_id, member_id) values (1, 1, 2);
insert into trade (item_id, seller_id, member_id) values (1, 1, 3);

insert into trade (item_id, seller_id, member_id) values (2, 2, 1);
insert into trade (item_id, seller_id, member_id) values (2, 2, 4);

insert into trade (item_id, seller_id, member_id) values (3, 3, 1);
insert into trade (item_id, seller_id, member_id) values (3, 3, 4);

insert into trade (item_id, seller_id, member_id) values (4, 4, 1);
insert into trade (item_id, seller_id, member_id) values (4, 4, 2);

insert into trade (item_id, seller_id, member_id) values (2, 1, 2);
insert into trade (item_id, seller_id, member_id) values (2, 1, 3);