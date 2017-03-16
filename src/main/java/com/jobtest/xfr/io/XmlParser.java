package com.jobtest.xfr.io;

import com.google.inject.Singleton;
import com.jobtest.xfr.pool.UnmarshallerFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Singleton
public class XmlParser {


    private GenericObjectPool<Unmarshaller> unmarshallerGenericObjectPool;

    public XmlParser() throws JAXBException {


        unmarshallerGenericObjectPool = new GenericObjectPool<>(new UnmarshallerFactory());
        unmarshallerGenericObjectPool.setBlockWhenExhausted(true);
        unmarshallerGenericObjectPool.setMaxTotal(20);
        unmarshallerGenericObjectPool.setMaxIdle(-1);
        unmarshallerGenericObjectPool.setMaxWaitMillis(-1);


    }

    public Object getObject(File file) throws Exception {

        Unmarshaller unmarshaller = unmarshallerGenericObjectPool.borrowObject();
        Object unmarshaled = unmarshaller.unmarshal(file);
        unmarshallerGenericObjectPool.returnObject(unmarshaller);
        return unmarshaled;
    }

    public void saveObject(File file, Object o) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(o.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(o,file);
    }

}
