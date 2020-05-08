package nicehome2018.nicehome;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Altayeb on 10/25/2018.
 */
public class SlideShow_adapter extends PagerAdapter {
    private Context context;
    LayoutInflater inflater;



    ArrayList<String> urls;
    int detail;


    public SlideShow_adapter(Context context, ArrayList<String> images) {
        this.context = context;

        this.urls=images;
        //this.detail=det;
      }





    @Override
    public int getCount() {

            return urls.size();
        }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.slide_show_layout,container,false);
        ImageView imgView=(ImageView)view.findViewById(R.id.slide_show_img_view);

        if(!urls.isEmpty()){
            Glide.with(context).load(URL.IMAGE_PATH+urls.get(position))
                    .into(imgView);
            container.addView(view);
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
