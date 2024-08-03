package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(
        Long id,
        @JsonProperty("medico_id") Long idMedico,
        @JsonProperty("paciente_id") Long idPaciente,
        LocalDateTime data
) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getId(),
                consulta.getPaciente().getId(), consulta.getData());
    }
}
