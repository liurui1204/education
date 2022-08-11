package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Helper;

import cn.hutool.core.bean.BeanUtil;
import com.mohe.nanjinghaiguaneducation.common.crontab.ApplicationContextUtil;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity.NJRespMsgEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Settings;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

public class NJXmlHelper {
    public NJXmlHelper() {
    }

    public static String Serialize(Object obj) {
        try {
            Settings settings = (Settings) ApplicationContextUtil.getBean("scheduleSettings");
            JAXBContext context = JAXBContext.newInstance(new Class[]{obj.getClass()});
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty("jaxb.encoding", settings.getXmlEncoding());
            marshaller.setProperty("jaxb.formatted.output", false);
            marshaller.setProperty("jaxb.fragment", true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj, writer);
            return writer.toString();
        } catch (Exception var4) {
            throw new RuntimeException(var4);
        }
    }

    public static <T> T Deserialize(String xml, Class<T> valueType) {
        try {
            JAXBContext context = JAXBContext.newInstance(valueType);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xml = xml.trim();

            return (T)unmarshaller.unmarshal(new StringReader(xml));

            /*
            XMLInputFactory factory = XMLInputFactory.newInstance();
//get Reader connected to XML input from somewhere..
            Reader reader = getXmlReader();
            try {
                XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
                return unmarshaller.unmarshal(streamReader, valueType);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
//            unmarshaller.unmarshal(streamReader, valueType);
//            if(BeanUtil.isNotEmpty(unmarshal)){
////                BeanUtil.copyProperties(T, unmarshal);
//                return (valueType)unmarshal;
//            }
            return null;
            */
        } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
        }
    }

    public static NJRespMsgEntity DeserializeType(String xml) {
        try {
            JAXBContext context = JAXBContext.newInstance(NJRespMsgEntity.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            xml = xml.trim();

            return (NJRespMsgEntity)unmarshaller.unmarshal(new StringReader(xml));

            /*
            XMLInputFactory factory = XMLInputFactory.newInstance();
//get Reader connected to XML input from somewhere..
            Reader reader = getXmlReader();
            try {
                XMLStreamReader streamReader = factory.createXMLStreamReader(reader);
                return unmarshaller.unmarshal(streamReader, valueType);
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
//            unmarshaller.unmarshal(streamReader, valueType);
//            if(BeanUtil.isNotEmpty(unmarshal)){
////                BeanUtil.copyProperties(T, unmarshal);
//                return (valueType)unmarshal;
//            }
            return null;
            */
        } catch (Exception var4) {
            throw new RuntimeException(var4.getMessage());
        }
    }
}
