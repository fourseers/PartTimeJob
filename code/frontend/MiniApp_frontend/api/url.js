const login = "/auth/wechat/login"
const register = "/auth/wechat/register"
const host = "http://47.103.112.85:30552"
const register_data = "/warehouse/user/register-info"
const user_info = "/warehouse/user/info"
const modify_info = "/warehouse/user/info"
const job_list = "/arrangement/user/jobs"
const job_detail = "/arrangement/user/job?identifier="
const apply_job = "/arrangement/user/apply"
const checkin = "/arrangement/user/checkin"
const checkout = "/arrangement/user/checkout"
const cv_list = "/warehouse/user/cv-list"
const cv_curd = "/warehouse/user/cv"
const shop_inform = "/warehouse/user/shop?shop_id="
const shop_score = "/warehouse/user/shop/score"
const schedule = "/arrangement/user/schedule"
const check_status = "/arrangement/user/check-status"
const applied_time = "/arrangement/user/job/applied-time?job_id="
const application_list = "/arrangement/user/applications"
const billing = "/billing/user/works"
const balance = "/billing/user/balance"
const draw_all = "/billing/user/drawAll"
const draw_history = "/billing/user/draw-history"
const jobs_smart = "/arrangement/user/jobs-smart"

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
  schedule,
  check_status,
  applied_time,
  application_list,
  billing,
  balance,
  draw_all,
  draw_history,
  jobs_smart
}