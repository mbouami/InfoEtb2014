package com.bouami.infoetb2014.app;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.protocol.HTTP;

import java.util.List;

/**
 * Created by Mohammed on 08/03/14.
 */
public class ItemDetailFragment extends Fragment {
    public static final String ARG_ETAB_ID = "id_etab";

    /**
     * The dummy content this fragment is presenting.
     */
    private Etab etab_en_cours;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ETAB_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            etab_en_cours = ItemListFragment.etabs.get(ItemListFragment.indiceetab);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        if (etab_en_cours != null) {
            if (rootView != null)
                ((TextView) rootView.findViewById(R.id.etab_type_nom)).setText(etab_en_cours.getType() + " " + etab_en_cours.getNom());
            if (rootView != null)
                ((TextView) rootView.findViewById(R.id.etab_adresse)).setText("Adresse : " + etab_en_cours.getAdresse());
            if (rootView != null) {
                ((TextView) rootView.findViewById(R.id.etab_tel)).setText("Tél : " + etab_en_cours.getTel());
            }
            if (rootView != null)
                ((TextView) rootView.findViewById(R.id.etab_fax)).setText("Fax : " + etab_en_cours.getFax());
            if (rootView != null) {
                ((TextView) rootView.findViewById(R.id.etab_mail)).setText("Mel : " + etab_en_cours.getEmail());
            }
            if (rootView != null) {
                if (etab_en_cours.getAnimateurs().size()>0) {
                    ((TextView) rootView.findViewById(R.id.etab_animateur)).setText("Animateur : " + etab_en_cours.getAnimateurs().get(0).getNom());
                }
            }
            LinearLayout zone_infos_etab = (LinearLayout) rootView.findViewById(R.id.layout_infos_etab);
            if (etab_en_cours.getPersonnels()!=null) {
                LinearLayout zone_personnel = new LinearLayout(getActivity());
                zone_personnel.setOrientation(LinearLayout.VERTICAL);
                for (int i=0;i<etab_en_cours.getPersonnels().size();i++) {
                    zone_personnel.addView(AffichetText(etab_en_cours.getPersonnels().get(i).getStatut(),etab_en_cours.getPersonnels().get(i).getNom()));
                }
                zone_infos_etab.addView(zone_personnel);
            }

            rootView.findViewById(R.id.image_map).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AfficherPlan(etab_en_cours.getMapAdresse());
                }
            });
            rootView.findViewById(R.id.image_tel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Appeler(etab_en_cours.getTel());
                }
            });
            rootView.findViewById(R.id.image_mail).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    AnvoyerEmail(etab_en_cours.getEmail());
                }
            });
            if (etab_en_cours.getAnimateurs().size()>0) {
                rootView.findViewById(R.id.zone_animateur).setVisibility(1);
                rootView.findViewById(R.id.image_animateur_mail).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        AnvoyerEmail(etab_en_cours.getAnimateurs().get(0).getEmail());
                    }
                });
                rootView.findViewById(R.id.image_animateur_tel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        Appeler(etab_en_cours.getAnimateurs().get(0).getTel());
                    }
                });
            }
        }

        return rootView;
    }

    public TextView AffichetText(String label,String message) {
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                4, getActivity().getResources().getDisplayMetrics());
        TextView champ = new TextView(getActivity());
        champ.setPadding(padding, padding, padding, padding);
        champ.setTextAppearance(getActivity(), android.R.attr.textAppearanceLarge);
        champ.setText(label+" : "+message);
/*        champ.setTextSize(16);*/
        return champ;
    }
    public void ModifierText(String item, int tv) {
        TextView view = (TextView) getView().findViewById(tv);
        view.setText(item);
    }

    public  void AfficherPlan(String adresse) {
        Uri location = Uri.parse("geo:0,0?q=+"+adresse);
        Intent localisation = new Intent(Intent.ACTION_VIEW, location);
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = null;
        if (packageManager != null) {
            activities = packageManager.queryIntentActivities(localisation, 0);
        }
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            startActivity(localisation);
        }
//		startActivity(localisation);

    }

    public  void Appeler(String tel) {
        Uri number = Uri.parse("tel:" + tel);
//        Intent callIntent = new Intent(Intent.ACTION_CALL, number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = null;
        if (null != packageManager) {
            activities = packageManager.queryIntentActivities(callIntent, 0);
        }
        boolean isIntentSafe = false;
        if (activities != null) {
            isIntentSafe = activities.size() > 0;
        }
        if (isIntentSafe) {
            startActivity(callIntent);
        }
//        startActivity(callIntent);
    }

    public  void AnvoyerEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {email}); // recipients
//    	emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
//    	emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
//    	emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = null;
        if (null != packageManager) {
            activities = packageManager.queryIntentActivities(emailIntent, 0);
        }
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(emailIntent);
        }
//        startActivity(emailIntent);
    }
}
