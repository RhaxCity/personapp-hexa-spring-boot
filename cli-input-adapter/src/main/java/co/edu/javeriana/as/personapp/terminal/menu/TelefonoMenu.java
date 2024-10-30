package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class TelefonoMenu {

    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;
    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_VER_TELEFONO = 2;
    private static final int OPCION_CREAR_TELEFONO = 3;
    private static final int OPCION_ELIMINAR_TELEFONO = 4;
    private static final int OPCION_EDITAR_TELEFONO= 5;

    public void iniciarMenu(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
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
                        telefonoInputAdapterCli.setPhoneOutputPortInjection("MARIA");
                        menuOpciones(telefonoInputAdapterCli, keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        telefonoInputAdapterCli.setPhoneOutputPortInjection("MONGO");
                        menuOpciones(telefonoInputAdapterCli, keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard) {
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
                        telefonoInputAdapterCli.historial();
                        break;
                    case OPCION_VER_TELEFONO:
                        telefonoInputAdapterCli.findOne(findPhone(keyboard));
                        break;
                    case OPCION_CREAR_TELEFONO:
                        telefonoInputAdapterCli.createPhone(createEntity(keyboard));
                        break;
                    case OPCION_ELIMINAR_TELEFONO:
                        telefonoInputAdapterCli.deletePhone(findPhone(keyboard));
                        break;
                    case OPCION_EDITAR_TELEFONO:
                        telefonoInputAdapterCli.editPhone(editPhone(keyboard));
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
        System.out.println(OPCION_VER_TODO + " para ver todas los telefonos");
        System.out.println(OPCION_VER_TELEFONO + " para ver un telefono");
        System.out.println(OPCION_CREAR_TELEFONO + " para crear un telefono");
        System.out.println(OPCION_ELIMINAR_TELEFONO + " para eliminar un telefono");
        System.out.println(OPCION_EDITAR_TELEFONO + " para editar un telefono");
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

    private String findPhone(Scanner keyboard){
        try{
            System.out.println("Ingrese el numero de telefono: ");
            return keyboard.next();
        }catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return findPhone(keyboard);
        }
    }
    private TelefonoModelCli createEntity(Scanner keyboard){
        try{
            TelefonoModelCli phone = new TelefonoModelCli();
            System.out.println("Ingrese número de telefono: ");
            phone.setNum(keyboard.next());
            return phoneData(keyboard, phone);
        } catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return createEntity(keyboard);
        }

    }

    private TelefonoModelCli editPhone(Scanner keyboard){
        try {
            String number = findPhone(keyboard);
            TelefonoModelCli phone = new TelefonoModelCli();
            phone.setNum(number);
            return phoneData(keyboard, phone);
        }catch (InputMismatchException e) {
            System.out.println("Datos incorrectos.");
            return editPhone(keyboard);
        }
    }

    private TelefonoModelCli phoneData(Scanner keyboard, TelefonoModelCli phone) {
        System.out.println("Ingrese operador: ");
        phone.setOper(keyboard.next());
        System.out.println("Ingrese identificación del dueño: ");
        phone.setDuenio(keyboard.nextInt());
        return phone;
    }

}

