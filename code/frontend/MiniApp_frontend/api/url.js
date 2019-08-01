const login = "/auth/wechat/login"
const register = "/auth/wechat/register"
const host = "http://202.120.40.8:30552"
const register_data = "/warehouse/user/register-info"
const user_info = "/warehouse/user/info"
const modify_info = "/warehouse/user/info"
const job_list = "/arrangement/user/jobs"
const job_detail = "/arrangement/user/job?job_id="
const apply_job = "/arrangement/user/apply"
const checkin = "/arrangement/user/checkin"
const checkout = "/arrangement/user/checkout"
const cv_list = "/warehouse/user/cv-list"
const cv_curd = "/warehouse/user/cv"
const shop_inform = "/warehouse/user/shop?shop_id="
const shop_score = "/warehouse/user/shop/score"
const applied_time = "/arrangement/user/job/applied-time?job_id="
const application_list = "/arrangement/user/applications"

module.exports = {
  login,
  register,
  host,
  register_data,
  user_info,
  modify_info,
  job_list,
  job_detail,
  apply_job,
  checkin,
  checkout,
  cv_list,
  cv_curd,
  shop_inform,
  shop_score,
  applied_time,
  application_list
}