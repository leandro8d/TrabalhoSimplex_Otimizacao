/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain.Commom;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 *
 * @author Leandro
 */
public class Util {

    public List<String> JsonArrayStringToList(JSONArray js) throws JSONException {

        List<String> lista1 = new ArrayList<String>();
        for (int i = 0; i < js.length(); i++) {
            lista1.add(js.getString(i));
        }
        return lista1;
    }
    
    public List<Integer> JsonArrayIntToList(JSONArray js, Type as) throws JSONException {

        List<Integer> lista1 = new ArrayList<Integer>();
        for (int i = 0; i < js.length(); i++) {
            lista1.add(js.getInt(i));
        }
        return lista1;
    }

}
