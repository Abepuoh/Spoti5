package com.Abe.Spoti.MariaDBConnection;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.Abe.Spoti.App;

public class xmlConnection {
	
	  public static String getConectionInfo(String data) {
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    factory.setNamespaceAware(true);
		    DocumentBuilder builder;
		    Document doc = null;
		    String url = null;
		    try {
		      builder = factory.newDocumentBuilder();
		      doc = builder.parse(App.class.getResourceAsStream("server.xml"));

		      // Create XPathFactory object
		      XPathFactory xpathFactory = XPathFactory.newInstance();

		      // Create XPath object
		      XPath xpath = xpathFactory.newXPath();
		      XPathExpression expr = xpath.compile("/conexion/" + data + "/text()");
		      url = (String) expr.evaluate(doc, XPathConstants.STRING);

		    } catch (XPathExpressionException e) {
		      e.printStackTrace();
		    } catch (SAXException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    } catch (IOException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    } catch (ParserConfigurationException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }

		    return url;
		  }	
}