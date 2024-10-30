package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.application.usecase.PhoneUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.mapper.TelefonoMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Adapter
public class TelefonoInputAdapterCli {
    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personOutputPortMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personOutputPortMongo;

    @Autowired
    @Qualifier("phoneOutputAdapterMaria")
    private PhoneOutputPort phoneOutputPortMaria;

    @Autowired
    @Qualifier("phoneOutputAdapterMongo")
    private PhoneOutputPort phoneOutputPortMongo;

    @Autowired
    private TelefonoMapperCli telefonoMapperCli;

    PersonInputPort personInputPort;
    PhoneInputPort phoneInputPort;

    public void setPhoneOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            personInputPort = new PersonUseCase(personOutputPortMaria);
            phoneInputPort = new PhoneUseCase(phoneOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            personInputPort = new PersonUseCase(personOutputPortMongo);
            phoneInputPort = new PhoneUseCase(phoneOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial PersonaEntity in Input Adapter");
        phoneInputPort.findAll().stream()
                .map(telefonoMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }

    public void createPhone(TelefonoModelCli telefonoModelCli){
        log.info("Into phoneEntity in Input Adapter");
        try {
            Person person = personInputPort.findOne(telefonoModelCli.getDuenio());
            phoneInputPort.create(telefonoMapperCli.fromAdapterCliToDomain(telefonoModelCli, person));
            System.out.println("Phone created.");
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }

    public void findOne(String number) {
        log.info("Into findOne PersonaEntity in Input Adapter");
        try {
            Phone phone = phoneInputPort.findOne(number);
            TelefonoModelCli telefonoModelCli = telefonoMapperCli.fromDomainToAdapterCli(phone);
            System.out.println("Phone found:\n" + telefonoModelCli.toString());
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error no se encontró.");
        }
    }
    public void deletePhone(String number) {
        log.info("Into deletePhone PersonaEntity in Input Adapter");
        try {
            if (phoneInputPort.drop(number)) {
                System.out.println("Phone deleted.");
            } else {
                System.out.println("Error at delete.");
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }

    public void editPhone(TelefonoModelCli telefonoModelCli) {
        log.info("Into editPerson PersonaEntity in Input Adapter");
        try {
            Person owner = personInputPort.findOne(telefonoModelCli.getDuenio());
            phoneInputPort.edit(telefonoModelCli.getNum(),telefonoMapperCli.fromAdapterCliToDomain(telefonoModelCli, owner));
            System.out.println("Person edited.");
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }

}
