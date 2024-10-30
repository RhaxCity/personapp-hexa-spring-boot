package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.EstudioModelCli;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class EstudioMapperCli {
    @Autowired
    private PersonaMapperCli personaMapperCli;

    public EstudioModelCli fromDomainToAdapterCli(Study study) {
        EstudioModelCli estudioModelCli = new EstudioModelCli();
        estudioModelCli.setCc_per(study.getPerson().getIdentification());
        estudioModelCli.setId_prof(study.getProfession().getIdentification());
        estudioModelCli.setFecha(study.getGraduationDate());
        estudioModelCli.setUniver(study.getUniversityName());
        return estudioModelCli;
    }

    public Study fromAdapterCliToDomain(EstudioModelCli estudioModelCli, Person person, Profession profession) {
        Study study = new Study();
        study.setGraduationDate(estudioModelCli.getFecha());
        study.setUniversityName(estudioModelCli.getUniver());
        study.setPerson(person);
        study.setProfession(profession);
        return study;
    }
}
