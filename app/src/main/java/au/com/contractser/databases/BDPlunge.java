package au.com.contractser.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import au.com.contractser.models.User;

public class BDPlunge extends SQLiteOpenHelper {
    private static String DB_PATH = "data/data/au.com.contractser/databases/";
    private static final String NOME_BD = "contracts.db";
    private static final int VERSAO_BD = 1;
    private SQLiteDatabase dbQuery;
    private final Context dbContexto;

    public BDPlunge(Context context) {
        super(context, NOME_BD, null, VERSAO_BD);
        this.dbContexto = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void criarDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if(!dbExist){
            this.getReadableDatabase();
            try{
                this.copiarDataBase();
            }catch (IOException e){
                throw new Error("Erro ao copiar Banco de dados");
            }
        }
        else{
            int x = buscarVersaoBd();
            if (VERSAO_BD > x ){
                String myPath = DB_PATH + NOME_BD;
                User user = new User();
                SQLiteDatabase db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

                String[] colunas = new String[]{"idUser", "nameUser", "emailUser", "password", "phoneNumber", "role"};
                String arg = ""+1;
                String[] args = new String[]{arg};

                Cursor cursor =  db.query("tbl_user", colunas, "_id = ?", args, null, null, null);
                if (cursor.getCount() > 0){
                    cursor.moveToFirst();
                    user.setIdUser(cursor.getInt(0));
                    user.setNameUser(cursor.getString(1));
                    user.setEmailUser(cursor.getString(2));
                    user.setPassword(cursor.getString(3));
                    user.setPhoneNumber(cursor.getString(5));
                    user.setRole(cursor.getString(6));
                }
                db.close();
                this.getReadableDatabase();
                try {
                    this.deletarDataBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                this.getReadableDatabase();
                try{
                    this.copiarDataBase();
                }catch (IOException e){
                    throw new Error("Erro ao copiar Banco de dados");
                }
                SQLiteDatabase db2 = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
                ContentValues valores = new ContentValues();
                valores.put("nameUser", user.getNameUser());
                valores.put("emailUser", user.getEmailUser());
                valores.put("password", user.getPassword());
                valores.put("phoneNumber", user.getPhoneNumber());
                valores.put("role", user.getRole());

                db2.insert("tbl_user", null, valores);
                db2.close();
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + NOME_BD;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch (SQLiteException e){

        }
        if(checkDB != null){
            checkDB.close();
        }

        return  checkDB != null ? true : false;
    }

    private void copiarDataBase() throws IOException{
        InputStream myInput = dbContexto.getAssets().open(NOME_BD);
        String outFileName = DB_PATH + NOME_BD;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void abrirDataBase() throws SQLException {
        String myPath = DB_PATH + NOME_BD;
        dbQuery = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    public void deletarDataBase()throws SQLException{
        String myPath = DB_PATH + NOME_BD;
        SQLiteDatabase checkDB = null;
        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        checkDB.close();
        dbContexto.deleteDatabase(NOME_BD);
    }

    public int buscarVersaoBd(){
        String myPath = DB_PATH + NOME_BD;
        int x ;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        x = db.getVersion();
        return x;
    }
}
