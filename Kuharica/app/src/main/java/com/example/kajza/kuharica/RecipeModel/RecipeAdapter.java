package com.example.kajza.kuharica.RecipeModel;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kajza.kuharica.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.width;
import static com.example.kajza.kuharica.R.id.my_image1;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    ArrayList<Recipe> recipeList;
    LayoutInflater li;
    int Resource;
    ViewHolder holder;
    public Uri uri;







    public RecipeAdapter(Context context, int resource, ArrayList<Recipe> list) {
        super(context, resource, list);
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        recipeList = list;
    }




    @Override
    public View getView (int position, View conveertView, ViewGroup parent){

        //convert view = dizajn
        View v = conveertView;
        if (v == null){
            holder = new ViewHolder();
            v= li.inflate(Resource,null);
            holder.tvname = (TextView) v.findViewById(R.id.tvname);
            holder.description = (TextView) v.findViewById(R.id.descriptionTxt);
            holder.draweeView = (SimpleDraweeView) v.findViewById(my_image1);
            v.setTag(holder);

        } else {
            holder = (ViewHolder) v.getTag();
        }

        uri = Uri.parse(recipeList.get(position).getImage());
        holder.draweeView.setImageURI(uri);
        holder.tvname.setText(recipeList.get(position).getName());
        holder.description.setText(recipeList.get(position).getDescription());
        return v;


    }

    static class ViewHolder {

        public SimpleDraweeView draweeView;
        public TextView tvname;
        public TextView description;


    }
}
