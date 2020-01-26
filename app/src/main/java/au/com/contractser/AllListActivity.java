package au.com.contractser;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.contractser.databases.BDContract;
import au.com.contractser.models.Contract;
import au.com.contractser.utils.CaptureSignatureView;
import au.com.contractser.utils.MaskEditUtil;
import au.com.contractser.utils.ViewPrintAdapter;

public class AllListActivity extends AppCompatActivity {
    private AlertDialog alerta;
    private final int PERMISSAO_REQUEST = 2;
    String caminhoDaImagem;
    private Uri uriAss;

    //Variaveis da Camera
    private static final int CAMERA = 1;
    ImageView ivPhotoPassport;
    String caminhoPassaport;
    private Uri uri;


    Contract contractEditado = null;

    String helmet = "Helmet";
    String phoneHolder = "Phone Holder";
    String removableFrontLight = "Removable Front Light";
    String removableRearLight = "Removable Rear Light";
    String fixedFrontLight = "Fixed Front Light";
    String fixedRearLight = "Fixed Rear Light";

    boolean sucesso;

    String accessories, passportPhoto;
    int idCont;

    //Variaveis de edição
    String fullNameEdt, dateBirthEdt, passportNumberEdt, passportCountryEdt, sexoEdt, addressEdt;
    String suburbEdt, stateEdt, postCodeEdt, emailEdt, schoolEdt, visaAgencyEdt, austrNumberEdt, whatsNumberEdt;
    String serialNumberEdt, modelEdt, colorEdt, weeksEdt, startDateEdt, returnDateEdt, weeklyRateEdt;
    String pmtDayEdt, firtsWeekEdt, secDepEdt, notesEdt;


    Spinner spnModel, spnColor, spnWeeklyRate, spnPmtDay, spnFirstWeek, spnSecDep;
    CheckBox chkTerm3, chkTerm1;

    EditText txtFullNameEdt, txtDateBirthEdt, txtPassportNumberEdt, txtPassportCountryEdt;
    EditText txtAddressEdt, txtSuburbEdt, txtStateEdt, txtPostCodeEdt, txtEmailEdt, txtSchoolEdt;
    EditText txtVisaAgencyEdt, txtAustrNumberEdt, txtWhatsappNumberEdt, txtSerialNumberEdt;
    EditText txtStartDateEdt, txtReturnDateEdt, txtNotesEdt;

    Spinner spnModelEdt, spnColorEdt, spnWeeklyRateEdt, spnPmtDayEdt, spnFirstWeekEdt, spnSecDepEdt;
    CheckBox chkHelmetEdt, chkPhoneHolderEdt, chkRemovableFrontLightEdt;
    CheckBox chkRemovableRearLightEdt, chkFixedFrontLightEdt, chkFixedRearLightEdt;
    CheckBox chkTerm3Viu, chkTerm1Viu;
    CheckBox chkTerm3Pdf, chkTerm1Pdf;

    RadioGroup rgWeeksEdt, rgSexoEdt;
    RadioButton btnSexoEdt, btnWeeksEdt;

    LinearLayout mContent;
    CaptureSignatureView mSig;

    TextView tvFullName, tvDateBirth, tvGender, tvPassportNumber, tvPassportCountry;
    TextView tvEmailClient, tvAdress, tvSuburb, tvState, tvPostCode, tvSchool, tvVisaAgency;
    TextView tvAustNumber, tvWhatsNumber, tvSerieNumber, tvModel, tvColor, tvStartDate, tvNotes;
    TextView tvReturnDate, tvNumWeeks, tvWeeklyRate, tvPmtDay, tvFirstWeek, tvSecDep, tvAccessories;

    TextView tvFullNameEdt, tvDateBirthEdt, tvGenderEdt, tvPassportNumberEdt, tvPassportCountryEdt;
    TextView tvEmailClientEdt, tvAdressEdt, tvSuburbEdt, tvStateEdt, tvPostCodeEdt, tvSchoolEdt, tvVisaAgencyEdt;
    TextView tvAustNumberEdt, tvWhatsNumberEdt, tvSerieNumberEdt, tvModelEdt, tvColorEdt, tvStartDateEdt, tvNotesEdt;
    TextView tvReturnDateEdt, tvNumWeeksEdt, tvWeeklyRateEdt, tvPmtDayEdt, tvFirstWeekEdt, tvSecDepEdt, tvAccessoriesEdt;

    TextView tvFullNameViu, tvDateBirthViu, tvGenderViu, tvPassportNumberViu, tvPassportCountryViu;
    TextView tvEmailClientViu, tvAdressViu, tvSuburbViu, tvStateViu, tvPostCodeViu, tvSchoolViu, tvVisaAgencyViu;
    TextView tvAustNumberViu, tvWhatsNumberViu, tvSerieNumberViu, tvModelViu, tvColorViu, tvStartDateViu, tvNotesViu;
    TextView tvReturnDateViu, tvNumWeeksViu, tvWeeklyRateViu, tvPmtDayViu, tvFirstWeekViu, tvSecDepViu, tvAccessoriesViu;
    ImageView ivAssinaturaViu;

    TextView tvFullNamePdf, tvDateBirthPdf, tvGenderPdf, tvPassportNumberPdf, tvPassportCountryPdf;
    TextView tvEmailClientPdf, tvAdressPdf, tvSuburbPdf, tvStatePdf, tvPostCodePdf, tvSchoolPdf, tvVisaAgencyPdf;
    TextView tvAustNumberPdf, tvWhatsNumberPdf, tvSerieNumberPdf, tvModelPdf, tvColorPdf, tvStartDatePdf, tvNotesPdf;
    TextView tvReturnDatePdf, tvNumWeeksPdf, tvWeeklyRatePdf, tvPmtDayPdf, tvFirstWeekPdf, tvSecDepPdf, tvAccessoriesPdf;
    ImageView ivAssinaturaPdf;

    Contract c;

