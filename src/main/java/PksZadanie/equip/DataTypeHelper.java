/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PksZadanie.equip;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mathis
 */
public class DataTypeHelper {

    //  public byte[] byteArray;
    //  public byte singleByte;
    /*
     public DataTypeHelper(byte[] byteArray) {
     this.byteArray = byteArray;
  
     }

     public DataTypeHelper(byte singleByte) {
     this.singleByte = singleByte;
     }
     */
    public static String UnitConverter(double size) {

        String output;

        DecimalFormat temp1 = new DecimalFormat("#,#0.0");
        double temp = size;
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int i = 0;
        while (temp > 1) {
            i++;
            temp = temp / (1024);
            System.out.println(temp);
        }
        temp = temp * 1024;
        double out = new Double(temp1.format(temp)).doubleValue();
        output = out + " " + units[i - 1];// + "( " + out2 + " B )";

        return output;
    }

    public static Integer singleToInt(byte singleByte) {
        Integer result = 0;
        result = (singleByte & 0xff);
        return result;

    }

    public static Integer toInt(byte[] byteArray) {
        Integer result = 0;

        for (int i = 0; i < byteArray.length - 1; i++) {
            result = ((byteArray[i] & 0xff) << 8) | ((byteArray[i + 1] & 0xff));

        }
        return result;

    }

    public static String bToString(byte singleByte) {
        StringBuilder newString = new StringBuilder();
        newString.append(String.format("%02X ", singleByte));
        return newString.toString();
    }

    public static String macAdressConvertor(byte[] macAdressByteArray) {
        String macAdress = null;
        for (int i = 0; i < 6; i++) {
            if (macAdress != null) {
                macAdress = macAdress + " " + DataTypeHelper.bToString(macAdressByteArray[i]);
            } else {
                macAdress = DataTypeHelper.bToString(macAdressByteArray[i]);
            }
        }
        return macAdress;

    }

    public static String ipAdressConvertor(byte[] ipAdressByteArray) {
        String ipAdress = null;
        for (int i = 0; i < 4; i++) {
            if (ipAdress != null) {
                ipAdress = ipAdress + "." + DataTypeHelper.singleToInt(ipAdressByteArray[i]);
            } else {
                ipAdress = DataTypeHelper.singleToInt(ipAdressByteArray[i]).toString();
            }
        }
        return ipAdress;

    }

    public static Integer getIhl(byte rByte) {
        Integer output = 0;
        output = DataTypeHelper.singleToInt(rByte);
        output = output & 0x0F;
        return output;
    }

    public static ArrayList<String> getTcpPortFlags(Frame frame) {
        ArrayList<String> flags = new ArrayList<>();
        byte flagByte;
        Integer mask = 1;
        Integer value = 0;
        flagByte = frame.getIpv4parser().getTcpParser().getFlags();
        for (int i = 0; i < 8; i++) {
            value = 0;
            value = DataTypeHelper.singleToInt(flagByte) & mask;
            if (value > 0 && i == 7) {
                flags.add("CWR");
            } else if (value > 0 && i == 6) {
                flags.add("ECE");
            } else if (value > 0 && i == 5) {
                flags.add("URG");
            } else if (value > 0 && i == 4) {
                flags.add("ACK");
            } else if (value > 0 && i == 3) {
                flags.add("PSH");
            } else if (value > 0 && i == 2) {
                flags.add("RST");
            } else if (value > 0 && i == 1) {
                flags.add("SYN");
            } else if (value > 0 && i == 0) {
                flags.add("FIN");
            }
            mask = mask << 1;
        }
        return flags;
    }

    public static String getIcmpType(Integer type) throws FileNotFoundException {
        String typeMessage = null;
        try {

            FileReader file = new FileReader("F:\\Moje dokumenty\\Martin HUdec\\škola\\FIIT\\4. sem\\PKS\\pkspkspks\\src\\main\\java\\files\\IcmpTypes.txt");
            Scanner scan = new Scanner(file);
            while (scan.hasNext()) {
                if (scan.hasNextInt()) {
                    if (scan.nextInt() == type) {
                        while (scan.hasNextInt() != true) {
                            if (typeMessage == null) {
                                typeMessage = scan.next();
                            } else {
                                typeMessage += " " + scan.next();
                            }
                        }
                        break;
                    }
                } else {
                    scan.next();
                }
            }
            file.close();
        } catch (IOException e) {
        }

        return typeMessage;
    }
}
