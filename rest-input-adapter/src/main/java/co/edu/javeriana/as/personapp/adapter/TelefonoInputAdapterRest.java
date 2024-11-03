package co.edu.javeriana.as.personapp.adapter;

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
import co.edu.javeriana.as.personapp.mapper.TelefonoMapperRest;
import co.edu.javeriana.as.personapp.model.request.TelefonoRequest;
import co.edu.javeriana.as.personapp.model.response.TelefonoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Adapter
public class TelefonoInputAdapterRest {
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
    private TelefonoMapperRest telefonoMapperRest;

    PersonInputPort personInputPort;
    PhoneInputPort phoneInputPort;

    private String setPhoneOutputPortInjection(String dbOption) throws InvalidOptionException {
        if(dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())){
            phoneInputPort = new PhoneUseCase(phoneOutputPortMaria);
            personInputPort = new PersonUseCase(personOutputPortMaria);
            return DatabaseOption.MARIA.toString();
        }else if(dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())){
            phoneInputPort = new PhoneUseCase(phoneOutputPortMongo);
            personInputPort = new PersonUseCase(personOutputPortMongo);
            return DatabaseOption.MONGO.toString();
        }else{
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public List<TelefonoResponse> historial(String database) {
        log.info("Into historial TelefonoEntity in Input Adapter");
        try{
            if(setPhoneOutputPortInjection(database).equalsIgnoreCase(DatabaseOption.MARIA.toString())){
                return phoneInputPort.findAll().stream().map(telefonoMapperRest::fromDomainToAdapterRestMaria)
                        .collect(Collectors.toList());
            }else{
                return phoneInputPort.findAll().stream().map(telefonoMapperRest::fromDomainToAdapterRestMongo)
                        .collect(Collectors.toList());
            }
        }catch(InvalidOptionException e){
            log.warn("Invalid database option: " + database+" "+e.getMessage());
            return new ArrayList<TelefonoResponse>();
        }
    }

    public TelefonoResponse createPhone(TelefonoRequest request) {
        try {
            setPhoneOutputPortInjection(request.getDatabase());
            Person owner = personInputPort.findOne(Integer.parseInt(request.getOwner()));
            Phone phone = phoneInputPort.create(telefonoMapperRest.fromAdapterToDomain(request, owner));
            return telefonoMapperRest.fromDomainToAdapterRestMaria(phone);
        } catch (Exception e) {
            log.warn("Invalid database option: " + request.getDatabase()+" "+e.getMessage());
            return null;
        }
    }

    public TelefonoResponse findOne(String database, String number) {
        try{
            setPhoneOutputPortInjection(database);
            Phone phone = phoneInputPort.findOne(number);
            return telefonoMapperRest.fromDomainToAdapterRestMaria(phone);
        } catch (Exception e) {
            log.warn("Invalid database option: " + database+" "+e.getMessage());
            return null;
        }
    }

    public TelefonoResponse deletePhone(String database, String number) {
        try{
            setPhoneOutputPortInjection(database);
            return new TelefonoResponse(number,"DELETED","DELETED",database,"DELETED");
        } catch (Exception e) {
            log.warn("Invalid database option: " + database+" "+e.getMessage());
            return null;
        }
    }

    public TelefonoResponse editPhone(TelefonoRequest request) {
        try{
            setPhoneOutputPortInjection(request.getDatabase());
            Person owner = personInputPort.findOne(Integer.parseInt(request.getOwner()));
            log.info("Owner unicornio: "+owner.toString());
            Phone phone = phoneInputPort.edit(request.getNumber(),telefonoMapperRest.fromAdapterToDomain(request, owner));
            log.info("Phone unicornio: "+phone.toString());
            return telefonoMapperRest.fromDomainToAdapterRestMaria(phone);
        } catch (Exception e) {
            log.warn("Invalid database option: " + request.getDatabase()+" "+e.getMessage());
            return null;
        }
    }
}

