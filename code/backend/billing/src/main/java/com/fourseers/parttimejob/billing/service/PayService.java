package com.fourseers.parttimejob.billing.service;

import com.fourseers.parttimejob.common.entity.WechatUser;
import org.springframework.stereotype.Service;

@Service
public interface PayService {

    /**
     * Get method signature of this payment method.
     * @return
     */
    String getMethod();

    /**
     * Pay user given amount of money.
     * @param user      Wechat user to pay
     * @param payment   Amount of money to pay
     * @return          Metadata of payment
     */
    PaymentResult pay(WechatUser user, Double payment);

    public class PaymentResult {
        private boolean success;
        private String meta;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMeta() {
            return meta;
        }

        public void setMeta(String meta) {
            this.meta = meta;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
