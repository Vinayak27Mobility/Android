package android.com.gorentjoy.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by vinayakkulkarni on 7/3/16.
 */
public class CategoryResponse {

    @SerializedName("categories")
    private List<Category> categories = new ArrayList<Category>();

    /**
     *
     * @return
     * The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public class Category {

        @SerializedName("id_category")
       
        private String idCategory;
        @SerializedName("name")
       
        private String name;
        @SerializedName("order")
       
        private String order;
        @SerializedName("created")
       
        private String created;
        @SerializedName("id_category_parent")
       
        private String idCategoryParent;
        @SerializedName("parent_deep")
       
        private String parentDeep;
        @SerializedName("seoname")
       
        private String seoname;
        @SerializedName("description")
       
        private String description;
        @SerializedName("price")
       
        private String price;
        @SerializedName("last_modified")
       
        private String lastModified;
        @SerializedName("has_image")
       
        private String hasImage;
        @SerializedName("icon")
       
        private String icon;

        /**
         *
         * @return
         * The idCategory
         */
        public String getIdCategory() {
            return idCategory;
        }

        /**
         *
         * @param idCategory
         * The id_category
         */
        public void setIdCategory(String idCategory) {
            this.idCategory = idCategory;
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
         * The order
         */
        public String getOrder() {
            return order;
        }

        /**
         *
         * @param order
         * The order
         */
        public void setOrder(String order) {
            this.order = order;
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
         * The idCategoryParent
         */
        public String getIdCategoryParent() {
            return idCategoryParent;
        }

        /**
         *
         * @param idCategoryParent
         * The id_category_parent
         */
        public void setIdCategoryParent(String idCategoryParent) {
            this.idCategoryParent = idCategoryParent;
        }

        /**
         *
         * @return
         * The parentDeep
         */
        public String getParentDeep() {
            return parentDeep;
        }

        /**
         *
         * @param parentDeep
         * The parent_deep
         */
        public void setParentDeep(String parentDeep) {
            this.parentDeep = parentDeep;
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
         * The description
         */
        public String getDescription() {
            return description;
        }

        /**
         *
         * @param description
         * The description
         */
        public void setDescription(String description) {
            this.description = description;
        }

        /**
         *
         * @return
         * The price
         */
        public String getPrice() {
            return price;
        }

        /**
         *
         * @param price
         * The price
         */
        public void setPrice(String price) {
            this.price = price;
        }

        /**
         *
         * @return
         * The lastModified
         */
        public String getLastModified() {
            return lastModified;
        }

        /**
         *
         * @param lastModified
         * The last_modified
         */
        public void setLastModified(String lastModified) {
            this.lastModified = lastModified;
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
         * The icon
         */
        public String getIcon() {
            return icon;
        }

        /**
         *
         * @param icon
         * The icon
         */
        public void setIcon(String icon) {
            this.icon = icon;
        }

    }

}
