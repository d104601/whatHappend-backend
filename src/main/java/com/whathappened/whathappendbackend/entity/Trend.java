package com.whathappened.whathappendbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trend {
}
