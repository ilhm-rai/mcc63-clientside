/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author RAI
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {
    
    private Long id;
    private String code;
    private String name;
    private Region region;
}