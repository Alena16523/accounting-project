package com.cydeo.javahedgehogsproject.dto;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "eur",
        "cad",
        "gbp",
        "inr",
        "jpy"
})
@Generated("jsonschema2pojo")
public class Usd {

    @JsonProperty("eur")
    private Double eur;

    @JsonProperty("cad")
    private Double cad;

    @JsonProperty("gbp")
    private Double gbp;

    @JsonProperty("inr")
    private Double inr;

    @JsonProperty("jpy")
    private Double jpy;


    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @JsonAnyGetter
    public Double getEur() {
        return eur;
    }

    @JsonAnySetter
    public void setEur(Double eur) {
        this.eur = eur;
    }

    @JsonAnyGetter
    public Double getCad() {
        return cad;
    }

    @JsonAnySetter
    public void setCad(Double cad) {
        this.cad = cad;
    }

    @JsonAnyGetter
    public Double getGbp() {
        return gbp;
    }

    @JsonAnySetter
    public void setGbp(Double gbp) {
        this.gbp = gbp;
    }

    @JsonAnyGetter
    public Double getInr() {
        return inr;
    }

    @JsonAnySetter
    public void setInr(Double inr) {
        this.inr = inr;
    }

    @JsonAnyGetter
    public Double getJpy() {
        return jpy;
    }

    @JsonAnySetter
    public void setJpy(Double jpy) {
        this.jpy = jpy;
    }
}