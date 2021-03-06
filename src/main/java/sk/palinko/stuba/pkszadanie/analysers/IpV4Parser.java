/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.palinko.stuba.pkszadanie.analysers;

import sk.palinko.stuba.pkszadanie.equip.DataTypeHelper;
import java.util.ArrayList;
import org.krakenapps.pcap.util.Buffer;

/**
 *
 * @author Mathis
 */
public class IpV4Parser extends AbstractAnalyser implements IAnalyser {

    private final byte[] sourceIPbyte = new byte[4];
    private String sourceIP;
    private String destinationIP;
    private final byte[] destinationIPbyte = new byte[4];
    private Integer ipV4length;
    private Integer ipV4theMostSentBytes = 0;
    private Integer ihl;
    private IcmpParser icmpParser;
    private byte ipv4Protocol;
    private String ipv4ProtocolName;
    private boolean isIcmp;
    private boolean isTcp;
    private boolean isUdp;
    private UdpParser udpParser = null;
    private TcpParser tcpParser = null;
 

    public IpV4Parser(Buffer buffer) {
        
        super(buffer);
    }

    @Override
    public void analyse() {
        ihl = DataTypeHelper.getIhl(buffer.get());

        buffer.skip(1);
        byte[] ipv4Length = new byte[2];
        ipv4Length[0] = buffer.get();
        ipv4Length[1] = buffer.get();
        ipV4length = DataTypeHelper.toInt(ipv4Length);

        if (ihl > 5) {
            ipV4length = ihl * 4;
        }
        ipV4theMostSentBytes = ipV4length + 14;
        buffer.skip(5);
        ipv4Protocol = buffer.get();
        ipv4ProtocolName = DataTypeHelper.portMap.get(DataTypeHelper.singleToInt(ipv4Protocol));
        buffer.skip(2);
        for (int i = 0; i < 4; i++) {
            sourceIPbyte[i] = buffer.get();
            // DataTypeHelper newInt = new DataTypeHelper(sourceIPbyte[i]);
            if (i == 0) {
                sourceIP = DataTypeHelper.singleToInt(sourceIPbyte[i]).toString();
            } else {
                sourceIP += DataTypeHelper.singleToInt(sourceIPbyte[i]).toString();

            }
            if (i < 3) {
                sourceIP += ".";
            } else {
                sourceIP += "\n";
            }
        }
        for (int i = 0; i < 4; i++) {
            destinationIPbyte[i] = buffer.get();
            // DataTypeHelper newInt = new DataTypeHelper(destinationIPbyte[i]);
            if (i == 0) {
                destinationIP = DataTypeHelper.singleToInt(destinationIPbyte[i]).toString();
            } else {
                destinationIP += DataTypeHelper.singleToInt(destinationIPbyte[i]).toString();

            }
            if (i < 3) {
                destinationIP += ".";
            } else {
                destinationIP += "\n";
            }
        }

        if (ihl > 5) {
            buffer.skip(ihl - 20);
        }

        if (ipv4ProtocolName.equalsIgnoreCase("ICMP")) {
            isIcmp = true;
            icmpParser = new IcmpParser(buffer);
        } 
        else if (ipv4ProtocolName.equalsIgnoreCase("TCP")) {
            isTcp = true;

            tcpParser = new TcpParser(buffer);
        } 
        else if (ipv4ProtocolName.equalsIgnoreCase("UDP")) {
            isUdp = true;
            udpParser = new UdpParser(buffer);
        } else {
            if(DataTypeHelper.otherPorts.contains(ipv4ProtocolName) == false){
            DataTypeHelper.otherPorts.add(ipv4ProtocolName);
            }
        }
    }

    public boolean getIsIcmp() {
        return isIcmp;
    }

    public TcpParser getTcpParser() {
        return tcpParser;
    }

    public boolean getIsTcp() {
        return isTcp;
    }

    public UdpParser getUdpParser() {
        return udpParser;
    }

    public boolean isIsUdp() {
        return isUdp;
    }

    public boolean isIsTcp() {
        return isTcp;
    }

    public Integer getiPv4length() {
        return ipV4length;
    }

    public void setIpV4TheMostSentBytes(Integer ipV4) {
        this.ipV4theMostSentBytes = ipV4;
    }

    public Integer getIpV4theMostSentBytes() {
        return ipV4theMostSentBytes;
    }

    public byte[] getSourceIPbyte() {
        return sourceIPbyte;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public byte[] getDestinationIPbyte() {
        return destinationIPbyte;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public IcmpParser getIcmpParser() {
        return icmpParser;
    }

}
