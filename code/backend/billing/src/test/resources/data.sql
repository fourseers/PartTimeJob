insert into merchant_user (username, password) values ('罗永浩', 'some password');
insert into company (company_name, boss_user_id) values ('锤科', 1);
update merchant_user set company_company_id = 1 where username = '罗永浩';

insert into merchant_user (username, password) values ('Tim Cook', 'some password');
insert into company (company_name, boss_user_id) values ('Apple', 2);
update merchant_user set company_company_id = 2 where username = 'Tim Cook';

insert into merchant_user (username, password) values ('Poor user', 'some password');

insert into industry (industry_name) values ('IT');
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('somewhere in Chendu', '锤子', 'Chendu', 1, 'Aquire Apple in someday!', 30, 100, 'Sichuan', '锤科总部', 1);
insert into wechat_user (city, country, education, gender, identity, name, openid, phone) values
('Shanghai', 'China', '高中毕业', 1, '310000000000000000', '苦逼打工仔', 'fake_openid', '13000000000');
insert into job (begin_apply_time, begin_date, education, end_apply_time, end_date, begin_time, end_time, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id) values
('2019-07-14 16:00:00', '2019-07-16', '高中毕业', '2019-07-15 16:00:00', '2019-07-17', '08:00:00', '16:00:00', 'sell Chuizi products', 'seller', 10, 2, 100, 1);

insert into work (checkin, checkout, log, salary_confirmed, score, work_date, billing_bill_id, job_job_id, worker_user_id) values
('08:00:00', '16:00:00', null, true, 5, '2019-07-17', null, 1, 1);
insert into billing (meta, method, payment, work_work_id) values (null, '微信支付', 100, 1);
update work set billing_bill_id = 1 where work_id = 1;
insert into work (checkin, checkout, log, salary_confirmed, score, work_date, billing_bill_id, job_job_id, worker_user_id) values
('08:00:00', '16:00:00', null, false, 5, '2019-07-18', null, 1, 1);
