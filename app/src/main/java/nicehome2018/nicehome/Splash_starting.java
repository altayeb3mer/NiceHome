package nicehome2018.nicehome;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

public class Splash_starting extends AwesomeSplash {

 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_starting);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
*/
    @Override
    public void initSplash(ConfigSplash configSplash) {
        //Get full screen
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Background color
        configSplash.setBackgroundColor(R.color.splash_background);
        configSplash.setAnimCircularRevealDuration(700);
        configSplash.setRevealFlagX(Flags.REVEAL_LEFT);
        configSplash.setRevealFlagX(Flags.REVEAL_BOTTOM);

        //Logo
        configSplash.setLogoSplash(R.drawable.splashlogo);
        configSplash.setAnimLogoSplashDuration(4000);
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);

        //Title
        configSplash.setTitleSplash(null);
       // configSplash.setTitleSplash("نايس هوم العقارية");
       // configSplash.setTitleTextColor(R.color.white);


    }

    @Override
    public void animationsFinished() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }


}
