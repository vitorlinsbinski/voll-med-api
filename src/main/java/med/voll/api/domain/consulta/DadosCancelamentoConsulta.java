package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoConsulta(
        @NotNull
        @JsonAlias({"id_consulta", "consulta_id"}) Long consultaId,
        @NotNull
        @JsonAlias({"motivo_cancelamento"}) MotivoCancelamento motivoCancelamento
) {
}
