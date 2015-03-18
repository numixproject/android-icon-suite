package com.numix.icons_circle.activity;

        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Toast;

        import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;
        import it.neokree.materialnavigationdrawer.elements.MaterialSection;
        import it.neokree.materialnavigationdrawer.elements.listeners.MaterialSectionListener;

        import com.numix.icons_circle.R;
        import com.numix.icons_circle.fragment.IconFragmentAll;

public class MainActivity extends MaterialNavigationDrawer {

    View v;
    private MainFragment fragment = null;

    @Override
    public void init(Bundle savedInstanceState) {
        MaterialSection home = newSection("Numix Circle", new MainFragment());

        // Set Drawer Header Image
        setDrawerHeaderImage(R.drawable.background);

        // Define new sections
        this.addSection(home);

        this.addSection(this.newSection("Icons", new IconFragmentAll()));


        this.addSection(newSection("About us",
                // <!-- R.drawable.ic_ic_swap_horiz_24px -->,
                new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection section) {
                        requestIcons(v);
                    }
                }));

        this.addSection(newSection("Contact us",
                // <!-- R.drawable.ic_ic_swap_horiz_24px -->,
                new MaterialSectionListener() {
                    @Override
                    public void onClick(MaterialSection section) {
                        contactUsDialog();
                    }
                }));

        fragment = (MainFragment)home.getTargetFragment();
    }

    // Methods to do actions

    public void showIcons() {
        Intent intent = new Intent(this, AllIcons.class);
        startActivity(intent);
    }

    public void contactUsDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Please read");
        alertDialog.setMessage("Do not send icon request via mail. Use the built in tool instead.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        contactUs();
                    }
                });
        alertDialog.show();

    }

    public void contactUs() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"team@numixproject.org"});
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void applyTheme(View v) {
        fragment.applyTheme(v);
    }

    public void showBackground(View v) {
        fragment.showBackground(v);
    }

    public void requestIcons(View v) {
        fragment.requestIcons(v);
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

    public void googleplay(View v) {
        String url = "https://play.google.com/store/apps/developer?id=Numix";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}