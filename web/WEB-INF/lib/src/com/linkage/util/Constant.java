package com.linkage.util;



import java.io.StringReader;
import java.io.StringWriter;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


 public final class Constant
{
public static String beanToXml(Object obj,Class<?> load) {
	String result = null; 
	try{
		JAXBContext context = JAXBContext.newInstance(load);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		StringWriter writer = new StringWriter();
		marshaller.marshal(obj,writer);
		result = writer.toString();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}

public static Object xmlToBean(String xmlStr,Class<?> load) {
	Object object =null;
	try{
		JAXBContext context = JAXBContext.newInstance(load);  
		Unmarshaller unmarshaller = context.createUnmarshaller(); 
		object = unmarshaller.unmarshal(new StringReader(xmlStr));
	}catch (Exception e) {
		e.printStackTrace();
	}
   return object;
}

}

