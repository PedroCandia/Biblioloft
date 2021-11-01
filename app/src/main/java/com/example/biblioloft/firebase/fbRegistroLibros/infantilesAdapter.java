package com.example.biblioloft.firebase.fbRegistroLibros;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.biblioloft.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class infantilesAdapter extends RecyclerView.Adapter<infantilesAdapter.booksHolder>{

    Context context;
    ArrayList<Infantiles> list;

    DatabaseReference dbRef;

    public infantilesAdapter(Context context, ArrayList<Infantiles> list) {
        this.context = context;
        this.list = list;
    }

    public infantilesAdapter.booksHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list_books,parent,false);
        return new infantilesAdapter.booksHolder(v);
    }

    public void onBindViewHolder(@NonNull @NotNull infantilesAdapter.booksHolder holder, int position) {

        dbRef = FirebaseDatabase.getInstance().getReference().child("books").child("infantiles");

        Infantiles books = list.get(position);
        holder.tipoLibro.setText(books.getTipoLibro());
        holder.nombreLibro.setText(books.getNombreLibro());
        holder.descripcionLibro.setText(books.getDescripcionLibro());
        holder.paginasLibro.setText(books.getPaginasLibro());
        Picasso.get().load(books.getImageLibro()).into(holder.imageLibro);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                dialogo1.setCancelable(true);

                final CharSequence[] opciones = new CharSequence[2];
                opciones[0] = "Editar";
                opciones[1] = "Borrar";
                dialogo1.setTitle("Seleccione una opción");
                dialogo1.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(opciones[which] == "Editar"){
                            AppCompatActivity activity = (AppCompatActivity) context;
                            //Fragment myFragment = new EditarDesayunoFragment(foods.getP_ID());
                            //activity.getSupportFragmentManager().beginTransaction().replace(R.id.body_container, myFragment).addToBackStack(null).commit();
                        }
                        if(opciones[which] == "Borrar"){
                            list.clear();

                            dbRef.child(books.getLibroID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    Toast.makeText(context, "Se ha borrado correctamente", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogo1.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class booksHolder extends RecyclerView.ViewHolder{

        TextView tipoLibro, nombreLibro, descripcionLibro, paginasLibro;
        ImageView imageLibro;

        public booksHolder(View itemView){
            super(itemView);

            tipoLibro = itemView.findViewById(R.id.item_tipo);
            nombreLibro = itemView.findViewById(R.id.item_nombreLibro);
            descripcionLibro = itemView.findViewById(R.id.item_descripcionLibro);
            paginasLibro = itemView.findViewById(R.id.item_paginasLibro);
            imageLibro = itemView.findViewById(R.id.item_imagen);
        }
    }
}