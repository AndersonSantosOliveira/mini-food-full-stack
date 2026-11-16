package br.com.adsuema.minifood.dto.clientedto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DadosDeAtulizacaoDoCliente(
        @Schema(example = "user name")
        @Pattern(regexp = ".*\\S.*", message = "O nome não pode ficar em branco")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,

        @Schema(example = "user@gmail.com")
        @Pattern(regexp = ".*\\S.*", message = "O e-mail não pode ficar em branco")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "O e-mail deve ter no máximo 150 caracteres")
        String email,

        @Schema(example = "(98)98574-1236")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Formato de telefone inválido (ex: 11999998888 ou (11) 99999-8888)"
        )
        String telefone

) {


}
