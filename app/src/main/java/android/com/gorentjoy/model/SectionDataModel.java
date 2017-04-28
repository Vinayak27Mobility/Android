package android.com.gorentjoy.model;

import java.util.ArrayList;

/**
 * Created by vinayakkulkarni on 7/3/16.
 */
public class SectionDataModel {



    private String headerTitle;
    private String headerId;

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    private ArrayList<AdResponse.Ad> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerTitle, ArrayList<AdResponse.Ad> allItemsInSection) {
        this.headerTitle = headerTitle;
        //this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<AdResponse.Ad> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<AdResponse.Ad> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}
