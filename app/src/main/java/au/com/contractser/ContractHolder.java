package au.com.contractser;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ContractHolder extends RecyclerView.ViewHolder {

    public TextView txtFullNome;
    public TextView txtDados;
    public ImageButton btnEditar;
    public ImageButton btnExibir;
    public ImageButton btnPhoto;

    public ContractHolder(View itemView){
        super(itemView);
        txtFullNome = (TextView)itemView.findViewById(R.id.txt_full_nome);
        txtDados = (TextView)itemView.findViewById(R.id.txt_dados);
        btnEditar = (ImageButton)itemView.findViewById(R.id.btn_edit);
        btnExibir = (ImageButton)itemView.findViewById(R.id.btn_exibe);
        btnPhoto = (ImageButton)itemView.findViewById(R.id.btn_doc);
    }

}
