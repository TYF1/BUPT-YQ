package teleDemo.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class tbuserTest {
    @Test
    void setPhoneNumber() {
        tbuser tbuserTest = new tbuser();
        tbuserTest.setPhoneNumber("19855520985");
        assertEquals("19855520985",tbuserTest.getPhoneNumber());
    }

    @Test
    void getId() {
        tbuser tbuserTest = new tbuser();
        assertEquals(0,tbuserTest.getId());
    }

    @Test
    void getPhoneNumber() {
        tbuser tbuserTest = new tbuser();
        assertEquals(null,tbuserTest.getPhoneNumber());
    }

    @Test
    void getStatus() {
        tbuser tbuserTest = new tbuser();
        assertEquals(null,tbuserTest.getStatus());
    }
}