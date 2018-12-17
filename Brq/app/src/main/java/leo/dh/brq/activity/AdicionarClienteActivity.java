package leo.dh.brq.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import leo.dh.brq.R;
import leo.dh.brq.buscar_endereco.Address;
import leo.dh.brq.buscar_endereco.Util;
import leo.dh.brq.buscar_endereco.ZipCodeListener;
import leo.dh.brq.helper.ClienteDAO;
import leo.dh.brq.model.Cliente;

public class AdicionarClienteActivity extends AppCompatActivity {

    private TextInputEditText editCliente;
    private Cliente clienteAtual;

    private EditText etZipCode;
    private Util util;


    private EditText number;
    private EditText street;
    private EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cliente);

        number = (EditText) findViewById(R.id.et_number);
        street = (EditText) findViewById(R.id.et_street);
        city = (EditText) findViewById(R.id.et_city);


        editCliente = findViewById(R.id.textCliente);
        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener(new ZipCodeListener(this));


        // Recuperar cliente, caso seja edicao
        clienteAtual = (Cliente) getIntent().getSerializableExtra("clienteSelecionado");

        // Configurar cliente na caixa de texto
        if (clienteAtual != null) {
            editCliente.setText(clienteAtual.getNomeCliente());

        }

        util = new Util(this,
                R.id.et_zip_code,
                R.id.et_street,
              //  R.id.et_complement,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number);

    }

    public String getUriZipCode() {
        return "https://viacep.com.br/ws/" + etZipCode.getText() + "/json/";
    }

    public void lockFields(boolean isToLock) {
        util.lockFields(isToLock);
    }

    public void setDataViews(Address address) {
        setField(R.id.et_street, address.getLogradouro());
       // setField(R.id.et_complement, address.getComplemento());
        setField(R.id.et_neighbor, address.getBairro());
        setField(R.id.et_city, address.getLocalidade());
        setField(R.id.sp_state, address.getUf());
    }

    private void setField(int id, String data) {
        ((EditText) findViewById(id)).setText(data);
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
                    if (!nomeCliente.isEmpty()) {

                        Cliente cliente = new Cliente();
                        cliente.setNomeCliente(nomeCliente);
                        cliente.setId(clienteAtual.getId());

                        //atualizar no banco de dados
                        if (clienteDAO.atualizar(cliente)) {

                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao Atualizar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Erro ao Atualizar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }


                } else {//salvar

                    String nomeCliente = editCliente.getText().toString();
                    if (!nomeCliente.isEmpty()) {
                        Cliente cliente = new Cliente();
                        cliente.setNomeCliente(nomeCliente);
                        if (clienteDAO.salvar(cliente)) {
                            finish();
                            Toast.makeText(getApplicationContext(),
                                    "Sucesso ao Salvar Cliente",
                                    Toast.LENGTH_SHORT).show();

                        } else {
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

    public void maps(View view) {

        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + number.getText() + street.getText()  + city.getText());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }
}
