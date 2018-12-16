package leo.dh.brq.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_cliente);

        editCliente = findViewById(R.id.textCliente);
        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener( new ZipCodeListener( this ) );

        // Recuperar cliente, caso seja edicao
        clienteAtual = ( Cliente ) getIntent().getSerializableExtra("clienteSelecionado");

        // Configurar cliente na caixa de texto
        if (clienteAtual != null){
            editCliente.setText(clienteAtual.getNomeCliente());

        }

        //Spinner spStates = (Spinner) findViewById(R.id.sp_state);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter
          //      .createFromResource(this,
            //            R.array.states,
              //          android.R.layout.simple_spinner_item);
        //spStates.setAdapter(adapter);

        util = new Util(this,
                R.id.et_zip_code,
                R.id.et_street,
                R.id.et_complement,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number);
                //R.id.tv_zip_code_search

    }

    public String getUriZipCode(){
        return "https://viacep.com.br/ws/"+etZipCode.getText()+"/json/";
    }

    public void lockFields( boolean isToLock ){
        util.lockFields( isToLock );
    }

    public void setDataViews(Address address){
        setField( R.id.et_street, address.getLogradouro() );
        setField( R.id.et_complement, address.getComplemento() );
        setField( R.id.et_neighbor, address.getBairro() );
        setField( R.id.et_city, address.getLocalidade() );
        setField( R.id.sp_state, address.getUf() );
    }

    private void setField( int id, String data ){
        ((EditText) findViewById(id)).setText( data );
    }

   /* private void setSpinner( int id, int arrayId, String data ){
        String[] itens = getResources().getStringArray(arrayId);

        for( int i = 0; i < itens.length; i++ ){

            if( itens[i].endsWith( "("+data+")" ) ){
                ((Spinner) findViewById(id)).setSelection( i );
                return;
            }
        }
        ((Spinner) findViewById(id)).setSelection( 0 );
    }
*/
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
