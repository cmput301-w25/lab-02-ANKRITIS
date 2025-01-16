package com.example.listcity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayList<String> dataList;
    ArrayAdapter<String> cityAdapter;
    int selectedCityPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney",  "Berlin", "Vienna", "Tokyo" , "Beijing", "Osaka", "New Delhi" };
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));
        dataList.add("Mumbai");


        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        // Set item click listener for selecting a city
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCityPosition = position;
            }
        });
    }

    public void addCity(View view) {
        final EditText input;
        input = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add City")
                .setView(input)
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newCity = input.getText().toString().trim();
                        if (!newCity.isEmpty()) {
                            addNewCity(newCity);
                        } else {
                            Toast.makeText(MainActivity.this, "City name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    private void addNewCity(String newCity) {
        dataList.add(newCity);
        cityAdapter.notifyDataSetChanged();
        Toast.makeText(this, "City added", Toast.LENGTH_SHORT).show();
    }

    // Method to delete the selected city from the list
    public void deleteSelectedCity(View view) {
        if (selectedCityPosition != -1) {
            showDeleteDialog(selectedCityPosition);
        } else {
            Toast.makeText(this, "Select a city to delete", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to show dialog for deleting a city
    private void showDeleteDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete City")
                .setMessage("Do you want to delete this city?")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCity(position);
                        selectedCityPosition = -1; // Reset selected city position
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();
    }

    // Method to delete a city from the list
    private void deleteCity(int position) {
        dataList.remove(position);
        cityAdapter.notifyDataSetChanged();
        Toast.makeText(this, "City deleted", Toast.LENGTH_SHORT).show();
    }
}

