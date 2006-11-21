using System;
using System.Collections.Generic;
using System.Text;
using System.Reflection;

namespace org.bn.coders
{
    class CoderUtils
    {
        public static T getAttribute<T>(ICustomAttributeProvider field)
        {
            object[] attrs = field.GetCustomAttributes(typeof(T), false);
            if (attrs != null && attrs.Length > 0)
            {
                T attribute = (T)attrs[0];
                return attribute;
            }
            else
                return default(T);
        }

        public static bool isAttributePresent<T>(ICustomAttributeProvider field)
        {
            object[] attrs = field.GetCustomAttributes(typeof(T), false);
            if (attrs != null && attrs.Length > 0)
                return true;
            else
                return false;
        }

        public static int getIntegerLength(int val)
        {
            int mask = 0x7f800000;
            int sizeOfInt = 4;
            if (val < 0)
            {
                while (((mask & val) == mask) && (sizeOfInt > 1))
                {
                    mask = mask >> 8;
                    sizeOfInt--;
                }
            }
            else
            {
                while (((mask & val) == 0) && (sizeOfInt > 1))
                {
                    mask = mask >> 8;
                    sizeOfInt--;
                }
            }
            return sizeOfInt;
        }

        public static int getPositiveIntegerLength(int val)
        {
            if (val < 0)
            {
                int mask = 0x7f800000;
                int sizeOfInt = 4;
                while (((mask & ~val) == mask) && (sizeOfInt > 1))
                {
                    mask = mask >> 8;
                    sizeOfInt--;
                }
                return sizeOfInt;
            }
            else
                return getIntegerLength(val);
        }


    }
}
