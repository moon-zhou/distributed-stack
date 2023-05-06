package org.moonzhou.consistenthash.hashimpl;

import org.moonzhou.consistenthash.HashStrategy;

public class JdkHashCodeStrategy implements HashStrategy {

    @Override
    public int getHashCode(String origin) {
        return origin.hashCode();
    }

}
