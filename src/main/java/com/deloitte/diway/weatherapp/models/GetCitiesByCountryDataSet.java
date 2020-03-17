package com.deloitte.diway.weatherapp.models;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "NewDataSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCitiesByCountryDataSet {

    @XmlElement(name = "Table")
    private List<City> cities;

    @Override
    public String toString() {
        return "{" +
                "cities=" + cities +
                '}';
    }
}
