package co.edu.javeriana.as.personapp.terminal.adapter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PersonUseCase;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class PersonaInputAdapterCli {

	@Autowired
	@Qualifier("personOutputAdapterMaria")
	private PersonOutputPort personOutputPortMaria;

	@Autowired
	@Qualifier("personOutputAdapterMongo")
	private PersonOutputPort personOutputPortMongo;

	@Autowired
	private PersonaMapperCli personaMapperCli;

	PersonInputPort personInputPort;

	public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
		if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMaria);
		} else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
			personInputPort = new PersonUseCase(personOutputPortMongo);
		} else {
			throw new InvalidOptionException("Invalid database option: " + dbOption);
		}
	}

	public void historial1() {
		log.info("Into historial PersonaEntity in Input Adapter");
		List<PersonaModelCli> persona = personInputPort.findAll().stream().map(personaMapperCli::fromDomainToAdapterCli)
					.collect(Collectors.toList());
		persona.forEach(p -> System.out.println(p.toString()));
	}
	public void historial() {
	    log.info("Into historial PersonaEntity in Input Adapter");
	    personInputPort.findAll().stream()
	        .map(personaMapperCli::fromDomainToAdapterCli)
	        .forEach(System.out::println);
	}

	public void crearPersona(PersonaModelCli persona, String dbOption) {
		log.info("Into crear PersonaEntity in Input Adapter");
		try {
			setPersonOutputPortInjection(dbOption);
			personInputPort.create(personaMapperCli.fromAdapterCliToDomain(persona));
			System.out.println("Persona creada correctamente: " + persona.toString());
		} catch (Exception e) {
			log.warn(e.getMessage());
			System.out.println("Error al crear persona");
		}
	}

	public void editarPersona(PersonaModelCli persona, String dbOption) {
		log.info("Into editar PersonaEntity in Input Adapter");
		try {
			setPersonOutputPortInjection(dbOption);
			personInputPort.edit(persona.getCc(),personaMapperCli.fromAdapterCliToDomain(persona));
			System.out.println("Persona editada correctamente: " + persona.toString());
		} catch (Exception e) {
			log.warn(e.getMessage());
			System.out.println("Error al editar persona");
		}
	}

	public void eliminarPersona(String dbOption, int cc) {
		log.info("Into eliminar PersonaEntity in Input Adapter");
		try {
			setPersonOutputPortInjection(dbOption);
			boolean resultado = personInputPort.drop(cc);
			if (resultado)
				System.out.println("Persona eliminada correctamente: " + cc);
		} catch (Exception e) {
			log.warn(e.getMessage());
			System.out.println("Error al eliminar persona");
		}
	}

	public void buscarPersona(String dbOption, int cc) {
		log.info("Into buscar PersonaEntity in Input Adapter");
		try {
			setPersonOutputPortInjection(dbOption);
			Person person = personInputPort.findOne(cc);
			PersonaModelCli persona = personaMapperCli.fromDomainToAdapterCli(person);
			System.out.println("Persona encontrada: " + persona.toString());
		} catch (Exception e) {
			log.warn(e.getMessage());
			System.out.println("Error al buscar persona");
		}
	}

}
