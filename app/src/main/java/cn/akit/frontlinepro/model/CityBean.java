package cn.akit.frontlinepro.model;

import java.util.LinkedList;

/**
 * Sam
 * 2018/9/21
 * current system date
 **/
public class CityBean {

    private LinkedList<City> data;

    public LinkedList<City> getData() {
        return data;
    }

    public void setData(LinkedList<City> data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "CityBean{" +
                "data=" + data +
                '}';
    }

    public static class City {

        private String countryName;
        private String countryPinyin;
        private String phoneCode;
        private String countryCode;
        private String firstLetter;

        public String getFirstLetter() {
            return firstLetter;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getCountryPinyin() {
            return countryPinyin;
        }

        public void setCountryPinyin(String countryPinyin) {
            this.countryPinyin = countryPinyin;
        }

        public String getPhoneCode() {
            return phoneCode;
        }

        public void setPhoneCode(String phoneCode) {
            this.phoneCode = phoneCode;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        @Override
        public String toString() {
            return "City{" +
                    "countryName='" + countryName + '\'' +
                    ", countryPinyin='" + countryPinyin + '\'' +
                    ", phoneCode='" + phoneCode + '\'' +
                    ", countryCode='" + countryCode + '\'' +
                    '}';
        }
    }


}
