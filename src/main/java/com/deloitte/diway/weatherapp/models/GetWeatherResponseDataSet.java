package com.deloitte.diway.weatherapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "NewDataSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetWeatherResponseDataSet {

    @XmlElement(name = "Location")
    private String location;

    @XmlElement(name = "Time")
    private String time;

    @XmlElement(name = "Wind")
    private String wind;

    @XmlElement(name = "Visibility")
    private String visibility;

    @XmlElement(name = "SkyConditions")
    private String skyConditions;

    @XmlElement(name = "Temperature")
    private String temperature;

    @XmlElement(name = "DewPoint")
    private String dewPoint;

    @XmlElement(name = "RelativeHumidity")
    private String relativeHumidity;

    @XmlElement(name = "Status")
    private String status;

    @Override
    public String toString() {
        return "{" +
                "location='" + location + '\'' +
                ", time='" + time + '\'' +
                ", wind='" + wind + '\'' +
                ", visibility='" + visibility + '\'' +
                ", skyConditions='" + skyConditions + '\'' +
                ", temperature='" + temperature + '\'' +
                ", dewPoint='" + dewPoint + '\'' +
                ", relativeHumidity='" + relativeHumidity + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
