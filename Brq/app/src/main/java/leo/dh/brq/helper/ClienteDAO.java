package leo.dh.brq.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import leo.dh.brq.model.Cliente;

public class ClienteDAO implements IClienteDAO{

    private SQLiteDatabase escreve;
    private SQLiteDatabase ler;

    public ClienteDAO(Context context){
        DbHelper db = new DbHelper( context );
        escreve = db.getWritableDatabase();
        ler = db.getReadableDatabase();

    }


    @Override
    public boolean salvar(Cliente cliente) {

        ContentValues cv = new ContentValues();
        cv.put("nome", cliente.getNomeCliente() );

        try{
            escreve.insert(DbHelper.TABELA_CLIENTES,null, cv );
            Log.i("INFO","Cliente salvo com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao salvar cliente" + e.getMessage());
            return false;

        }


        return true;
    }

    @Override
    public boolean atualizar(Cliente cliente) {

        ContentValues cv = new ContentValues();
        cv.put("nome",cliente.getNomeCliente());

        try{
            String[] args = {cliente.getId().toString()};
            escreve.update(DbHelper.TABELA_CLIENTES, cv, "id=?", args );
            Log.i("INFO","Cliente Atualizado com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao Atualizar cliente" + e.getMessage());
            return false;

        }

        return true;
    }

    @Override
    public boolean deletar(Cliente cliente) {

        try{
            String[] args= {cliente.getId().toString()};
            escreve.delete(DbHelper.TABELA_CLIENTES,"id=?", args);

            Log.i("INFO","Cliente Removido com sucesso");
        }catch (Exception e){
            Log.e("INFO","Erro ao Remover cliente" + e.getMessage());
            return false;

        }

        return true;
    }

    @Override
    public List<Cliente> listar() {

        List<Cliente> clientes = new ArrayList<>();

        String sql = "SELECT * FROM " + DbHelper.TABELA_CLIENTES + " ;";
        Cursor c = ler.rawQuery(sql,null);

        while (c.moveToNext()){

            Cliente cliente = new Cliente();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeCliente = c.getString(c.getColumnIndex("nome"));

            cliente.setId(id);
            cliente.setNomeCliente(nomeCliente);

            clientes.add( cliente);

        }

        return clientes;



    }
}
