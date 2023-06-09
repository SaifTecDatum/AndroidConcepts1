package com.myapps.androidconcepts.Models;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecyclerRetrofit_Model implements Parcelable {

    public final static Creator<RecyclerRetrofit_Model> CREATOR = new Creator<RecyclerRetrofit_Model>() {
        public RecyclerRetrofit_Model createFromParcel(android.os.Parcel in) {
            return new RecyclerRetrofit_Model(in);
        }

        public RecyclerRetrofit_Model[] newArray(int size) {
            return (new RecyclerRetrofit_Model[size]);
        }
    };

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("company")
    @Expose
    private Company company;

    protected RecyclerRetrofit_Model(android.os.Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.username = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.address = ((Address) in.readValue((Address.class.getClassLoader())));
        this.phone = ((String) in.readValue((String.class.getClassLoader())));
        this.website = ((String) in.readValue((String.class.getClassLoader())));
        this.company = ((Company) in.readValue((Company.class.getClassLoader())));
    }

    public RecyclerRetrofit_Model() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(username);
        dest.writeValue(email);
        dest.writeValue(address);
        dest.writeValue(phone);
        dest.writeValue(website);
        dest.writeValue(company);
    }

    public int describeContents() {
        return 0;
    }

    public static class Address implements Parcelable {
        public final static Creator<Address> CREATOR = new Creator<Address>() {
            public Address createFromParcel(android.os.Parcel in) {
                return new Address(in);
            }

            public Address[] newArray(int size) {
                return (new Address[size]);
            }
        };
        @SerializedName("street")
        @Expose
        private String street;
        @SerializedName("suite")
        @Expose
        private String suite;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("zipcode")
        @Expose
        private String zipcode;
        @SerializedName("geo")
        @Expose
        private Geo geo;

        protected Address(android.os.Parcel in) {
            this.street = ((String) in.readValue((String.class.getClassLoader())));
            this.suite = ((String) in.readValue((String.class.getClassLoader())));
            this.city = ((String) in.readValue((String.class.getClassLoader())));
            this.zipcode = ((String) in.readValue((String.class.getClassLoader())));
            this.geo = ((Geo) in.readValue((Geo.class.getClassLoader())));
        }

        public Address() {
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public Geo getGeo() {
            return geo;
        }

        public void setGeo(Geo geo) {
            this.geo = geo;
        }

        public void writeToParcel(android.os.Parcel dest, int flags) {
            dest.writeValue(street);
            dest.writeValue(suite);
            dest.writeValue(city);
            dest.writeValue(zipcode);
            dest.writeValue(geo);
        }

        public int describeContents() {
            return 0;
        }

        public static class Geo implements Parcelable {
            public final static Creator<Geo> CREATOR = new Creator<Geo>() {
                public Geo createFromParcel(android.os.Parcel in) {
                    return new Geo(in);
                }

                public Geo[] newArray(int size) {
                    return (new Geo[size]);
                }
            };
            @SerializedName("lat")
            @Expose
            private String lat;
            @SerializedName("lng")
            @Expose
            private String lng;

            protected Geo(android.os.Parcel in) {
                this.lat = ((String) in.readValue((String.class.getClassLoader())));
                this.lng = ((String) in.readValue((String.class.getClassLoader())));
            }

            public Geo() {
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public void writeToParcel(android.os.Parcel dest, int flags) {
                dest.writeValue(lat);
                dest.writeValue(lng);
            }

            public int describeContents() {
                return 0;
            }

        }
    }

    public static class Company implements Parcelable {
        public final static Creator<Company> CREATOR = new Creator<Company>() {
            public Company createFromParcel(android.os.Parcel in) {
                return new Company(in);
            }

            public Company[] newArray(int size) {
                return (new Company[size]);
            }
        };
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("catchPhrase")
        @Expose
        private String catchPhrase;
        @SerializedName("bs")
        @Expose
        private String bs;

        protected Company(android.os.Parcel in) {
            this.name = ((String) in.readValue((String.class.getClassLoader())));
            this.catchPhrase = ((String) in.readValue((String.class.getClassLoader())));
            this.bs = ((String) in.readValue((String.class.getClassLoader())));
        }

        public Company() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }

        public void writeToParcel(android.os.Parcel dest, int flags) {
            dest.writeValue(name);
            dest.writeValue(catchPhrase);
            dest.writeValue(bs);
        }

        public int describeContents() {
            return 0;
        }

    }
}







