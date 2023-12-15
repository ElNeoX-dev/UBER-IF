package com.malveillance.uberif.xml;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

class XMLfileOpenerTest {

    @Test
    void testAcceptWithXmlFile() {
        XMLfileOpener fileOpener = XMLfileOpener.getInstance();
        File xmlFile = new File("test.xml");

        assertTrue(fileOpener.accept(xmlFile));
    }

    @Test
    void testAcceptWithNonXmlFile() {
        XMLfileOpener fileOpener = XMLfileOpener.getInstance();
        File nonXmlFile = new File("test.txt");

        assertFalse(fileOpener.accept(nonXmlFile));
    }

    @Test
    void testAcceptWithDirectory() {
        XMLfileOpener fileOpener = XMLfileOpener.getInstance();
        File directory = new File("src");

        assertTrue(fileOpener.accept(directory));
    }

    @Test
    void testAcceptWithNull() {
        XMLfileOpener fileOpener = XMLfileOpener.getInstance();

        assertFalse(fileOpener.accept(null));
    }

    @Test
    void testGetDescription() {
        XMLfileOpener fileOpener = XMLfileOpener.getInstance();

        assertEquals("XML file", fileOpener.getDescription());
    }
}
