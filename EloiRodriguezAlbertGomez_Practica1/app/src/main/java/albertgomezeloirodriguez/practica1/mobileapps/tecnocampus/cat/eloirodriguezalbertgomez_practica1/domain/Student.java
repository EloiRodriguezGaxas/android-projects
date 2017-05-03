package albertgomezeloirodriguez.practica1.mobileapps.tecnocampus.cat.eloirodriguezalbertgomez_practica1.domain;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eloit on 26/4/2017.
 */

public class Student {

    @NotNull
    private String nom;
    @NotNull
    private String cognom;
    @NotNull
    private String telf;
    @NotNull
    private String dni;
    @NotNull
    private String grau;
    @NotNull
    private String curs;


    public Student(String nom, String cognom, String telf, String dni, String grau, String curs) {
        this.nom = nom;
        this.cognom = cognom;
        this.telf = telf;
        this.dni = dni;
        this.grau = grau;
        this.curs = curs;

    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public String getTelf() {
        return telf;
    }

    public void setTelf(String telf) {
        this.telf = telf;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGrau() {
        return grau;
    }

    public void setGrau(String grau) {
        this.grau = grau;
    }

    public String getCurs() {
        return curs;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }

    @Override
    public String toString() {
        return nom + " " + cognom + " " + " " + grau + " ";
    }
}