    ActionBar barraSup;
    Toast toast, toast2;


    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        //Toolbar toolbar = findViewById(R.id.toolbar_all);
        //setSupportActionBar(toolbar);
        barraSup = getSupportActionBar();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else { ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSAO_REQUEST);
            }
        }


        Intent intent = getIntent();
        if (intent.hasExtra("contract_edt")){
            findViewById(R.id.includemain_all).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecliente_edt_all).setVisibility(View.VISIBLE);
            findViewById(R.id.includebike_edt_all).setVisibility(View.INVISIBLE);

            txtFullNameEdt = (EditText)findViewById(R.id.txtNome_edt);
            txtFullNameEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtDateBirthEdt = (EditText)findViewById(R.id.txtDateBirth_edt);
            txtDateBirthEdt.addTextChangedListener(MaskEditUtil.mask(txtDateBirthEdt, MaskEditUtil.FORMAT_DATE));
            txtPassportNumberEdt = (EditText)findViewById(R.id.txtPassportNumber_edt);
            txtPassportNumberEdt.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
            txtPassportCountryEdt = (EditText)findViewById(R.id.txtPassportCountry_edt);
            txtPassportCountryEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            rgSexoEdt = (RadioGroup)findViewById(R.id.rgSexo_edt);
            txtAddressEdt = (EditText)findViewById(R.id.txtAddress_edt);
            txtAddressEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtSuburbEdt = (EditText)findViewById(R.id.txtSuburb_edt);
            txtSuburbEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtStateEdt = (EditText)findViewById(R.id.txtState_edt);
            txtStateEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtPostCodeEdt = (EditText)findViewById(R.id.txtPostCode_edt);
            txtPostCodeEdt.addTextChangedListener(MaskEditUtil.mask(txtPostCodeEdt, MaskEditUtil.FORMAT_POST_CODE));
            txtEmailEdt = (EditText)findViewById(R.id.txtEmail_edt);
            txtEmailEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtSchoolEdt = (EditText)findViewById(R.id.txtSchool_edt);
            txtSchoolEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtVisaAgencyEdt = (EditText)findViewById(R.id.txtVisaAgency_edt);
            txtVisaAgencyEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            txtAustrNumberEdt = (EditText)findViewById(R.id.txtAustrNumber_edt);
            txtAustrNumberEdt.addTextChangedListener(MaskEditUtil.mask(txtAustrNumberEdt, MaskEditUtil.FORMAT_PHONE));
            txtWhatsappNumberEdt = (EditText)findViewById(R.id.txtWhatsapp_edt);
            txtWhatsappNumberEdt.addTextChangedListener(MaskEditUtil.mask(txtWhatsappNumberEdt, MaskEditUtil.FORMAT_PHONE_WA));

            //Campos Form Bike
            txtSerialNumberEdt = (EditText)findViewById(R.id.txtSerialNumber_edt);
            txtSerialNumberEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
            spnModelEdt = (Spinner)findViewById(R.id.spnModel_edt);
            spnColorEdt = (Spinner)findViewById(R.id.spnColor_edt);
            rgWeeksEdt = (RadioGroup)findViewById(R.id.rgWeeks_edt);
            txtStartDateEdt = (EditText)findViewById(R.id.txtStartDate_edt);
            txtStartDateEdt.addTextChangedListener(MaskEditUtil.mask(txtStartDateEdt, MaskEditUtil.FORMAT_DATE));
            txtReturnDateEdt = (EditText)findViewById(R.id.txtReturnDate_edt);
            txtReturnDateEdt.addTextChangedListener(MaskEditUtil.mask(txtReturnDateEdt, MaskEditUtil.FORMAT_DATE));
            spnWeeklyRateEdt = (Spinner)findViewById(R.id.spnWeeklyRate_edt);
            spnPmtDayEdt = (Spinner)findViewById(R.id.spnPmtDay_edt);
            spnFirstWeekEdt = (Spinner)findViewById(R.id.spnFirstWeek_edt);
            spnSecDepEdt = (Spinner)findViewById(R.id.spnSecDep_edt);
            txtNotesEdt = (EditText)findViewById(R.id.txtNotes_edt);
            txtNotesEdt.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

            chkHelmetEdt = (CheckBox)findViewById(R.id.chkHelmet_edt);
            chkPhoneHolderEdt = (CheckBox)findViewById(R.id.chkPhoneHolder_edt);
            chkRemovableFrontLightEdt = (CheckBox)findViewById(R.id.chkRemovableFrontLight_edt);
            chkRemovableRearLightEdt = (CheckBox)findViewById(R.id.chkRemovableRearLight_edt);
            chkFixedFrontLightEdt = (CheckBox)findViewById(R.id.chkFixedFrontLight_edt);
            chkFixedRearLightEdt = (CheckBox)findViewById(R.id.chkFixedRearLight_edt);

            int idCont = intent.getIntExtra("contract_edt", 0);

            Log.d("claudio", "O que vem no intent é : "+ idCont);

            BDContract bdContr = new BDContract(AllListActivity.this);
            contractEditado = bdContr.buscarPorId(idCont);

            txtFullNameEdt.setText(contractEditado.getFullName());
            txtDateBirthEdt.setText(contractEditado.getDateBirth());
            txtPassportNumberEdt.setText(contractEditado.getPassportNumber());
            txtPassportCountryEdt.setText(contractEditado.getPassportCountry());
            if (contractEditado.getSexo() != null){
                RadioButton rb;
                if (contractEditado.getSexo().contentEquals("MALE")){
                    rb = (RadioButton)findViewById(R.id.rbMasculino_edt);
                }else  if (contractEditado.getSexo().contentEquals("FEMALE")){
                    rb = (RadioButton)findViewById(R.id.rbFeminino_edt);
                }else {
                    rb = (RadioButton)findViewById(R.id.rbOutro_edt);
                }
                rb.setChecked(true);
            }
            txtAddressEdt.setText(contractEditado.getAddress());
            txtSuburbEdt.setText(contractEditado.getSuburb());
            txtStateEdt.setText(contractEditado.getState());
            txtPostCodeEdt.setText(contractEditado.getPostcode());
            txtEmailEdt.setText(contractEditado.getEmail());
            txtSchoolEdt.setText(contractEditado.getSchool());
            txtVisaAgencyEdt.setText(contractEditado.getVisaAgency());
            txtAustrNumberEdt.setText(contractEditado.getAustralianNumber());
            txtWhatsappNumberEdt.setText(contractEditado.getWhatsApp());
            txtSerialNumberEdt.setText(contractEditado.getSerialNumber());
            spnModelEdt.setSelection(getIndex(spnModelEdt, contractEditado.getModel()));
            spnColorEdt.setSelection(getIndex(spnColorEdt, contractEditado.getColor()));
            if (contractEditado.getNumberWeeks() != null){
                RadioButton rb2;
                if (contractEditado.getNumberWeeks().contentEquals("04 Weeks")){
                    rb2 = (RadioButton)findViewById(R.id.rbQuatroWeeks_edt);
                }else if (contractEditado.getNumberWeeks().contentEquals("08 Weeks")){
                    rb2 = (RadioButton)findViewById(R.id.rbOitoWeeks_edt);
                }else {
                    rb2 = (RadioButton)findViewById(R.id.rbOutrosWeeks_edt);
                }
                rb2.setChecked(true);
            }
            txtStartDateEdt.setText(contractEditado.getStartDate());
            txtReturnDateEdt.setText(contractEditado.getReturnDate());
            spnWeeklyRateEdt.setSelection(getIndex(spnWeeklyRateEdt, contractEditado.getWeeklyRate()));
            spnPmtDayEdt.setSelection(getIndex(spnPmtDayEdt, contractEditado.getPmtDay()));
            spnFirstWeekEdt.setSelection(getIndex(spnFirstWeekEdt, contractEditado.getFirstWeek()));
            spnSecDepEdt.setSelection(getIndex(spnSecDepEdt, contractEditado.getSecDep()));
            txtNotesEdt.setText(contractEditado.getNotes());

            String voltaAcessorios = contractEditado.getAccessoriesIncluded();


            List<String> myList = new ArrayList<String>(Arrays.asList(voltaAcessorios.split(", ")));

            if(myList.contains(helmet)) {
                chkHelmetEdt.setChecked(true);
            } else {
                chkHelmetEdt.setChecked(false);
            }
            if(myList.contains(phoneHolder)) {
                chkPhoneHolderEdt.setChecked(true);
            } else {
                chkPhoneHolderEdt.setChecked(false);
            }
            if(myList.contains(removableFrontLight)) {
                chkRemovableFrontLightEdt.setChecked(true);
            } else {
                chkRemovableFrontLightEdt.setChecked(false);
            }
            if(myList.contains(removableRearLight)) {
                chkRemovableRearLightEdt.setChecked(true);
            } else {
                chkRemovableRearLightEdt.setChecked(false);
            }
            if(myList.contains(fixedFrontLight)) {
                chkFixedFrontLightEdt.setChecked(true);
            } else {
                chkFixedFrontLightEdt.setChecked(false);
            }
            if(myList.contains(fixedRearLight)) {
                chkFixedRearLightEdt.setChecked(true);
            } else {
                chkFixedRearLightEdt.setChecked(false);
            }

            TextView tvAcessoriosEdt = (TextView)findViewById(R.id.tvAcessorios2_edt);
            tvAcessoriosEdt.setText(voltaAcessorios);


        } //Final do If do Intent

        else if(intent.hasExtra("contract_pdf")){

            findViewById(R.id.includemain_all).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecliente_edt_all).setVisibility(View.INVISIBLE);
            findViewById(R.id.includeassina_viu_all).setVisibility(View.VISIBLE);


            tvFullNameViu = (TextView)findViewById(R.id.tvFullName_viu);
            tvDateBirthViu = (TextView)findViewById(R.id.tvDateBirth_viu);
            tvGenderViu = (TextView)findViewById(R.id.tvGender_viu);
            tvPassportNumberViu = (TextView)findViewById(R.id.tvPassportNum_viu);
            tvPassportCountryViu = (TextView)findViewById(R.id.tvPassportCountry_viu);
            tvEmailClientViu = (TextView)findViewById(R.id.tvEmailClient_viu);
            tvAdressViu = (TextView)findViewById(R.id.tvAdress_viu);
            tvSuburbViu = (TextView)findViewById(R.id.tvSuburb_viu);
            tvStateViu = (TextView)findViewById(R.id.tvState_viu);
            tvPostCodeViu = (TextView)findViewById(R.id.tvPostCode_viu);
            tvSchoolViu = (TextView) findViewById(R.id.tvScholl_viu);
            tvVisaAgencyViu = (TextView)findViewById(R.id.tvVisaAgency_viu);
            tvAustNumberViu = (TextView)findViewById(R.id.tvAustNumber_viu);
            tvWhatsNumberViu = (TextView)findViewById(R.id.tvWhatsNumber_viu);
            tvSerieNumberViu = (TextView)findViewById(R.id.tvSerieNumber_viu);
            tvModelViu = (TextView)findViewById(R.id.tvModel_viu);
            tvColorViu = (TextView)findViewById(R.id.tvColor_viu);
            tvStartDateViu = (TextView)findViewById(R.id.tvStartDate_viu);
            tvReturnDateViu = (TextView)findViewById(R.id.tvReturnDate_viu);
            tvNumWeeksViu = (TextView)findViewById(R.id.tvNumWeeks_viu);
            tvWeeklyRateViu = (TextView)findViewById(R.id.tvWeeklyRate_viu);
            tvPmtDayViu = (TextView)findViewById(R.id.tvPmtDay_viu);
            tvFirstWeekViu = (TextView)findViewById(R.id.tvFirstWeek_viu);
            tvSecDepViu = (TextView)findViewById(R.id.tvSecDep_viu);
            tvAccessoriesViu = (TextView)findViewById(R.id.tvAccessorios_viu);
            tvNotesViu = (TextView)findViewById(R.id.tvNotes_viu);
            chkTerm1Viu = (CheckBox)findViewById(R.id.chkTerm3_viu);
            chkTerm3Viu = (CheckBox)findViewById(R.id.chkTerm1_viu);
            ivAssinaturaViu = (ImageView)findViewById(R.id.iv_assinatura_viu);

            idCont = intent.getIntExtra("contract_pdf", 0);

            BDContract bdContrPdf = new BDContract(this);
            Contract cPdf = new Contract();
            cPdf = bdContrPdf.buscarPorId(idCont);

            String caminhoPdf = cPdf.getAssinaturaPhoto();

            tvFullNameViu.setText("Full Name: \n" + cPdf.getFullName());
            tvDateBirthViu.setText("Date of Burth: \n" + cPdf.getDateBirth());
            tvGenderViu.setText("Gender: \n" + cPdf.getSexo());
            tvPassportNumberViu.setText("Passport Num.: \n" + cPdf.getPassportNumber());
            tvPassportCountryViu.setText("Passport Country: \n" + cPdf.getPassportCountry());
            tvEmailClientViu.setText("E-mail: \n" + cPdf.getEmail());
            tvAdressViu.setText("Adress: \n" + cPdf.getAddress());
            tvSuburbViu.setText("Suburb: \n" + cPdf.getSuburb());
            tvStateViu.setText("State: \n" + cPdf.getState());
            tvPostCodeViu.setText("Post Code: \n" + cPdf.getPostcode());
            tvSchoolViu.setText("English / VET School: \n" + cPdf.getSchool());
            tvVisaAgencyViu.setText("Visa Agency: \n" + cPdf.getVisaAgency());
            tvAustNumberViu.setText("Australian Mobile Num.: \n" + cPdf.getAustralianNumber());
            tvWhatsNumberViu.setText("WhatsApp Number: \n" + cPdf.getWhatsApp());
            tvSerieNumberViu.setText("Serial Number: \n" + cPdf.getSerialNumber());
            tvModelViu.setText("Model: \n" + cPdf.getModel());
            tvColorViu.setText("Color: \n" + cPdf.getColor());
            tvStartDateViu.setText("Contract Start Date: \n" + cPdf.getStartDate());
            tvReturnDateViu.setText("Equipment Return Date: \n" + cPdf.getReturnDate());
            tvNumWeeksViu.setText("Num. of Weeks: \n" + cPdf.getNumberWeeks());
            tvWeeklyRateViu.setText("Weekly Rate: \n" + cPdf.getWeeklyRate());
            tvPmtDayViu.setText("Rent payment day: \n" + cPdf.getPmtDay());
            tvFirstWeekViu.setText("1st Week Rent Paid: \n" + cPdf.getFirstWeek());
            tvSecDepViu.setText("Security Deposit paid: \n" + cPdf.getSecDep());
            tvAccessoriesViu.setText("Accessories included in this rent: \n" + cPdf.getAccessoriesIncluded());
            tvNotesViu.setText("Notes: \n" + cPdf.getNotes());

            String sd_pathPdf = "/storage/emulated/0/Pictures/";
            //Carrega o arquivo da pasta pictures
            File imgFilePdf = new File(sd_pathPdf, caminhoPdf);
            Log.d("claudio", "O sd_pathPdf é: "+ imgFilePdf.toString());

            Bitmap myBitmapPdf = BitmapFactory.decodeFile(imgFilePdf.getPath());

            ivAssinaturaViu.setImageBitmap(myBitmapPdf);

        }// Final do else do 2 Intent

        else if (intent.hasExtra("contract_photo")){

            findViewById(R.id.includemain_all).setVisibility(View.INVISIBLE);
            findViewById(R.id.includefoto_passaporte_all).setVisibility(View.VISIBLE);

            idCont = intent.getIntExtra("contract_photo", 0);

            final BDContract bdContrPho = new BDContract(this);
            Contract cPho = new Contract();
            Log.d("claudio", "o id para buscar o contrato para registrar a foto é: "+ idCont);

            cPho = bdContrPho.buscarPorId(idCont);

            String nomeArquivoPho = cPho.getIdContract()+"_"+cPho.getFullName();

            String caminhoPho = cPho.getPassportPhoto();

            Log.d("claudio", "caminho que vem do banco sem foto é: "+ caminhoPho);

            if (caminhoPho == null){

                ivPhotoPassport =(ImageView)findViewById(R.id.iv_photo_passport);

                usarCamera(findViewById(R.id.list_all_activity), nomeArquivoPho);

                Button btnSalvar = (Button)findViewById(R.id.btnVoltar_photo);
                btnSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean photo = false;
                        final BDContract bdPassaport = new BDContract(AllListActivity.this);
                        photo = bdPassaport.salvarPassaport(caminhoPassaport, idCont);

                        if (!photo){
                            Toast.makeText(AllListActivity.this, "Something happened and the picture was not saved!", Toast.LENGTH_LONG).show();
                            finish();
                            Intent it = new Intent(AllListActivity.this, AllListActivity.class);
                            startActivity(it);
                        } else{
                            Toast.makeText(AllListActivity.this, "Photo saved on Database!", Toast.LENGTH_LONG).show();
                            finish();
                            Intent it = new Intent(AllListActivity.this, AllListActivity.class);
                            startActivity(it);
                        }
                    }
                });

            }else {
                ivPhotoPassport =(ImageView)findViewById(R.id.iv_photo_passport);

                File photoFile = new File(caminhoPho);
                Log.d("claudio", "O caminho que volta do banco é: "+ caminhoPho);

                Bitmap bitmapPass = BitmapFactory.decodeFile(photoFile.getPath());
                ivPhotoPassport.setImageBitmap(bitmapPass);

                Button btnSalvar = (Button)findViewById(R.id.btnVoltar_photo);
                btnSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        Intent it = new Intent(AllListActivity.this, AllListActivity.class);
                        startActivity(it);
                    }
                });

            }

        }

        final Button btnCloseViu = (Button)findViewById(R.id.btnAssina_viu);
        btnCloseViu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent it = new Intent(AllListActivity.this, AllListActivity.class);
                startActivity(it);
            }
        });

        Button btnEncerrar = (Button)findViewById(R.id.btnEncerra_viu);
        btnEncerrar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog alertEncerra;
                AlertDialog.Builder builder = new AlertDialog.Builder(AllListActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to reactivate this contract?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean encerrado;
                        BDContract bdEncerra =  new BDContract(AllListActivity.this);
                        encerrado = bdEncerra.reativarContract(idCont);
                        if(!encerrado){
                            toast = Toast.makeText(AllListActivity.this, "Error - Contract still inactive", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 1500);
                        }else{
                            toast2 = Toast.makeText(AllListActivity.this, "Success - Contract is reactivated!", Toast.LENGTH_LONG);
                            toast2.setGravity(Gravity.CENTER, 0, 0);
                            toast2.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast2.cancel();
                                }
                            }, 1500);
                            finish();
                            Intent it = new Intent(AllListActivity.this, AllListActivity.class);
                            startActivity(it);
                        }
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertEncerra = builder.create();
                alertEncerra.show();
            }
        });

        Button btnGerarPdf = (Button)findViewById(R.id.btnPdf_viu);
        btnGerarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implementar metodo para gerar PDF

                findViewById(R.id.includeassina_viu_all).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecontract_pdf_all).setVisibility(View.VISIBLE);

                tvFullNamePdf = (TextView)findViewById(R.id.tvFullName_pdf);
                tvDateBirthPdf = (TextView)findViewById(R.id.tvDateBirth_pdf);
                tvGenderPdf = (TextView)findViewById(R.id.tvGender_pdf);
                tvPassportNumberPdf = (TextView)findViewById(R.id.tvPassportNum_pdf);
                tvPassportCountryPdf = (TextView)findViewById(R.id.tvPassportCountry_pdf);
                tvEmailClientPdf = (TextView)findViewById(R.id.tvEmailClient_pdf);
                tvAdressPdf = (TextView)findViewById(R.id.tvAdress_pdf);
                tvSuburbPdf = (TextView)findViewById(R.id.tvSuburb_pdf);
                tvStatePdf = (TextView)findViewById(R.id.tvState_pdf);
                tvPostCodePdf = (TextView)findViewById(R.id.tvPostCode_pdf);
                tvSchoolPdf = (TextView) findViewById(R.id.tvScholl_pdf);
                tvVisaAgencyPdf = (TextView)findViewById(R.id.tvVisaAgency_pdf);
                tvAustNumberPdf = (TextView)findViewById(R.id.tvAustNumber_pdf);
                tvWhatsNumberPdf = (TextView)findViewById(R.id.tvWhatsNumber_pdf);
                tvSerieNumberPdf = (TextView)findViewById(R.id.tvSerieNumber_pdf);
                tvModelPdf = (TextView)findViewById(R.id.tvModel_pdf);
                tvColorPdf = (TextView)findViewById(R.id.tvColor_pdf);
                tvStartDatePdf = (TextView)findViewById(R.id.tvStartDate_pdf);
                tvReturnDatePdf = (TextView)findViewById(R.id.tvReturnDate_pdf);
                tvNumWeeksPdf = (TextView)findViewById(R.id.tvNumWeeks_pdf);
                tvWeeklyRatePdf = (TextView)findViewById(R.id.tvWeeklyRate_pdf);
                tvPmtDayPdf = (TextView)findViewById(R.id.tvPmtDay_pdf);
                tvFirstWeekPdf = (TextView)findViewById(R.id.tvFirstWeek_pdf);
                tvSecDepPdf = (TextView)findViewById(R.id.tvSecDep_pdf);
                tvAccessoriesPdf = (TextView)findViewById(R.id.tvAccessorios_pdf);
                tvNotesPdf = (TextView)findViewById(R.id.tvNotes_pdf);
                chkTerm1Pdf = (CheckBox)findViewById(R.id.chkTerm3_pdf);
                chkTerm3Pdf = (CheckBox)findViewById(R.id.chkTerm1_pdf);
                ivAssinaturaPdf = (ImageView)findViewById(R.id.iv_assinatura_pdf);


                BDContract bdContr2 = new BDContract(AllListActivity.this);
                Contract c2 = new Contract();
                c2 = bdContr2.buscarPorId(idCont);
                Log.d("claudio", "idCont é:"+ idCont);

                String caminho2 = c2.getAssinaturaPhoto();

                tvFullNamePdf.setText("Full Name: \n" + c2.getFullName());
                tvDateBirthPdf.setText("Date of Burth: \n" + c2.getDateBirth());
                tvGenderPdf.setText("Gender: \n" + c2.getSexo());
                tvPassportNumberPdf.setText("Passport Num.: \n" + c2.getPassportNumber());
                tvPassportCountryPdf.setText("Passport Country: \n" + c2.getPassportCountry());
                tvEmailClientPdf.setText("E-mail: \n" + c2.getEmail());
                tvAdressPdf.setText("Adress: \n" + c2.getAddress());
                tvSuburbPdf.setText("Suburb: \n" + c2.getSuburb());
                tvStatePdf.setText("State: \n" + c2.getState());
                tvPostCodePdf.setText("Post Code: \n" + c2.getPostcode());
                tvSchoolPdf.setText("English / VET School: \n" + c2.getSchool());
                tvVisaAgencyPdf.setText("Visa Agency: \n" + c2.getVisaAgency());
                tvAustNumberPdf.setText("Australian Mobile Num.: \n" + c2.getAustralianNumber());
                tvWhatsNumberPdf.setText("WhatsApp Number: \n" + c2.getWhatsApp());
                tvSerieNumberPdf.setText("Serial Number: \n" + c2.getSerialNumber());
                tvModelPdf.setText("Model: \n" + c2.getModel());
                tvColorPdf.setText("Color: \n" + c2.getColor());
                tvStartDatePdf.setText("Contract Start Date: \n" + c2.getStartDate());
                tvReturnDatePdf.setText("Equipment Return Date: \n" + c2.getReturnDate());
                tvNumWeeksPdf.setText("Num. of Weeks: \n" + c2.getNumberWeeks());
                tvWeeklyRatePdf.setText("Weekly Rate: \n" + c2.getWeeklyRate());
                tvPmtDayPdf.setText("Rent payment day: \n" + c2.getPmtDay());
                tvFirstWeekPdf.setText("1st Week Rent Paid: \n" + c2.getFirstWeek());
                tvSecDepPdf.setText("Security Deposit paid: \n" + c2.getSecDep());
                tvAccessoriesPdf.setText("Accessories included in this rent: \n" + c2.getAccessoriesIncluded());
                tvNotesPdf.setText("Notes: \n" + c2.getNotes());

                barraSup.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


                String sd_path2 = "/storage/emulated/0/Pictures/";
                //Carrega o arquivo da pasta pictures
                File imgFile2 = new File(sd_path2, caminho2);
                Log.d("claudio", "O sd_path2 o que gera pdf é: "+ imgFile2.toString());

                Bitmap myBitmap2 = BitmapFactory.decodeFile(imgFile2.getPath());

                ivAssinaturaPdf.setImageBitmap(myBitmap2);
                String nomePDF2 = c2.getIdContract()+ "_"+c2.getFullName();

                PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
                printManager.print("Contract", new ViewPrintAdapter(nomePDF2, AllListActivity.this,
                        findViewById(R.id.main_activity)), null);
            }
        });

        Button btnSalvarEdt = (Button)findViewById(R.id.btnSalvar_edt);
        btnSalvarEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtFullNameEdt.getText().toString().length() <= 5){
                    txtFullNameEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                    alerta = alertaErro();
                    alerta.show();
                    txtFullNameEdt.setFocusable(true);
                    findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                    findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                }else{
                    txtFullNameEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                    fullNameEdt = txtFullNameEdt.getText().toString();
                    if (txtDateBirthEdt.getText().toString().length() != 10){
                        txtDateBirthEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                        alerta = alertaErro();
                        alerta.show();
                        txtDateBirthEdt.setFocusable(true);
                        findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                        findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                    }else{
                        txtDateBirthEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                        dateBirthEdt = txtDateBirthEdt.getText().toString();
                        if (txtPassportNumberEdt.getText().toString().length() <= 3){
                            txtPassportNumberEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                            alerta = alertaErro();
                            alerta.show();
                            txtPassportNumberEdt.setFocusable(true);
                            findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                            findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                        }else {
                            txtPassportNumberEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                            passportNumberEdt = txtPassportNumberEdt.getText().toString();
                            if (txtPassportCountryEdt.getText().toString().length() <= 1){
                                txtPassportCountryEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                alerta = alertaErro();
                                alerta.show();
                                txtPassportCountryEdt.setFocusable(true);
                                findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                            }else {
                                txtPassportCountryEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                passportCountryEdt = txtPassportCountryEdt.getText().toString();
                                if (rgSexoEdt.getCheckedRadioButtonId() == -1){
                                    rgSexoEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                    alerta = alertaErro();
                                    alerta.show();
                                    rgSexoEdt.setFocusable(true);
                                    findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                    findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                }else {
                                    rgSexoEdt.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    int idSexo = rgSexoEdt.getCheckedRadioButtonId();
                                    btnSexoEdt = (RadioButton)findViewById(idSexo);
                                    sexoEdt = btnSexoEdt.getText().toString();
                                    if (txtAddressEdt.getText().toString().length() <= 4){
                                        txtAddressEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                        alerta = alertaErro();
                                        alerta.show();
                                        txtAddressEdt.setFocusable(true);
                                        findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                        findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                    }else {
                                        txtAddressEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                        addressEdt = txtAddressEdt.getText().toString();
                                        if (txtSuburbEdt.getText().toString().length() <= 3){
                                            txtSuburbEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                            alerta = alertaErro();
                                            alerta.show();
                                            txtSuburbEdt.setFocusable(true);
                                            findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                            findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                        }else {
                                            txtSuburbEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                            suburbEdt = txtSuburbEdt.getText().toString();
                                            if (txtStateEdt.getText().toString().length() <= 1){
                                                txtStateEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                alerta = alertaErro();
                                                alerta.show();
                                                txtStateEdt.setFocusable(true);
                                                findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                                findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                            }else {
                                                txtStateEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                stateEdt = txtStateEdt.getText().toString();
                                                if (txtPostCodeEdt.getText().toString().length() <= 3){
                                                    txtPostCodeEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                    alerta = alertaErro();
                                                    alerta.show();
                                                    txtPostCodeEdt.setFocusable(true);
                                                    findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                                    findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                                }else {
                                                    txtPostCodeEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                    postCodeEdt = txtPostCodeEdt.getText().toString();
                                                    if (txtEmailEdt.getText().toString().length() <= 6){
                                                        txtEmailEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                        alerta = alertaErro();
                                                        alerta.show();
                                                        txtEmailEdt.setFocusable(true);
                                                        findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                                        findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                                    }else {
                                                        txtEmailEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                        emailEdt = txtEmailEdt.getText().toString();
                                                        if (txtSchoolEdt.getText().toString().length() <=2){
                                                            txtSchoolEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                            alerta = alertaErro();
                                                            alerta.show();
                                                            txtSchoolEdt.setFocusable(true);
                                                            findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                                            findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                                        }else {
                                                            txtSchoolEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                            schoolEdt = txtSchoolEdt.getText().toString();
                                                            if (txtVisaAgencyEdt.getText().toString().length() <= 2){
                                                                txtVisaAgencyEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                alerta = alertaErro();
                                                                alerta.show();
                                                                txtVisaAgencyEdt.setFocusable(true);
                                                                findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                                                findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                                            }else {
                                                                txtVisaAgencyEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                visaAgencyEdt = txtVisaAgencyEdt.getText().toString();
                                                                if (txtAustrNumberEdt.getText().toString().length() <= 17){
                                                                    txtAustrNumberEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                    alerta = alertaErro();
                                                                    alerta.show();
                                                                    txtAustrNumberEdt.setFocusable(true);
                                                                    findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                                                                    findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                                                }else {
                                                                    txtAustrNumberEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                    austrNumberEdt = txtAustrNumberEdt.getText().toString();
                                                                    whatsNumberEdt = txtWhatsappNumberEdt.getText().toString();
                                                                    if (txtSerialNumberEdt.getText().toString().length() <= 7){
                                                                        txtSerialNumberEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                        alerta = alertaErro();
                                                                        alerta.show();
                                                                        txtSerialNumberEdt.setFocusable(true);
                                                                    }else {
                                                                        txtSerialNumberEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                        serialNumberEdt = txtSerialNumberEdt.getText().toString();
                                                                        if (spnModelEdt.getSelectedItemId() <= 0){
                                                                            spnModelEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                            alerta = alertaErro();
                                                                            alerta.show();
                                                                            spnModelEdt.setFocusable(true);
                                                                        }else {
                                                                            spnModelEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                            modelEdt = spnModelEdt.getSelectedItem().toString();
                                                                            if (spnColorEdt.getSelectedItemId() <= 0){
                                                                                spnColorEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                alerta = alertaErro();
                                                                                alerta.show();
                                                                                spnColorEdt.setFocusable(true);
                                                                            }else {
                                                                                spnColorEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                colorEdt = spnColorEdt.getSelectedItem().toString();
                                                                                if (rgWeeksEdt.getCheckedRadioButtonId() == -1){
                                                                                    rgWeeksEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                    alerta = alertaErro();
                                                                                    alerta.show();
                                                                                    rgWeeksEdt.setFocusable(true);
                                                                                }else {
                                                                                    rgWeeksEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                    int idWeeks = rgWeeksEdt.getCheckedRadioButtonId();
                                                                                    btnWeeksEdt = (RadioButton)findViewById(idWeeks);
                                                                                    weeksEdt = btnWeeksEdt.getText().toString();
                                                                                    if (txtStartDateEdt.getText().toString().length() != 10){
                                                                                        txtStartDateEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                        alerta = alertaErro();
                                                                                        alerta.show();
                                                                                        txtStartDateEdt.setFocusable(true);
                                                                                    }else {
                                                                                        txtStartDateEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                        startDateEdt = txtStartDateEdt.getText().toString();
                                                                                        if (txtReturnDateEdt.getText().toString().length() != 10){
                                                                                            txtReturnDateEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                            alerta = alertaErro();
                                                                                            alerta.show();
                                                                                            txtReturnDateEdt.setFocusable(true);
                                                                                        }else {
                                                                                            txtReturnDateEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                            returnDateEdt = txtReturnDateEdt.getText().toString();
                                                                                            if (spnWeeklyRateEdt.getSelectedItemId() <= 0 ){
                                                                                                spnWeeklyRateEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                alerta = alertaErro();
                                                                                                alerta.show();
                                                                                                spnWeeklyRateEdt.setFocusable(true);
                                                                                            }else {
                                                                                                spnWeeklyRateEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                weeklyRateEdt = spnWeeklyRateEdt.getSelectedItem().toString();
                                                                                                if (spnPmtDayEdt.getSelectedItemId() <= 0){
                                                                                                    spnPmtDayEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                    alerta = alertaErro();
                                                                                                    alerta.show();
                                                                                                    spnPmtDayEdt.setFocusable(true);
                                                                                                }else {
                                                                                                    spnPmtDayEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                    pmtDayEdt = spnPmtDayEdt.getSelectedItem().toString();
                                                                                                    if (spnFirstWeekEdt.getSelectedItemId() <= 0){
                                                                                                        spnFirstWeekEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                        alerta = alertaErro();
                                                                                                        alerta.show();
                                                                                                        spnFirstWeekEdt.setFocusable(true);
                                                                                                    }else {
                                                                                                        spnFirstWeekEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                        firtsWeekEdt = spnFirstWeekEdt.getSelectedItem().toString();
                                                                                                        if (spnSecDepEdt.getSelectedItemId() <= 0){
                                                                                                            spnSecDepEdt.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                            alerta = alertaErro();
                                                                                                            alerta.show();
                                                                                                            spnSecDepEdt.setFocusable(true);
                                                                                                        }else {
                                                                                                            spnSecDepEdt.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                            secDepEdt = spnSecDepEdt.getSelectedItem().toString();

                                                                                                            notesEdt = txtNotesEdt.getText().toString();

                                                                                                            List<String> novoAcessorioLista = new ArrayList<String>();

                                                                                                            if (chkHelmetEdt.isChecked()){
                                                                                                                novoAcessorioLista.add(helmet);
                                                                                                            }
                                                                                                            if (chkPhoneHolderEdt.isChecked()){
                                                                                                                novoAcessorioLista.add(phoneHolder);
                                                                                                            }
                                                                                                            if (chkRemovableRearLightEdt.isChecked()){
                                                                                                                novoAcessorioLista.add(removableRearLight);
                                                                                                            }
                                                                                                            if (chkRemovableFrontLightEdt.isChecked()){
                                                                                                                novoAcessorioLista.add(removableFrontLight);
                                                                                                            }
                                                                                                            if (chkFixedFrontLightEdt.isChecked()){
                                                                                                                novoAcessorioLista.add(fixedFrontLight);
                                                                                                            }
                                                                                                            if (chkFixedRearLightEdt.isChecked()){
                                                                                                                novoAcessorioLista.add(fixedRearLight);
                                                                                                            }

                                                                                                            String acessEdtFinal = novoAcessorioLista.toString();

                                                                                                            acessEdtFinal = acessEdtFinal.replace("[", "");
                                                                                                            acessEdtFinal = acessEdtFinal.replace("]", "");

                                                                                                            passportPhoto = "null";

                                                                                                            Contract contract = new Contract();

                                                                                                            contract.setIdContract(contractEditado.getIdContract());
                                                                                                            contract.setFullName(fullNameEdt);
                                                                                                            contract.setPassportNumber(passportNumberEdt);
                                                                                                            contract.setPassportCountry(passportCountryEdt);
                                                                                                            contract.setDateBirth(dateBirthEdt);
                                                                                                            contract.setSexo(sexoEdt);
                                                                                                            contract.setAddress(addressEdt);
                                                                                                            contract.setSuburb(suburbEdt);
                                                                                                            contract.setState(stateEdt);
                                                                                                            contract.setPostcode(postCodeEdt);
                                                                                                            contract.setEmail(emailEdt);
                                                                                                            contract.setSchool(schoolEdt);
                                                                                                            contract.setVisaAgency(visaAgencyEdt);
                                                                                                            contract.setAustralianNumber(austrNumberEdt);
                                                                                                            contract.setWhatsApp(whatsNumberEdt);
                                                                                                            contract.setSerialNumber(serialNumberEdt);
                                                                                                            contract.setModel(modelEdt);
                                                                                                            contract.setColor(colorEdt);
                                                                                                            contract.setNumberWeeks(weeksEdt);
                                                                                                            contract.setWeeklyRate(weeklyRateEdt);
                                                                                                            contract.setStartDate(startDateEdt);
                                                                                                            contract.setReturnDate(returnDateEdt);
                                                                                                            contract.setPmtDay(pmtDayEdt);
                                                                                                            contract.setFirstWeek(firtsWeekEdt);
                                                                                                            contract.setSecDep(secDepEdt);
                                                                                                            contract.setAccessoriesIncluded(acessEdtFinal);
                                                                                                            contract.setNotes(notesEdt);

                                                                                                            BDContract bdContract = new BDContract(AllListActivity.this);

                                                                                                            bdContract.atualizar(contract);

                                                                                                            sucesso = true;

                                                                                                            if (!sucesso) {
                                                                                                                Snackbar.make(view, "Contract not saved " + accessories, Snackbar.LENGTH_LONG)
                                                                                                                        .setAction("Action", null).show();
                                                                                                            } else {

                                                                                                                int idContr = contractEditado.getIdContract();

                                                                                                                BDContract bdContract2 = new BDContract(AllListActivity.this);

                                                                                                                Log.d("claudio", "O id que chamo no banco é: " + idContr);

                                                                                                                c = bdContract2.buscarPorId(idContr);

                                                                                                                tvFullNameEdt.setText("Full Name: \n" + c.getFullName());
                                                                                                                tvDateBirthEdt.setText("Date of Burth: \n" + c.getDateBirth());
                                                                                                                tvGenderEdt.setText("Gender: \n" + c.getSexo());
                                                                                                                tvPassportNumberEdt.setText("Passport Num.: \n" + c.getPassportNumber());
                                                                                                                tvPassportCountryEdt.setText("Passport Country: \n" + c.getPassportCountry());
                                                                                                                tvEmailClientEdt.setText("E-mail: \n" + c.getEmail());
                                                                                                                tvAdressEdt.setText("Adress: \n" + c.getAddress());
                                                                                                                tvSuburbEdt.setText("Suburb: \n" + c.getSuburb());
                                                                                                                tvStateEdt.setText("State: \n" + c.getState());
                                                                                                                tvPostCodeEdt.setText("Post Code: \n" + c.getPostcode());
                                                                                                                tvSchoolEdt.setText("English / VET School: \n" + c.getSchool());
                                                                                                                tvVisaAgencyEdt.setText("Visa Agency: \n" + c.getVisaAgency());
                                                                                                                tvAustNumberEdt.setText("Australian Mobile Num.: \n" + c.getAustralianNumber());
                                                                                                                tvWhatsNumberEdt.setText("WhatsApp Number: \n" + c.getWhatsApp());
                                                                                                                tvSerieNumberEdt.setText("Serial Number: \n" + c.getSerialNumber());
                                                                                                                tvModelEdt.setText("Model: \n" + c.getModel());
                                                                                                                tvColorEdt.setText("Color: \n" + c.getColor());
                                                                                                                tvStartDateEdt.setText("Contract Start Date: \n" + c.getStartDate());
                                                                                                                tvReturnDateEdt.setText("Equipment Return Date: \n" + c.getReturnDate());
                                                                                                                tvNumWeeksEdt.setText("Num. of Weeks: \n" + c.getNumberWeeks());
                                                                                                                tvWeeklyRateEdt.setText("Weekly Rate: \n" + c.getWeeklyRate());
                                                                                                                tvPmtDayEdt.setText("Rent payment day: \n" + c.getPmtDay());
                                                                                                                tvFirstWeekEdt.setText("1st Week Rent Paid: \n" + c.getFirstWeek());
                                                                                                                tvSecDepEdt.setText("Security Deposit paid: \n" + c.getSecDep());
                                                                                                                tvAccessoriesEdt.setText("Accessories included in this rent: \n" + c.getAccessoriesIncluded());
                                                                                                                tvNotesEdt.setText("Notes: \n" + c.getNotes());


                                                                                                                findViewById(R.id.includeassina_edt_all).setVisibility(View.VISIBLE);
                                                                                                                findViewById(R.id.includecliente_edt_all).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includebike_edt_all).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includemain_all).setVisibility(View.INVISIBLE);
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }//fechamento do elseEDT 1
            }
        });


        tvFullName = (TextView)findViewById(R.id.tvFullName);
        tvDateBirth = (TextView)findViewById(R.id.tvDateBirth);
        tvGender = (TextView)findViewById(R.id.tvGender);
        tvPassportNumber = (TextView)findViewById(R.id.tvPassportNum);
        tvPassportCountry = (TextView)findViewById(R.id.tvPassportCountry);
        tvEmailClient = (TextView)findViewById(R.id.tvEmailClient);
        tvAdress = (TextView)findViewById(R.id.tvAdress);
        tvSuburb = (TextView)findViewById(R.id.tvSuburb);
        tvState = (TextView)findViewById(R.id.tvState);
        tvPostCode = (TextView)findViewById(R.id.tvPostCode);
        tvSchool = (TextView) findViewById(R.id.tvScholl);
        tvVisaAgency = (TextView)findViewById(R.id.tvVisaAgency);
        tvAustNumber = (TextView)findViewById(R.id.tvAustNumber);
        tvWhatsNumber = (TextView)findViewById(R.id.tvWhatsNumber);
        tvSerieNumber = (TextView)findViewById(R.id.tvSerieNumber);
        tvModel = (TextView)findViewById(R.id.tvModel);
        tvColor = (TextView)findViewById(R.id.tvColor);
        tvStartDate = (TextView)findViewById(R.id.tvStartDate);
        tvReturnDate = (TextView)findViewById(R.id.tvReturnDate);
        tvNumWeeks = (TextView)findViewById(R.id.tvNumWeeks);
        tvWeeklyRate = (TextView)findViewById(R.id.tvWeeklyRate);
        tvPmtDay = (TextView)findViewById(R.id.tvPmtDay);
        tvFirstWeek = (TextView)findViewById(R.id.tvFirstWeek);
        tvSecDep = (TextView)findViewById(R.id.tvSecDep);
        tvAccessories = (TextView)findViewById(R.id.tvAccessorios);
        tvNotes = (TextView)findViewById(R.id.tvNotes);

        tvFullNameEdt = (TextView)findViewById(R.id.tvFullName_edt);
        tvDateBirthEdt = (TextView)findViewById(R.id.tvDateBirth_edt);
        tvGenderEdt = (TextView)findViewById(R.id.tvGender_edt);
        tvPassportNumberEdt = (TextView)findViewById(R.id.tvPassportNum_edt);
        tvPassportCountryEdt = (TextView)findViewById(R.id.tvPassportCountry_edt);
        tvEmailClientEdt = (TextView)findViewById(R.id.tvEmailClient_edt);
        tvAdressEdt = (TextView)findViewById(R.id.tvAdress_edt);
        tvSuburbEdt = (TextView)findViewById(R.id.tvSuburb_edt);
        tvStateEdt = (TextView)findViewById(R.id.tvState_edt);
        tvPostCodeEdt = (TextView)findViewById(R.id.tvPostCode_edt);
        tvSchoolEdt = (TextView) findViewById(R.id.tvScholl_edt);
        tvVisaAgencyEdt = (TextView)findViewById(R.id.tvVisaAgency_edt);
        tvAustNumberEdt = (TextView)findViewById(R.id.tvAustNumber_edt);
        tvWhatsNumberEdt = (TextView)findViewById(R.id.tvWhatsNumber_edt);
        tvSerieNumberEdt = (TextView)findViewById(R.id.tvSerieNumber_edt);
        tvModelEdt = (TextView)findViewById(R.id.tvModel_edt);
        tvColorEdt = (TextView)findViewById(R.id.tvColor_edt);
        tvStartDateEdt = (TextView)findViewById(R.id.tvStartDate_edt);
        tvReturnDateEdt = (TextView)findViewById(R.id.tvReturnDate_edt);
        tvNumWeeksEdt = (TextView)findViewById(R.id.tvNumWeeks_edt);
        tvWeeklyRateEdt = (TextView)findViewById(R.id.tvWeeklyRate_edt);
        tvPmtDayEdt = (TextView)findViewById(R.id.tvPmtDay_edt);
        tvFirstWeekEdt = (TextView)findViewById(R.id.tvFirstWeek_edt);
        tvSecDepEdt = (TextView)findViewById(R.id.tvSecDep_edt);
        tvAccessoriesEdt = (TextView)findViewById(R.id.tvAccessorios_edt);
        tvNotesEdt = (TextView)findViewById(R.id.tvNotes_edt);


        chkTerm1 = (CheckBox)findViewById(R.id.chkTerm1);
        chkTerm3 = (CheckBox)findViewById(R.id.chkTerm3);


        Button btnNextEdt = (Button)findViewById(R.id.btnProximo_edt);
        btnNextEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain_all).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente_edt_all).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike_edt_all).setVisibility(View.VISIBLE);
            }
        });

        Button btnVoltarEdt = (Button)findViewById(R.id.btnVoltar_edt);
        btnVoltarEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain_all).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente_edt_all).setVisibility(View.VISIBLE);
                findViewById(R.id.includebike_edt_all).setVisibility(View.INVISIBLE);
            }
        });



        Button btnAssinaEdt = (Button)findViewById(R.id.btnAssina_edt);
        btnAssinaEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterAll.adicionarContract(c);
                finish();
                Intent it = new Intent(AllListActivity.this, AllListActivity.class);
                startActivity(it);
            }
        });

        configurarRecyclerAll();
    }

    @Override
    public void onBackPressed(){
        final AlertDialog alertDialog;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure want to close the app?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            finish();
            Intent it = new Intent(AllListActivity.this, AllListActivity.class);
            startActivity(it);
            //Toast.makeText(MainActivity.this, "teste de clique de botão", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_list){
            finish();
            Intent it = new Intent(AllListActivity.this, MainActivity.class);
            startActivity(it);
            //Toast.makeText(AllListActivity.this, "teste de clique de botão listar todos", Toast.LENGTH_LONG).show();
            return true;
        }
        else if (id == R.id.action_list_all){
            finish();
            Intent it = new Intent(AllListActivity.this, AllListActivity.class);
            startActivity(it);
            //Toast.makeText(AllListActivity.this, "teste de clique de botão listar todos", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public AlertDialog alertaErro(){
        AlertDialog envio;

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("Please check the red fields");
        builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });

        envio = builder.create();

        return envio;
    }

    RecyclerView recyclerViewAll;
    private ContractAllAdapter adapterAll;

    private void configurarRecyclerAll(){
        recyclerViewAll = (RecyclerView)findViewById(R.id.recyclerViewAll);
        //Configurando o gerenciador de layout para ser uma lista
        LinearLayoutManager layoutManagerAll = new LinearLayoutManager(this);
        recyclerViewAll.setLayoutManager(layoutManagerAll);

        BDContract bdContractAll = new BDContract(this);
        adapterAll = new ContractAllAdapter(bdContractAll.buscarTodos());
        recyclerViewAll.setAdapter(adapterAll);
        recyclerViewAll.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void usarCamera(View view, String nomeArquivo){

        File diretorioPass = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        Log.d("claudio", "O valor do diretorioPass no usarCamera é "+ diretorioPass.toString());

        File imagemPass = new File(diretorioPass.getPath() + "/" + nomeArquivo + ".jpg");
        uri = Uri.fromFile(imagemPass);

//        Log.d("claudio", "O valor do uriPassport é - aqui na Camera "+ uri.toString());

        Intent intentCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intentCam.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intentCam, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA){
            if (resultCode == RESULT_OK){
                Intent novaIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                sendBroadcast(novaIntent);
                Bundle extras = data.getExtras();
                Bitmap fotoBitmap = (Bitmap)extras.get("data");
                ivPhotoPassport.setImageBitmap(fotoBitmap);

                Uri uriPass = getImagemUri(getApplicationContext(), fotoBitmap);
                File fileFinal = new File(getRealPathFromUri(uriPass));
                caminhoPassaport = fileFinal.getPath();
                Log.d("claudio", "caminhopPassport é:"+ caminhoPassaport);

                Log.d("claudio", "o fileFinal que é o caminho real da imagem é: "+ fileFinal);

            }
        }
    }

    public Uri getImagemUri(Context inContext, Bitmap inImagem){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImagem.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImagem, caminhoPassaport, null);
        return Uri.parse(path);
    }

    public String getRealPathFromUri(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return  cursor.getString(idx);
    }

}
