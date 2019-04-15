package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public class APODCarousel extends PagerAdapter implements APODAdapter {


    private Context context;
    private ViewHolder holder;
    private TextView apodTitle;
    private List<APOD> apodList;

    public APODCarousel(Context context, List<APOD> apodList) {
        this.context = context;
        this.apodList = apodList;
    }


    @Override
    public int getCount() {
        return apodList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object.equals(view);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = container.getChildAt(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.view_apod_apdater_item, null);

            holder = new ViewHolder();

            holder.apodTitle = view.findViewById(R.id.apod_adapter_apod_title);
            holder.apodTitle.setText(apodList.get(position).getTitle());

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        return view;
    }

    private class ViewHolder {
        private TextView apodTitle;
    }
}
