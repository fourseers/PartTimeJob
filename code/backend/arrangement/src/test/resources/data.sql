insert into industry (industry_name) values ('IT');
insert into merchant_user (username, password) values ('Tim Cook', 'some password');
insert into wechat_user (city, country, education, gender, identity, name, openid, phone)
    values ('Shanghai', 'China', '高中毕业', true, '310110123412341234', 'CYJ', 'fakeOpenid', '13812345678');
insert into company (company_name, boss_user_id) values ('Apple', 1);
update merchant_user set company_company_id = 1 where username = 'Tim Cook';
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('310101', 'Apple', '310100', 1, 'Make Apple great again', 30, 120, '310000', 'Apple iamp', 1);
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('310101', 'Apple', '310100', 1, 'Make Apple great again', 30, 120, '310000', 'Apple iamp II', 1);
insert into merchant_user (username, password) values ('葛越', 'another password');
update merchant_user set company_company_id = 1 where username = '葛越';
insert into merchant_user (username, password) values ('罗永浩', 'Chuizi nb');
insert into company (company_name, boss_user_id) values ('锤子', 3);
update merchant_user set company_company_id = 2 where username = '罗永浩';
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('510104', '锤子', '510100', 1, 'Aquire Apple in someday!', 30, 100, '510000', '锤科总部', 2);
insert into tag (name) values ('IT');
insert into tag (name) values ('互联网');
insert into job (begin_apply_time, begin_date, education, end_apply_time, end_date, begin_time, end_time, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id, manual_stop) values
('2019-07-14 16:00:00', '2019-07-16', '本科毕业', '2019-07-15 16:00:00', '2019-07-17', '08:00:00', '16:00:00', 'sell Apple products', 'seller', 10, 2, 100, 2, false);
insert into job_tag_list (job_list_job_id, tag_list_id) values (1, 1);
insert into merchant_user (username, password) values ('poor user', 'poor password');
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('310101', 'Apple', '310100', 1, 'Make Apple great again', 30, 120, '310000', 'Apple iamp III', 1);
insert into merchant_user (username, password) values ('Bill Gates', 'some password');
insert into company (company_name, boss_user_id) values ('MS', 5);
update merchant_user set company_company_id = 3 where username = 'Bill Gates';
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('310101', 'MS', '310100', 1, 'MS 大法好', 30, 120, '310000', 'MS-1', 3);
insert into shop (address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
('310101', 'MS', '310100', 1, 'MS 大法好', 30, 120, '310000', 'MS-2', 3);

insert into shop (shop_id, address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
(7, '310101', 'Apple', '310100', 1, 'Make Apple great again', 31.203798, 121.466012, '310000', '瑞金医院', 1);
insert into shop (shop_id, address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
(8, '310101', 'Apple', '310100', 1, 'Make Apple great again', 31.295241, 121.503894, '310000', '复旦大学', 1);
insert into shop (shop_id, address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
(9, '310101', 'Apple', '310100', 1, 'Make Apple great again', 31.238295, 121.499699, '310000', '东方明珠', 1);
insert into shop (shop_id, address, brand, city, industry_industry_id, introduction, latitude, longitude, province, shop_name, company_company_id) values
(10, '310101', 'Apple', '310100', 1, 'Make Apple great again', 31.230339, 121.473656, '310000', '上海市政府', 1);

insert into job (begin_apply_time, begin_date, education, end_apply_time, end_date, begin_time, end_time, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id, manual_stop) values
('2019-07-14 16:00:00', '2019-07-16', '高中毕业', '2019-07-15 16:00:00', '2019-07-17', '08:00:00', '16:00:00', '扮演精神病人', 'seller', 10, 2, 100, 7, false);
insert into job (begin_apply_time, begin_date, education, end_apply_time, end_date, begin_time, end_time, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id, manual_stop) values
('2019-07-14 16:00:00', '2019-07-16', '初中毕业及以下', '2019-07-15 16:00:00', '2019-07-17', '08:00:00', '16:00:00', '当一个无用之人', 'seller', 10, 2, 100, 8, false);
insert into job (begin_apply_time, begin_date, education, end_apply_time, end_date, begin_time, end_time, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id, manual_stop) values
('2019-07-14 16:00:00', '2019-07-16', '高中毕业', '2019-07-15 16:00:00', '2019-07-17', '08:00:00', '16:00:00', '跳伞运动员', 'seller', 10, 2, 100, 9, false);
insert into job (begin_apply_time, begin_date, education, end_apply_time, end_date, begin_time, end_time, job_detail, job_name, need_amount, need_gender, salary, shop_shop_id, manual_stop) values
('2019-07-14 16:00:00', '2019-07-16', '本科毕业', '2019-07-15 16:00:00', '2019-07-17', '08:00:00', '16:00:00', '炸弹小组技术专家', 'seller', 10, 2, 100, 10, false);

insert into work (checkin, checkout, log, salary_confirmed, score, work_date, billing_bill_id, job_job_id, worker_user_id) values
('08:00:00', '16:00:00', null, false, null, '2019-07-16', null, 1, 1);
insert into work (checkin, checkout, log, salary_confirmed, score, work_date, billing_bill_id, job_job_id, worker_user_id) values
('08:00:00', '16:00:00', null, false, 5, '2019-07-17', null, 2, 1);
