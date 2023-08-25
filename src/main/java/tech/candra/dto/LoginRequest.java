package tech.candra.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
//@Setter
public class LoginRequest {
    private String username;
    private String password;
    @JsonIgnore
    private Boolean isActive;
}
