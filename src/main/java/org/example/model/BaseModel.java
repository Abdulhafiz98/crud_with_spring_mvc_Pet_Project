package org.example.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModel {
    protected int id;
    protected String createdBy;
    protected LocalDateTime createdDate;
    protected LocalDateTime updatedDate;

}
