package leo.dh.brq.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import leo.dh.brq.R;
import leo.dh.brq.adapter.ClienteAdapter;
import leo.dh.brq.helper.ClienteDAO;
import leo.dh.brq.helper.DbHelper;
import leo.dh.brq.helper.RecyclerItemClickListener;
import leo.dh.brq.model.Cliente;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClienteAdapter clienteAdapter;
    private List<Cliente>listaClientes = new ArrayList<>();
    private Cliente clienteSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurar recycler
        recyclerView= findViewById(R.id.recyclerView);

       /* DbHelper db = new DbHelper( getApplicationContext());
        ContentValues cv = new ContentValues();
        cv.put("nome", "teste");

        db.getWritableDatabase().insert("clientes", null, cv );
*/
        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                // Recuperar Cliente para edicao
                                Cliente clienteSelecionado = listaClientes.get( position);

                                // Envia cliente para tela adicionar cliente
                                Intent intent = new Intent(MainActivity.this, AdicionarClienteActivity.class);
                                intent.putExtra("clienteSelecionado", clienteSelecionado);

                                startActivity( intent );

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                //Recuperar Cliente para deletar
                                clienteSelecionado = listaClientes.get(position);



                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

                                //configurar titulo da mensagem
                                dialog.setTitle("Confirmar exclusao");
                                dialog.setMessage("Deseja excluir o cliente: " + clienteSelecionado.getNomeCliente() + "?" );

                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());
                                        if(clienteDAO.deletar(clienteSelecionado)){

                                            carregarListaClientes();

                                            Toast.makeText(getApplicationContext(),
                                                    "Sucesso ao Excluir Cliente",
                                                    Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getApplicationContext(),
                                                    "Erro ao Excluir Cliente",
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });
                                dialog.setNegativeButton("Não", null);

                                //Exibir dialog
                                dialog.create();
                                dialog.show();


                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AdicionarClienteActivity.class);
                startActivity(intent);
            }
        });
    }

    public void carregarListaClientes(){

        //Listar clientes
        ClienteDAO clienteDAO = new ClienteDAO( getApplicationContext());
        listaClientes = clienteDAO.listar();



        //Exibe lista de clientes no Recyclerview

        //Configurar Adapter
        clienteAdapter = new ClienteAdapter( listaClientes );


        //Configurar Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(clienteAdapter);

    }

    @Override
    protected void onStart() {
        carregarListaClientes();
        super.onStart();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
