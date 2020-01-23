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
import android.print.PrintManager;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.util.Log;
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

import com.google.android.material.appbar.AppBarLayout;
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

public class MainActivity extends AppCompatActivity {
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
    String userName = "admin";
    Bitmap assBitmap;
    byte[] assinatura;
    boolean sucesso;

    String fullName, dateBirth, passportNumber, passportCountry, sexo, address;
    String suburb, state, postCode, email, school, visaAgency, austrNumber, whatsNumber;
    String serialNumber, model, color, weeks, startDate, returnDate, weeklyRate;
    String pmtDay, firtsWeek, secDep, accessories, notes, passportPhoto;
    int termo1, termo3, idCont;

    //Variaveis de edição
    String fullNameEdt, dateBirthEdt, passportNumberEdt, passportCountryEdt, sexoEdt, addressEdt;
    String suburbEdt, stateEdt, postCodeEdt, emailEdt, schoolEdt, visaAgencyEdt, austrNumberEdt, whatsNumberEdt;
    String serialNumberEdt, modelEdt, colorEdt, weeksEdt, startDateEdt, returnDateEdt, weeklyRateEdt;
    String pmtDayEdt, firtsWeekEdt, secDepEdt, notesEdt;

    EditText txtFullName, txtDateBirth, txtPassportNumber, txtPassportCountry;
    EditText txtAddress, txtSuburb, txtState, txtPostCode, txtEmail, txtSchool;
    EditText txtVisaAgency, txtAustrNumber, txtWhatsappNumber, txtSerialNumber;
    EditText txtStartDate, txtReturnDate, txtNotes;

    Spinner spnModel, spnColor, spnWeeklyRate, spnPmtDay, spnFirstWeek, spnSecDep;
    CheckBox chkHelmet, chkPhoneHolder, chkRemovableFrontLight;
    CheckBox chkRemovableRearLight, chkFixedFrontLight, chkFixedRearLight;
    CheckBox chkTerm3, chkTerm1;

    RadioGroup rgWeeks, rgSexo;
    RadioButton btnSexo, btnWeeks;

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

    LinearLayout mContent, mPdfButton;
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        barraSup = getSupportActionBar();



