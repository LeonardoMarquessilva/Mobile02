package leo.dh.brq.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import leo.dh.brq.R;
import leo.dh.brq.helper.ClienteDAO;
import leo.dh.brq.model.Cliente;

public class AdicionarClienteActivity extends AppCompatActivity {

    private TextInputEditText editCliente;
    private Cliente clienteAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cliente);

        editCliente = findViewById(R.id.textCliente);

        // Recuperar cliente, caso seja edicao
        clienteAtual = ( Cliente ) getIntent().getSerializableExtra("clienteSelecionado");

        // Configurar cliente na caixa de texto
        if (clienteAtual != null){
            editCliente.setText(clienteAtual.getNomeCliente());

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_cliente, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemSalvar:
                //Executa acao para o item salvar

                ClienteDAO clienteDAO = new ClienteDAO(getApplicationContext());

                if (clienteAtual != null) {//edicao

                    String nomeCliente = editCliente.getText().toString();
                    if (!nomeCliente.isEmpty() ){

                        Cliente cliente = new Cliente();
                        cliente.setNomeCliente( nomeCliente);
                        cliente.setId(clienteAtual.getId());

                        //atualizar no banco de dados
                        if (clienteDAO.atualizar(cliente)){

                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao Atualizar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        }else{
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao Atualizar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }




                } else {//salvar



                    String nomeCliente = editCliente.getText().toString();
                    if (!nomeCliente.isEmpty() ){
                        Cliente cliente = new Cliente();
                        cliente.setNomeCliente(nomeCliente);
                        if(clienteDAO.salvar(cliente)){
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao Salvar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        }else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao Salvar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }

                 }


                    break;
                }

                return super.onOptionsItemSelected(item);
        }
    }
