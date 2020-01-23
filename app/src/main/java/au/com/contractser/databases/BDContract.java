package au.com.contractser.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import au.com.contractser.models.Contract;

public class BDContract {
    private SQLiteDatabase bd;

    public  BDContract(Context context) {
        BDPlunge auxBD = new BDPlunge(context);


        bd = auxBD.getWritableDatabase();
    }

    public boolean inserir (Contract contract){
        boolean teste;
        ContentValues valores = new ContentValues();
        valores.put("fullName", contract.getFullName());
        valores.put("passportNumber", contract.getPassportNumber());
        valores.put("passportCountry", contract.getPassportCountry());
        valores.put("dateBirth", contract.getDateBirth());
        valores.put("sexo", contract.getSexo());
        valores.put("address", contract.getAddress());
        valores.put("suburb", contract.getSuburb());
        valores.put("state", contract.getState());
        valores.put("postcode", contract.getPostcode());
        valores.put("email", contract.getEmail());
        valores.put("school", contract.getSchool());
        valores.put("visaAgency", contract.getVisaAgency());
        valores.put("australianNumber", contract.getAustralianNumber());
        valores.put("whatsApp", contract.getWhatsApp());
        valores.put("serialNumber", contract.getSerialNumber());
        valores.put("model", contract.getModel());
        valores.put("color", contract.getColor());
        valores.put("numberWeeks", contract.getNumberWeeks());
        valores.put("weeklyRate", contract.getWeeklyRate());
        valores.put("startDate", contract.getStartDate());
        valores.put("returnDate", contract.getReturnDate());
        valores.put("pmtDay", contract.getPmtDay());
        valores.put("firstWeek", contract.getFirstWeek());
        valores.put("secDep", contract.getSecDep());
        valores.put("accessoriesIncluded", contract.getAccessoriesIncluded());
        valores.put("notes", contract.getNotes());
        valores.put("passportPhoto", contract.getPassportPhoto());
        valores.put("assinaturaPhoto", contract.getAssinaturaPhoto());
        valores.put("userName", contract.getUserName());
        valores.put("termo1", contract.getTermo1());
        valores.put("termo2", contract.getTermo2());
        valores.put("termo3", contract.getTermo3());

       teste = bd.insert("tbl_contracts", null, valores) > 0;

        bd.close();

       return teste;


    }

    public void atualizar (Contract contract){
        ContentValues valores = new ContentValues();
        valores.put("fullName", contract.getFullName());
        valores.put("passportNumber", contract.getPassportNumber());
        valores.put("passportCountry", contract.getPassportCountry());
        valores.put("dateBirth", contract.getDateBirth());
        valores.put("sexo", contract.getSexo());
        valores.put("address", contract.getAddress());
        valores.put("suburb", contract.getSuburb());
        valores.put("state", contract.getState());
        valores.put("postcode", contract.getPostcode());
        valores.put("email", contract.getEmail());
        valores.put("school", contract.getSchool());
        valores.put("visaAgency", contract.getVisaAgency());
        valores.put("australianNumber", contract.getAustralianNumber());
        valores.put("whatsApp", contract.getWhatsApp());
        valores.put("serialNumber", contract.getSerialNumber());
        valores.put("model", contract.getModel());
        valores.put("color", contract.getColor());
        valores.put("numberWeeks", contract.getNumberWeeks());
        valores.put("weeklyRate", contract.getWeeklyRate());
        valores.put("startDate", contract.getStartDate());
        valores.put("returnDate", contract.getReturnDate());
        valores.put("pmtDay", contract.getPmtDay());
        valores.put("firstWeek", contract.getFirstWeek());
        valores.put("secDep", contract.getSecDep());
        valores.put("accessoriesIncluded", contract.getAccessoriesIncluded());
        valores.put("notes", contract.getNotes());

        Log.d("claudio", "Estou depois de setar o contrato para atualizar o id é:"+contract.getIdContract());

        bd.update("tbl_contracts", valores, "idContract = ?", new String[]{"" + contract.getIdContract()});

        bd.close();
    }

    public boolean salvarAssinatura(String arquivoPath, int id){
        String[] args = new String[]{""+id};
        ContentValues valores = new ContentValues();
       valores.put("assinaturaPhoto", arquivoPath);

       return bd.update("tbl_contracts", valores, "idContract = ?", args) > 0;
    }

    public boolean salvarPassaport(String arquivoPath, int id){
        String[] args = new String[]{""+id};
        ContentValues valores = new ContentValues();
        valores.put("passportPhoto", arquivoPath);

        return bd.update("tbl_contracts", valores, "idContract = ?", args) > 0;
    }

