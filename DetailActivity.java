package com.akgi.momskitchen;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.akgi.momskitchen.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final DbHelper helper = new DbHelper(this);

    if(getIntent().getIntExtra("type",0) == 1) {
        final int image = getIntent().getIntExtra("image", 0);
        final int price = Integer.parseInt(getIntent().getStringExtra("price"));
        final String name = getIntent().getStringExtra("name");
        final String description = getIntent().getStringExtra("description");

        binding.detailImage.setImageResource(image);
        binding.priceLbl.setText(String.format("%d", price));
        binding.foodName.setText(name);
        binding.detailDescription.setText(description);



        binding.insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = helper.insertOrder(
                        binding.nameBox.getText().toString(),
                        binding.numberBox.getText().toString(),
                        price,
                        image,
                        name,
                        description,
                        Integer.parseInt(binding.quantity.getText().toString())
                );
                if (isInserted) {
                    Toast.makeText(DetailActivity.this, "Data Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    else {
        int id = getIntent().getIntExtra("id",0);
        Cursor cursor = helper.getOrdersById(id);
        final int image = cursor.getInt(4);
        binding.detailImage.setImageResource(image);
        binding.priceLbl.setText(String.format("%d", cursor.getInt(3)));
        binding.foodName.setText(cursor.getString(6));
        binding.detailDescription.setText(cursor.getString(5));

        binding.nameBox.setText(cursor.getString(1));
        binding.numberBox.setText(cursor.getString(2));
        binding.insertBtn.setText("Update Order");
        binding.insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUpdated = helper.updateOrder(
                        binding.nameBox.getText().toString(),
                        binding.numberBox.getText().toString(),
                        Integer.parseInt(binding.priceLbl.getText().toString()),
                        image,
                        binding.foodName.getText().toString(),
                        binding.detailDescription.getText().toString(),
                        1,
                        id
                );
                if (isUpdated){
                    Toast.makeText(DetailActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DetailActivity.this, "Failed to update", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    }
}