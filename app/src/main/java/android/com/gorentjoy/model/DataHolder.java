package android.com.gorentjoy.model;

import android.com.gorentjoy.ui.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinayakkulkarni on 7/31/16.
 */
public class DataHolder {
    private List<CategoryResponse.Category> allCategories;
    private List<CategoryResponse.Category> homeCategories;
    private List<CategoryResponse.Category> mergedCategories;

    public ArrayList<AdResponse.Ad> getSelectedAds() {
        return selectedAds;
    }

    public void setSelectedAds(ArrayList<AdResponse.Ad> selectedAds) {
        this.selectedAds = selectedAds;
    }

    private ArrayList<AdResponse.Ad> selectedAds;

    public List<CategoryResponse.Category> getMergedCategories() {
        return mergedCategories;
    }

    public void setMergedCategories(List<CategoryResponse.Category> mergedCategories) {
        this.mergedCategories = mergedCategories;
    }

    private AdResponse.Ad selectedAd;

    public AdResponse.Ad getSelectedAd() {
        return selectedAd;
    }

    public void setSelectedAd(AdResponse.Ad selectedAd) {
        this.selectedAd = selectedAd;
    }

    public List<CategoryResponse.Category> getHomeCategories() {
        return homeCategories;
    }

    public void setHomeCategories(List<CategoryResponse.Category> homeCategories) {
        this.homeCategories = homeCategories;
    }
    private List<LocationResponse.Location> allLocations;
    private List<LocationResponse.Location> mergedLocations;

    public List<LocationResponse.Location> getMergedLocations() {
        return mergedLocations;
    }

    public void setMergedLocations(List<LocationResponse.Location> mergedLocations) {
        this.mergedLocations = mergedLocations;
    }

    private String parentCategoryId;
    private String selectedCityId;
    private String selectedAreaId;

    public String getSelectedAreaId() {
        return selectedAreaId;
    }

    public void setSelectedAreaId(String selectedAreaId) {
        this.selectedAreaId = selectedAreaId;
    }

    public List<LocationResponse.Location> getAllLocations() {
        return allLocations;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public String getSelectedCityId() {
        return selectedCityId;
    }

    public void setSelectedCityId(String selectedCityId) {
        this.selectedCityId = selectedCityId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public void setAllLocations(List<LocationResponse.Location> allLocations) {
        this.allLocations = allLocations;
    }

    public List<CategoryResponse.Category> getAllCategories() {
        return allCategories;
    }

    public void setAllCategories(List<CategoryResponse.Category> allCategories) {
        this.allCategories = allCategories;
    }

    public void clearData() {
        allCategories = null;
        homeCategories = null;
        allLocations = null;
        mergedCategories = null;
        mergedLocations = null;
        HomeActivity.AdHandler.index = 0;
        HomeActivity.AdHandler.adapter = null;
    }

    private static final DataHolder holder = new DataHolder();

    public static DataHolder getInstance() {
        return holder;
    }
}
