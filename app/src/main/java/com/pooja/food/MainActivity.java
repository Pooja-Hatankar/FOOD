package com.pooja.food;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name,price,desc,category;
    Button add,display;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        desc=findViewById(R.id.desc);
        category=findViewById(R.id.category);
        add=findViewById(R.id.btn_add);
        display=findViewById(R.id.btn_display);
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseDatabase=FirebaseDatabase.getInstance();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,FoodDisplay.class);
                startActivity(intent);
            }
        });
    }
    public void addItem(){
        String foodname=name.getText().toString().trim();
        String foodprice=price.getText().toString().trim();
        String fooddescription=desc.getText().toString().trim();
        String foodcategory=category.getText().toString().trim();

        myRef=firebaseDatabase.getReference().child("food").push();
        myRef.child("name").setValue(foodname);
        myRef.child("price").setValue(foodprice);
        myRef.child("description").setValue(fooddescription);
        myRef.child("category").setValue(foodcategory);
        Toast.makeText(MainActivity.this,"Success...",Toast.LENGTH_LONG).show();
    }

}
