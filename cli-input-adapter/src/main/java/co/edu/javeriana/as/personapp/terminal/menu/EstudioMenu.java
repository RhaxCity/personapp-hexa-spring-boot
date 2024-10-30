package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.EstudioInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class EstudioMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;
    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR_ESTUDIO =2;
    // mas opciones

    public void iniciarMenu(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
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
                        estudioInputAdapterCli.setStudyOutputPortInjection("MARIA");
                        menuOpciones(estudioInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        estudioInputAdapterCli.setStudyOutputPortInjection("MONGO");
                        menuOpciones(estudioInputAdapterCli,keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(EstudioInputAdapterCli estudioInputAdapterCli, Scanner keyboard) {
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
                        estudioInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR_ESTUDIO:
                        estudioInputAdapterCli.createStudy(createEntity(keyboard));
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
        System.out.println(OPCION_CREAR_ESTUDIO + " para crear un estudio");
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

    private EstudioModelCli createEntity(Scanner keyboard){
        try {
            EstudioModelCli estudio = new EstudioModelCli();
            System.out.println("Ingrese identificacion de la profesión: ");
            estudio.setId_prof(keyboard.nextInt());
            System.out.println("Ingrese identificacion de la persona: ");
            estudio.setCc_per(keyboard.nextInt());
            System.out.println("Ingrese fecha de graduación: ");
            int day, month, year;
            System.out.println("Dia: ");
            day = keyboard.nextInt();
            System.out.println("Mes: ");
            month = keyboard.nextInt();
            System.out.println("Año: ");
            year = keyboard.nextInt();
            estudio.setFecha(LocalDate.of(year, month, day));
            System.out.println(estudio.getFecha());
            keyboard.nextLine();
            System.out.println("Ingrese la universidad: ");
            estudio.setUniver(keyboard.nextLine());
            return estudio;
        } catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return createEntity(keyboard);
        }
    }

}

