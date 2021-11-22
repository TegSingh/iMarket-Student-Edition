package com.example.imarket_student_edition.Activities;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.imarket_student_edition.Models.ProductModel;
import com.example.imarket_student_edition.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    private List<ProductModel> productModelList;
    private List<ProductModel> productModeSource;

    CustomAdapter(Context context, List<ProductModel> productModelList){
       this.context = context;
       this.productModelList = productModelList;
       productModeSource = productModelList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("Called oncreateviewholder");
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.product_recycle_view, parent,false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.setProduct(productModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productPrice = itemView.findViewById(R.id.ProductPrice_rec);
            productName = itemView.findViewById(R.id.ProductName_rec);
        }

        void setProduct(ProductModel productmodel){

            System.out.println("Set product method called with product:"+ productmodel.toString());
            productName.setText(productmodel.getName());
            if(productmodel.getName().trim().isEmpty()){
                productName.setText("No text found");
            }

            productPrice.setText(productmodel.getPrice());
            if(productmodel.getPrice().trim().isEmpty()){
                productName.setText("No price found");
            }

        }

    }


}


