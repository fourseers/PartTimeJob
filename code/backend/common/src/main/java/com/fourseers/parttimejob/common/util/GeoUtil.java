package com.fourseers.parttimejob.common.util;

public class GeoUtil {

    public static class Point {
        private Float longitude;
        private Float latitude;

        public Point(Float longitude, Float latitude) {
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public Float getLongitude() {
            return longitude;
        }

        public void setLongitude(Float longitude) {
            this.longitude = longitude;
        }

        public Float getLatitude() {
            return latitude;
        }

        public void setLatitude(Float latitude) {
            this.latitude = latitude;
        }
    }

    public static Float getDistance(Point p1, Point p2) {
        return (float)(Math.sqrt(
                Math.pow(p1.getLatitude() - p2.getLatitude(), 2) +
                Math.pow(p1.getLongitude() - p2.getLongitude(), 2)));
    }

    public static boolean within(Point actual, Point dest, Float range) {
        return getDistance(actual, dest) <= range;
    }

    public static boolean within(Point actual, Point dest) {
        // 0.001 in longitude / latitude is about 100m
        return within(actual, dest, 0.001f);
    }
}
