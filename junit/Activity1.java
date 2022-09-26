package activities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Activity1 {
    static ArrayList<String> list;

    @BeforeEach
    public void setUp(){
       list = new ArrayList<String>();
       list.add("alpha");
       list.add("beta");
    }

    @Test
    public void insertTest(){
        assertEquals(2, list.size(),"Size doesn't match");

        list.add("gamma");

        assertEquals(3, list.size(), "Size doesn't match after increasing element");

        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta", list.get(1), "Wrong element");
        assertEquals("gamma", list.get(2), "Wrong element");
    }

    @Test
    public void replaceTest(){
        assertEquals(2, list.size(),"Size doesn't match");
        list.add("gamma2");
        assertEquals(3, list.size(), "Size doesn't match after increasing element");

        list.set(1,"beta2");

        assertEquals("alpha", list.get(0), "Wrong element");
        assertEquals("beta2", list.get(1), "Wrong element");
        assertEquals("gamma2", list.get(2), "Wrong element");
    }
}
