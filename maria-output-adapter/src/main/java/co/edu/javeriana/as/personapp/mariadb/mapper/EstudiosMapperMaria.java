package co.edu.javeriana.as.personapp.mariadb.mapper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.ProfesionEntity;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper
public class EstudiosMapperMaria {

    @Autowired
    private PersonaMapperMaria personaMapperMaria;

    @Autowired
    private ProfesionMapperMaria profesionMapperMaria;

    public EstudiosEntity fromDomainToAdapter(Study study) {
        EstudiosEntityPK estudioPK = new EstudiosEntityPK();
        estudioPK.setCcPer(study.getPerson().getIdentification());
        estudioPK.setIdProf(study.getProfession().getIdentification());
        
        EstudiosEntity estudio = new EstudiosEntity();
        estudio.setEstudiosPK(estudioPK);
        estudio.setFecha(validateFecha(study.getGraduationDate()));
        estudio.setUniver(validateUniver(study.getUniversityName()));
        
        return estudio;
    }

    private Date validateFecha(LocalDate graduationDate) {
        return graduationDate != null
                ? Date.from(graduationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant())
                : null;
    }

    private String validateUniver(String universityName) {
        return universityName != null ? universityName : "";
    }

    public Study fromAdapterToDomain(EstudiosEntity estudiosEntity) {
        Study study = new Study();
		
		log.warn("Mapping from adapter to domain"+ estudiosEntity);

        // En lugar de llamar al adaptador, construir directamente con identificador
        study.setPerson(validatePrimaryPersona(estudiosEntity.getPersona()));
        study.setProfession(validatePrimaryProfesion(estudiosEntity.getProfesion()));
        
        study.setGraduationDate(validateGraduationDate(estudiosEntity.getFecha()));
        study.setUniversityName(validateUniversityName(estudiosEntity.getUniver()));
        
        return study; // Retornar el objeto Study construido
    }

    private LocalDate validateGraduationDate(Date graduationDate) {
		log.warn("Mapping validateGraduationDate: {}", graduationDate);

        if (graduationDate == null) {
            log.warn("La fecha de graduación es nula.");
            return null; // Devuelve null si la fecha es nula
        }

        try {
            // Convertir Date a LocalDate
            return graduationDate.toInstant()
                                 .atZone(ZoneId.systemDefault()) // Ajusta según sea necesario
                                 .toLocalDate();
        } catch (Exception e) {
            log.error("Error al convertir la fecha de graduación: {}", e.getMessage(), e);
            return null; // Devuelve null en caso de error
        }
	}

	private String validateUniversityName(String univer) {
		return univer != null ? univer : "";
	}

    private @NonNull Person validatePrimaryPersona(@NonNull PersonaEntity persona) {
        Person owner = new Person();
        owner.setIdentification(persona.getCc()); // Asigna solo el ID
        return owner;
    }

    private @NonNull Profession validatePrimaryProfesion(@NonNull ProfesionEntity profesion) {
        Profession profession = new Profession();
        profession.setIdentification(profesion.getId()); // Asigna solo el ID
        return profession;
    }
}
