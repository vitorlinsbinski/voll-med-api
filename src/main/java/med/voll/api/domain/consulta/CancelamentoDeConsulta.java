package med.voll.api.domain.consulta;

import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class CancelamentoDeConsulta {
    @Autowired
    private ConsultaRepository repository;

    public void cancelar(DadosCancelamentoConsulta dados) {
        if(!this.repository.existsById(dados.consultaId())) {
            throw new ValidacaoException("Consulta inexistente.");
        }

        var consulta = this.repository.getReferenceById(dados.consultaId());
        var antecedenciaMinima24Horas = Duration.between(LocalDateTime.now(),
                consulta.getData()).toHours() >= 24;

        if(!antecedenciaMinima24Horas) {
            throw new ValidacaoException("Uma consulta só pode ser cencelada " +
                    "com antecedência mínima de 24 horas.");
        }

        consulta.cancelar(dados.motivoCancelamento());
    }
}
