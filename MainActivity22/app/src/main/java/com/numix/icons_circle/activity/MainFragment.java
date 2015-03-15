package com.numix.icons_circle.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.numix.icons_circle.R;
public class MainFragment extends Fragment {

    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity faActivity  = (FragmentActivity)    super.getActivity();
        // Replace LinearLayout by the type of the root element of the layout you're trying to load
        LinearLayout llLayout    = (LinearLayout)    inflater.inflate(R.layout.main, container, false);
        llLayout.findViewById(R.id.mainLayout);

        return llLayout; // We must return the loaded Layout
    }


    // Methods to do actions

    public void applyTheme(View v) {
        Intent launcher = new Intent(super.getActivity(), ApplyLauncherMain.class);
        startActivity(launcher);
    }

    public void showBackground() {
        Intent wall = new Intent(super.getActivity(), Wallpaper.class);
        startActivity(wall);
    }

    public void requestIcons() {
        boolean installed = appInstalledOrNot("org.numixproject.iconsubmit");

        if (installed) {
            //This intent will help you to launch if the package is already installed
            Intent LaunchIntent = super.getActivity().getPackageManager()
                    .getLaunchIntentForPackage("org.numixproject.iconsubmit");
            startActivity(LaunchIntent);

        } else {
            new AlertDialog.Builder(super.getActivity())
                    .setTitle("Request new Icon")
                    .setMessage("You will now download an app from Google Play to send us non themed apps.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "https://play.google.com/store/apps/details?id=org.numixproject.iconsubmit";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    })
                    .show();
        }
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = super.getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    public void googleplay() {
        String url = "https://play.google.com/store/apps/developer?id=Numix";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}