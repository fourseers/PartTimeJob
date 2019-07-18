package com.fourseers.parttimejob.common.entity;

public class Etc {

    public enum Education {
        BELOW_SENIOR("初中毕业及以下"),
        SENIOR_HIGH("高中毕业"),
        BANCHELOR("本科毕业"),
        ABOVE_BANCHELOR("研究生毕业及以上");

        private String name;
        private Education(String name) {
            this.name = name;
        }
        private String getName() {
            return this.name;
        }
        public Education fromName(String name) {
            switch(name) {
                case "初中毕业及以下":
                    return BELOW_SENIOR;
                case "高中毕业":
                    return SENIOR_HIGH;
                case "本科毕业":
                    return BANCHELOR;
                case "研究生毕业及以上":
                    return ABOVE_BANCHELOR;
                default:
                    return null;
            }
        }
    };
}
