package tech.candra.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmationToken {

    private String password;
    private String email;
    @JsonIgnore
    private String token;
}
