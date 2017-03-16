package com.antin.mr.test.tomysql3;

/**
 * Created by Administrator on 2017/3/14.
 */

import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Dom4j 解析XML文档
 */
public class Dom4jDemo {

    public static void parserXml(String xml) {
        // File inputXml = new File(fileName);
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(xml);
            Element users = document.getRootElement();
            for (Iterator i = users.elementIterator(); i.hasNext(); ) {
                Element user = (Element) i.next();
                for (Iterator j = user.elementIterator(); j.hasNext(); ) {
                    Element node = (Element) j.next();
                    System.out.println(node.getName() + ":" + node.getText());
                }
                System.out.println();
            }
        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        }
    }

}