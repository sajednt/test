package com.sajed.sajednt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class adapterItem extends RecyclerView.Adapter<adapterItem.ContactViewHolder> {

	private List<userItem> item;
	 Context context;
	Activity activity;


	public adapterItem(List<userItem> contents , Context con , Activity act ) {
        this.item = contents;
        this.context = con;
        this.activity = act;
    }

    @Override
    public int getItemViewType(int position) {
    	
		return position;

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_item, parent, false);
                return new adapterItem.ContactViewHolder(view) {
                };
    }
    
    public userItem getItem(int position) {
        return item.get(position);
    }

    public  class ContactViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        protected TextView id;
        protected TextView firstName;
        protected TextView lastName;
        protected LinearLayout linear;
        protected ImageView avatar;

        public ContactViewHolder(View v) {
             super(v);

             id =  (TextView) v.findViewById(R.id.item_id);
             firstName =  (TextView) v.findViewById(R.id.item_first_name);
             lastName =  (TextView) v.findViewById(R.id.item_last_name);

             avatar = (ImageView) v.findViewById(R.id.item_logo);
             linear = (LinearLayout) v.findViewById(R.id.item_linear);
             
             linear.setClickable(true);
             linear.setOnClickListener(this);

        }
        
        

		@Override
		public void onClick(View v) {
            userItem iw = item.get(getPosition());
            Intent i = new Intent(context , singleUser.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            i.putExtra(constant.id , iw.id);
            i.putExtra(constant.last_name , iw.lastName);
            i.putExtra(constant.first_name , iw.firstName);
            i.putExtra(constant.email , iw.email);
            i.putExtra(constant.avatar , iw.image);
            context.startActivity(i);
		}
    }
    
	@Override
	public void onBindViewHolder(final ContactViewHolder arg0, int arg1) {
		// TODO Auto-generated method stub
    	final userItem ie = item.get(arg1);

    	arg0.id.setText(ie.id);
    	arg0.firstName.setText(ie.firstName);
        arg0.lastName.setText(ie.lastName);

        arg0.avatar.setVisibility(View.VISIBLE);

			ImageLoader imageLoader = AppController.getInstance().getImageLoader();
			
			    imageLoader.get(ie.image, new ImageListener() {
					
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onResponse(ImageContainer response, boolean arg1) {
						// TODO Auto-generated method stub
						arg0.avatar.setImageBitmap(response.getBitmap());
					}
				});
    		
    	}

	public void updateList(List<userItem> data) {
		item = data;
        notifyDataSetChanged();
    }
    

}


