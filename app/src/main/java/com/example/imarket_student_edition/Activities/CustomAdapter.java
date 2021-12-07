package com.example.imarket_student_edition.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    private List<ProductModel> productModelList;
    Activity activity;
    // gets the context from the parent activity and the productModel list
    CustomAdapter(Activity activity,Context context, List<ProductModel> productModelList){
       this.context = context;
       this.activity = activity;
       this.productModelList = productModelList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //System.out.println("Called onCreateViewHolder");
        // set an inflater to set up the resource file called "product_recycle_view"
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.product_recycle_view, parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // bind the productModel List with the vew holder
            holder.setProduct(productModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;
        CardView CardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // get all the view holder form the resource file
            productPrice = itemView.findViewById(R.id.ProductPrice_rec);
            productName = itemView.findViewById(R.id.ProductName_rec);
            productImage = itemView.findViewById(R.id.product_image_rec);
            CardView = itemView.findViewById(R.id.ProductRecPage);
        }

        void setProduct(ProductModel productmodel){

           // System.out.println("Set product method called with product:"+ productmodel.toString());
            // check if the information in the product list is empty
            productName.setText(productmodel.getName());
            if(productmodel.getName().trim().isEmpty()){
                productName.setText("No Name found for the product");
            }
            productPrice.setText("$ "+productmodel.getPrice());
            if(productmodel.getPrice().trim().isEmpty()){
                productName.setText("No price found");
            }
            // if there is no path then make the image view as gone
            if (!(productmodel.getImg_video_url()).equals("No Image")){
                productImage.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(productmodel.getImg_video_url())));
                productImage.setVisibility(View.VISIBLE);
            }else {
                productImage.setVisibility(View.GONE);
            }
            // on click for the card view
            CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // go the product activity page to display the product in detail
                    Intent intent = new Intent(context, ProductActivity.class);
                    // send the information through intent
                    intent.putExtra("name", productmodel.getName());
                    intent.putExtra("price",productmodel.getPrice() );
                    intent.putExtra("condition",productmodel.getDescription());
                    intent.putExtra("image", productmodel.getImg_video_url());
                    intent.putExtra("plocation",productmodel.getLocation());
                    intent.putExtra("uid", String.valueOf(productmodel.getUser_id()));
                    intent.putExtra("unumber", productmodel.getPhone_number());
                    activity.startActivityForResult(intent, 1);
                }
            });
        }
    }
}


