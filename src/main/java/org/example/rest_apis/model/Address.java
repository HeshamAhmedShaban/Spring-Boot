package org.example.rest_apis.model;

public class Address {

    private Long id;
    private String street;
    private String city;
    private String country;
    private String postalCode;

    public Address() {
    }

    public Address(
            Long id,
            String street,
            String city,
            String country,
            String postalCode
    ) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