    public void deletar(Contract contract){

        bd.delete("tbl_contracts", "idContract =" + contract.getIdContract(), null);
    }

    public Contract buscarPorBike(String arg){
        Contract contract = new Contract();
        String[] colunas = new String[]{"idContract", "fullName", "passportNumber", "passportCountry", "dateBirth", "sexo",
                "address", "suburb", "state", "postcode", "email", "school", "visaAgency", "australianNumber", "whatsApp",
                "serialNumber", "model", "color", "numberWeeks", "weeklyRate", "startDate", "returnDate", "pmtDay", "firstWeek",
                "secDep", "accessoriesIncluded", "notes", "ativo", "fechaData", "passportPhoto", "assinaturaPhoto", "userName",
                "termo1", "termo2", "termo3"};
        String[] args = new String[]{arg};
        Cursor cursor = bd.query("tbl_contracts", colunas, "serialNumber = ?", args, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            contract.setIdContract(cursor.getInt(0));
            contract.setFullName(cursor.getString(1));
            contract.setPassportNumber(cursor.getString(2));
            contract.setPassportCountry(cursor.getString(3));
            contract.setDateBirth(cursor.getString(4));
            contract.setSexo(cursor.getString(5));
            contract.setAddress(cursor.getString(6));
            contract.setSuburb(cursor.getString(7));
            contract.setState(cursor.getString(8));
            contract.setPostcode(cursor.getString(9));
            contract.setEmail(cursor.getString(10));
            contract.setSchool(cursor.getString(11));
            contract.setVisaAgency(cursor.getString(12));
            contract.setAustralianNumber(cursor.getString(13));
            contract.setWhatsApp(cursor.getString(14));
            contract.setSerialNumber(cursor.getString(15));
            contract.setModel(cursor.getString(16));
            contract.setColor(cursor.getString(17));
            contract.setNumberWeeks(cursor.getString(18));
            contract.setWeeklyRate(cursor.getString(19));
            contract.setStartDate(cursor.getString(20));
            contract.setReturnDate(cursor.getString(21));
            contract.setPmtDay(cursor.getString(22));
            contract.setFirstWeek(cursor.getString(23));
            contract.setSecDep(cursor.getString(24));
            contract.setAccessoriesIncluded(cursor.getString(25));
            contract.setNotes(cursor.getString(26));
            contract.setAtivo(cursor.getInt(27));
            contract.setFechaData(cursor.getString(28));
            contract.setPassportPhoto(cursor.getString(29));
            contract.setAssinaturaPhoto(cursor.getString(30));
            contract.setUserName(cursor.getString(31));
            contract.setTermo1(cursor.getInt(32));
            contract.setTermo2(cursor.getInt(33));
            contract.setTermo3(cursor.getInt(34));
        }

        return contract;

    }

    public Contract buscarPorId(int id){
        Contract contract = new Contract();
        String[] colunas = new String[]{"idContract", "fullName", "passportNumber", "passportCountry", "dateBirth", "sexo",
                "address", "suburb", "state", "postcode", "email", "school", "visaAgency", "australianNumber", "whatsApp",
                "serialNumber", "model", "color", "numberWeeks", "weeklyRate", "startDate", "returnDate", "pmtDay", "firstWeek",
                "secDep", "accessoriesIncluded", "notes", "ativo", "fechaData", "passportPhoto", "assinaturaPhoto", "userName",
                "termo1", "termo2", "termo3"};
        String[] args = new String[]{""+id};
        Cursor cursor = bd.query("tbl_contracts", colunas, "idContract = ?", args, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            contract.setIdContract(cursor.getInt(0));
            contract.setFullName(cursor.getString(1));
            contract.setPassportNumber(cursor.getString(2));
            contract.setPassportCountry(cursor.getString(3));
            contract.setDateBirth(cursor.getString(4));
            contract.setSexo(cursor.getString(5));
            contract.setAddress(cursor.getString(6));
            contract.setSuburb(cursor.getString(7));
            contract.setState(cursor.getString(8));
            contract.setPostcode(cursor.getString(9));
            contract.setEmail(cursor.getString(10));
            contract.setSchool(cursor.getString(11));
            contract.setVisaAgency(cursor.getString(12));
            contract.setAustralianNumber(cursor.getString(13));
            contract.setWhatsApp(cursor.getString(14));
            contract.setSerialNumber(cursor.getString(15));
            contract.setModel(cursor.getString(16));
            contract.setColor(cursor.getString(17));
            contract.setNumberWeeks(cursor.getString(18));
            contract.setWeeklyRate(cursor.getString(19));
            contract.setStartDate(cursor.getString(20));
            contract.setReturnDate(cursor.getString(21));
            contract.setPmtDay(cursor.getString(22));
            contract.setFirstWeek(cursor.getString(23));
            contract.setSecDep(cursor.getString(24));
            contract.setAccessoriesIncluded(cursor.getString(25));
            contract.setNotes(cursor.getString(26));
            contract.setAtivo(cursor.getInt(27));
            contract.setFechaData(cursor.getString(28));
            contract.setPassportPhoto(cursor.getString(29));
            contract.setAssinaturaPhoto(cursor.getString(30));
            contract.setUserName(cursor.getString(31));
            contract.setTermo1(cursor.getInt(32));
            contract.setTermo2(cursor.getInt(33));
            contract.setTermo3(cursor.getInt(34));
        }

        cursor.close();

        return contract;
    }

