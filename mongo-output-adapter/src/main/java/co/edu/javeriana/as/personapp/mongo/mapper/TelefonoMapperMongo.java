package co.edu.javeriana.as.personapp.mongo.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper
public class TelefonoMapperMongo {

	@Autowired
	private PersonaMapperMongo personaMapperMongo;

	public TelefonoDocument fromDomainToAdapter(Phone phone) {
		TelefonoDocument telefonoDocument = new TelefonoDocument();
		telefonoDocument.setId(phone.getNumber());
		telefonoDocument.setOper(phone.getCompany());
		telefonoDocument.setPrimaryDuenio(validateDuenio(phone.getOwner()));
		return telefonoDocument;
	}

	private PersonaDocument validateDuenio(@NonNull Person owner) {
		return owner != null ? personaMapperMongo.fromDomainToAdapter(owner) : new PersonaDocument();
	}

	public Phone fromAdapterToDomain(TelefonoDocument telefonoDocument) {
		Phone phone = new Phone();
		log.warn("Mapping from domain to adapter"+ phone);
		phone.setNumber(telefonoDocument.getId());
		phone.setCompany(telefonoDocument.getOper());
		phone.setOwner(validateOwner(telefonoDocument.getPrimaryDuenio()));
		return phone;
	}

	private @NonNull Person validateOwner(PersonaDocument duenio) {
		Person owner = new Person();
		owner.setIdentification(duenio.getId());
		return owner;
	}
}