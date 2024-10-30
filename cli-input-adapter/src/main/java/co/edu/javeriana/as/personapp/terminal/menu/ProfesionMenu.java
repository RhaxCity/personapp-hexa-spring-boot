package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.ProfesionInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class ProfesionMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;
    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_VER_PROFESION =2;
    private static final int OPCION_CREAR_PROFESION =3;
    private static final int OPCION_ELIMINAR_PROFESION = 4;
    private static final int OPCION_EDITAR_PROFESION= 5;

    public void iniciarMenu(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuMotorPersistencia();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case PERSISTENCIA_MARIADB:
                        profesionInputAdapterCli.setProfessionOutputPortInjection("MARIA");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        profesionInputAdapterCli.setProfessionOutputPortInjection("MONGO");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuOpciones();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                        isValid = true;
                        break;
                    case OPCION_VER_TODO:
                        profesionInputAdapterCli.historial();
                        break;
                    case OPCION_VER_PROFESION:
                        profesionInputAdapterCli.findOne(findProfession(keyboard));
                        break;
                    case OPCION_CREAR_PROFESION:
                        profesionInputAdapterCli.createProfession(createEntity(keyboard));
                        break;
                    case OPCION_ELIMINAR_PROFESION:
                        profesionInputAdapterCli.deleteProfession(findProfession(keyboard));
                        break;
                    case OPCION_EDITAR_PROFESION:
                        profesionInputAdapterCli.editPerson(editProfession(keyboard));
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_TODO + " para ver todas las personas");
        System.out.println(OPCION_VER_PROFESION + " para ver una profesion");
        System.out.println(OPCION_CREAR_PROFESION + " para crear una profesion");
        System.out.println(OPCION_ELIMINAR_PROFESION + " para eliminar una profesion");
        System.out.println(OPCION_EDITAR_PROFESION + " para editar una profesion");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuMotorPersistencia() {
        System.out.println("----------------------");
        System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
        System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
        System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
    }

    private int leerOpcion(Scanner keyboard) {
        try {
            System.out.print("Ingrese una opción: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("Solo se permiten números.");
            return leerOpcion(keyboard);
        }
    }

    private int findProfession(Scanner keyboard){
        try{
            System.out.println("Ingrese id de la profesión: ");
            return keyboard.nextInt();
        }catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return findProfession(keyboard);
        }
    }

    private ProfesionModelCli createEntity(Scanner keyboard){
        try{
            ProfesionModelCli profession = new ProfesionModelCli();
            System.out.println("Ingrese id: ");
            profession.setId(keyboard.nextInt());
            return professionData(keyboard, profession);
        } catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return createEntity(keyboard);
        }
    }

    private ProfesionModelCli editProfession(Scanner keyboard){
        try {
            int id = findProfession(keyboard);
            ProfesionModelCli profession = new ProfesionModelCli();
            profession.setId(id);
            return professionData(keyboard, profession);
        }catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return editProfession(keyboard);
        }
    }

    private ProfesionModelCli professionData(Scanner keyboard, ProfesionModelCli profession) {
        System.out.println("Ingrese nombre: ");
        profession.setNom(keyboard.next());
        System.out.println("Ingrese descripcion: ");
        profession.setDes(keyboard.next());
        return profession;
    }

}

