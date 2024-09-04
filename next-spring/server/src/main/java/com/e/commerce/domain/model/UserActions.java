package com.e.commerce.domain.model;


import com.e.commerce.domain.common.BaseModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userActions")
public class UserActions extends BaseModel {

    private String username;
    private String ip;
    private String methodName;
    private String arguments;
    private String result;
    private LocalDateTime timestamp;
}
