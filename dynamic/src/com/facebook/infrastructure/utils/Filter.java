package com.facebook.infrastructure.utils;

import com.facebook.infrastructure.io.ICompactSerializer;

import java.io.UnsupportedEncodingException;

public abstract class Filter {
    int hashCount;
    
    private static MurmurHash hasher = new MurmurHash();

    int getHashCount()
    {
        return hashCount;
    }

    public int[] getHashBuckets(String key) {
        return Filter.getHashBuckets(key, hashCount, buckets());
    }

    abstract int buckets();
    public abstract void add(String key);
    public abstract boolean isPresent(String key);

    // for testing
    abstract ICompactSerializer tserializer();
    abstract int emptyBuckets();

    // murmur is faster than a sha-based approach and provides as-good collision
    // resistance.  the combinatorial generation approach described in
    // http://www.eecs.harvard.edu/~kirsch/pubs/bbbf/esa06.pdf
    // does prove to work in actual tests, and is obviously faster
    // than performing further iterations of murmur.
    static int[] getHashBuckets(String key, int hashCount, int max) {
        byte[] b;
        try {
            b = key.getBytes("UTF-16");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        int[] result = new int[hashCount];
        int hash1 = hasher.hash(b, b.length, 0);
        int hash2 = hasher.hash(b, b.length, hash1);
        for (int i = 0; i < hashCount; i++) {
            result[i] = Math.abs((hash1 + i * hash2) % max);
        }
        return result;
    }}