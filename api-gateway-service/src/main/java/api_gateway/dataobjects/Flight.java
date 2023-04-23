package api_gateway.dataobjects;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class Flight {
    private String id;
    @NotEmpty
    private String srcCountry;
    @NotEmpty
    private String dstCountry;
    @NotEmpty
    private String srcCity;
    @NotEmpty
    private String dstCity;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private Integer price;

    public Flight() {
        // To avoid problem with conversion to JSON object
    }

    public Flight(String id, String srcCountry, String dstCountry, String srcCity, String dstCity, LocalDate startDate, Integer price) {
        this.id = id;
        this.srcCountry = srcCountry;
        this.dstCountry = dstCountry;
        this.srcCity = srcCity;
        this.dstCity = dstCity;
        this.startDate = startDate;
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcCountry() {
        return srcCountry;
    }

    public void setSrcCountry(String srcCountry) {
        this.srcCountry = srcCountry;
    }

    public String getDstCountry() {
        return dstCountry;
    }

    public void setDstCountry(String dstCountry) {
        this.dstCountry = dstCountry;
    }

    public String getSrcCity() {
        return srcCity;
    }

    public void setSrcCity(String srcCity) {
        this.srcCity = srcCity;
    }

    public String getDstCity() {
        return dstCity;
    }

    public void setDstCity(String dstCity) {
        this.dstCity = dstCity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
