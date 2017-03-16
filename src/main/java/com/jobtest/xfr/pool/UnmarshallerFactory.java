package com.jobtest.xfr.pool;

import com.jobtest.xfr.domain.Entry;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Created by goddenis on 07.02.2017.
 */
public class UnmarshallerFactory extends BasePooledObjectFactory<Unmarshaller>{


    final JAXBContext context ;

    public UnmarshallerFactory() throws JAXBException {
        context = JAXBContext.newInstance(Entry.class);
    }


    @Override
    public Unmarshaller create() throws Exception {
        return context.createUnmarshaller();
    }

    @Override
    public PooledObject<Unmarshaller> wrap(Unmarshaller obj) {
        return new DefaultPooledObject<>(obj);
    }
}
