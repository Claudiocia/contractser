package au.com.contractser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import au.com.contractser.databases.BDContract;
import au.com.contractser.models.Contract;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    String query;
    CustomListAdapter adapter;
    ListView lv;
    List<Contract> mList;
    Contract contract;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        query = getIntent().getExtras().getString("query");

        BDContract bdContractList = new BDContract(this);
        mList = bdContractList.searchContract(query);

        if (mList.isEmpty()){
            toast = Toast.makeText(ResultActivity.this, "There is no result for this search", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 2000);
            Intent it = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(it);
            finish();
        }else {
            setContentView(R.layout.activity_result);

            lv = (ListView)findViewById(R.id.res_listView);lv.setVerticalFadingEdgeEnabled(true);

            adapter = new CustomListAdapter(this);
            lv.setAdapter(adapter);

           // mToolbar = (Toolbar)findViewById(R.id.toolbar_result);
           // mToolbar.setTitle("Search Result");
            //setSupportActionBar(mToolbar);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    int pos = arg2;

                    int x = mList.get(pos).getIdContract();

                    Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                    intent.putExtra("pos", x);

                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            finish();
            Intent it = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(it);
            //Toast.makeText(MainActivity.this, "teste de clique de botão", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_list){
            finish();
            Intent it = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(it);
            //Toast.makeText(AllListActivity.this, "teste de clique de botão listar todos", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_list_all){
            finish();
            Intent it = new Intent(ResultActivity.this, AllListActivity.class);
            startActivity(it);
            //Toast.makeText(AllListActivity.this, "teste de clique de botão listar todos", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    //class ListAdapter
    class CustomListAdapter extends BaseAdapter{
        private LayoutInflater layoutInflater;

        public CustomListAdapter(ResultActivity activity){
            layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public  String adicionarEnd(int x){
            String endComp = "";

            BDContract bdContract = new BDContract(ResultActivity.this);
            contract = bdContract.buscarPorId(x);

            endComp = contract.getAddress() + " - " +
                    contract.getSuburb() + "/" +
                    contract.getState() + " - Postcode: " +
                    contract.getPostcode();

            return  endComp;
        }

        public String comporBike(int x){
            String dadosBike = "";
            BDContract bdContractBike = new BDContract(ResultActivity.this);
            contract = bdContractBike.buscarPorId(x);
            dadosBike ="Model: "+ contract.getModel() + " - " +
                    contract.getColor() + " - S/N: " +
                    contract.getSerialNumber();

            return  dadosBike;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;
            int pos = position;

            if (listItem == null){
                listItem = layoutInflater.inflate(R.layout.item_list_result, null);
            }
            //Colocando na tela os elementos da busca
            TextView tvNome = (TextView)listItem.findViewById(R.id.nome_res);
            TextView tvBike = (TextView)listItem.findViewById(R.id.bike_dados_res);
            TextView tvEnd = (TextView)listItem.findViewById(R.id.end_res);
            TextView tvEmail = (TextView) listItem.findViewById(R.id.email_res);
            TextView tvTel = (TextView) listItem.findViewById(R.id.telefone_res);
            TextView tvTel2 = (TextView) listItem.findViewById(R.id.telefone2_res);

            int idRes = mList.get(pos).getIdContract();
            String endere = adicionarEnd(idRes);
            String nome = mList.get(pos).getFullName();
            String bike = comporBike(idRes);
            String email = mList.get(pos).getEmail();
            String telAustr = mList.get(pos).getAustralianNumber();
            String tel2;
            if (mList.get(pos).getWhatsApp() == null){
                tel2 = "";
            }else {
                tel2 = mList.get(pos).getWhatsApp();
            }

            tvNome.setText(nome);
            tvBike.setText(bike);
            tvEnd.setText(endere);
            tvEmail.setText(email);
            tvTel.setText(telAustr);
            tvTel2.setText(tel2);


            return listItem;
        }
    }
}
