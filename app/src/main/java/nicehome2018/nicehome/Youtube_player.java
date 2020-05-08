package nicehome2018.nicehome;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtube_player extends YouTubeBaseActivity {
    private  final String API_KEY="AIzaSyBeNneOvzwwEZrB6u7fzg_jSrXktLQ4E8I";
    String VIDEO_CODE;

    Toolbar toolbar;

    YouTubePlayerView youTubePlayerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_player);

        Bundle bundle = getIntent().getExtras();
        VIDEO_CODE = bundle.getString("VIDEO_CODE");
        VIDEO_CODE=VIDEO_CODE.replace("https://www.youtube.com/watch?v=", "");

        toolbar = (Toolbar) findViewById(R.id.pro_details_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(VIDEO_CODE);
                youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(Youtube_player.this, getResources().getString(R.string.youtube_player_error), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
