/*
 * Trabalho Simplex
 * Autores:Leandro;Jonathan;Yuri  * 
 */
package Domain.DTO;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Leandro;Jonathan;Yuri
 */
@XmlRootElement(name = "RetornoJsonDTO")
public class RetornoJsonDTO {
    @XmlElement(name="data")
    private List data;
    @XmlElement(name="error")
    private boolean error;
    @XmlElement(name="errorMessage")
    private String errorMessage;

    /**
     * @return the data
     */
    
    public List getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List data) {
        this.data = data;
    }

    /**
     * @return the error
     */
    public boolean isError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(boolean error) {
        this.error = error;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
