package leo.dh.brq.helper;

import java.util.List;

import leo.dh.brq.model.Cliente;

public interface IClienteDAO {

    public boolean salvar (Cliente cliente);
    public boolean atualizar (Cliente cliente);
    public boolean deletar (Cliente cliente);
    public List<Cliente> listar();
}
