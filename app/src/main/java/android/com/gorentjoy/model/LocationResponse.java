package android.com.gorentjoy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vinayakkulkarni on 7/3/16.
 */
public class LocationResponse {

    @SerializedName("locations")
    private List<Location> locations = new ArrayList<Location>();

    /**
     * @return The locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * @param locations The locations
     */
    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }


    public class Location {

        @SerializedName("id_location")

        private String idLocation;
        @SerializedName("name")

        private String name;
        @SerializedName("order")

        private String order;
        @SerializedName("id_location_parent")

        private String idLocationParent;
        @SerializedName("parent_deep")

        private String parentDeep;
        @SerializedName("seoname")

        private String seoname;
        @SerializedName("description")

        private Object description;
        @SerializedName("last_modified")

        private Object lastModified;
        @SerializedName("has_image")

        private String hasImage;
        @SerializedName("latitude")

        private Object latitude;
        @SerializedName("longitude")

        private Object longitude;
        @SerializedName("id_geoname")

        private Object idGeoname;
        @SerializedName("fcodename_geoname")

        private Object fcodenameGeoname;
        @SerializedName("icon")

        private Boolean icon;

        /**
         * @return The idLocation
         */
        public String getIdLocation() {
            return idLocation;
        }

        /**
         * @param idLocation The id_location
         */
        public void setIdLocation(String idLocation) {
            this.idLocation = idLocation;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The order
         */
        public String getOrder() {
            return order;
        }

        /**
         * @param order The order
         */
        public void setOrder(String order) {
            this.order = order;
        }

        /**
         * @return The idLocationParent
         */
        public String getIdLocationParent() {
            return idLocationParent;
        }

        /**
         * @param idLocationParent The id_location_parent
         */
        public void setIdLocationParent(String idLocationParent) {
            this.idLocationParent = idLocationParent;
        }

        /**
         * @return The parentDeep
         */
        public String getParentDeep() {
            return parentDeep;
        }

        /**
         * @param parentDeep The parent_deep
         */
        public void setParentDeep(String parentDeep) {
            this.parentDeep = parentDeep;
        }

        /**
         * @return The seoname
         */
        public String getSeoname() {
            return seoname;
        }

        /**
         * @param seoname The seoname
         */
        public void setSeoname(String seoname) {
            this.seoname = seoname;
        }

        /**
         * @return The description
         */
        public Object getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(Object description) {
            this.description = description;
        }

        /**
         * @return The lastModified
         */
        public Object getLastModified() {
            return lastModified;
        }

        /**
         * @param lastModified The last_modified
         */
        public void setLastModified(Object lastModified) {
            this.lastModified = lastModified;
        }

        /**
         * @return The hasImage
         */
        public String getHasImage() {
            return hasImage;
        }

        /**
         * @param hasImage The has_image
         */
        public void setHasImage(String hasImage) {
            this.hasImage = hasImage;
        }

        /**
         * @return The latitude
         */
        public Object getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        /**
         * @return The longitude
         */
        public Object getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        /**
         * @return The idGeoname
         */
        public Object getIdGeoname() {
            return idGeoname;
        }

        /**
         * @param idGeoname The id_geoname
         */
        public void setIdGeoname(Object idGeoname) {
            this.idGeoname = idGeoname;
        }

        /**
         * @return The fcodenameGeoname
         */
        public Object getFcodenameGeoname() {
            return fcodenameGeoname;
        }

        /**
         * @param fcodenameGeoname The fcodename_geoname
         */
        public void setFcodenameGeoname(Object fcodenameGeoname) {
            this.fcodenameGeoname = fcodenameGeoname;
        }

        /**
         * @return The icon
         */
        public Boolean getIcon() {
            return icon;
        }

        /**
         * @param icon The icon
         */
        public void setIcon(Boolean icon) {
            this.icon = icon;
        }


    }
}
