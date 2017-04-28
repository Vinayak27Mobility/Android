package android.com.gorentjoy.async;

import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.util.Util;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vinayak_kulkarni on 5/9/2016.
 */
public class MyAsyncTask extends AsyncTask {
    private final Context context;
    private CustomProgressDialog progressDialog;
    private final String dbRequestId;
    private final OnTaskCompleted listener;
    private final String TAG = MyAsyncTask.class.getSimpleName();

    public MyAsyncTask(Context context, String dbReq, OnTaskCompleted listener) {
        this.context = context;
        this.dbRequestId = dbReq;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new CustomProgressDialog(context);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Object retObject = null;
        if (dbRequestId.equals(Constants.FETCH_CATEGORY)) {
            List<CategoryResponse.Category> homeCate = new ArrayList<>();
            List<CategoryResponse.Category> subCate = new ArrayList<>();
            List<CategoryResponse.Category> subsubCate = new ArrayList<>();
            List<CategoryResponse.Category> subsubsubCate = new ArrayList<>();

            List<CategoryResponse.Category> mergedCate = new ArrayList<>();
            for (CategoryResponse.Category category : DataHolder.getInstance().getAllCategories()) {
                if (category.getParentDeep().equals("0")) {
                    homeCate.add(category);
                } else if (category.getParentDeep().equals("1")) {
                    subCate.add(category);
                } else if (category.getParentDeep().equals("2")) {
                    subsubCate.add(category);
                } else if (category.getParentDeep().equals("3")) {
                    subsubsubCate.add(category);
                }
            }

            if (!(homeCate.isEmpty())) {
                Collections.sort(homeCate, new CategoryComparator());
                retObject = homeCate;
            }

            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "homeCate size " + homeCate.size());
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "subCate size " + subCate.size());
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "subsubCate size " + subsubCate.size());
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "subsubsubCate size " + subsubsubCate.size());

            if(!(subsubCate.isEmpty())) {
                Collections.sort(subsubCate, new CategoryComparator());
            }

            if(!(subsubsubCate.isEmpty())) {
                Collections.sort(subsubsubCate, new CategoryComparator());
            }

            if (!(subCate.isEmpty())) {
                Collections.sort(subCate, new CategoryComparator());
                for (CategoryResponse.Category homeCategoty : homeCate) {
                    mergedCate.add(homeCategoty);
                    for (CategoryResponse.Category subCategoty : subCate) {
                        if (subCategoty.getIdCategoryParent().equals(homeCategoty.getIdCategory())) {
                            mergedCate.add(subCategoty);
                            for (CategoryResponse.Category subsubCategoty : Util.isSafeList(subsubCate)) {
                                if (subsubCategoty.getIdCategoryParent().equals(subCategoty.getIdCategory())) {
                                    mergedCate.add(subsubCategoty);
                                    for (CategoryResponse.Category subsubsubCategoty : Util.isSafeList(subsubsubCate)) {
                                        if (subsubsubCategoty.getIdCategoryParent().equals(subsubCategoty.getIdCategory())) {
                                            mergedCate.add(subsubsubCategoty);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (!(mergedCate.isEmpty())) {
                    DataHolder.getInstance().setMergedCategories(mergedCate);
                }
            }
        }
        return retObject;
    }

    @Override
    protected void onPostExecute(Object dbResult) {
        progressDialog.dismiss();
        listener.onTaskCompleted(dbResult);
        super.onPostExecute(dbResult);
    }

    class CategoryComparator implements Comparator<CategoryResponse.Category> {

        @Override
        public int compare(CategoryResponse.Category e1, CategoryResponse.Category e2) {
            if (e1.getOrder().equals(e2.getOrder()))
                return (e1.getName().compareTo(e2.getName()));
            else
                return (Integer.valueOf(e1.getOrder()).compareTo(Integer.valueOf(e2.getOrder())));
        }
    }
}
