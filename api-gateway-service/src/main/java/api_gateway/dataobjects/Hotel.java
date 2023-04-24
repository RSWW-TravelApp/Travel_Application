package api_gateway.dataobjects;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Hotel {
    private String id;
    private String name;
    private String country;
    private String city;
    private Integer rooms;
    private Integer adults;
    private Integer ppl3plus;
    private Integer ppl10plus;
    private Integer ppl18plus;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    public Hotel() {
    }

    public Hotel(String id, String name, String country, String city, Integer rooms, Integer adults, Integer ppl3plus, Integer ppl10plus, Integer ppl18plus, LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.rooms = rooms;
        this.adults = adults;
        this.ppl3plus = ppl3plus;
        this.ppl10plus = ppl10plus;
        this.ppl18plus = ppl18plus;
        this.startDate = startDate;
    }

    public Integer getAdults() {
        return adults;
    }

    public void setAdults(Integer adults) {
        this.adults = adults;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public Integer getPpl3plus() {
        return ppl3plus;
    }

    public void setPpl3plus(Integer ppl3plus) {
        this.ppl3plus = ppl3plus;
    }

    public Integer getPpl10plus() {
        return ppl10plus;
    }

    public void setPpl10plus(Integer ppl10plus) {
        this.ppl10plus = ppl10plus;
    }

    public Integer getPpl18plus() {
        return ppl18plus;
    }

    public void setPpl18plus(Integer ppl18plus) {
        this.ppl18plus = ppl18plus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
