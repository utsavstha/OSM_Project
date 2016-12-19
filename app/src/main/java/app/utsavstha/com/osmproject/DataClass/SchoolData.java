package app.utsavstha.com.osmproject.DataClass;

/**
 * Created by utsav on 12/18/2016.
 */
public class SchoolData {
    private int id;
    private double lat;
    private double lon;

    private String name;
    private String designation;
    private String contactEmail;
    private String description;
    private String phoneNumber;
    private String website;
    private String addressHouseNumber;
    private String addressStreet;
    private String openingHours;

    public SchoolData(int id, double lat, double lon, String name,
                      String designation, String contactEmail,
                      String description, String phoneNumber, String website,
                      String addressHouseNumber, String addressStreet,
                      String openingHours) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.designation = designation;
        this.contactEmail = contactEmail;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.addressHouseNumber = addressHouseNumber;
        this.addressStreet = addressStreet;
        this.openingHours = openingHours;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public String getDesignation() {
        return designation;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getOpeningHours() {
        return openingHours;
    }
}
