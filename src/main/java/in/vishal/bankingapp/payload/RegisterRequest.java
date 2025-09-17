package in.vishal.bankingapp.payload;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class RegisterRequest {
    @NotBlank private String username;
    @NotBlank private String password;
    private String email;
}
