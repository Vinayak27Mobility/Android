package android.com.gorentjoy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vinayakkulkarni on 7/3/16.
 */
public class AdResponse {

    @SerializedName("Ad")
    private List<Ad> ads = new ArrayList<Ad>();

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

    public class Ad {

        @SerializedName("favorited")

        private String favorited;
        @SerializedName("cf_eyecolor")

        private Object cfEyecolor;
        @SerializedName("created")

        private String created;
        @SerializedName("address")

        private String address;
        @SerializedName("has_images")

        private String hasImages;
        @SerializedName("cf_duration")

        private String cfDuration;
        @SerializedName("cf_from")

        private Object cfFrom;
        @SerializedName("description")

        private String description;
        @SerializedName("thumb")

        private String thumb;
        @SerializedName("url")

        private String url;
        @SerializedName("cf_body")

        private Object cfBody;
        @SerializedName("website")

        private Object website;
        @SerializedName("last_modified")

        private Object lastModified;
        @SerializedName("cf_height")

        private Object cfHeight;
        @SerializedName("id_category")

        private String idCategory;
        @SerializedName("featured")

        private Object featured;
        @SerializedName("cf_per_month")

        private Object cfPerMonth;
        @SerializedName("id_location")

        private String idLocation;
        @SerializedName("ip_address")

        private Object ipAddress;
        @SerializedName("id_ad")

        private String idAd;
        @SerializedName("longitude")

        private String longitude;
        @SerializedName("status")

        private String status;
        @SerializedName("cf_to")

        private Object cfTo;
        @SerializedName("price")

        private String price;
        @SerializedName("published")

        private String published;
        @SerializedName("cf_hair")

        private Object cfHair;
        @SerializedName("cf_status")

        private Object cfStatus;

        @SerializedName("cf_age")

        private Object cfAge;
        @SerializedName("id_user")

        private String idUser;
        @SerializedName("stock")

        private Object stock;
        @SerializedName("title")

        private String title;
        @SerializedName("seotitle")

        private String seotitle;
        @SerializedName("phone")

        private String phone;
        @SerializedName("rate")

        private Object rate;
        @SerializedName("cf_occupation")

        private Object cfOccupation;
        @SerializedName("latitude")

        private String latitude;

        /**
         * @return The favorited
         */
        public String getFavorited() {
            return favorited;
        }

        /**
         * @param favorited The favorited
         */
        public void setFavorited(String favorited) {
            this.favorited = favorited;
        }

        /**
         * @return The cfEyecolor
         */
        public Object getCfEyecolor() {
            return cfEyecolor;
        }

        /**
         * @param cfEyecolor The cf_eyecolor
         */
        public void setCfEyecolor(Object cfEyecolor) {
            this.cfEyecolor = cfEyecolor;
        }

        /**
         * @return The created
         */
        public String getCreated() {
            return created;
        }

        /**
         * @param created The created
         */
        public void setCreated(String created) {
            this.created = created;
        }

        /**
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @param address The address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * @return The hasImages
         */
        public String getHasImages() {
            return hasImages;
        }

        /**
         * @param hasImages The has_images
         */
        public void setHasImages(String hasImages) {
            this.hasImages = hasImages;
        }

        /**
         * @return The cfDuration
         */
        public String getCfDuration() {
            return cfDuration;
        }

        /**
         * @param cfDuration The cf_duration
         */
        public void setCfDuration(String cfDuration) {
            this.cfDuration = cfDuration;
        }

        /**
         * @return The cfFrom
         */
        public Object getCfFrom() {
            return cfFrom;
        }

        /**
         * @param cfFrom The cf_from
         */
        public void setCfFrom(Object cfFrom) {
            this.cfFrom = cfFrom;
        }

        /**
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * @param description The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         * @return The thumb
         */
        public String getThumb() {
            return thumb;
        }

        /**
         * @param thumb The thumb
         */
        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        /**
         * @return The url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url The url
         */
        public void setUrl(String url) {
            this.url = url;
        }

        /**
         * @return The cfBody
         */
        public Object getCfBody() {
            return cfBody;
        }

        /**
         * @param cfBody The cf_body
         */
        public void setCfBody(Object cfBody) {
            this.cfBody = cfBody;
        }

        /**
         * @return The website
         */
        public Object getWebsite() {
            return website;
        }

