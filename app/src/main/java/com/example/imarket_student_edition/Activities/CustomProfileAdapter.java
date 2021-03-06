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

public class CustomProfileAdapter extends RecyclerView.Adapter<CustomProfileAdapter.MyViewHolder> {
    Context context;
    private List<ProductModel> productModelList;

    Activity activity;
    // gets the context from the parent activity and the productModel list
    CustomProfileAdapter(Activity activity, Context context, List<ProductModel> productModelList){
        this.context = context;
        this.activity = activity;
        this.productModelList = productModelList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //System.out.println("Called onCreateViewHolder");
        // set an inflater to set up the resource file called "profile_recycle_view"
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.profile_recycle_view, parent,false);
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
            productPrice = itemView.findViewById(R.id.ProductPrice_rec);
            productName = itemView.findViewById(R.id.ProductName_rec);
            productImage = itemView.findViewById(R.id.product_image_rec);
            CardView = itemView.findViewById(R.id.ProductRecPage);
        }

        void setProduct(ProductModel productmodel){
         //   System.out.println("Set product method called with product:"+ productmodel.toString());
            productName.setText(productmodel.getName());
            if(productmodel.getName().trim().isEmpty()){
                productName.setText("No text found");
            }
            productPrice.setText("$ "+productmodel.getPrice());
            if(productmodel.getPrice().trim().isEmpty()){
                productName.setText("No price found");
            }
            if (!(productmodel.getImg_video_url()).equals("No Image")){
                productImage.setImageBitmap(BitmapFactory.decodeFile(String.valueOf(productmodel.getImg_video_url())));
                productImage.setVisibility(View.VISIBLE);
            }else {
                productImage.setVisibility(View.GONE);
            }
            CardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UpdateProductActivity.class);
                    intent.putExtra("name", productmodel.getName());
                    intent.putExtra("price",productmodel.getPrice() );
                    intent.putExtra("condition",productmodel.getDescription());
                    intent.putExtra("image", productmodel.getImg_video_url());
                    intent.putExtra("uid", String.valueOf(productmodel.getId()));
                    intent.putExtra("ulocation",productmodel.getLocation());
                    intent.putExtra("updatenumber", productmodel.getPhone_number());
                    context.startActivity(intent);
                    activity.startActivityForResult(intent, 1);
                }
            });
        }
    }
}


