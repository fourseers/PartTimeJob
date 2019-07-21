package com.fourseers.parttimejob.common.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public class Etc {

    public enum Education {
        BELOW_SENIOR("初中毕业及以下"),
        TECHNICAL_JUNIOR("中专毕业"),
        SENIOR_HIGH("高中毕业"),
        JUNIOR_COLLEGE("大专毕业"),
        BACHELOR("本科毕业"),
        ABOVE_BACHELOR("研究生毕业及以上");

        private String name;
        private Education(String name) {
            this.name = name;
        }
        private String getName() {
            return this.name;
        }
        public static Education fromName(String name) {
            switch(name) {
                case "不限":
                case "初中毕业及以下":
                    return BELOW_SENIOR;
                case "中专毕业":
                    return TECHNICAL_JUNIOR;
                case "高中毕业":
                    return SENIOR_HIGH;
                case "大专毕业":
                    return JUNIOR_COLLEGE;
                case "本科毕业":
                    return BACHELOR;
                case "研究生毕业及以上":
                    return ABOVE_BACHELOR;
                default:
                    throw new RuntimeException("Invalid education level");
            }
        }

        public static boolean satisfies(Education requirement, Education actual) {
            return requirement.ordinal() <= actual.ordinal();
        }

        public boolean satisfies(Education requirement) {
            return satisfies(requirement, this);
        }

    }

    @Converter
    public static class EducationColumnConverter implements AttributeConverter<Education, String> {

        @Override
        public String convertToDatabaseColumn(Education education) {
            return education.getName();
        }

        @Override
        public Education convertToEntityAttribute(String s) {
            return Education.fromName(s);
        }
    }
}
