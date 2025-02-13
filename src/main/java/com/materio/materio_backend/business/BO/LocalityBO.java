package com.materio.materio_backend.business.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalityBO {
    private String name;
    private int cp;
    private String address;
    private String city;
}
