package co.edu.javeriana.as.personapp.terminal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstudioModelCli {
    private Integer id_prof;
    private Integer cc_per;
    private LocalDate fecha;
    private String univer;
}