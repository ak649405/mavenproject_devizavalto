package hu.gov.allamkincstar.mavenproject_devizavalto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import static java.sql.Types.NULL;
import java.util.ArrayList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DevizaValto {

    private static float forintArfolyam;
    private static final String FILENAME = "arfolyam.xml";


    
    public static java.util.List<String> xmlBeolvas() {

        java.util.List<String> arfolyamok = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Cube");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                if (elem.getAttribute("currency") != null && !elem.getAttribute("currency").isEmpty()) {
                    arfolyamok.add(elem.getAttribute("currency") + "-" + elem.getAttribute("rate"));
                    if ("HUF".equalsIgnoreCase(elem.getAttribute("currency"))) {
                        forintArfolyam = Float.valueOf(elem.getAttribute("rate"));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return arfolyamok;
    }


    public static java.util.List<String> webBeolvas() {

        java.util.List<String> arfolyamok = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
           
            URL url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml");
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));

            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Cube");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elem = (Element) nodeList.item(i);
                if (elem.getAttribute("currency") != null && !elem.getAttribute("currency").isEmpty()) {
                    arfolyamok.add(elem.getAttribute("currency") + "-" + elem.getAttribute("rate"));
                    if ("HUF".equalsIgnoreCase(elem.getAttribute("currency"))) {
                        forintArfolyam = Float.valueOf(elem.getAttribute("rate"));
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return arfolyamok;
    }

    public static float valtas( float osszeg, String a ) {

           // Váltás !!!
           // A beírt összeget osztom a kiválasztott deviza árfolyamával, amjd a kapott értéket szorzom a forintnál megtalálható árfolyammal.
           float kivalasztottArfolyam;
           float eredmeny;

           //System.out.println( a );

           if      ( osszeg == 0 || osszeg == NULL ){
              System.out.println( "Érték hiba!" );
              eredmeny = 0;
           }
           else if ( a.isEmpty() ) {
              System.out.println( "Nincs kiválasztva valuta!" );
              eredmeny = 0;
           }
           else {
               kivalasztottArfolyam = Float.valueOf( a.toString().substring( a .toString().indexOf("-") + 1));
               eredmeny = ( osszeg / kivalasztottArfolyam ) * forintArfolyam;
               System.out.println( "Fizetendő:"+ String.valueOf(Math.round(eredmeny)) + " Ft" ) ;
               // eredmeny = Math.round(atvaltottOsszegForintban);
           }
           
           return eredmeny;
    }

    public static void arfolyamXmlLeszed() throws MalformedURLException  {
        
        URL url;
        url = new URL("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"); 
        String file;
        file = "c:\\users\\antalk\\NetBeansProjects\\mavenproject_devizavalto\\arfolyam.xml";

        try ( 
           BufferedInputStream in = new BufferedInputStream(url.openStream());
           FileOutputStream fileOutputStream = new FileOutputStream(file) ) {
           byte dataBuffer[] = new byte[1024];
           int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
            fileOutputStream.write(dataBuffer, 0, bytesRead);
           }
     
           } catch (IOException e) {
               System.out.println( "Hiba az 'arfolyam.xml' létrehozásánál!" );
               // Logger.getLogger(DevizaValto.class.getName()).log(Level.SEVERE, null, ex);             
               // handle exception
           }
  
}
/* */

}
