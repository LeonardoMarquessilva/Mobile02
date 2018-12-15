package leo.dh.brq.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private Long id;
    private String nomeCliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
