package leo.dh.brq.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import leo.dh.brq.R;
import leo.dh.brq.model.Cliente;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.MyViewHolder> {

    private List<Cliente> listaClientes;

    public ClienteAdapter(List<Cliente> lista ) {
        this.listaClientes = lista;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {

        View itemLista = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.lista_cliete_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Cliente cliente  = listaClientes.get(position);
        holder.cliente.setText(cliente.getNomeCliente());


    }

    @Override
    public int getItemCount() {
        return this.listaClientes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView cliente;


        public MyViewHolder(View itemView) {
            super(itemView);

          cliente = itemView.findViewById(R.id.textCliente);

        }
    }
}
