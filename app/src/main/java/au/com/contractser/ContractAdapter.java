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

public class ContractAdapter extends RecyclerView.Adapter<ContractHolder> {
    private final List<Contract> contracts;
    Contract contract;
    String dados;

    public ContractAdapter(List<Contract> contracts){
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
        return new ContractHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContractHolder holder, int position) {

        dados = "S/N: " + contracts.get(position).getSerialNumber() + " - Model: "+ contracts.get(position).getModel() + " - Color: " + contracts.get(position).getColor();

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
