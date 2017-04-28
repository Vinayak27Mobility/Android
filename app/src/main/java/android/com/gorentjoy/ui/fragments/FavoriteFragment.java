package android.com.gorentjoy.ui.fragments;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
/*public class FavoriteFragment extends Fragment implements ClickListner {
    private Context context;

    private final String TAG = FavoriteFragment.class.getSimpleName();
    private View rootView;
    GridListingAdapter adapter;
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Fragment thisInstance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        thisInstance = this;
        rootView = inflater.inflate(R.layout.fragment_ads, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        ListingService listingService = ListingService.getInstance();
        progressDialog = new CustomProgressDialog(context);
        progressDialog.show();
        listingService.getFavorite(context, new AdHandler(this), null, TAG);

        return rootView;
    }

    @Override
    public void ItemClicked(int position) {
        Toast.makeText(context, "position is " + position, Toast.LENGTH_SHORT).show();
        AdResponse.Ad ad = adapter.horizontalList.get(position);
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "ad object " + ad.getTitle());
        DataHolder.getInstance().setSelectedAd(ad);

        Fragment fragTwo = new AdDetailFragment();
        String tag = AdDetailFragment.class.getSimpleName();

        ((HomeActivity) context).fragmentManager.beginTransaction().add(R.id.flContent, fragTwo, tag).addToBackStack(tag).commit();

    }


    private static class AdHandler extends WeakReferenceHandler<FavoriteFragment> {

        Context context;

        public AdHandler(FavoriteFragment reference) {
            super(reference);
        }

        @Override
        public void handleMessage(FavoriteFragment reference, Message msg) {
            context = reference.context;
            if (reference.progressDialog != null) {
                reference.progressDialog.dismiss();
            }

            if (msg.arg2 == Constants.SUCCESS) {
                ArrayList<AdResponse.Ad> dataSet = (ArrayList) msg.obj;
                if (!dataSet.isEmpty()) {
                    reference.adapter = new GridListingAdapter(reference.context, dataSet);
                    reference.recyclerView.setAdapter(reference.adapter);
                    reference.adapter.setListner(reference);
                } else {
                    reference.rootView.findViewById(R.id.empty_view);
                }
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(reference.context,
                        null,
                        context.getResources().getString(R.string.error_alert_title),
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

                NetworkErrorManager.showErrors(context, msg.arg2, alert);
            }
        }
    }
}*/
