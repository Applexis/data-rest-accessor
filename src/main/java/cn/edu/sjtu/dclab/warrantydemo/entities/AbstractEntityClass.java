package cn.edu.sjtu.dclab.warrantydemo.entities;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "entity")
@XmlType(propOrder = { "id", "name" })
public class AbstractEntityClass {
	
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) throws JAXBException {
		AbstractEntityClass cl = new AbstractEntityClass();
		cl.id = "123";
		cl.name = "haha";
	    JAXBContext context = JAXBContext.newInstance(AbstractEntityClass.class);
	    Marshaller m = context.createMarshaller();
	    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

	    // Write to System.out
	    m.marshal(cl, System.out);

	}
}