        /**
         * @param website The website
         */
        public void setWebsite(Object website) {
            this.website = website;
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
         * @return The cfHeight
         */
        public Object getCfHeight() {
            return cfHeight;
        }

        /**
         * @param cfHeight The cf_height
         */
        public void setCfHeight(Object cfHeight) {
            this.cfHeight = cfHeight;
        }

        /**
         * @return The idCategory
         */
        public String getIdCategory() {
            return idCategory;
        }

        /**
         * @param idCategory The id_category
         */
        public void setIdCategory(String idCategory) {
            this.idCategory = idCategory;
        }

        /**
         * @return The featured
         */
        public Object getFeatured() {
            return featured;
        }

        /**
         * @param featured The featured
         */
        public void setFeatured(Object featured) {
            this.featured = featured;
        }

        /**
         * @return The cfPerMonth
         */
        public Object getCfPerMonth() {
            return cfPerMonth;
        }

        /**
         * @param cfPerMonth The cf_per_month
         */
        public void setCfPerMonth(Object cfPerMonth) {
            this.cfPerMonth = cfPerMonth;
        }

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
         * @return The ipAddress
         */
        public Object getIpAddress() {
            return ipAddress;
        }

        /**
         * @param ipAddress The ip_address
         */
        public void setIpAddress(Object ipAddress) {
            this.ipAddress = ipAddress;
        }

        /**
         * @return The idAd
         */
        public String getIdAd() {
            return idAd;
        }

        /**
         * @param idAd The id_ad
         */
        public void setIdAd(String idAd) {
            this.idAd = idAd;
        }

        /**
         * @return The longitude
         */
        public String getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        /**
         * @return The status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @param status The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * @return The cfTo
         */
        public Object getCfTo() {
            return cfTo;
        }

        /**
         * @param cfTo The cf_to
         */
        public void setCfTo(Object cfTo) {
            this.cfTo = cfTo;
        }

        /**
         * @return The price
         */
        public String getPrice() {
            return price;
        }

        /**
         * @param price The price
         */
        public void setPrice(String price) {
            this.price = price;
        }

        /**
         * @return The published
         */
        public String getPublished() {
            return published;
        }

        /**
         * @param published The published
         */
        public void setPublished(String published) {
            this.published = published;
        }

        /**
         * @return The cfHair
         */
        public Object getCfHair() {
            return cfHair;
        }

        /**
         * @param cfHair The cf_hair
         */
        public void setCfHair(Object cfHair) {
            this.cfHair = cfHair;
        }

        /**
         * @return The cfStatus
         */
        public Object getCfStatus() {
            return cfStatus;
        }

        /**
         * @param cfStatus The cf_status
         */
        public void setCfStatus(Object cfStatus) {
            this.cfStatus = cfStatus;
        }

        /**
         * @return The cfAge
         */
        public Object getCfAge() {
            return cfAge;
        }

        /**
         * @param cfAge The cf_age
         */
        public void setCfAge(Object cfAge) {
            this.cfAge = cfAge;
        }

        /**
         * @return The idUser
         */
        public String getIdUser() {
            return idUser;
        }

        /**
         * @param idUser The id_user
         */
        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        /**
         * @return The stock
         */
        public Object getStock() {
            return stock;
        }

        /**
         * @param stock The stock
         */
        public void setStock(Object stock) {
            this.stock = stock;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The seotitle
         */
        public String getSeotitle() {
            return seotitle;
        }

        /**
         * @param seotitle The seotitle
         */
        public void setSeotitle(String seotitle) {
            this.seotitle = seotitle;
        }

        /**
         * @return The phone
         */
        public String getPhone() {
            return phone;
        }

        /**
         * @param phone The phone
         */
        public void setPhone(String phone) {
            this.phone = phone;
        }

        /**
         * @return The rate
         */
        public Object getRate() {
            return rate;
        }

        /**
         * @param rate The rate
         */
        public void setRate(Object rate) {
            this.rate = rate;
        }

        /**
         * @return The cfOccupation
         */
        public Object getCfOccupation() {
            return cfOccupation;
        }

        /**
         * @param cfOccupation The cf_occupation
         */
        public void setCfOccupation(Object cfOccupation) {
            this.cfOccupation = cfOccupation;
        }

        /**
         * @return The latitude
         */
        public String getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

    }

}
