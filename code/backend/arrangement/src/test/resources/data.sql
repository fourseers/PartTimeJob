insert into merchant_user (username, password) values ('Tim Cook', 'some password');
insert into company (company_name, boss_user_id) values ('Apple', 1);
update merchant_user set company_company_id = 1 where username = 'Tim Cook';
insert into shop (address, brand, city, industry, introduction, latitude, longitude, province, shop_name, company_company_id) values
('somewhere in Shanghai', 'Apple', 'Shanghai', 'IT', 'Make Apple great again', 30, 120, 'Shanghai', 'Apple iamp', 1);
insert into shop (address, brand, city, industry, introduction, latitude, longitude, province, shop_name, company_company_id) values
('somewhere in Shanghai', 'Apple', 'Shanghai', 'IT', 'Make Apple great again', 30, 120, 'Shanghai', 'Apple iamp II', 1);
insert into merchant_user (username, password) values ('葛越', 'another password');
update merchant_user set company_company_id = 1 where username = '葛越';
insert into merchant_user (username, password) values ('罗永浩', 'Chuizi nb');
insert into company (company_name, boss_user_id) values ('锤子', 3);
update merchant_user set company_company_id = 2 where username = '罗永浩';
insert into shop (address, brand, city, industry, introduction, latitude, longitude, province, shop_name, company_company_id) values
('somewhere in Chendu', '锤子', 'Chendu', 'IT', 'Aquire Apple in someday!', 30, 100, 'Sichuan', '锤科总部', 2);
insert into tag (name) values ('IT');
insert into tag (name) values ('互联网');
insert into job (begin_apply_date, begin_date, education, end_apply_date, end_date, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id) values
('2019-07-14 16:00:00', '2019-07-16 16:00:00', '大学本科以上', '2019-07-15 16:00:00', '2019-07-17 16:00:00', 'sell Apple products', 'seller', 10, 2, 100, 2);
insert into job_tag_list (job_list_job_id, tag_list_id) values (1, 1);
insert into merchant_user (username, password) values ('poor user', 'poor password');
insert into shop (address, brand, city, industry, introduction, latitude, longitude, province, shop_name, company_company_id) values
('somewhere in Shanghai', 'Apple', 'Shanghai', 'IT', 'Make Apple great again', 30, 120, 'Shanghai', 'Apple iamp III', 1);
