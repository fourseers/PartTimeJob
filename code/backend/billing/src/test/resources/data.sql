insert into merchant_user (username, password) values ('罗永浩', 'some password');
insert into company (company_name, boss_user_id) values ('锤科', 1);
update merchant_user set company_company_id = 1 where username = '罗永浩';
insert into industry (industry_name) values ('IT');
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('somewhere in Chendu', '锤子', 'Chendu', 1, 'Aquire Apple in someday!', 30, 100, 'Sichuan', '锤科总部', 1);
insert into wechat_user (city, country, education, gender, identity, name, openid, phone) values
('Shanghai', 'China', '高中', 1, '310000000000000000', '苦逼打工仔', 'fake_openid', '13000000000');
insert into job (begin_apply_date, begin_date, education, end_apply_date, end_date, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id) values
('2019-07-14 16:00:00', '2019-07-16 16:00:00', '高中', '2019-07-15 16:00:00', '2019-07-17 16:00:00', 'sell Chuizi products', 'seller', 10, 2, 100, 1);
insert into billing (paid, work_date, employee_user_id, job_job_id, shop_shop_id) values
(true, '2019-07-15', 1, 1, 1);
