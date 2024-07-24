package med.voll.api.paciente;

public record DadosListagemPaciente(
        Long id,
        String email,
        String cpf
) {
    public DadosListagemPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getEmail(), paciente.getCpf());
    }
}
