/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PersonasDao;
import java.time.Instant;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import orm.Personas;
import java.util.Date;

/**
 *
 * @author ander
 */
@ManagedBean(name = "pC")
@RequestScoped
public class PersonasController {

    //Objetos necesarios:
    private Personas per;
    private List<Personas> listaPer;

    //Operacion:
    char ope = '\0';
    private String tituloOpe;

    //Inyectar dependencias a los session bean:
    @EJB
    PersonasDao perDao;

    /**
     * Creates a new instance of PersonasController
     */
    public PersonasController() {
        per = new Personas();
    }

    public Personas getPer() {
        return per;
    }

    public void setPer(Personas per) {
        this.per = per;
    }

    public List<Personas> getListaPer() {
        return listaPer;
    }

    public void setListaPer(List<Personas> listaPer) {
        this.listaPer = listaPer;
    }

    //Método que se ejecuta después de cargar la página
    //manejada por el controller:
    @PostConstruct
    public void inicializar() {
        listaPer = perDao.selectPersonas();
    }

    //Retornar la edad:
    public int calcularEdad(Date fecNaci) {

        //Fecha nacimiento:
        int dn = fecNaci.getDay(), mn = fecNaci.getMonth(), an = fecNaci.getYear();

        //Fecha actual:
        Date fecAct = Date.from(Instant.now());
        int da = fecAct.getDay(), ma = fecAct.getMonth(), aa = fecAct.getYear();

        int edad;

        if (ma < mn || (ma == mn && da < dn)) {
            edad = aa - an - 1;
        } else {
            edad = aa - an;
        }

        return edad;
    }

    //Métodos de redireccionamiento de páginas:
    public String doVerIndex(){
        return "index";
    }
    public String doVolver() {

        //actualizar:
        listaPer = perDao.selectPersonas();
        return "index";

    }

    public String doNuevo() {
        ope = 'A';
        setTituloOpe("Nueva Persona");
        per = new Personas();
        return "nuevo";
    }

    public String doGuardar() {
        if (ope == 'A') {
            perDao.insertPersona(per);
        } else {
            perDao.updatePersona(per);
        }

        return doVolver();
    }

    public String doPrepararModificacion(Personas p) {
        ope = 'M';
        setTituloOpe("Modificar Persona");
        per = p;
        return "nuevo";
    }

    public String doBorrar(Personas p) {
        perDao.deletePersona(p);
        return doVolver();
    }

    public String getTituloOpe() {
        return tituloOpe;
    }

    public void setTituloOpe(String tituloOpe) {
        this.tituloOpe = tituloOpe;
    }
}
