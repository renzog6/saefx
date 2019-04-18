package ar.nex.equipo;

import ar.nex.pedido.*;

/**
 *
 * @author Renzo
 */
public enum EmpresaSelect {

    RRR(1, "Resconi Raul"), 
    RCM(2, "RCM S.A."),
    OTRA(3, "Otra Empresa");

    private long id;
    private String nombre;

    private EmpresaSelect(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    
}