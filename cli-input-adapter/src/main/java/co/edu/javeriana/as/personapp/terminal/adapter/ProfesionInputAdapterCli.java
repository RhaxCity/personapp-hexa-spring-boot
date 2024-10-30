package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfessionUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@Slf4j
@Adapter
public class ProfesionInputAdapterCli {
    @Autowired
    @Qualifier("professionOutputAdapterMaria")
    private ProfessionOutputPort professionOutputPortMaria;

    @Autowired
    @Qualifier("professionOutputAdapterMongo")
    private ProfessionOutputPort professionOutputPortMongo;

    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    ProfessionInputPort professionInputPort;

    public void setProfessionOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            professionInputPort = new ProfessionUseCase(professionOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            professionInputPort = new ProfessionUseCase(professionOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial PersonaEntity in Input Adapter");
        professionInputPort.findAll().stream()
                .map(profesionMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }
    public void findOne(int id) {
        log.info("Into findOne ProfessionEntity in Input Adapter");
        try {
            Profession profession = professionInputPort.findOne(id);
            ProfesionModelCli profesionModelCli = profesionMapperCli.fromDomainToAdapterCli(profession);
            System.out.println("Profession found:\n" + profesionModelCli.toString());
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }
    public void createProfession(ProfesionModelCli profesionModelCli){
        log.info("Into crearProfession StudyEntity in Input Adapter");
        try{
            professionInputPort.create(profesionMapperCli.fromAdapterCliToDomain(profesionModelCli));
            System.out.println("Profession created.");
        }catch (Exception e){
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }

    public void deleteProfession(int id) {
        log.info("Into deleteProfession PersonaEntity in Input Adapter");
        try {
            if (professionInputPort.drop(id)){
                System.out.println("Profession deleted.");
            }else{
                System.out.println("Error at delete.");
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }

    public void editPerson(ProfesionModelCli profesionModelCli) {
        log.info("Into editProffesion PersonaEntity in Input Adapter");
        try {
            professionInputPort.edit(profesionModelCli.getId(),profesionMapperCli.fromAdapterCliToDomain(profesionModelCli));
            System.out.println("Profession edited.");
        } catch (Exception e) {
            log.warn(e.getMessage());
            System.out.println("Error.");
        }
    }

}
