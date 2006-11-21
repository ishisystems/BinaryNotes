package org.bn.coders;

public class CoderUtils {
    public static int getIntegerLength(int value) {
        int mask = 0x7f800000;
        int sizeOfInt = 4;
        if (value < 0) {
            while (((mask & value) == mask) && (sizeOfInt > 1)) {
              mask = mask >> 8 ;
              sizeOfInt-- ;
            }
        }
        else {
          while (((mask & value) == 0) && (sizeOfInt > 1)) {
            mask = mask >> 8 ;
            sizeOfInt -- ;
          }
        }
        return sizeOfInt;
    }
    
    public static int getPositiveIntegerLength(int value) {
        if (value < 0) {
            int mask = 0x7f800000;
            int sizeOfInt = 4;        
            while (((mask & ~value) == mask) && (sizeOfInt > 1)) {
              mask = mask >> 8 ;
              sizeOfInt-- ;
            }
            return sizeOfInt;
        }
        else
            return getIntegerLength(value);
    }
    
}
