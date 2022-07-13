package com.example.stonemixapp_v2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }
    //---------------------------------------------------------------
    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.nombreMaterial.setText(model.getNombreMaterial());
        holder.descripcionMaterial.setText(model.getDescripcionMaterial());
        holder.cantidadMaterial.setText(model.getCantidadMaterial());
        //revisa los common por si las dudas
        Glide.with(holder.img.getContext())
                .load(model.getUrlMaterial())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    //  view -----
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();
                //dialogPlus.show();

                View view = dialogPlus.getHolderView();
                EditText nombreMaterial = view.findViewById(R.id.txtNombreMaterial);
                EditText cantidadMaterial = view.findViewById(R.id.txtCantidadMaterial);
                EditText descripcionMaterial = view.findViewById(R.id.txtDescripcionMaterial);
                EditText urlMaterial = view.findViewById(R.id.txtUrlMaterial);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                nombreMaterial.setText(model.getNombreMaterial());
                cantidadMaterial.setText(model.getCantidadMaterial());
                descripcionMaterial.setText(model.getDescripcionMaterial());
                urlMaterial.setText(model.getUrlMaterial());

                dialogPlus.show();
                // --------
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("nombreMaterial", nombreMaterial.getText().toString());
                        map.put("cantidadMaterial", cantidadMaterial.getText().toString());
                        map.put("descripcionMaterial", descripcionMaterial.getText().toString());
                        map.put("urlMaterial", urlMaterial.getText().toString());

                        if (nombreMaterial.getText().toString().trim().equals("") || cantidadMaterial.getText().toString().trim().equals("")
                                || descripcionMaterial.getText().toString().trim().equals("") || urlMaterial.getText().toString().trim().equals("")) {
                            new SweetAlertDialog(holder.nombreMaterial.getContext(), SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Error")
                                    .setContentText("Completar campos vacios")
                                    .show();
                            return;
                        } else {
                            FirebaseDatabase.getInstance().getReference().child("materiales")
                                    .child(getRef(position).getKey()).updateChildren(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            new SweetAlertDialog(holder.nombreMaterial.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Correcto")
                                                    .setContentText("Datos Actualizados")
                                                    .show();
                                            dialogPlus.dismiss();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(Exception e) {
                                            Toast.makeText(holder.nombreMaterial.getContext(), "Error al Momento de Actualizar", Toast.LENGTH_SHORT).show();
                                            dialogPlus.dismiss();
                                        }
                                    });

                        }
                    }
                });
            }
        });
        // Función para eliminar
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.nombreMaterial.getContext());
                builder.setTitle("¿Estás seguro de eliminarlo?");
                builder.setMessage("Los datos borrados no se pueden deshacer.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        FirebaseDatabase.getInstance().getReference().child("materiales")
                                .child(getRef(position).getKey()).removeValue();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        Toast.makeText(holder.nombreMaterial.getContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }
    //--------------------------------------------------------------------
    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView nombreMaterial, descripcionMaterial, cantidadMaterial;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            nombreMaterial = (TextView)itemView.findViewById(R.id.txtnombre);
            descripcionMaterial = (TextView)itemView.findViewById(R.id.txtdescripcion);
            cantidadMaterial = (TextView)itemView.findViewById(R.id.txtcantidad);

            //botones
            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }
}
