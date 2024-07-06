package com.example.adminniospersonal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.DeleteViewHolder> {
    private List<PdfModal> pdfList;
    Context context;
    public DeleteAdapter(Context context,List<PdfModal> pdfList){
        this.context = context;
        this.pdfList = pdfList;
    }

    @NonNull
    @Override
    public DeleteAdapter.DeleteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_item, parent, false);
        return new DeleteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeleteAdapter.DeleteViewHolder holder, int position) {
        PdfModal pdf = pdfList.get(position);

        // Bind data to the ViewHolder
        holder.nameTextView.setText(pdf.getName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePdfFromFirebase(pdf);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }
    public static class DeleteViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        Button edit, delete;
        // Add other TextViews as needed

        public DeleteViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pdfFileName);
            edit = itemView.findViewById(R.id.view);
            delete = itemView.findViewById(R.id.save);
            // Initialize other TextViews
        }
    }

    private void deletePdfFromFirebase(PdfModal pdf) {
        // Assuming you have a reference to your Firebase Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        // Assuming your PdfModal class has a unique identifier field named "id"

        // Reference the specific PDF using its ID and remove it from Firebase
        databaseReference.child(pdf.getStandard())
                .orderByChild("category")
                .equalTo(pdf.category)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PdfModal pdfModal = snapshot.getValue(PdfModal.class);
                            if (pdfModal != null && pdfModal.getName().equals(pdf.getName())) {
                                snapshot.getRef().removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // PDF deleted successfully
                                                Toast.makeText(context, "PDF deleted successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Handle failure
                                                Toast.makeText(context, "Failed to delete PDF", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle errors
                        Toast.makeText(context, "Error deleting PDF", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
