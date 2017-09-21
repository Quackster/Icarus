package org.alexdev.icarus.encryption;

/**
 * Edited: 11/25/2015 (Scott Stamp).
 * (from Morgoth, thanks Jordan!)
 */
import java.math.BigInteger;
import java.util.Random;

public class DiffieHellman {
    public int BITLENGTH = 30;

    public BigInteger Prime;
    public BigInteger Generator;

    public BigInteger PrivateKey;
    public BigInteger PublicKey;

    public BigInteger PublicClientKey;

    public BigInteger SharedKey;

    public DiffieHellman() {
        this.InitDH();
    }

    public DiffieHellman(int b) {
        this.BITLENGTH = b;

        this.InitDH();
    }

    public DiffieHellman(BigInteger prime, BigInteger generator) {
        this.Prime = prime;
        this.Generator = generator;

        this.PrivateKey = new BigInteger(GenerateRandomHexString(BITLENGTH), 16);

        if (this.Generator.intValue() > this.Prime.intValue()) {
            BigInteger temp = this.Prime;
            this.Prime = this.Generator;
            this.Generator = temp;
        }

        this.PublicKey = this.Generator.modPow(this.PrivateKey, this.Prime);
    }

    public static String GenerateRandomHexString(int len) {
        int rand = 0;
        String result = "";

        Random rnd = new Random();

        for (int i = 0; i < len; i++) {
            rand = 1 + (int) (rnd.nextDouble() * 254); // 1 - 255
            result += Integer.toString(rand, 16);
        }
        return result;
    }

    private void InitDH() {
        this.PublicKey = BigInteger.valueOf(0);
        Random random = new Random();
        while (this.PublicKey.intValue() == 0) {
            this.Prime = BigInteger.probablePrime(BITLENGTH, random);
            this.Generator = BigInteger.probablePrime(BITLENGTH, random);

            this.PrivateKey = new BigInteger(GenerateRandomHexString(BITLENGTH), 16);

            if (this.PrivateKey.intValue() < 1) {
                continue;
            }

            if (this.Generator.intValue() > this.Prime.intValue()) {
                BigInteger temp = this.Prime;
                this.Prime = this.Generator;
                this.Generator = temp;
            }

            this.PublicKey = this.Generator.modPow(this.PrivateKey, this.Prime);
        }
    }

    public void GenerateSharedKey(String ckey) {
        this.PublicClientKey = new BigInteger(ckey);

        this.SharedKey = this.PublicClientKey.modPow(this.PrivateKey, this.Prime);
    }
}