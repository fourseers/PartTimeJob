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
  checkout
}