/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PksZadanie.equip;

import PksZadanie.analysers.IpV4Analyser;
import org.krakenapps.pcap.packet.PcapPacket;
import org.krakenapps.pcap.util.Buffer;

/**
 *
 * @author Mathis
 */
public final class Frame {

    private String sourceMAC;
    private final byte[] sourceMACByte = new byte[6];
    private String destinationMAC;
    private final byte[] destinationMACByte = new byte[6];
    private final Integer id;
    private final Integer frameLength;
    private final Integer frameLengthWire;
    private String frameType;
    private final PcapPacket packet;
    public Buffer buffer;
    public byte[] etherType;

    public Frame(int id, PcapPacket packet) {
        this.id = id;
        this.packet = packet;
        frameLength = packet.getPacketHeader().getInclLen();
        frameLengthWire = packet.getPacketHeader().getOrigLen();
        buffer = packet.getPacketData();
        this.findMacAdress(0);
        this.findMacAdress(1);
        this.findEtherType();
    }

    public void findEtherType() {
        Integer etherTypeInt;
        etherType = new byte[]{buffer.get(), buffer.get()};
        etherTypeInt =(Integer) new ByteTo(etherType).toInt();
        System.out.println(etherTypeInt);

        if (etherTypeInt >= 1534) {
            frameType = "Ethernet II";
            if (etherTypeInt == 2048) {
                IpV4Analyser Ipv4 = new IpV4Analyser(buffer);
                Ipv4.analyse();
                System.out.println("je to IpV4");
            }

        }
        if (etherTypeInt <= 1500) {
            byte temp = buffer.get();
            if (temp == 0xff) {
                if (buffer.get() == 0xff) {
                    frameType = "Novell raw IEEE 802.3";
                }
            } else if (temp == 0xaa) {
                if (buffer.get() == 0xaa) {
                    frameType = "IEEE 802.2 SNAP";
                }
            } else {
                System.out.print(temp);
                System.out.print(buffer.get());
                frameType = "IEEE 802.2 LLC";
            }
        }
    }

    public static int toInt(byte[] bytes) {
        Integer result = 0;
        result = ((bytes[0] & 0xff) << 8) | ((bytes[1] & 0xff));
        return result;
    }

    public void findMacAdress(Integer type) {
        if (type == 1) {
            //zdrojova MAC adresa

            for (int i = 0; i < 6; i++) {
                byte temp = buffer.get();
                ByteTo macbyte = new ByteTo(temp);
                sourceMACByte[i] = temp;
                if (sourceMAC != null) {
                    sourceMAC = sourceMAC + " " + macbyte.bToString();
                } else {
                    sourceMAC = macbyte.bToString();
                }
            }
        }
        if (type == 0) {
            // destination MAC adress
            for (int i = 0; i < 6; i++) {
                byte temp1 = buffer.get();
                ByteTo macbyte1 = new ByteTo(temp1);
                destinationMACByte[i] = temp1;
                if (destinationMAC != null) {
                    destinationMAC = destinationMAC + " " + macbyte1.bToString();
                } else {
                    destinationMAC = macbyte1.bToString();
                }
            }
        }
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public byte[] getDestinationMACByte() {
        return destinationMACByte;
    }

    public byte[] getSourceMACByte() {
        return sourceMACByte;
    }

    public String getDestinationMAC() {
        return destinationMAC;
    }

    public Integer getFrameLength() {
        return frameLength;
    }

    public Integer getFrameLengthWire() {
        return frameLengthWire;
    }

    public String getFrameType() {
        return frameType;
    }

    public int getId() {
        return id;
    }

    public String getSourceMAC() {
        return sourceMAC;
    }

}
