package org.rtsang.cpu;

public class ALU {
    public boolean negative = false;
    public boolean zero = false;
    private byte checkFlags(byte result){
        if(result < 0){
            negative = true;
            zero = false;
        }
        else if(result == 0)
        {
            negative = false;
            zero = true;
        }
        else{
            negative = false;
            zero = false;
        }
        return result;
    }
    public byte add(byte a, byte b)
    {
        return checkFlags((byte)(a+b));
    }
    public byte sub(byte a, byte b)
    {
        return checkFlags((byte) (a-b));
    }
    public byte or(byte a, byte b)
    {
        return checkFlags((byte) (a|b));
    }
    public byte nor(byte a, byte b)
    {
        return checkFlags((byte)~(a|b));
    }
    public byte xor(byte a, byte b)
    {
        return checkFlags((byte)(a^b));
    }
    public byte and(byte a, byte b)
    {
        return checkFlags((byte)(a&b));
    }
    public byte nand(byte a, byte b)
    {
        return checkFlags((byte)~(a&b));
    }
    public byte rsh(byte a, byte b)
    {
        return checkFlags((byte)(a>>b));
    }
    public byte lsh(byte a, byte b)
    {
        return checkFlags((byte)(a<<b));
    }
    public byte div(byte a, byte b)
    {
        return checkFlags((byte) (a / b));
    }
    public byte[] mlt(byte a, byte b)
    {
        int result = a * b;
        byte lower =  (byte) (result & 0xFF);
        result = result >> 4;
        byte high = (byte) (result & 0xFF);

        negative = high < 0;
        zero = high == 0 && lower == 0;

        return new byte[]{high, lower};
    }

}
