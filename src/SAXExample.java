import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXExample {
    final String fileName = "phonebook.xml";

    DefaultHandler handler = new DefaultHandler() {
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            System.out.println("\t<" + qName + ">");
            // Если есть атрибуты, выводим их
            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println("\t\t" + attributes.getQName(i) + " = " + attributes.getValue(i));
            }
        }

        @Override
        public void characters(char ch[], int start, int length) throws SAXException {
            String content = new String(ch, start, length).trim();
            if (!content.isEmpty()) {
                System.out.println("\t\t" + content);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            System.out.println("\t</" + qName + ">");
        }

        @Override
        public void startDocument() throws SAXException {
            System.out.println("Начало разбора документа!");
        }

        @Override
        public void endDocument() throws SAXException {
            System.out.println("Разбор документа завершен!");
        }
    };

    public SAXExample() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            // Стартуем разбор XML-документа
            saxParser.parse(fileName, handler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new SAXExample();
        System.exit(0);
    }
}