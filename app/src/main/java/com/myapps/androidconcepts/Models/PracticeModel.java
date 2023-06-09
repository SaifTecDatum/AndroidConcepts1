package com.myapps.androidconcepts.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PracticeModel {
    /*@SerializedName("@context")      //context arrayList json data is in wrong format, So the app is crashing. In that list we have
    @Expose                           //one string & one object wch is not correct. correct format should have only dataTypes or objects.
    private List<String> context = null;*/

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("geometry")
    @Expose
    private Geometry geometry;

    @SerializedName("properties")
    @Expose
    private Properties properties;


    /*public List<String> getContext() {
        return context;
    }

    public void setContext(List<String> context) {
        this.context = context;
    }*/


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }


    public static class Geometry  {

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("coordinates")
        @Expose
        private List<List<List<Double>>> coordinates = null;


        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<List<List<Double>>> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<List<List<Double>>> coordinates) {
            this.coordinates = coordinates;
        }

    }


    public static class Properties {

        @SerializedName("updated")
        @Expose
        private String updated;

        @SerializedName("units")
        @Expose
        private String units;

        @SerializedName("forecastGenerator")
        @Expose
        private String forecastGenerator;

        @SerializedName("generatedAt")
        @Expose
        private String generatedAt;

        @SerializedName("updateTime")
        @Expose
        private String updateTime;

        @SerializedName("validTimes")
        @Expose
        private String validTimes;

        @SerializedName("elevation")
        @Expose
        private Elevation elevation;

        @SerializedName("periods")
        @Expose
        private List<Period> periods = null;


        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

        public String getForecastGenerator() {
            return forecastGenerator;
        }

        public void setForecastGenerator(String forecastGenerator) {
            this.forecastGenerator = forecastGenerator;
        }

        public String getGeneratedAt() {
            return generatedAt;
        }

        public void setGeneratedAt(String generatedAt) {
            this.generatedAt = generatedAt;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getValidTimes() {
            return validTimes;
        }

        public void setValidTimes(String validTimes) {
            this.validTimes = validTimes;
        }

        public Elevation getElevation() {
            return elevation;
        }

        public void setElevation(Elevation elevation) {
            this.elevation = elevation;
        }

        public List<Period> getPeriods() {
            return periods;
        }

        public void setPeriods(List<Period> periods) {
            this.periods = periods;
        }


        public static class Elevation {

            @SerializedName("unitCode")
            @Expose
            private String unitCode;
            @SerializedName("value")
            @Expose
            private Double value;


            public String getUnitCode() {
                return unitCode;
            }

            public void setUnitCode(String unitCode) {
                this.unitCode = unitCode;
            }

            public Double getValue() {
                return value;
            }

            public void setValue(Double value) {
                this.value = value;
            }

        }

        public static class Period {

            @SerializedName("number")
            @Expose
            private Integer number;

            @SerializedName("name")
            @Expose
            private String name;

            @SerializedName("startTime")
            @Expose
            private String startTime;

            @SerializedName("endTime")
            @Expose
            private String endTime;

            @SerializedName("isDaytime")
            @Expose
            private Boolean isDaytime;

            @SerializedName("temperature")
            @Expose
            private Integer temperature;

            @SerializedName("temperatureUnit")
            @Expose
            private String temperatureUnit;

            @SerializedName("temperatureTrend")
            @Expose
            private Object temperatureTrend;

            @SerializedName("windSpeed")
            @Expose
            private String windSpeed;

            @SerializedName("windDirection")
            @Expose
            private String windDirection;

            @SerializedName("icon")
            @Expose
            private String icon;

            @SerializedName("shortForecast")
            @Expose
            private String shortForecast;

            @SerializedName("detailedForecast")
            @Expose
            private String detailedForecast;


            public Integer getNumber() {
                return number;
            }

            public void setNumber(Integer number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public Boolean getIsDaytime() {
                return isDaytime;
            }

            public void setIsDaytime(Boolean isDaytime) {
                this.isDaytime = isDaytime;
            }

            public Integer getTemperature() {
                return temperature;
            }

            public void setTemperature(Integer temperature) {
                this.temperature = temperature;
            }

            public String getTemperatureUnit() {
                return temperatureUnit;
            }

            public void setTemperatureUnit(String temperatureUnit) {
                this.temperatureUnit = temperatureUnit;
            }

            public Object getTemperatureTrend() {
                return temperatureTrend;
            }

            public void setTemperatureTrend(Object temperatureTrend) {
                this.temperatureTrend = temperatureTrend;
            }

            public String getWindSpeed() {
                return windSpeed;
            }

            public void setWindSpeed(String windSpeed) {
                this.windSpeed = windSpeed;
            }

            public String getWindDirection() {
                return windDirection;
            }

            public void setWindDirection(String windDirection) {
                this.windDirection = windDirection;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getShortForecast() {
                return shortForecast;
            }

            public void setShortForecast(String shortForecast) {
                this.shortForecast = shortForecast;
            }

            public String getDetailedForecast() {
                return detailedForecast;
            }

            public void setDetailedForecast(String detailedForecast) {
                this.detailedForecast = detailedForecast;
            }

        }
    }

}
