package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validacoes.ValidadorAgendamentoDeConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.exception.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private List<ValidadorAgendamentoDeConsulta> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if(!this.pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("ID do paciente informado não existe" +
                    ".");
        }

        if(dados.idMedico() != null && !this.medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("ID do médico informado não existe.");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente =
                this.pacienteRepository.getReferenceById(dados.idPaciente());
        var medico = escolherMedico(dados);

        if(medico == null) {
            throw new ValidacaoException("""
                        Não foi possível encontrar um médico disponível nessa data.
                    """);
        }

        var consulta = new Consulta(medico, paciente, dados.data());

        this.consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null) {
            var medico = this.medicoRepository.getReferenceById(dados.idMedico());
            validarDisponibilidadeMedico(medico, dados.data());
            return medico;
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando médico não for escolhido.");
        }

        var medicoAleatorio = this.medicoRepository.getRandomByEspecialidadeAndData(dados.especialidade(), dados.data());
        validarDisponibilidadeMedico(medicoAleatorio, dados.data());
        return medicoAleatorio;
    }

    private void validarDisponibilidadeMedico(Medico medico, LocalDateTime dataConsulta) {
        if(medico == null) {
            throw new ValidacaoException("Não foi possível encontrar um " +
                    "médico nessa data e horário.");
        }

        var consultaMaisRecenteDoMedico =
                this.consultaRepository.getTop1ConsultaByMedicoIdOrderByDataDesc(medico.getId());

        if (consultaMaisRecenteDoMedico != null) {
            var dataConsultaMaisRecente = consultaMaisRecenteDoMedico.getData();
            var diferencaEmMinutosEntreConsultas = Duration.between(dataConsultaMaisRecente, dataConsulta).toMinutes();

            if (diferencaEmMinutosEntreConsultas < 60) {
                throw new ValidacaoException("O médico escolhido para essa data e horário está indisponível.");
            }
        }
    }


}
