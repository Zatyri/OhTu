package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoOikeinAlussa(){
        assertTrue(kortti.saldo() == 10);
    }
    
    @Test
    public void kortinSaldoKasvaaKunLisaaRahaa(){
        kortti.lataaRahaa(10);
        assertTrue(kortti.saldo() == 20);
    }
    
    @Test
    public void kortinSaldoVaheneeKunOttaaRahaa(){
        kortti.otaRahaa(5);
        assertTrue(kortti.saldo() == 5);
    }
    
    @Test
    public void kortinSaldoEiMuutuKunOnLiianVahanRahaa(){
        kortti.otaRahaa(1000000);
        assertTrue(kortti.saldo() == 10);
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosTarpeeksiRahaa(){
        assertTrue(kortti.otaRahaa(5));
    }
    
        @Test
    public void otaRahaaPalauttaaFalseJosLiianVahanRahaa(){
        assertFalse(kortti.otaRahaa(100000));
    }
}
