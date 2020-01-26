package au.com.contractser;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import au.com.contractser.models.Contract;

public class ContractAllAdapter extends RecyclerView.Adapter<ContractHolder> {
    private final List<Contract> contracts;
    Contract contract;
    String dados;

    public ContractAllAdapter(List<Contract> contracts){
        this.contracts = contracts;
    }

    public void atualizarContract(Contract contract){
        contracts.set(contracts.indexOf(contract), contract);
        notifyItemChanged(contracts.indexOf(contract));
    }

    public void adicionarContract(Contract contract){
        contracts.add(contract);
        notifyItemInserted(getItemCount());
    }


    @NonNull
    @Override
    public ContractHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContractHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_all, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContractHolder holder, int position) {

        String status;
        if (contracts.get(position).getAtivo() == 1){
            status = "Active";
        }else{
            status = "Inactive";
        }

        dados = "Current status: " + status + " Modified: "+ contracts.get(position).getFechaData() + " - S/N Bike: " + contracts.get(position).getSerialNumber();

        holder.txtFullNome.setText(contracts.get(position).getFullName());
        holder.txtDados.setText(dados);

        final Contract contract = contracts.get(position);
        holder.btnEditar.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                final View view = v;
                Activity activity = getActivity(view);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("contract_edt", contract.getIdContract());
                activity.finish();
                activity.startActivity(intent);
            }
        });

        holder.btnExibir.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                final View view = v;
                Activity activity = getActivity(view);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("contract_pdf", contract.getIdContract());
                activity.finish();
                activity.startActivity(intent);
            }
        });

        holder.btnPhoto.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                final View view = v;
                Activity activity = getActivity(view);
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("contract_photo", contract.getIdContract());
                activity.finish();
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return contracts != null ? contracts.size() : 0;
    }

    private Activity getActivity(View view){
        Context context = view.getContext();
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
