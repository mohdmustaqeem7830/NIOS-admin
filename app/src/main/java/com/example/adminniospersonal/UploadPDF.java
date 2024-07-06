package com.example.adminniospersonal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;

import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

public class UploadPDF extends AppCompatActivity {
    AutoCompleteTextView classInput, categoryInput, subjectInput;
    TextInputEditText name;
    private int currentPdfIndex = 0;
    String[] classArray, categoryArray, subjectArray;

    StorageReference storageReference;
    DatabaseReference databaseReference1, databaseReference2, databaseReference3, databaseReference4, databaseReference5,databaseReference6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.black));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        classInput = findViewById(R.id.class_input);
        categoryInput = findViewById(R.id.category_input);
        subjectInput = findViewById(R.id.subject_input);


        classArray = getResources().getStringArray(R.array.standard);
        categoryArray = getResources().getStringArray(R.array.category);

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, classArray);

        classInput.setAdapter(classAdapter);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, categoryArray);
        categoryInput.setAdapter(categoryAdapter);

        classInput.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 3 || position == 4 || position ==5){
                    findViewById(R.id.txtCategory).setVisibility(View.GONE);
                    findViewById(R.id.tilCategory).setVisibility(View.GONE);
                    findViewById(R.id.txtSubjects).setVisibility(View.GONE);
                    findViewById(R.id.tilSubjects).setVisibility(View.GONE);
                } else if (position==2){
                    {
                        findViewById(R.id.txtCategory).setVisibility(View.VISIBLE);
                        findViewById(R.id.tilCategory).setVisibility(View.VISIBLE);
                        findViewById(R.id.txtSubjects).setVisibility(View.GONE);
                        findViewById(R.id.tilSubjects).setVisibility(View.GONE);
                       String[] newVocationalArray = getResources().getStringArray(R.array.medium_categories_X);
                        ArrayAdapter<String> newcategoryAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, newVocationalArray);
                        categoryInput.setAdapter(newcategoryAdapter);


                    }
                } else {
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
                            subjectArray = getResources().getStringArray(R.array.medium_categories_X);
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
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference1 = FirebaseDatabase.getInstance().getReference("X");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("XII");
        databaseReference3 = FirebaseDatabase.getInstance().getReference("VOCATIONAL");
        databaseReference4 = FirebaseDatabase.getInstance().getReference("DIPLOMA_ENGLISH");
        databaseReference5 = FirebaseDatabase.getInstance().getReference("DIPLOMA_HINDI");
        databaseReference6 = FirebaseDatabase.getInstance().getReference("NOTIFICATION");
    }
    private void addSubject(String[] subjectArray) {
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, subjectArray);
        subjectInput.setAdapter(subjectAdapter);
    }
    public void UploadPdf(View view) {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("application/pdf");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); // Allow multiple file selection
                        startActivityForResult(Intent.createChooser(intent, "Select PDF files..."), 1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(UploadPDF.this, "permission required ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<Uri> selectedPdfList = new ArrayList<>();

            if (data.getData() != null) {
                // Single file selected
                selectedPdfList.add(data.getData());
            } else if (data.getClipData() != null) {
                // Multiple files selected
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Uri pdfUri = clipData.getItemAt(i).getUri();
                    selectedPdfList.add(pdfUri);
                }
            }

           uploadFiles(selectedPdfList);
        }
    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (nameIndex != -1) {
                        result = cursor.getString(nameIndex);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }


    private void uploadFiles(List<Uri> pdfList) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        if (currentPdfIndex < pdfList.size()) {
            Uri data = pdfList.get(currentPdfIndex);
            String pdfFileName = getFileName(data);


            if (classInput.getText().toString().equals("X")) {
                StorageReference reference = storageReference.child(classInput.getText().toString() + "/" + System.currentTimeMillis() + ".pdf");
                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();
                                PdfModal pdfModal = new PdfModal(classInput.getText().toString(), categoryInput.getText().toString() + "_" + subjectInput.getText().toString(), uri.toString(), pdfFileName);
                                databaseReference1.child(databaseReference1.push().getKey()).setValue(pdfModal);
                                Toast.makeText(getApplicationContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                                currentPdfIndex++;
                                uploadFiles(pdfList);
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded: " + (currentPdfIndex+1)+"/"+pdfList.size());
                            }
                        });
            } else if (classInput.getText().toString().equals("XII")) {
                StorageReference reference = storageReference.child(classInput.getText().toString() + "/" + System.currentTimeMillis() + ".pdf");
                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();
                                PdfModal pdfModal = new PdfModal(classInput.getText().toString(), categoryInput.getText().toString() + "_" + subjectInput.getText().toString(), uri.toString(),pdfFileName);
                                databaseReference2.child(databaseReference2.push().getKey()).setValue(pdfModal);
                                Toast.makeText(getApplicationContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                                currentPdfIndex++;
                                uploadFiles(pdfList);
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded: " + (currentPdfIndex+1)+"/"+pdfList.size());
                            }
                        });
            } else if (classInput.getText().toString().equals("VOCATIONAL")) {
                StorageReference reference = storageReference.child(classInput.getText().toString() + "/" + System.currentTimeMillis() + ".pdf");
                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();
                                PdfModal pdfModal = new PdfModal(classInput.getText().toString(), categoryInput.getText().toString(), uri.toString(), pdfFileName);
                                databaseReference3.child(databaseReference3.push().getKey()).setValue(pdfModal);
                                Toast.makeText(getApplicationContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                                currentPdfIndex++;
                                uploadFiles(pdfList);
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded: " +(currentPdfIndex+1)+"/"+pdfList.size());
                            }
                        });
            } else if (classInput.getText().toString().equals("DIPLOMA_ENGLISH")) {
                StorageReference reference = storageReference.child(classInput.getText().toString() + "/" + System.currentTimeMillis() + ".pdf");
                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();
                                PdfModal pdfModal = new PdfModal(classInput.getText().toString(), "Not Required" + "_" + "Not Required", uri.toString(),pdfFileName);
                                databaseReference4.child(databaseReference4.push().getKey()).setValue(pdfModal);
                                Toast.makeText(getApplicationContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                                currentPdfIndex++;
                                uploadFiles(pdfList);
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded: " +(currentPdfIndex+1)+"/"+pdfList.size());
                            }
                        });
            } else if (classInput.getText().toString().equals("DIPLOMA_HINDI")) {
                StorageReference reference = storageReference.child(classInput.getText().toString() + "/" + System.currentTimeMillis() + ".pdf");
                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();
                                PdfModal pdfModal = new PdfModal(classInput.getText().toString(), "Not Required" + "_" + "Not Required", uri.toString(), pdfFileName);
                                databaseReference5.child(databaseReference5.push().getKey()).setValue(pdfModal);
                                Toast.makeText(getApplicationContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                                currentPdfIndex++;
                                uploadFiles(pdfList);
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded: "+(currentPdfIndex+1)+"/"+pdfList.size());
                            }
                        });
            } else if (classInput.getText().toString().equals("NOTIFICATION")) {
                StorageReference reference = storageReference.child(classInput.getText().toString() + "/" + System.currentTimeMillis() + ".pdf");
                reference.putFile(data)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isComplete()) ;
                                Uri uri = uriTask.getResult();
                                PdfModal pdfModal = new PdfModal(classInput.getText().toString(), "Not Required" + "_" + "Not Required", uri.toString(),pdfFileName);
                                databaseReference6.child(databaseReference6.push().getKey()).setValue(pdfModal);
                                Toast.makeText(getApplicationContext(), "File Uploaded Successfully!!", Toast.LENGTH_SHORT).show();
                                currentPdfIndex++;
                                uploadFiles(pdfList);
                                progressDialog.dismiss();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                                progressDialog.setMessage("Uploaded: " +(currentPdfIndex+1)+"/"+pdfList.size());
                            }
                        });
            } else {
                Toast.makeText(this, "Please select class...!!!", Toast.LENGTH_SHORT).show();
                // Reset the current index when all PDFs are processed
            }
        }
        else{
            currentPdfIndex = 0;
            progressDialog.dismiss();
        }


    }


}