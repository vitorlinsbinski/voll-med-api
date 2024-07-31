package med.voll.api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosRegistroUsuario(
        @NotBlank
        @Email
        String login,
        @NotBlank
        @Pattern(regexp = ".{8,}", message = "A senha deve ter no mínimo 8 " +
                "caracteres")
        String senha
) {
}
