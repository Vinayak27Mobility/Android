package android.com.gorentjoy.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by vinayakkulkarni on 7/2/16.
 */
public class RegisterResponse {

    @SerializedName("user")
    private User user;

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    public class User {

        @SerializedName("hybridauth_provider_name")

        private Object hybridauthProviderName;
        @SerializedName("has_image")

        private String hasImage;
        @SerializedName("cf_about")

        private Object cfAbout;
        @SerializedName("logins")

        private String logins;
        @SerializedName("id_location")

        private Object idLocation;
        @SerializedName("last_modified")

        private Object lastModified;
        @SerializedName("id_user")

        private String idUser;
        @SerializedName("rate")

        private Object rate;
        @SerializedName("user_token")

        private String userToken;
        @SerializedName("created")

        private String created;
        @SerializedName("description")

        private Object description;
        @SerializedName("api_token")

        private String apiToken;
        @SerializedName("name")

        private String name;
        @SerializedName("stripe_user_id")

        private Object stripeUserId;
        @SerializedName("last_failed")

        private Object lastFailed;
        @SerializedName("last_ip")

        private Object lastIp;
        @SerializedName("status")

        private String status;
        @SerializedName("id_role")

        private String idRole;
        @SerializedName("image")

        private String image;
        @SerializedName("device_id")

        private Object deviceId;
        @SerializedName("notification_date")

        private Object notificationDate;
        @SerializedName("last_login")

        private Object lastLogin;
        @SerializedName("cf_duration")

        private Object cfDuration;
        @SerializedName("failed_attempts")

        private String failedAttempts;
        @SerializedName("email")

        private String email;
        @SerializedName("seoname")

        private String seoname;
        @SerializedName("subscriber")

        private String subscriber;

        /**
         *
         * @return
         * The hybridauthProviderName
         */
        public Object getHybridauthProviderName() {
            return hybridauthProviderName;
        }

        /**
         *
         * @param hybridauthProviderName
         * The hybridauth_provider_name
         */
        public void setHybridauthProviderName(Object hybridauthProviderName) {
            this.hybridauthProviderName = hybridauthProviderName;
        }

        /**
         *
         * @return
         * The hasImage
         */
        public String getHasImage() {
            return hasImage;
        }

        /**
         *
         * @param hasImage
         * The has_image
         */
        public void setHasImage(String hasImage) {
            this.hasImage = hasImage;
        }

        /**
         *
         * @return
         * The cfAbout
         */
        public Object getCfAbout() {
            return cfAbout;
        }

        /**
         *
         * @param cfAbout
         * The cf_about
         */
        public void setCfAbout(Object cfAbout) {
            this.cfAbout = cfAbout;
        }

        /**
         *
         * @return
         * The logins
         */
        public String getLogins() {
            return logins;
        }

        /**
         *
         * @param logins
         * The logins
         */
        public void setLogins(String logins) {
            this.logins = logins;
        }

        /**
         *
         * @return
         * The idLocation
         */
        public Object getIdLocation() {
            return idLocation;
        }

        /**
         *
         * @param idLocation
         * The id_location
         */
        public void setIdLocation(Object idLocation) {
            this.idLocation = idLocation;
        }

        /**
         *
         * @return
         * The lastModified
         */
        public Object getLastModified() {
            return lastModified;
        }

        /**
         *
         * @param lastModified
         * The last_modified
         */
        public void setLastModified(Object lastModified) {
            this.lastModified = lastModified;
        }

        /**
         *
         * @return
         * The idUser
         */
        public String getIdUser() {
            return idUser;
        }

        /**
         *
         * @param idUser
         * The id_user
         */
        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        /**
         *
         * @return
         * The rate
         */
        public Object getRate() {
            return rate;
        }

        /**
         *
         * @param rate
         * The rate
         */
        public void setRate(Object rate) {
            this.rate = rate;
        }

        /**
         *
         * @return
         * The userToken
         */
        public String getUserToken() {
            return userToken;
        }

        /**
         *
         * @param userToken
         * The user_token
         */
        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        /**
         *
         * @return
         * The created
         */
        public String getCreated() {
            return created;
        }

        /**
         *
         * @param created
         * The created
         */
        public void setCreated(String created) {
            this.created = created;
        }

        /**
         *
         * @return
         * The description
         */
        public Object getDescription() {
            return description;
        }

        /**
         *
         * @param description
         * The description
         */
        public void setDescription(Object description) {
            this.description = description;
        }

        /**
         *
         * @return
         * The apiToken
         */
        public String getApiToken() {
            return apiToken;
        }

        /**
         *
         * @param apiToken
         * The api_token
         */
        public void setApiToken(String apiToken) {
            this.apiToken = apiToken;
        }

        /**
         *
         * @return
         * The name
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @param name
         * The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         *
         * @return
         * The stripeUserId
         */
        public Object getStripeUserId() {
            return stripeUserId;
        }

        /**
         *
         * @param stripeUserId
         * The stripe_user_id
         */
        public void setStripeUserId(Object stripeUserId) {
            this.stripeUserId = stripeUserId;
        }

        /**
         *
         * @return
         * The lastFailed
         */
        public Object getLastFailed() {
            return lastFailed;
        }

        /**
         *
         * @param lastFailed
         * The last_failed
         */
        public void setLastFailed(Object lastFailed) {
            this.lastFailed = lastFailed;
        }

        /**
         *
         * @return
         * The lastIp
         */
        public Object getLastIp() {
            return lastIp;
        }

        /**
         *
         * @param lastIp
         * The last_ip
         */
        public void setLastIp(Object lastIp) {
            this.lastIp = lastIp;
        }

        /**
         *
         * @return
         * The status
         */
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The idRole
         */
        public String getIdRole() {
            return idRole;
        }

        /**
         *
         * @param idRole
         * The id_role
         */
        public void setIdRole(String idRole) {
            this.idRole = idRole;
        }

        /**
         *
         * @return
         * The image
         */
        public String getImage() {
            return image;
        }

        /**
         *
         * @param image
         * The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         *
         * @return
         * The deviceId
         */
        public Object getDeviceId() {
            return deviceId;
        }

        /**
         *
         * @param deviceId
         * The device_id
         */
        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        /**
         *
         * @return
         * The notificationDate
         */
        public Object getNotificationDate() {
            return notificationDate;
        }

        /**
         *
         * @param notificationDate
         * The notification_date
         */
        public void setNotificationDate(Object notificationDate) {
            this.notificationDate = notificationDate;
        }

        /**
         *
         * @return
         * The lastLogin
         */
        public Object getLastLogin() {
            return lastLogin;
        }

        /**
         *
         * @param lastLogin
         * The last_login
         */
        public void setLastLogin(Object lastLogin) {
            this.lastLogin = lastLogin;
        }

        /**
         *
         * @return
         * The cfDuration
         */
        public Object getCfDuration() {
            return cfDuration;
        }

        /**
         *
         * @param cfDuration
         * The cf_duration
         */
        public void setCfDuration(Object cfDuration) {
            this.cfDuration = cfDuration;
        }

        /**
         *
         * @return
         * The failedAttempts
         */
        public String getFailedAttempts() {
            return failedAttempts;
        }

        /**
         *
         * @param failedAttempts
         * The failed_attempts
         */
        public void setFailedAttempts(String failedAttempts) {
            this.failedAttempts = failedAttempts;
        }

        /**
         *
         * @return
         * The email
         */
        public String getEmail() {
            return email;
        }

        /**
         *
         * @param email
         * The email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         *
         * @return
         * The seoname
         */
        public String getSeoname() {
            return seoname;
        }

        /**
         *
         * @param seoname
         * The seoname
         */
        public void setSeoname(String seoname) {
            this.seoname = seoname;
        }

        /**
         *
         * @return
         * The subscriber
         */
        public String getSubscriber() {
            return subscriber;
        }

        /**
         *
         * @param subscriber
         * The subscriber
         */
        public void setSubscriber(String subscriber) {
            this.subscriber = subscriber;
        }

    }

}
