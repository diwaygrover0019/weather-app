package com.deloitte.diway.weatherapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name = "Table")
@XmlAccessorType(XmlAccessType.FIELD)
public class City {

    @XmlElement(name = "Country")
    private String country;

    @XmlElement(name = "City")
    private String city;

    @Override
    public String toString() {
        return "{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
