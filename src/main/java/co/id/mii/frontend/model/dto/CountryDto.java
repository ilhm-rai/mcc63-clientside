/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.frontend.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

/**
 *
 * @author RAI
 */
@Data
public class CountryDto {

    private Long id;

    @NotEmpty(message = "Code is required")
    private String code;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotNull(message = "Region is required")
    private Long regionId;
}
