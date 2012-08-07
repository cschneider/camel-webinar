/**
 * Copyright (C) 2010 Talend Inc. - www.talend.com
 */
package docservice.service;

import java.io.FileOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import docservice.common.DocService;

public class DocServiceImpl implements DocService {

    @Override
    public DataHandler read(String id) {
        FileDataSource fds = new FileDataSource("target/" + id);
        return new DataHandler(fds);
    }

    @Override
    public void write(String id, DataHandler content) {
        try {
            FileOutputStream fos = new FileOutputStream("target/" + id);
            content.writeTo(fos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
