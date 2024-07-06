package com.example.adminniospersonal;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DeletePDF extends AppCompatActivity {
    AutoCompleteTextView classInput, categoryInput, subjectInput;
    Button show;
    DeleteAdapter deleteAdapter;
    List<PdfModal> pdfList = new ArrayList<>();
    RecyclerView recyclerView ;

    String[] classArray, categoryArray, subjectArray;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_pdf);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        classInput = findViewById(R.id.class_input);
        categoryInput = findViewById(R.id.category_input);
        subjectInput = findViewById(R.id.subject_input);
        show = findViewById(R.id.show);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        deleteAdapter = new DeleteAdapter(getApplicationContext(),pdfList);

        recyclerView.setAdapter(deleteAdapter);

        classArray = getResources().getStringArray(R.array.standard);
        categoryArray = getResources().getStringArray(R.array.category);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, classArray);

        classInput.setAdapter(classAdapter);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, categoryArray);
        categoryInput.setAdapter(categoryAdapter);

        classInput.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if( position == 3 || position == 4 || position ==5){
                    findViewById(R.id.txtCategory).setVisibility(View.GONE);
                    findViewById(R.id.tilCategory).setVisibility(View.GONE);
                    findViewById(R.id.txtSubjects).setVisibility(View.GONE);
                    findViewById(R.id.tilSubjects).setVisibility(View.GONE);
                }else if (position==2){

                        findViewById(R.id.txtCategory).setVisibility(View.VISIBLE);
                        findViewById(R.id.tilCategory).setVisibility(View.VISIBLE);
                        findViewById(R.id.txtSubjects).setVisibility(View.GONE);
                        findViewById(R.id.tilSubjects).setVisibility(View.GONE);
                        String[] newVocationalArray = getResources().getStringArray(R.array.medium_categories_X);
                        ArrayAdapter<String> newcategoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, newVocationalArray);
                        categoryInput.setAdapter(newcategoryAdapter);


                    }
                    else {
                    findViewById(R.id.txtCategory).setVisibility(View.VISIBLE);
                    findViewById(R.id.tilCategory).setVisibility(View.VISIBLE);
                    findViewById(R.id.txtSubjects).setVisibility(View.VISIBLE);
                    findViewById(R.id.tilSubjects).setVisibility(View.VISIBLE);
                }
            }
        });

        categoryInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (classInput.getText().toString().equals("X")) {
                    switch (i) {
                        case 0:
                        case 7:
                            subjectArray = getResources().getStringArray(R.array.subject_english_medium_X);
                            addSubject(subjectArray);
                            break;

                        case 5:
                            subjectArray = getResources().getStringArray(R.array.worksheet_X);
                            addSubject(subjectArray);
                            break;

                        case 1:
                            subjectArray = getResources().getStringArray(R.array.subject_hindi_medium_X);
                            addSubject(subjectArray);
                            break;

                        case 2:
                            subjectArray = getResources().getStringArray(R.array.subject_english_medium_X);
                            addSubject(subjectArray);
                            break;

                        case 3:
                            subjectArray = getResources().getStringArray(R.array.guide_books_X);
                            addSubject(subjectArray);
                            break;

                        case 4:
                        case 6:
                        case 8:
                            subjectArray = getResources().getStringArray(R.array.medium_categories_XII);
                            addSubject(subjectArray);
                            break;

                        case 9:
                            findViewById(R.id.txtSubjects).setVisibility(View.GONE);
                            findViewById(R.id.tilSubjects).setVisibility(View.GONE);
                            break;
                    }

                } else if (classInput.getText().toString().equals("XII")) {
                    switch (i) {
                        case 0:
                        case 7:
                            subjectArray = getResources().getStringArray(R.array.subject_english_medium_XII);
                            addSubject(subjectArray);
                            break;

                        case 5:
                            subjectArray = getResources().getStringArray(R.array.work_sheet_XII);
                            addSubject(subjectArray);
                            break;

                        case 1:
                            subjectArray = getResources().getStringArray(R.array.subject_hindi_medium_XII);
                            addSubject(subjectArray);
                            break;

                        case 2:
                            subjectArray = getResources().getStringArray(R.array.subject_english_medium_XII);
                            addSubject(subjectArray);
                            break;

                        case 3:
                            subjectArray = getResources().getStringArray(R.array.guide_books_XII);
                            addSubject(subjectArray);
                            break;

                        case 4:
                        case 6:
                        case 8:
                            subjectArray = getResources().getStringArray(R.array.medium_categories_XII);
                            addSubject(subjectArray);
                            break;


                        case 9:
                            findViewById(R.id.txtSubjects).setVisibility(View.GONE);
                            findViewById(R.id.tilSubjects).setVisibility(View.GONE);
                            break;
                    }
                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

// Replace "your_database_reference_path" with the actual path in your Firebase Realtime Database
                DatabaseReference databaseReference1 = firebaseDatabase.getReference();
                if(classInput.getText().toString().equals("DIPLOMA_HINDI") ||classInput.getText().toString().equals("DIPLOMA_ENGLISH") ) {

                    databaseReference1.child(classInput.getText().toString()).orderByChild("category").equalTo("Not Required_Not Required")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Clear previous data
                                    pdfList.clear();

                                    // Iterate through the dataSnapshot to get PDF details
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        PdfModal pdfModal = snapshot.getValue(PdfModal.class);
                                        pdfList.add(pdfModal);
                                    }
                                    deleteAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle errors
                                    Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                else if(classInput.getText().toString().equals("VOCATIONAL")  ) {

                    databaseReference1.child(classInput.getText().toString()).orderByChild("category").equalTo(categoryInput.getText().toString())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Clear previous data
                                    pdfList.clear();

                                    // Iterate through the dataSnapshot to get PDF details
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        PdfModal pdfModal = snapshot.getValue(PdfModal.class);
                                        pdfList.add(pdfModal);
                                    }
                                    deleteAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle errors
                                    Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    databaseReference1.child(classInput.getText().toString()).orderByChild("category").equalTo(categoryInput.getText().toString()+"_"+subjectInput.getText().toString())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    // Clear previous data
                                    pdfList.clear();

                                    // Iterate through the dataSnapshot to get PDF details
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        PdfModal pdfModal = snapshot.getValue(PdfModal.class);
                                        pdfList.add(pdfModal);
                                    }
                                    deleteAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle errors
                                    Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT).show();
                                }
                            });
                }


            }
        });

    }
    private void addSubject(String[] subjectArray) {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, subjectArray);
        subjectInput.setAdapter(subjectAdapter);
    }


}