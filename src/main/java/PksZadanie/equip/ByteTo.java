/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PksZadanie.equip;

/**
 *
 * @author Mathis
 */
public class ByteTo {

    public byte[] byteArray;
    public byte singleByte;
  

    public ByteTo(byte[] byteArray) {
        this.byteArray = byteArray;
  
    }

    public ByteTo(byte singleByte) {
        this.singleByte = singleByte;
    }

    public Integer singleToInt() {
        Integer result = 0;
        result = (singleByte & 0xff);
        return result;

    }

    public Integer toInt() {
        Integer result = 0;

        for (int i = 0; i < byteArray.length - 1; i++) {
            result = ((byteArray[i] & 0xff) << 8) | ((byteArray[i + 1] & 0xff));

        }
        return result;

    }

    public String bToString() {
        StringBuilder newString = new StringBuilder();
        newString.append(String.format("%02X ", singleByte));
        return newString.toString();
    }
}