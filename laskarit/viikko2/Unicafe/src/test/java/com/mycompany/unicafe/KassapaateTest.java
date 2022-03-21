package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    
    Kassapaate kassapaate;
    Maksukortti kortti;
    
    @Before
    public void SetUp(){
        kassapaate = new Kassapaate();  
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void alkuSaldoOikea(){
        assertTrue(kassapaate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void myytyjenLounaidenMaaraAlussa0(){
        assertTrue(kassapaate.edullisiaLounaitaMyyty() + kassapaate.maukkaitaLounaitaMyyty() == 0);
    }
    
    @Test
    public void kassanSaldoKasvaaKunLounasOstetaanJaMyytyjenLounaidenMaaraKasvaaSekaVaihtorahaOnOikea(){
        int vaihtoRaha = kassapaate.syoEdullisesti(300);
        assertTrue(vaihtoRaha == 60 && kassapaate.kassassaRahaa() == 100240);
        vaihtoRaha = kassapaate.syoMaukkaasti(410);
        assertTrue(vaihtoRaha == 10 && kassapaate.kassassaRahaa() == 100640);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 1 && kassapaate.maukkaitaLounaitaMyyty() == 1);
    }
    
    @Test
    public void kassanSaldoEiMuutuKunLiianVahanRahaa(){
        int palautetaan = kassapaate.syoEdullisesti(200);
        palautetaan += kassapaate.syoMaukkaasti(20);
        assertTrue(kassapaate.kassassaRahaa() == 100000);
        assertTrue(palautetaan == 220);
    }
    
    @Test
    public void onnistunutMaksukorttiOstosPalauttaaTrueJaLounaidenMaaraKasvaa(){
        boolean onnistui = kassapaate.syoEdullisesti(kortti);
        assertTrue(onnistui);
        onnistui = kassapaate.syoMaukkaasti(kortti);
        assertTrue(onnistui);
        assertTrue(kassapaate.edullisiaLounaitaMyyty() == 1 && kassapaate.maukkaitaLounaitaMyyty() == 1);
        assertTrue(kassapaate.kassassaRahaa() == 100000);

    }
    
    @Test
    public void epaOnnistunutMaksukorttiOstosPalauttaaFalseEikaKasvataMyytyjenLounaidenMaaraa(){
            Maksukortti toinenKortti = new Maksukortti(200);
            boolean onnistui = kassapaate.syoEdullisesti(toinenKortti);
            assertTrue(!onnistui);
            onnistui = kassapaate.syoMaukkaasti(toinenKortti);
            assertTrue(!onnistui);
            assertTrue(kassapaate.edullisiaLounaitaMyyty() == 0 && kassapaate.maukkaitaLounaitaMyyty() == 0);
            assertTrue(kassapaate.kassassaRahaa() == 100000);
    }
    
    @Test
    public void kortilleRahanLataaminenOnnistuu(){
        kassapaate.lataaRahaaKortille(kortti, 50);
        assertTrue(kortti.saldo() == 1050 && kassapaate.kassassaRahaa() == 100050);
    }
    
    @Test
    public void kortilleRahanLataaminenEiOnnistuuJosMaksuNegatiivinen(){
        kassapaate.lataaRahaaKortille(kortti, -50);
        assertTrue(kortti.saldo() == 1000 && kassapaate.kassassaRahaa() == 100000);
    }
}
