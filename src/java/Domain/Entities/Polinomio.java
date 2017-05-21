/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Entities;

import Domain.Commom.enumCondicao;
import Domain.Commom.DomainBase;
import Domain.Repository.Hibernate.Repositories.PolinomioRepository;
import java.util.List;

/**
 *
 * @author Leandro
 */
public class Polinomio extends DomainBase<PolinomioRepository> {

    public Polinomio() {
    }

       List<Variavel> expressao;
    enumCondicao condicao;
    double valor;  
    
    
}