    public List<Contract> buscarTodos(){
        List<Contract> list = new ArrayList<Contract>();

        String[] colunas = new String[]{"idContract", "fullName", "passportNumber", "passportCountry", "dateBirth", "sexo",
                "address", "suburb", "state", "postcode", "email", "school", "visaAgency", "australianNumber", "whatsApp",
                "serialNumber", "model", "color", "numberWeeks", "weeklyRate", "startDate", "returnDate", "pmtDay", "firstWeek",
                "secDep", "accessoriesIncluded", "notes", "ativo", "fechaData", "passportPhoto", "assinaturaPhoto", "userName",
                "termo1", "termo2", "termo3"};

        Cursor cursor = bd.query("tbl_contracts", colunas, null, null, null, null, "fullName ASC");

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Contract contract = new Contract();
                contract.setIdContract(cursor.getInt(0));
                contract.setFullName(cursor.getString(1));
                contract.setPassportNumber(cursor.getString(2));
                contract.setPassportCountry(cursor.getString(3));
                contract.setDateBirth(cursor.getString(4));
                contract.setSexo(cursor.getString(5));
                contract.setAddress(cursor.getString(6));
                contract.setSuburb(cursor.getString(7));
                contract.setState(cursor.getString(8));
                contract.setPostcode(cursor.getString(9));
                contract.setEmail(cursor.getString(10));
                contract.setSchool(cursor.getString(11));
                contract.setVisaAgency(cursor.getString(12));
                contract.setAustralianNumber(cursor.getString(13));
                contract.setWhatsApp(cursor.getString(14));
                contract.setSerialNumber(cursor.getString(15));
                contract.setModel(cursor.getString(16));
                contract.setColor(cursor.getString(17));
                contract.setNumberWeeks(cursor.getString(18));
                contract.setWeeklyRate(cursor.getString(19));
                contract.setStartDate(cursor.getString(20));
                contract.setReturnDate(cursor.getString(21));
                contract.setPmtDay(cursor.getString(22));
                contract.setFirstWeek(cursor.getString(23));
                contract.setSecDep(cursor.getString(24));
                contract.setAccessoriesIncluded(cursor.getString(25));
                contract.setNotes(cursor.getString(26));
                contract.setAtivo(cursor.getInt(27));
                contract.setFechaData(cursor.getString(28));
                contract.setPassportPhoto(cursor.getString(29));
                contract.setAssinaturaPhoto(cursor.getString(30));
                contract.setUserName(cursor.getString(31));
                contract.setTermo1(cursor.getInt(32));
                contract.setTermo2(cursor.getInt(33));
                contract.setTermo3(cursor.getInt(34));
                list.add(contract);
            }while (cursor.moveToNext());
        }

        return (list);
    }

    public List<Contract> buscarLista(){
        List<Contract> list = new ArrayList<Contract>();

        String[] colunas = new String[]{"idContract", "fullName", "passportNumber", "passportCountry", "dateBirth", "sexo",
                "address", "suburb", "state", "postcode", "email", "school", "visaAgency", "australianNumber", "whatsApp",
                "serialNumber", "model", "color", "numberWeeks", "weeklyRate", "startDate", "returnDate", "pmtDay", "firstWeek",
                "secDep", "accessoriesIncluded", "notes", "ativo", "fechaData", "passportPhoto", "assinaturaPhoto", "userName",
                "termo1", "termo2", "termo3"};

        String[] args = new String[]{"1"};

        Cursor cursor = bd.query("tbl_contracts", colunas, "ativo = ?", args, null, null, "fullName ASC");

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                Contract contract = new Contract();
                contract.setIdContract(cursor.getInt(0));
                contract.setFullName(cursor.getString(1));
                contract.setPassportNumber(cursor.getString(2));
                contract.setPassportCountry(cursor.getString(3));
                contract.setDateBirth(cursor.getString(4));
                contract.setSexo(cursor.getString(5));
                contract.setAddress(cursor.getString(6));
                contract.setSuburb(cursor.getString(7));
                contract.setState(cursor.getString(8));
                contract.setPostcode(cursor.getString(9));
                contract.setEmail(cursor.getString(10));
                contract.setSchool(cursor.getString(11));
                contract.setVisaAgency(cursor.getString(12));
                contract.setAustralianNumber(cursor.getString(13));
                contract.setWhatsApp(cursor.getString(14));
                contract.setSerialNumber(cursor.getString(15));
                contract.setModel(cursor.getString(16));
                contract.setColor(cursor.getString(17));
                contract.setNumberWeeks(cursor.getString(18));
                contract.setWeeklyRate(cursor.getString(19));
                contract.setStartDate(cursor.getString(20));
                contract.setReturnDate(cursor.getString(21));
                contract.setPmtDay(cursor.getString(22));
                contract.setFirstWeek(cursor.getString(23));
                contract.setSecDep(cursor.getString(24));
                contract.setAccessoriesIncluded(cursor.getString(25));
                contract.setNotes(cursor.getString(26));
                contract.setAtivo(cursor.getInt(27));
                contract.setFechaData(cursor.getString(28));
                contract.setPassportPhoto(cursor.getString(29));
                contract.setAssinaturaPhoto(cursor.getString(30));
                contract.setUserName(cursor.getString(31));
                contract.setTermo1(cursor.getInt(32));
                contract.setTermo2(cursor.getInt(33));
                contract.setTermo3(cursor.getInt(34));
                list.add(contract);
            }while (cursor.moveToNext());
        }

        cursor.close();

        return (list);
    }

    public Contract retornarUltimo(){

        String[] colunas = new String[]{"idContract", "fullName", "passportNumber", "passportCountry", "dateBirth", "sexo",
                "address", "suburb", "state", "postcode", "email", "school", "visaAgency", "australianNumber", "whatsApp",
                "serialNumber", "model", "color", "numberWeeks", "weeklyRate", "startDate", "returnDate", "pmtDay", "firstWeek",
                "secDep", "accessoriesIncluded", "notes", "ativo", "fechaData", "passportPhoto", "assinaturaPhoto", "userName",
                "termo1", "termo2", "termo3"};

        Cursor cursor = bd.query("tbl_contracts", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0 ){
            cursor.moveToLast();
            Contract contract = new Contract();
            contract.setIdContract(cursor.getInt(0));
            contract.setFullName(cursor.getString(1));
            contract.setPassportNumber(cursor.getString(2));
            contract.setPassportCountry(cursor.getString(3));
            contract.setDateBirth(cursor.getString(4));
            contract.setSexo(cursor.getString(5));
            contract.setAddress(cursor.getString(6));
            contract.setSuburb(cursor.getString(7));
            contract.setState(cursor.getString(8));
            contract.setPostcode(cursor.getString(9));
            contract.setEmail(cursor.getString(10));
            contract.setSchool(cursor.getString(11));
            contract.setVisaAgency(cursor.getString(12));
            contract.setAustralianNumber(cursor.getString(13));
            contract.setWhatsApp(cursor.getString(14));
            contract.setSerialNumber(cursor.getString(15));
            contract.setModel(cursor.getString(16));
            contract.setColor(cursor.getString(17));
            contract.setNumberWeeks(cursor.getString(18));
            contract.setWeeklyRate(cursor.getString(19));
            contract.setStartDate(cursor.getString(20));
            contract.setReturnDate(cursor.getString(21));
            contract.setPmtDay(cursor.getString(22));
            contract.setFirstWeek(cursor.getString(23));
            contract.setSecDep(cursor.getString(24));
            contract.setAccessoriesIncluded(cursor.getString(25));
            contract.setNotes(cursor.getString(26));
            contract.setAtivo(cursor.getInt(27));
            contract.setFechaData(cursor.getString(28));
            contract.setPassportPhoto(cursor.getString(29));
            contract.setAssinaturaPhoto(cursor.getString(30));
            contract.setUserName(cursor.getString(31));
            contract.setTermo1(cursor.getInt(32));
            contract.setTermo2(cursor.getInt(33));
            contract.setTermo3(cursor.getInt(34));

            cursor.close();

            return contract;

        }

        return null;
    }
}
