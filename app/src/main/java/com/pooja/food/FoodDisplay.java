package com.pooja.food;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FoodDisplay extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;

    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display);
        mAuth=FirebaseAuth.getInstance();
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadallFood();

    }
    private FirebaseRecyclerAdapter<foodmodel, FoodDisplay.MyFoodViewHolder> mAdapter;
    private void loadallFood() {
        String category="frankie";
        mRef=FirebaseDatabase.getInstance().getReference().child("food");
        Query query=mRef.orderByChild("category").equalTo(category);
        mAdapter=new FirebaseRecyclerAdapter<foodmodel,MyFoodViewHolder>
                (foodmodel.class,R.layout.item_food,FoodDisplay.MyFoodViewHolder.class,query){

            @Override
            protected void populateViewHolder(final FoodDisplay.MyFoodViewHolder viewHolder, final foodmodel model, int position) {
                    final String foodkey=getRef(position).getKey();
                    mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            viewHolder.foodname.setText(model.getFoodName());
                            Log.d("Food ","name="+model.getFoodName());
                            viewHolder.foodprice.setText(model.getFoodPrice());
                            Log.d("Food ","price="+model.getFoodPrice());
                            viewHolder.fooddescription.setText(model.getFoodDescription());
                            Log.d("Food ","desc="+model.getFoodDescription());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
            }
        };

    recyclerView.setAdapter(mAdapter);
    }


    public static class MyFoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodname,foodprice,fooddescription;
        public MyFoodViewHolder(View itemView){
            super(itemView);
            foodname=itemView.findViewById(R.id.food_name);
            foodprice=itemView.findViewById(R.id.food_price);
            fooddescription=itemView.findViewById(R.id.food_desc);

        }
    }
}
