/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.nuig.entitylinking.main.nel;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jmccrae
 */
public class NIFInputTest {
    
    public NIFInputTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class NIFInput.
     */
    @Test
    public void testConvert() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        NIFInput input = mapper.readValue("{\n" +
"  \"@context\": \"http://mixedemotions-project.eu/ns/context.jsonld\",\n" +
"  \"@id\": \"http://example.com#NIFExample\",\n" +
"  \"analysis\": [\n" +
"  ],\n" +
"  \"entries\": [\n" +
"    {\n" +
"      \"@id\": \"http://example.org#char=0,40\",\n" +
"      \"@type\": [\n" +
"        \"nif:RFC5147String\",\n" +
"        \"nif:Context\"\n" +
"      ],\n" +
"      \"nif:beginIndex\": 0,\n" +
"      \"nif:endIndex\": 40,\n" +
"      \"nif:isString\": \"My favourite actress is Natalie Portman\"\n" +
"    }\n" +
"  ]\n" +
"}", NIFInput.class);
        System.out.println(mapper.writeValueAsString(input));
    }
}