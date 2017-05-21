/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Entities;

import Domain.Commom.DomainBase;
import Domain.Repository.Hibernate.Repositories.VariavelRepository;

/**
 *
 * @author Leandro
 */
public class Variavel extends DomainBase<VariavelRepository> {

     double valor;
    
    public Variavel(double n) {
        setValor(n);
    }
    
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
    
}
