package com.numix.icons_circle.activity;

        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.support.v4.BuildConfig;
        import android.view.KeyEvent;
        import android.view.View;
        import android.webkit.JavascriptInterface;
        import android.webkit.WebSettings;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;


        import com.numix.icons_circle.R;
public class MainActivity extends Activity {

    // Start page: save your local index in /src/main/assets/www/index.html
    private final String INDEX = "file:///android_asset/www/index.html";

    private WebView webView;

    // Handle back button press
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set webview options
        setContentView(R.layout.main);

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();

        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();

        webSettings.setAllowFileAccessFromFileURLs(true); // Enable HTML Imports to access file://.
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // Interfaces
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        webView.setWebViewClient(new webViewClient());

        // Enable debugging in webview
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }

        // Start webview
        webView.loadUrl(INDEX);
    }

    private class webViewClient extends WebViewClient {
        // Show a splash screen until the WebView is ready
        @Override
        public void onPageFinished(WebView view, String url) {
            findViewById(R.id.imageView1).setVisibility(View.GONE);
            findViewById(R.id.webview).setVisibility(View.VISIBLE);
        }
    }

    public class WebAppInterface {
        /**
         * Show the wallpaper selector
         */
        @JavascriptInterface
        public void applyTheme() {
            Intent launcher = new Intent(MainActivity.this, ApplyLauncherMain.class);
            startActivity(launcher);
        }

        /**
         * Show the wallpaper selector
         */
        @JavascriptInterface
        public void showBackground() {
            Intent wall = new Intent(MainActivity.this, Wallpaper.class);
            startActivity(wall);
        }

        /**
         * Show the wallpaper selector
         */
        @JavascriptInterface
        public void requestIcons() {
            boolean installed = appInstalledOrNot("org.numixproject.iconsubmit");

            if (installed) {
                //This intent will help you to launch if the package is already installed
                Intent LaunchIntent = getPackageManager()
                        .getLaunchIntentForPackage("org.numixproject.iconsubmit");
                startActivity(LaunchIntent);

            } else {
                new AlertDialog.Builder(MainActivity.this)
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

        }
        @JavascriptInterface
        public void googleplay() {
            String url = "https://play.google.com/store/apps/developer?id=Numix";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        }
    }