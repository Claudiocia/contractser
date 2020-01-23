package au.com.contractser.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import au.com.contractser.models.User;

public class BDUser {
    private SQLiteDatabase bd;

    public BDUser(Context context) {
        BDPlunge auxBD = new BDPlunge(context);

        bd = auxBD.getWritableDatabase();
    }

    public void inserir(User user) {
        ContentValues valores = new ContentValues();
        valores.put("nameUser", user.getNameUser());
        valores.put("emailUser", user.getEmailUser());
        valores.put("password", user.getPassword());
        valores.put("phoneNumber", user.getPhoneNumber());
        valores.put("role", user.getRole());

        bd.insert("tbl_user", null, valores);
    }

    public void atualizar(User user) {
        ContentValues valores = new ContentValues();
        valores.put("nameUser", user.getNameUser());
        valores.put("emailUser", user.getEmailUser());
        valores.put("password", user.getPassword());
        valores.put("phoneNumber", user.getPhoneNumber());
        valores.put("role", user.getRole());

        bd.update("tbl_user", valores, "idUser = ?", new String[]{"" + user.getIdUser()});
    }

    public void deletar(User user){
        bd.delete("tbl_user", "idUser =" + user.getIdUser(), null);
    }

    public boolean buscaPorId(String arg){
        String[] colunas = new String[]{"idUser", "nameUser", "emailUser", "password", "phoneNumber", "role"};
        String[] args = new String[]{arg};


        Cursor c =  bd.query("tbl_user", colunas, "role = ?", args, null, null, null );
        Log.d("claudio", "a volta do banco vem - "+ c.getCount());
        if(c.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean existUser(String nameUser, String senhaUser){
        User user = new User();
        String[] colunas = new String[]{"idUser", "nameUser", "emailUser", "password", "phoneNumber", "role"};

        Cursor cursor = bd.rawQuery("SELECT * FROM tbl_user WHERE emailUser = '" + nameUser + "' AND password = '" + senhaUser + "'", null);

        if(cursor.getCount() > 0){
            return true;
        }
        return false;
    }

    public User buscarPorArg(int id){
        User user = new User();
        String[] colunas = new String[]{"idUser", "nameUser", "emailUser", "password", "phoneNumber", "role"};
        String[] args = new String[]{""+id};
        Cursor cursor = bd.query("tbl_user", colunas, "idUser = ?", args, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            user.setIdUser(cursor.getInt(0));
            user.setNameUser(cursor.getString(1));
            user.setEmailUser(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setPhoneNumber(cursor.getString(4));
            user.setRole(cursor.getString(5));
        }
        return user;
    }

    public User buscarPorEmail(String email){
        User user = new User();
        String[] colunas = new String[]{"idUser", "nameUser", "emailUser", "password", "phoneNumber", "role"};
        String[] args = new String[]{email};
        Cursor cursor = bd.query("tbl_user", colunas, "emailUser = ?", args, null, null, null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();
            user.setIdUser(cursor.getInt(0));
            user.setNameUser(cursor.getString(1));
            user.setEmailUser(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            user.setPhoneNumber(cursor.getString(4));
            user.setRole(cursor.getString(5));
        }
        return user;
    }

    public List<User> buscarTodos(){
        List<User> list = new ArrayList<User>();
        String[] colunas = new String[]{"idUser", "nameUser", "emailUser", "password", "phoneNumber", "role"};

        Cursor cursor = bd.query("tbl_user", colunas, null, null, null, null, "nameUser ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do{
                User user = new User();
                user.setIdUser(cursor.getInt(0));
                user.setNameUser(cursor.getString(1));
                user.setEmailUser(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                user.setPhoneNumber(cursor.getString(5));
                user.setRole(cursor.getString(6));
                list.add(user);

            }while (cursor.moveToNext());
        }

        return (list);
    }

}
