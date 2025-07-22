package com.audittrack.auditscheduler;
import com.audittrack.auditscheduler.entity.Persona;
public class TestLombok {
    public static void main(String[] args) {
        Persona p = new Persona();
        p.setNombre("Felipe");
        p.setEdad(39);

        System.out.println("Nombre: " + p.getNombre());
        System.out.println("Edad: " + p.getEdad());
    }
}