/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Commom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 *
 * @author Leandro
 */
public abstract class ListUtil<X,Y>{
   
public static <X,Y> List<X> convertToDTO(Collection<Y> ys, IListUtil<X,Y> c) {
    List<X> r = new ArrayList<X>(ys.size());
    for (Y y : ys) {
        r.add(c.toDTO(y));
    }
    return r;
}
    

}
