package android.com.gorentjoy.ui.fragments;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.net.NetworkErrorManager;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.service.PublishListingService;
import android.com.gorentjoy.storage.SharedPrefWrapper;
import android.com.gorentjoy.ui.FetchContentActivity;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.ui.adapter.PhotoAdaper;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.ui.widgets.MyGridView;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.util.Util;
import android.com.gorentjoy.util.WeakReferenceHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class UpdateDetailFragment extends Fragment implements View.OnClickListener{
    private Context context;

    private final String TAG = UpdateDetailFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private TextView cateTV, locationTV;
    private Button updateBtn;
    private TextInputLayout titleLayout, descLayout, priceLayout, phoneLayout, addressLayout;
    private EditText titleET, descET, priceET, phoneET, addressET;
    private Spinner priceModeSpinner;
    private String selectedCateId;
    private String selectedLocationId;
    private MyGridView photoGrid;
    private PhotoAdaper photoAdaper;
    private List<Bitmap> photoData;
    private static final int REQUEST_CAMERA = 0;
    private static final int REQUEST_GALLERY = 1;
    private static final int ADD_PHOTO = 2;
    private static final int UPDATE_PHOTO = 3;
    private static int SELECTED_PHOTO_INDEX = 0;
    private static int ACTION_SELECTED;
    private View rootView;
    private AdResponse.Ad selectedAd;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        ((HomeActivity) context).setActionBarTitle(getResources().getString(R.string.ad_detail_caption));
        rootView = inflater.inflate(R.layout.fragment_update_ad, container, false);

        selectedAd = DataHolder.getInstance().getSelectedAd();

        if (!TextUtils.isEmpty(selectedAd.getThumb())) {
            Picasso.with(context).load(selectedAd.getThumb()).into((ImageView) rootView.findViewById(R.id.adimageid));
        }

        cateTV = (TextView) rootView.findViewById(R.id.textcateid);
        cateTV.setOnClickListener(this);
        locationTV = (TextView) rootView.findViewById(R.id.textlocationid);
        locationTV.setOnClickListener(this);

        selectedCateId = selectedAd.getIdCategory();
        //cateTV.setText(selectedAd.getIdCategory());
        selectedLocationId = selectedAd.getIdLocation();
        //locationTV.setText();

        updateBtn = (Button) rootView.findViewById(R.id.btnpublishid);
        updateBtn.setOnClickListener(this);
        titleLayout = (TextInputLayout) rootView.findViewById(R.id.editlayouttitleid);
        descLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutdescid);
        priceLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutpriceid);
        phoneLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutphoneid);
        addressLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutaddressid);
        titleET = (EditText) rootView.findViewById(R.id.edittitleid);
        titleET.setText(selectedAd.getTitle());
        descET = (EditText) rootView.findViewById(R.id.editdescid);
        descET.setText(selectedAd.getDescription());
        priceET = (EditText) rootView.findViewById(R.id.editpriceid);
        priceET.setText(selectedAd.getPrice());
        priceModeSpinner = (Spinner) rootView.findViewById(R.id.spinnerpricemodeid);
        phoneET = (EditText) rootView.findViewById(R.id.editphoneid);
        phoneET.setText(selectedAd.getPhone());
        addressET = (EditText) rootView.findViewById(R.id.editaddressid);
        addressET.setText(selectedAd.getAddress());
        photoGrid = (MyGridView) rootView.findViewById(R.id.photolayoutid);
        addPhotoImage(0);

        photoAdaper = new PhotoAdaper(context, photoData);
        photoGrid.setAdapter(photoAdaper);
        photoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (photoData.size() == (Constants.MAX_PHOTO + 1) && position == 0) {
                    String text = String.format(getResources().getString(R.string.alert_max_photo), Constants.MAX_PHOTO);
                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                } else {
                    if (position == 0) {
                        ACTION_SELECTED = ADD_PHOTO;
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.popup_camera);
                        dialog.setTitle(getResources().getString(R.string.ad_popup_title));
                        dialog.setCancelable(true);
                        // there are a lot of settings, for dialog, check them all out!
                        // set up radiobutton
                        RadioButton camera = (RadioButton) dialog.findViewById(R.id.camera);
                        RadioButton gallery = (RadioButton) dialog.findViewById(R.id.gallery);
                        camera.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                requestForCameraPermission(REQUEST_CAMERA);
                            }
                        });
                        gallery.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                requestForCameraPermission(REQUEST_GALLERY);
                            }
                        });
                        dialog.show();
                    } else {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.popup_camera_edit);
                        dialog.setTitle(getResources().getString(R.string.ad_popup_title));
                        dialog.setCancelable(true);
                        // there are a lot of settings, for dialog, check them all out!
                        // set up radiobutton
                        RadioButton replace = (RadioButton) dialog.findViewById(R.id.replace);
                        RadioButton delete = (RadioButton) dialog.findViewById(R.id.delete);
                        replace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ACTION_SELECTED = UPDATE_PHOTO;
                                SELECTED_PHOTO_INDEX = position;
                                dialog.dismiss();
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.popup_camera);
                                dialog.setTitle(getResources().getString(R.string.ad_popup_title));
                                dialog.setCancelable(true);
                                // there are a lot of settings, for dialog, check them all out!
                                // set up radiobutton
                                RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.camera);
                                RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.gallery);
                                rd1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        requestForCameraPermission(REQUEST_CAMERA);
                                    }
                                });
                                rd2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                        requestForCameraPermission(REQUEST_GALLERY);
                                    }
                                });
                                dialog.show();
                            }
                        });
                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                photoData.remove(position);
                                photoAdaper.notifyDataSetChanged();
                            }
                        });
                        dialog.show();
                    }
                }

            }
        });

        return rootView;
    }

    private void addPhotoImage(int index) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.add);
        photoData = new ArrayList<>();
        photoData.add(index, largeIcon);
    }

    public void requestForCameraPermission(int request) {
        if (request == REQUEST_CAMERA) {
            // Start CameraActivity
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_GALLERY);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "resultCode code is " + resultCode);
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "requestCode code is " + requestCode);
        //if (resultCode != RESULT_OK) return;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "data is " + data);
        if (requestCode == Integer.parseInt(Constants.FETCH_CATEGORY) || requestCode == Integer.parseInt(Constants.FETCH_LOCATION)) {
            String[] receData = data.getStringArrayExtra(Constants.FETCH_CONTENT_REQ);

            if (requestCode == Integer.parseInt(Constants.FETCH_CATEGORY)) {
                selectedCateId = receData[1];
                cateTV.setText(receData[0]);
            } else if (requestCode == Integer.parseInt(Constants.FETCH_LOCATION)) {
                selectedLocationId = receData[1];
                locationTV.setText(receData[0]);
            }
        } else if (requestCode == REQUEST_CAMERA || requestCode == REQUEST_GALLERY) {
            switch (requestCode) {
                case REQUEST_CAMERA:
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                    File destination = new File(Environment.getExternalStorageDirectory(),
                            System.currentTimeMillis() + ".jpg");
                    FileOutputStream fo;
                    try {
                        destination.createNewFile();
                        fo = new FileOutputStream(destination);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    switch (ACTION_SELECTED) {
                        case ADD_PHOTO:
                            photoData.add(thumbnail);
                            break;
                        case UPDATE_PHOTO:
                            photoData.set(SELECTED_PHOTO_INDEX, thumbnail);
                            break;
                    }
                    photoAdaper.notifyDataSetChanged();
                    break;

                case REQUEST_GALLERY:
                    if (data != null) {
                        try {
                            Bitmap bm = MediaStore.Images.Media.getBitmap(context.getApplicationContext().getContentResolver(), data.getData());
                            int nh = (int) (bm.getHeight() * (512.0 / bm.getWidth()));
                            Bitmap scaled = Bitmap.createScaledBitmap(bm, 512, nh, true);
                            switch (ACTION_SELECTED) {
                                case ADD_PHOTO:
                                    photoData.add(scaled);
                                    break;
                                case UPDATE_PHOTO:
                                    photoData.set(SELECTED_PHOTO_INDEX, scaled);
                                    break;
                            }
                            photoAdaper.notifyDataSetChanged();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        Intent inte = new Intent(context, FetchContentActivity.class);
        if (v.getId() == cateTV.getId()) {
            inte.putExtra(Constants.FETCH_CONTENT_REQ, Constants.FETCH_CATEGORY);
            startActivityForResult(inte, Integer.parseInt(Constants.FETCH_CATEGORY));
        } else if (v.getId() == locationTV.getId()) {
            inte.putExtra(Constants.FETCH_CONTENT_REQ, Constants.FETCH_LOCATION);
            startActivityForResult(inte, Integer.parseInt(Constants.FETCH_LOCATION));
        } else if (v.getId() == updateBtn.getId()) {
            String titleTxt = titleET.getText().toString().trim();
            if (titleTxt.isEmpty()) {
                titleLayout.setError(getString(R.string.mandate));
                requestFocus(titleET);
                return;
            } else {
                titleLayout.setErrorEnabled(false);
                titleLayout.setError(null);
            }

            if (cateTV.getText().equals(getResources().getString(R.string.add_ad_cate))) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.error_alert_title)
                        .setMessage(R.string.error_cate)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return;
            }

            if (locationTV.getText().equals(getResources().getString(R.string.add_ad_location))) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.error_alert_title)
                        .setMessage(R.string.error_loca)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return;
            }


            String descTxt = descET.getText().toString().trim();

            if (descTxt.isEmpty()) {
                descLayout.setError(getString(R.string.mandate));
                requestFocus(descET);
                return;
            } else if (!(Util.validDesc(descTxt))) {
                descLayout.setErrorEnabled(true);
                descLayout.setError(getString(R.string.desc_not_valid));
                requestFocus(descET);
                return;
            }  else {
                descLayout.setErrorEnabled(false);
                descLayout.setError(null);
            }

            String priceTxt = priceET.getText().toString().trim();

            if (priceTxt.isEmpty()) {
                priceLayout.setError(getString(R.string.mandate));
                requestFocus(priceET);
                return;
            } else {
                priceLayout.setErrorEnabled(false);
                priceLayout.setError(null);
            }

            String priceMode = (String) priceModeSpinner.getSelectedItem();

            if (priceMode.equals(getResources().getStringArray(R.array.price_mode)[0])) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.error_alert_title)
                        .setMessage(R.string.error_price_mode)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return;
            } else {
                priceModeSpinner.requestFocus();
            }
            String phoneTxt = phoneET.getText().toString().trim();
            if (phoneTxt.isEmpty()) {
                phoneLayout.setError(getString(R.string.mandate));
                requestFocus(phoneET);
                return;
            } else if (!(Util.validPhone(phoneTxt))) {
                phoneLayout.setErrorEnabled(true);
                phoneLayout.setError(getString(R.string.phone_not_valid));
                requestFocus(phoneET);
                return;
            } else {
                phoneLayout.setErrorEnabled(false);
                phoneLayout.setError(null);
            }
            String addressTxt = addressET.getText().toString().trim();

            if (addressTxt.isEmpty()) {
                addressLayout.setError(getString(R.string.mandate));
                requestFocus(addressET);
                return;
            } else {
                addressLayout.setErrorEnabled(false);
                addressLayout.setError(null);
            }

            //Add ad api
            progressDialog = new CustomProgressDialog(context);
            progressDialog.show();
            updateBtn.setEnabled(false);
            Util.hideSoftKeyboard((Activity)context);

            PublishListingService publishListingService = PublishListingService.getInstance();
            HashMap<String, String> data = new HashMap<>();
            data.put("description", descTxt);
            data.put("phone", "");
            data.put("title", titleTxt);
            data.put("price", priceTxt);
            data.put("address", "");
            data.put("id_category", selectedCateId);
            data.put("apikey", Constants.API_KEY);
            data.put("id_user", SharedPrefWrapper.getInstance().getUserId());
            data.put("id_location", selectedLocationId);
            data.put("user_token", SharedPrefWrapper.getInstance().getUserToken());
            JSONObject postParam = new JSONObject(data);
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, data.toString());
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, data.toString());
            publishListingService.publishAd(context, new PublishHandler(this), postParam, TAG);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static class PublishHandler extends WeakReferenceHandler<UpdateDetailFragment> {

        public PublishHandler(UpdateDetailFragment reference) {
            super(reference);
        }

        @Override
        public void handleMessage(UpdateDetailFragment reference, Message msg) {

            if (reference.progressDialog != null) {
                reference.progressDialog.dismiss();
            }
            reference.updateBtn.setEnabled(true);

            if (msg.arg2 == Constants.SUCCESS) {
                Logger.log(Logger.LOG_TYPE.DEBUG, reference.TAG, "ad publish response " + msg.obj);
                Logger.log(Logger.LOG_TYPE.DEBUG, reference.TAG, "ad id " + msg.arg1);

                if(reference.photoData.size() == 1)
                    return;

                reference.updateBtn.setEnabled(false);
                HashMap<String, String> data = new HashMap<>();
                Bitmap bm = reference.photoData.get(1);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                data.put("image", encodedImage);

                data.put("apikey", Constants.API_KEY);
                data.put("id_user", SharedPrefWrapper.getInstance().getUserId());
                data.put("id", "" + msg.arg1);
                data.put("user_token", SharedPrefWrapper.getInstance().getUserToken());
                JSONObject postParam = new JSONObject(data);
                PublishListingService publishListingService = PublishListingService.getInstance();
                publishListingService.publishAdPhoto(reference.context, new PublishPhotoHandler(reference), postParam, "" + msg.arg1, reference.TAG);
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(reference.context,
                        null,
                        reference.context.getResources().getString(R.string.error_alert_title),
                        null,
                        R.string.button_ok,
                        0,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                        ,
                        null,
                        null);

                NetworkErrorManager.showErrors(reference.context, msg.arg2, alert);
            }
        }

        private static class PublishPhotoHandler extends WeakReferenceHandler<UpdateDetailFragment> {

            public static int index = 1;
            public PublishPhotoHandler(UpdateDetailFragment reference) {
                super(reference);
            }

            @Override
            public void handleMessage(UpdateDetailFragment reference, Message msg) {

                if (reference.progressDialog != null) {
                    reference.progressDialog.dismiss();
                }
                reference.updateBtn.setEnabled(true);

                if (msg.arg2 == Constants.SUCCESS) {
                    Logger.log(Logger.LOG_TYPE.DEBUG, reference.TAG, "image uploaded successfully");
                    index++;
                    try {
                        Bitmap bm = reference.photoData.get(index);
                        HashMap<String, String> data = new HashMap<>();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] imageBytes = baos.toByteArray();
                        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                        data.put("image", encodedImage);
                        data.put("apikey", Constants.API_KEY);
                        data.put("id_user", SharedPrefWrapper.getInstance().getUserId());
                        data.put("id", "" + msg.arg1);
                        data.put("user_token", SharedPrefWrapper.getInstance().getUserToken());
                        JSONObject postParam = new JSONObject(data);
                        PublishListingService publishListingService = PublishListingService.getInstance();
                        publishListingService.publishAdPhoto(reference.context, new PublishPhotoHandler(reference), postParam, "" + msg.arg1, reference.TAG);
                    } catch(Exception e) {
                    }

                } else {
                    android.app.AlertDialog.Builder alert = Util.createAlert(reference.context,
                            null,
                            reference.context.getResources().getString(R.string.error_alert_title),
                            null,
                            R.string.button_ok,
                            0,
                            0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }
                            ,
                            null,
                            null);

                    NetworkErrorManager.showErrors(reference.context, msg.arg2, alert);
                }
            }
        }
    }
}
