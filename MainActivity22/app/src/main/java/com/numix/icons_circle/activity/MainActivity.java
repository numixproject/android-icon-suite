package com.numix.icons_circle.activity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
        import it.neokree.materialnavigationdrawer.elements.MaterialSection;
        import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

        import com.numix.icons_circle.R;

public class MainActivity extends MaterialNavigationDrawer {

    @Override
    public void init(Bundle savedInstanceState) {
        MaterialSection home = newSection("Numix Circle", new MainFragment());

        // Set Drawer Header Image
        setDrawerHeaderImage(R.drawable.background);

        // Define new sections
        this.addSection(home);
        this.addSection(newSection("Apply Theme",
                // <!-- R.drawable.ic_ic_swap_horiz_24px -->,
                new MaterialSectionListener() {
            @Override
            public void onClick(MaterialSection section) {
                applyTheme();
            }
        }));

        this.addSection(newSection("Official Background",
                // <!-- R.drawable.ic_ic_swap_horiz_24px -->,
                new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection section) {
                        showBackground();
                    }
                }));

        this.addSection(newSection("Report missing Icon",
                // <!-- R.drawable.ic_ic_swap_horiz_24px -->,
                new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection section) {
                        requestIcons();
                    }
                }));
    }

    // Methods to do actions

    public void applyTheme() {
        Intent launcher = new Intent(MainActivity.this, ApplyLauncherMain.class);
        startActivity(launcher);
    }

    public void showBackground() {
        Intent wall = new Intent(MainActivity.this, Wallpaper.class);
        startActivity(wall);
    }

    public void requestIcons() {
        boolean installed = appInstalledOrNot("org.numixproject.iconsubmit");

        if (installed) {
            //This intent will help you to launch if the package is already installed
            Intent LaunchIntent = getPackageManager()
                    .getLaunchIntentForPackage("org.numixproject.iconsubmit");
            startActivity(LaunchIntent);

        } else {
            new AlertDialog.Builder(this)
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
        PackageManager pm = getPackageManager();
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