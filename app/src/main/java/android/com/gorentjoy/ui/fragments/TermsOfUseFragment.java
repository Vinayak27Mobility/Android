package android.com.gorentjoy.ui.fragments;

import android.com.gorentjoy.R;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class TermsOfUseFragment extends Fragment {
    private Context context;
    private View rootView;
    private CustomProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        ((HomeActivity)context).setActionBarTitle(context.getResources().getString(R.string.menu_tou));
        rootView = inflater.inflate(R.layout.fragment_tou, container, false);

        progressBar = new CustomProgressDialog(context);

        WebView webView = (WebView) rootView.findViewById(R.id.touweb);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Constants.TOU_URL);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Show progressbar
                progressBar.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Stop spinner or progressBar
                progressBar.dismiss();

            }
        });



        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title

        }
    }
}
