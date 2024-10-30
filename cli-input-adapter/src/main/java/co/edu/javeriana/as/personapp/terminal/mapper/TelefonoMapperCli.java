package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.model.TelefonoModelCli;

@Mapper
public class TelefonoMapperCli {
    public TelefonoModelCli fromDomainToAdapterCli(Phone phone) {
        TelefonoModelCli telefonoModelCli = new TelefonoModelCli();
        telefonoModelCli.setNum(phone.getNumber());
        telefonoModelCli.setOper(phone.getCompany());
        telefonoModelCli.setDuenio(phone.getOwner().getIdentification());
        return telefonoModelCli;
    }

    public Phone fromAdapterCliToDomain(TelefonoModelCli telefonoModelCli, Person person) {
        Phone phone = new Phone();
        phone.setNumber(telefonoModelCli.getNum());
        phone.setCompany(telefonoModelCli.getOper());
        phone.setOwner(person);
        return phone;
    }
}