        mContent = (LinearLayout)findViewById(R.id.assinatura);
        mSig = new CaptureSignatureView(this, null);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else { ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSAO_REQUEST);
            }
        }


        //Campos Form Cliente
        txtFullName = (EditText)findViewById(R.id.txtNome);
        txtFullName.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtDateBirth = (EditText)findViewById(R.id.txtDateBirth);
        txtDateBirth.addTextChangedListener(MaskEditUtil.mask(txtDateBirth, MaskEditUtil.FORMAT_DATE));
        txtPassportNumber = (EditText)findViewById(R.id.txtPassportNumber);
        txtPassportNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        txtPassportCountry = (EditText)findViewById(R.id.txtPassportCountry);
        txtPassportCountry.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        rgSexo = (RadioGroup)findViewById(R.id.rgSexo);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        txtAddress.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtSuburb = (EditText)findViewById(R.id.txtSuburb);
        txtSuburb.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtState = (EditText)findViewById(R.id.txtState);
        txtState.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtPostCode = (EditText)findViewById(R.id.txtPostCode);
        txtPostCode.addTextChangedListener(MaskEditUtil.mask(txtPostCode, MaskEditUtil.FORMAT_POST_CODE));
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtEmail.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtSchool = (EditText)findViewById(R.id.txtSchool);
        txtSchool.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtVisaAgency = (EditText)findViewById(R.id.txtVisaAgency);
        txtVisaAgency.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        txtAustrNumber = (EditText)findViewById(R.id.txtAustrNumber);
        txtAustrNumber.addTextChangedListener(MaskEditUtil.mask(txtAustrNumber, MaskEditUtil.FORMAT_PHONE));
        txtWhatsappNumber = (EditText)findViewById(R.id.txtWhatsapp);
        txtWhatsappNumber.addTextChangedListener(MaskEditUtil.mask(txtWhatsappNumber, MaskEditUtil.FORMAT_PHONE_WA));

        //Campos Form Bike
        txtSerialNumber = (EditText)findViewById(R.id.txtSerialNumber);
        txtSerialNumber.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        spnModel = (Spinner)findViewById(R.id.spnModel);
        spnColor = (Spinner)findViewById(R.id.spnColor);
        rgWeeks = (RadioGroup)findViewById(R.id.rgWeeks);
        txtStartDate = (EditText)findViewById(R.id.txtStartDate);
        txtStartDate.addTextChangedListener(MaskEditUtil.mask(txtStartDate, MaskEditUtil.FORMAT_DATE));
        txtReturnDate = (EditText)findViewById(R.id.txtReturnDate);
        txtReturnDate.addTextChangedListener(MaskEditUtil.mask(txtReturnDate, MaskEditUtil.FORMAT_DATE));
        spnWeeklyRate = (Spinner)findViewById(R.id.spnWeeklyRate);
        spnPmtDay = (Spinner)findViewById(R.id.spnPmtDay);
        spnFirstWeek = (Spinner)findViewById(R.id.spnFirstWeek);
        spnSecDep = (Spinner)findViewById(R.id.spnSecDep);
        txtNotes = (EditText)findViewById(R.id.txtNotes);
        txtNotes.setFilters(new InputFilter[] {new InputFilter.AllCaps()});

        chkHelmet = (CheckBox)findViewById(R.id.chkHelmet);
        chkPhoneHolder = (CheckBox)findViewById(R.id.chkPhoneHolder);
        chkRemovableFrontLight = (CheckBox)findViewById(R.id.chkRemovableFrontLight);
        chkRemovableRearLight = (CheckBox)findViewById(R.id.chkRemovableRearLight);
        chkFixedFrontLight = (CheckBox)findViewById(R.id.chkFixedFrontLight);
        chkFixedRearLight = (CheckBox)findViewById(R.id.chkFixedRearLight);


        Intent intent = getIntent();
        if (intent.hasExtra("contract_edt")){
            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);


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

            BDContract bdContr = new BDContract(MainActivity.this);
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
            spnModelEdt.setSelection(getIndex(spnModel, contractEditado.getModel()));
            spnColorEdt.setSelection(getIndex(spnColor, contractEditado.getColor()));
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
            spnWeeklyRateEdt.setSelection(getIndex(spnWeeklyRate, contractEditado.getWeeklyRate()));
            spnPmtDayEdt.setSelection(getIndex(spnPmtDay, contractEditado.getPmtDay()));
            spnFirstWeekEdt.setSelection(getIndex(spnFirstWeek, contractEditado.getFirstWeek()));
            spnSecDepEdt.setSelection(getIndex(spnSecDep, contractEditado.getSecDep()));
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

            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.includecliente_edt).setVisibility(View.INVISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
            findViewById(R.id.includeassina_viu).setVisibility(View.VISIBLE);


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

            findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
            findViewById(R.id.fab).setVisibility(View.INVISIBLE);
            findViewById(R.id.includefoto_passaporte).setVisibility(View.VISIBLE);

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

                usarCamera(findViewById(R.id.main_activity), nomeArquivoPho);

                Button btnSalvar = (Button)findViewById(R.id.btnVoltar_photo);
                btnSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean photo = false;
                        final BDContract bdPassaport = new BDContract(MainActivity.this);
                        photo = bdPassaport.salvarPassaport(caminhoPassaport, idCont);

                        if (!photo){
                            Toast.makeText(MainActivity.this, "Algo aconteceu e a foto não foi salva!", Toast.LENGTH_LONG).show();
                            finish();
                            Intent it = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(it);
                        } else{
                            Toast.makeText(MainActivity.this, "Photo saved on BaseDate!", Toast.LENGTH_LONG).show();
                            finish();
                            Intent it = new Intent(MainActivity.this, MainActivity.class);
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
                        Intent it = new Intent(MainActivity.this, MainActivity.class);
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
                Intent it = new Intent(MainActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        Button btnGerarPdf = (Button)findViewById(R.id.btnPdf_viu);
        btnGerarPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Implementar metodo para gerar PDF

                findViewById(R.id.includeassina_viu).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecontract_pdf).setVisibility(View.VISIBLE);

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


                BDContract bdContr2 = new BDContract(MainActivity.this);
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
                printManager.print("Contract", new ViewPrintAdapter(nomePDF2,MainActivity.this,
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

                                                                                                            BDContract bdContract = new BDContract(MainActivity.this);

                                                                                                            bdContract.atualizar(contract);

                                                                                                            sucesso = true;

                                                                                                            if (!sucesso) {
                                                                                                                Snackbar.make(view, "Contract not saved " + accessories, Snackbar.LENGTH_LONG)
                                                                                                                        .setAction("Action", null).show();
                                                                                                            } else {

                                                                                                                int idContr = contractEditado.getIdContract();

                                                                                                                BDContract bdContract2 = new BDContract(MainActivity.this);

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

                                                                                                                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includecliente).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includeassina_edt).setVisibility(View.VISIBLE);
                                                                                                                findViewById(R.id.includecliente_edt).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
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


        Button btnNext = (Button)findViewById(R.id.btnProximo);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike).setVisibility(View.VISIBLE);
                findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
            }
        });

        Button btnNextEdt = (Button)findViewById(R.id.btnProximo_edt);
        btnNextEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente_edt).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike_edt).setVisibility(View.VISIBLE);
            }
        });

        Button btnVoltar = (Button)findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente_edt).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
            }
        });

        Button btnVoltarEdt = (Button)findViewById(R.id.btnVoltar_edt);
        btnVoltarEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente).setVisibility(View.INVISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente_edt).setVisibility(View.VISIBLE);
                findViewById(R.id.includebike_edt).setVisibility(View.INVISIBLE);
            }
        });

        Button btnLimpar = (Button)findViewById(R.id.btnLimpar);
        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSig.ClearCanvas();
            }
        });

        Button btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (txtFullName.getText().toString().length() <= 5){
                    txtFullName.setBackgroundColor(Color.parseColor("#ff0000"));
                    alerta = alertaErro();
                    alerta.show();
                    txtFullName.setFocusable(true);
                    findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                    findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                }else{
                    txtFullName.setBackgroundColor(Color.parseColor("#9CF3E580"));
                    fullName = txtFullName.getText().toString();
                    if (txtDateBirth.getText().toString().length() != 10){
                        txtDateBirth.setBackgroundColor(Color.parseColor("#ff0000"));
                        alerta = alertaErro();
                        alerta.show();
                        txtDateBirth.setFocusable(true);
                        findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                        findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                    }else{
                        txtDateBirth.setBackgroundColor(Color.parseColor("#9CF3E580"));
                        dateBirth = txtDateBirth.getText().toString();
                        if (txtPassportNumber.getText().toString().length() <= 3){
                            txtPassportNumber.setBackgroundColor(Color.parseColor("#ff0000"));
                            alerta = alertaErro();
                            alerta.show();
                            txtPassportNumber.setFocusable(true);
                            findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                            findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                        }else {
                            txtPassportNumber.setBackgroundColor(Color.parseColor("#9CF3E580"));
                            passportNumber = txtPassportNumber.getText().toString();
                            if (txtPassportCountry.getText().toString().length() <= 1){
                                txtPassportCountry.setBackgroundColor(Color.parseColor("#ff0000"));
                                alerta = alertaErro();
                                alerta.show();
                                txtPassportCountry.setFocusable(true);
                                findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                            }else {
                                txtPassportCountry.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                passportCountry = txtPassportCountry.getText().toString();
                                if (rgSexo.getCheckedRadioButtonId() == -1){
                                    rgSexo.setBackgroundColor(Color.parseColor("#ff0000"));
                                    alerta = alertaErro();
                                    alerta.show();
                                    rgSexo.setFocusable(true);
                                    findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                    findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                }else {
                                    rgSexo.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    int idSexo = rgSexo.getCheckedRadioButtonId();
                                    btnSexo = (RadioButton)findViewById(idSexo);
                                    sexo = btnSexo.getText().toString();
                                    if (txtAddress.getText().toString().length() <= 4){
                                        txtAddress.setBackgroundColor(Color.parseColor("#ff0000"));
                                        alerta = alertaErro();
                                        alerta.show();
                                        txtAddress.setFocusable(true);
                                        findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                        findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                    }else {
                                        txtAddress.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                        address = txtAddress.getText().toString();
                                        if (txtSuburb.getText().toString().length() <= 3){
                                            txtSuburb.setBackgroundColor(Color.parseColor("#ff0000"));
                                            alerta = alertaErro();
                                            alerta.show();
                                            txtSuburb.setFocusable(true);
                                            findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                            findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                        }else {
                                            txtSuburb.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                            suburb = txtSuburb.getText().toString();
                                            if (txtState.getText().toString().length() <= 1){
                                                txtState.setBackgroundColor(Color.parseColor("#ff0000"));
                                                alerta = alertaErro();
                                                alerta.show();
                                                txtState.setFocusable(true);
                                                findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                            }else {
                                                txtState.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                state = txtState.getText().toString();
                                                if (txtPostCode.getText().toString().length() <= 3){
                                                    txtPostCode.setBackgroundColor(Color.parseColor("#ff0000"));
                                                    alerta = alertaErro();
                                                    alerta.show();
                                                    txtPostCode.setFocusable(true);
                                                    findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                                    findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                }else {
                                                    txtPostCode.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                    postCode = txtPostCode.getText().toString();
                                                    if (txtEmail.getText().toString().length() <= 6){
                                                        txtEmail.setBackgroundColor(Color.parseColor("#ff0000"));
                                                        alerta = alertaErro();
                                                        alerta.show();
                                                        txtEmail.setFocusable(true);
                                                        findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                                        findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                    }else {
                                                        txtEmail.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                        email = txtEmail.getText().toString();
                                                        if (txtSchool.getText().toString().length() <=2){
                                                            txtSchool.setBackgroundColor(Color.parseColor("#ff0000"));
                                                            alerta = alertaErro();
                                                            alerta.show();
                                                            txtSchool.setFocusable(true);
                                                            findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                                            findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                        }else {
                                                            txtSchool.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                            school = txtSchool.getText().toString();
                                                            if (txtVisaAgency.getText().toString().length() <= 2){
                                                                txtVisaAgency.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                alerta = alertaErro();
                                                                alerta.show();
                                                                txtVisaAgency.setFocusable(true);
                                                                findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                                                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                            }else {
                                                                txtVisaAgency.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                visaAgency = txtVisaAgency.getText().toString();
                                                                if (txtAustrNumber.getText().toString().length() <= 17){
                                                                    txtAustrNumber.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                    alerta = alertaErro();
                                                                    alerta.show();
                                                                    txtAustrNumber.setFocusable(true);
                                                                    findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                                                                    findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                                }else {
                                                                    txtAustrNumber.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                    austrNumber = txtAustrNumber.getText().toString();
                                                                    whatsNumber = txtWhatsappNumber.getText().toString();
                                                                    if (txtSerialNumber.getText().toString().length() <= 7){
                                                                        txtSerialNumber.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                        alerta = alertaErro();
                                                                        alerta.show();
                                                                        txtSerialNumber.setFocusable(true);
                                                                    }else {
                                                                        txtSerialNumber.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                        serialNumber = txtSerialNumber.getText().toString();
                                                                        if (spnModel.getSelectedItemId() <= 0){
                                                                            spnModel.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                            alerta = alertaErro();
                                                                            alerta.show();
                                                                            spnModel.setFocusable(true);
                                                                        }else {
                                                                            spnModel.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                            model = spnModel.getSelectedItem().toString();
                                                                            if (spnColor.getSelectedItemId() <= 0){
                                                                                spnColor.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                alerta = alertaErro();
                                                                                alerta.show();
                                                                                spnColor.setFocusable(true);
                                                                            }else {
                                                                                spnColor.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                color = spnColor.getSelectedItem().toString();
                                                                                if (rgWeeks.getCheckedRadioButtonId() == -1){
                                                                                    rgWeeks.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                    alerta = alertaErro();
                                                                                    alerta.show();
                                                                                    rgWeeks.setFocusable(true);
                                                                                }else {
                                                                                    rgWeeks.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                    int idWeeks = rgWeeks.getCheckedRadioButtonId();
                                                                                    btnWeeks = (RadioButton)findViewById(idWeeks);
                                                                                    weeks = btnWeeks.getText().toString();
                                                                                    if (txtStartDate.getText().toString().length() != 10){
                                                                                        txtStartDate.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                        alerta = alertaErro();
                                                                                        alerta.show();
                                                                                        txtStartDate.setFocusable(true);
                                                                                    }else {
                                                                                        txtStartDate.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                        startDate = txtStartDate.getText().toString();
                                                                                        if (txtReturnDate.getText().toString().length() != 10){
                                                                                            txtReturnDate.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                            alerta = alertaErro();
                                                                                            alerta.show();
                                                                                            txtReturnDate.setFocusable(true);
                                                                                        }else {
                                                                                            txtReturnDate.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                            returnDate = txtReturnDate.getText().toString();
                                                                                            if (spnWeeklyRate.getSelectedItemId() <= 0 ){
                                                                                                spnWeeklyRate.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                alerta = alertaErro();
                                                                                                alerta.show();
                                                                                                spnWeeklyRate.setFocusable(true);
                                                                                            }else {
                                                                                                spnWeeklyRate.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                weeklyRate = spnWeeklyRate.getSelectedItem().toString();
                                                                                                if (spnPmtDay.getSelectedItemId() <= 0){
                                                                                                    spnPmtDay.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                    alerta = alertaErro();
                                                                                                    alerta.show();
                                                                                                    spnPmtDay.setFocusable(true);
                                                                                                }else {
                                                                                                    spnPmtDay.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                    pmtDay = spnPmtDay.getSelectedItem().toString();
                                                                                                    if (spnFirstWeek.getSelectedItemId() <= 0){
                                                                                                        spnFirstWeek.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                        alerta = alertaErro();
                                                                                                        alerta.show();
                                                                                                        spnFirstWeek.setFocusable(true);
                                                                                                    }else {
                                                                                                        spnFirstWeek.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                        firtsWeek = spnFirstWeek.getSelectedItem().toString();
                                                                                                        if (spnSecDep.getSelectedItemId() <= 0){
                                                                                                            spnSecDep.setBackgroundColor(Color.parseColor("#ff0000"));
                                                                                                            alerta = alertaErro();
                                                                                                            alerta.show();
                                                                                                            spnSecDep.setFocusable(true);
                                                                                                        }else {
                                                                                                            spnSecDep.setBackgroundColor(Color.parseColor("#9CF3E580"));
                                                                                                            secDep = spnSecDep.getSelectedItem().toString();

                                                                                                            notes = txtNotes.getText().toString();

                                                                                                            List<String> acessorioLista =  new ArrayList<String>();

                                                                                                            if (chkHelmet.isChecked()){
                                                                                                                acessorioLista.add(helmet);
                                                                                                            }
                                                                                                            if (chkPhoneHolder.isChecked()){
                                                                                                                acessorioLista.add(phoneHolder);
                                                                                                            }
                                                                                                            if (chkRemovableRearLight.isChecked()){
                                                                                                                acessorioLista.add(removableRearLight);
                                                                                                            }
                                                                                                            if (chkRemovableFrontLight.isChecked()){
                                                                                                                acessorioLista.add(removableFrontLight);
                                                                                                            }
                                                                                                            if (chkFixedRearLight.isChecked()){
                                                                                                                acessorioLista.add(fixedRearLight);
                                                                                                            }
                                                                                                            if (chkFixedFrontLight.isChecked()){
                                                                                                                acessorioLista.add(fixedFrontLight);
                                                                                                            }

                                                                                                            accessories = acessorioLista.toString();

                                                                                                            accessories = accessories.replace("[", "");
                                                                                                            accessories = accessories.replace("]", "");

                                                                                                            Contract contract = new Contract();

                                                                                                            contract.setFullName(fullName);
                                                                                                            contract.setPassportNumber(passportNumber);
                                                                                                            contract.setPassportCountry(passportCountry);
                                                                                                            contract.setDateBirth(dateBirth);
                                                                                                            contract.setSexo(sexo);
                                                                                                            contract.setAddress(address);
                                                                                                            contract.setSuburb(suburb);
                                                                                                            contract.setState(state);
                                                                                                            contract.setPostcode(postCode);
                                                                                                            contract.setEmail(email);
                                                                                                            contract.setSchool(school);
                                                                                                            contract.setVisaAgency(visaAgency);
                                                                                                            contract.setAustralianNumber(austrNumber);
                                                                                                            contract.setWhatsApp(whatsNumber);
                                                                                                            contract.setSerialNumber(serialNumber);
                                                                                                            contract.setModel(model);
                                                                                                            contract.setColor(color);
                                                                                                            contract.setNumberWeeks(weeks);
                                                                                                            contract.setWeeklyRate(weeklyRate);
                                                                                                            contract.setStartDate(startDate);
                                                                                                            contract.setReturnDate(returnDate);
                                                                                                            contract.setPmtDay(pmtDay);
                                                                                                            contract.setFirstWeek(firtsWeek);
                                                                                                            contract.setSecDep(secDep);
                                                                                                            contract.setAccessoriesIncluded(accessories);
                                                                                                            contract.setNotes(notes);
                                                                                                            contract.setUserName(userName);

                                                                                                            BDContract bdContract = new BDContract(MainActivity.this);

                                                                                                            sucesso = bdContract.inserir(contract);

                                                                                                            if (!sucesso) {
                                                                                                                Snackbar.make(view, "Contract not saved " + accessories, Snackbar.LENGTH_LONG)
                                                                                                                        .setAction("Action", null).show();
                                                                                                            } else {

                                                                                                                BDContract bdContract3 = new BDContract(MainActivity.this);

                                                                                                                Contract contr = bdContract3.retornarUltimo();


                                                                                                                c = bdContract3.buscarPorId(contr.getIdContract());

                                                                                                                tvFullName.setText("Full Name: \n" + c.getFullName());
                                                                                                                tvDateBirth.setText("Date of Burth: \n" + c.getDateBirth());
                                                                                                                tvGender.setText("Gender: \n" + c.getSexo());
                                                                                                                tvPassportNumber.setText("Passport Num.: \n" + c.getPassportNumber());
                                                                                                                tvPassportCountry.setText("Passport Country: \n" + c.getPassportCountry());
                                                                                                                tvEmailClient.setText("E-mail: \n" + c.getEmail());
                                                                                                                tvAdress.setText("Adress: \n" + c.getAddress());
                                                                                                                tvSuburb.setText("Suburb: \n" + c.getSuburb());
                                                                                                                tvState.setText("State: \n" + c.getState());
                                                                                                                tvPostCode.setText("Post Code: \n" + c.getPostcode());
                                                                                                                tvSchool.setText("English / VET School: \n" + c.getSchool());
                                                                                                                tvVisaAgency.setText("Visa Agency: \n" + c.getVisaAgency());
                                                                                                                tvAustNumber.setText("Australian Mobile Num.: \n" + c.getAustralianNumber());
                                                                                                                tvWhatsNumber.setText("WhatsApp Number: \n" + c.getWhatsApp());
                                                                                                                tvSerieNumber.setText("Serial Number: \n" + c.getSerialNumber());
                                                                                                                tvModel.setText("Model: \n" + c.getModel());
                                                                                                                tvColor.setText("Color: \n" + c.getColor());
                                                                                                                tvStartDate.setText("Contract Start Date: \n" + c.getStartDate());
                                                                                                                tvReturnDate.setText("Equipment Return Date: \n" + c.getReturnDate());
                                                                                                                tvNumWeeks.setText("Num. of Weeks: \n" + c.getNumberWeeks());
                                                                                                                tvWeeklyRate.setText("Weekly Rate: \n" + c.getWeeklyRate());
                                                                                                                tvPmtDay.setText("Rent payment day: \n" + c.getPmtDay());
                                                                                                                tvFirstWeek.setText("1st Week Rent Paid: \n" + c.getFirstWeek());
                                                                                                                tvSecDep.setText("Security Deposit paid: \n" + c.getSecDep());
                                                                                                                tvAccessories.setText("Accessories included in this rent: \n" + c.getAccessoriesIncluded());
                                                                                                                tvNotes.setText("Notes: \n" + c.getNotes());

                                                                                                                mContent.addView(mSig, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);


                                                                                                                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includecliente).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includebike).setVisibility(View.INVISIBLE);
                                                                                                                findViewById(R.id.includeassina).setVisibility(View.VISIBLE);
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
                }//fechamento do else 1

            }
        });

        Button btnAssinaEdt = (Button)findViewById(R.id.btnAssina_edt);
        btnAssinaEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.adicionarContract(c);
                finish();
                Intent it = new Intent(MainActivity.this, MainActivity.class);
                startActivity(it);
            }
        });


        Button btnAssina = (Button)findViewById(R.id.btnAssina);
        btnAssina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!chkTerm1.isChecked()) {
                    chkTerm1.setBackgroundColor(Color.parseColor("#ff0000"));
                    alerta = alertaErro();
                    alerta.show();
                    chkTerm1.setFocusable(true);
                } else {
                    chkTerm1.setBackgroundColor(Color.parseColor("#9CF3E580"));
                    termo1 = 1;
                    if (!chkTerm3.isChecked()) {
                        chkTerm3.setBackgroundColor(Color.parseColor("#ff0000"));
                        alerta = alertaErro();
                        alerta.show();
                        chkTerm3.setFocusable(true);
                    } else {
                        chkTerm3.setBackgroundColor(Color.parseColor("#9CF3E580"));
                        termo3 = 1;

                        assinatura = mSig.getBytes();
                        assBitmap = mSig.getBitmap();

                        Log.d("Claudio", "O Tamanho da Assinatura é: "+ assinatura.length);

                        if (assinatura.length <= 2500){
                            AlertDialog alertDia;
                            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Error");
                            builder.setMessage("Please signture in the field");
                            builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            alertDia = builder.create();
                            alertDia.show();
                        }else {
                            int id = c.getIdContract();
                            String nomeArquivoAss = id+"_ass_"+ System.currentTimeMillis()+".jpg";

                            //saveImageToInternalStorage(nomeArquivo, assinatura);

                            //salvaImagens(nomeArquivo, assBitmap);
                            boolean salvo;

                            Log.d("claudio", "O arquivo assinatura tem o nome :" + nomeArquivoAss);

                            saveImagemDiretorio(nomeArquivoAss, assBitmap);

                            BDContract bdC = new BDContract(MainActivity.this);

                            salvo = bdC.salvarAssinatura(nomeArquivoAss, id);

                            if (!salvo){
                                AlertDialog alertDia;
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Error");
                                builder.setMessage("Please signture in the field");
                                builder.setPositiveButton("GO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                alertDia = builder.create();
                                alertDia.show();
                            }else {
                                adapter.adicionarContract(c);
                                finish();
                                Intent it = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(it);

                            }
                        }
                    }
                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findViewById(R.id.includemain).setVisibility(View.INVISIBLE);
                findViewById(R.id.includecliente).setVisibility(View.VISIBLE);
                findViewById(R.id.fab).setVisibility(View.INVISIBLE);
                findViewById(R.id.includeassina).setVisibility(View.INVISIBLE);
            }
        });

        configurarRecycler();
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
            Intent it = new Intent(MainActivity.this, MainActivity.class);
            startActivity(it);
            //Toast.makeText(MainActivity.this, "teste de clique de botão", Toast.LENGTH_LONG).show();
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

    RecyclerView recyclerView;
    private ContractAdapter adapter;

    private void configurarRecycler(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //Configurando o gerenciador de layout para ser uma lista
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        BDContract bdContract = new BDContract(this);
        adapter = new ContractAdapter(bdContract.buscarLista());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    public void saveImageToInternalStorage(String fileName, byte[] imagem){
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(imagem);
            fos.close();
        }catch (IOException e){
            Log.d("Internal Storage", "Error writing", e);
        }
    }

    public static void salvaImagens(String fileName, Bitmap bmp){
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bytes = stream.toByteArray();
            String nomeArquivo = Environment.getExternalStorageDirectory().getAbsolutePath() + fileName;

            FileOutputStream fos = new FileOutputStream(nomeArquivo);
            fos.write(bytes);
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveImagemDiretorio(String fileName, Bitmap bmp){

        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        Log.d("claudio", "o diretorio da assinatura é:"+ diretorio.toString());

        File file = new File(diretorio + "/" + fileName);
        uriAss = Uri.fromFile(file);

        Log.d("claudio", "o uri da assinatura é:"+ uriAss.toString());

        if (file.exists()){
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        caminhoDaImagem = uriAss.getPath();
        Log.d("claudio", "caminho da assinatura é:"+ caminhoDaImagem);


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



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == PERMISSAO_REQUEST) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // A permissão foi concedida. Pode continuar
            }
            else {
                // A permissão foi negada. Precisa ver o que deve ser desabilitado
            }

            return;
        }
    }
/*
    public void printPDF(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("Contract", new ViewPrintAdapter(nomePDF,this,
                findViewById(R.id.main_activity)), null);
    }
*/
    private View getContentView() {
        return findViewById(R.id.main_activity);
    }

}
