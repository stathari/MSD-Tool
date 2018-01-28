package com.src.mapUtility;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;    
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xerces.xs.XSModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.src.exception.mapUtility.MappingUtilityException;




public class Test {

    public static void main(String[] args) throws Exception {
        String each_row="",cell_value="";
        FileInputStream fis = null;
        List<String> data = new ArrayList<String>();
        int colmncount=0;
        try {
            //Mapping sheet. 
            fis = new FileInputStream(args[0]);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);
            System.out.println("Sheet value"+sheet);
            Iterator rows = sheet.rowIterator();
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                colmncount=row.getLastCellNum();
                Iterator cells = row.cellIterator();
                each_row="";
                while (cells.hasNext()) 
                {
                    if(each_row.length()!=0){
                        each_row=each_row+"/";
                    }
                    XSSFCell cell = (XSSFCell) cells.next();
                    cell_value=cell.getStringCellValue();
                    each_row=each_row+cell_value;    
                }
                if(!"".equals(each_row.trim())){
                data.add(each_row);
                }
            }
            System.out.println(data);
        } 
        catch (IOException e) {
            throw new MappingUtilityException(e);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        try {
            //XSD File
            String filename = args[1];
        //	 String filename = "C:/mappingfile/Sample1.xsd";
            final Document doc = loadXsdDocument(filename);
            //Find the docs root element and use it to find the targetNamespace
            final Element rootElem = doc.getDocumentElement();
            String targetNamespace = null;
            if (rootElem != null && rootElem.getNodeName().equals("xsd:schema")) {
                targetNamespace = rootElem.getAttribute("targetNamespace");
            } 
            //Parse the file into an XSModel object
            XSModel xsModel = new XSParser().parse(filename);
            //Define defaults for the XML generation
            XSInstance instance = new XSInstance();
            instance.minimumElementsGenerated = 1;
            instance.maximumElementsGenerated = 1;
            instance.generateDefaultAttributes = true;
            instance.generateOptionalAttributes = true;
            instance.generateFixedAttributes= true;
            instance.maximumRecursionDepth = 0;
            instance.generateAllChoices = true;
            instance.showContentModel = true;
            instance.generateOptionalElements = true;
            StringWriter outWriter = new StringWriter();
            //StreamResult result1 = new StreamResult( outWriter );
            //Build the sample xml doc
            //Replace first param to XMLDoc with a file input stream to write to file
            //Operation Name
            //QName rootElement = new QName(targetNamespace, "MatDressResultSnd");
            QName rootElement = new QName(targetNamespace, args[3]);
            //XMLD 
            XMLDocument sampleXml = new XMLDocument(new StreamResult(outWriter), true, 4, null);
            //sampleXml.startElement("MatDressResultSnd");  args[3]
            sampleXml.startElement(args[3]);
            instance.generate(xsModel, rootElement, sampleXml);
            //    transformer.transform( source, result );  
            StringBuffer sb = outWriter.getBuffer(); 
            String finalstring = sb.toString();
            //System.out.println("final string: "+finalstring);
            //System.out.println(System.getenv("MAPPING_PATH"));
            File file = new File(args[2]+"/"+args[3]+".xml");
          //  File file = new File("C:/mappingfile/Sample.xml");
            //File file = new File(System.getenv("mappingfilepath")+"Sample.xml");
            // if file doesnt exists, then create it
            if (file.exists()) {
                file.delete();
                // System.out.println("File exist");
                file.createNewFile();
            }else{
                System.out.println();
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(finalstring);
            bw.close(); 
        } catch (TransformerConfigurationException e){
            throw new MappingUtilityException(e);
        } catch (SAXException e) {
            throw new MappingUtilityException(e);
        }
        try {
        	int loaf=0, count=0;
           // File file = new File("C:/mappingfile/Sample.xml");
        	String filename=args[2]+"/"+args[3]+".xml";
           File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            System.out.println("Root element " + doc.getDocumentElement().getNodeName());
            System.out.println("System.getenv(mappingfilepath)"+System.getenv("mappingfilepath"));
            //File fileWrite = new File("C:/mappingfile/writeFile.csv");
            File fileWrite = new File(args[2]+"/"+args[3]+"_Result.csv");
            //File fileWrite = new File(System.getenv("mappingfilepath")+"writeFile.csv");
            if (fileWrite.exists()) {
                System.out.println("Checking file exists");
                fileWrite.delete();
                // System.out.println("File exist");
                fileWrite.createNewFile();
            }else{
                System.out.println("creating new file");
                fileWrite.createNewFile();
            }
            /*for(int i=0;i<data.size();i++){
            String nodestring= (String)data.get(i);
                String[] arr=nodestring.split(",");
                int loa=arr.length, loaf=arr.length, count=0;
                System.out.println("length of excel array"+loa);
                System.out.println(arr[loa-1]);
                NodeList nodeLst = doc.getElementsByTagName(arr[--loa]);
                for(int j=0;j<nodeLst.getLength();j++){
                System.out.println("nodeLst values J values"+j+"====="+nodeLst.item(j).getParentNode());
                }
            }*/

            for(int i=0; i< data.size();i++){
                String nodestring= (String) data.get(i);
                if(!nodestring.contains("/")){
                	continue;
                }
                String[] arr=nodestring.split("/");
              //  String nstringvalue=null;
                int loa=arr.length;
                loaf=arr.length;
                
                System.out.println("length of array "+loa);
                System.out.println("array main child value "+ arr[loa-1]);
                String a= Test.nodeName(filename, arr[loa-1]);
                NodeList nodeLst = doc.getElementsByTagName(a);
            	
            	//System.out.println("Nodelist names"+ nl.toString());
                System.out.println(" length of nodelist" + nodeLst.getLength());
                int s=0;
              
                count=0;
                for(int j=0;j<nodeLst.getLength();j++){
                    System.out.println("nodeLst top parent values for J value "+j+"====="+nodeLst.item(j).getParentNode().getParentNode().getParentNode());
                    }
               
                while ( s < nodeLst.getLength() ) {
                	count=0;
                	Node fstNode = nodeLst.item(s);
                   // System.out.println("Nodelst Values in for loop "+s+"====="+nodeLst.item(s).getParentNode());
                    System.out.println("node "+fstNode);
                    //loa--;
                    
                   Element fstElmnt = (Element) fstNode;
                   Element fstElmnt1 = fstElmnt;
                    int loa1=loa;
                    int loa2=0;
                    //loa1--;
                    if(fstElmnt1.getNodeName().equalsIgnoreCase(args[3])){
           			 loa2=loa1;
           			 System.out.println("checking root before while loop");
           			 Thread.sleep(5000);
           			 count++;
           			 break;
           		 }
                   int k=0;
                   Thread.sleep(2000);
                    	 while(loa1>0 && (fstElmnt1.getNodeName().length())!=0)
	                    	 {
                    		 System.out.println("array element "+ arr[loa1-1]+ " node element "+ fstElmnt1.getNodeName());
                    		 if(loa1>=1){
                    		 loa1--;
                    		 //if((arr[loa1].toUpperCase()).equals(fstElmnt1.getNodeName().toUpperCase())){
                    			 if(arr[loa1].equalsIgnoreCase(fstElmnt1.getNodeName())){
                    			 System.out.println("array element "+ arr[loa1]+ " node element "+ fstElmnt1.getNodeName());
	                    		 System.out.println("in if block");
	                    		 count++;
	                    		 //loa1--;
	                    		 System.out.println("loop count"+ ++k);
	                    		 System.out.println("count "+count+ " loa1 "+ loa1);
	                    		 System.out.println("root element"+ args[3]);
	                    		 
	                    	
	                    		 if(fstElmnt1.getNodeName().equalsIgnoreCase(args[3])){
	                    			// loa2=loa1;
	                    			 System.out.println("checking for root element");
	                    			// Thread.sleep(5000);
	                    			 count++;
	                    			
	                    		 }
	                    		 else{
	                    			 fstElmnt1=(Element) fstElmnt1.getParentNode();
		                    		 System.out.println("Element fstElmnt1 "+ fstElmnt1);
	                    			 
	                    		 }
	                    	 //Thread.sleep(5000);
	                    		 if(count==loaf){
	                    			 break;
	                    			 
	                    		 }
	                    		
                    		 	}
                    		 else{
                    			 s++;
                    			
                    			 break;
                    			 
                    		 }
                    		 }
                    		 
                    	              		 
	                     }
                    	 
                    	 if(count==loa){
                    		 System.out.println("count "+count+ " loa "+ loa);
                			 s++;
                			 break;
                		 }
                
                }
                if(loaf!=count){
                    System.out.println("Entered if of loaf!=count block");

                    int nscount;
                    FileWriter fw = new FileWriter(fileWrite, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    System.out.println("Nodestring values in 2nd if block "+nodestring.toString());
                    nscount=nodestring.split("/").length;
                    String nodestringfinal=" ";
                    while(nscount<colmncount){
                        nodestringfinal=nodestringfinal.concat(",");
                        nodestringfinal=nodestringfinal.concat(nodestring);
                        System.out.println("file string "+ nodestringfinal);
                        nscount++;
                    }
                    if(nodestringfinal!=" "){
                        bw.append(nodestringfinal);}
                    else
                        bw.append(nodestring);
                    bw.newLine(); 
                    bw.close();   
                }
            }
            
        }
        catch (Exception e) {
            throw new MappingUtilityException(e);
        }
    }

    public static Document loadXsdDocument(String inputName) throws MappingUtilityException {
        final String filename = inputName;

        final DocumentBuilderFactory factory = DocumentBuilderFactory
                .newInstance();
        factory.setValidating(false);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setIgnoringComments(true);
        Document doc = null;

        try {
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final File inputFile = new File(filename);
            doc = builder.parse(inputFile);
        } catch (Exception e) {
            throw new MappingUtilityException(e);
        }
        return doc;
    }
    
    public static String nodeName(String Xmlfile, String arrname)throws SAXException, IOException, ParserConfigurationException, TransformerException {

DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
        .newInstance();
DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
Document document = docBuilder.parse(new File(Xmlfile));

NodeList nodeList = document.getElementsByTagName("*");
for (int i = 0; i < nodeList.getLength(); i++) {
    Node node = nodeList.item(i);
    if (node.getNodeType() == Node.ELEMENT_NODE) {
        // do something with the current element
    	if(node.getNodeName().equalsIgnoreCase(arrname)){
    		System.out.println("returned from nodename function "+node.getNodeName());
    		return node.getNodeName();
    	}
        //System.out.println(node.getNodeName());
    }
}
return arrname;
}
}