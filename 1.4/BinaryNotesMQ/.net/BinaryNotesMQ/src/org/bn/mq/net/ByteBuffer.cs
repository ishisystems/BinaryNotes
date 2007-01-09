using System;
using System.Collections.Generic;
using System.Text;

namespace org.bn.mq.net
{
    public class ByteBuffer
    {
        private byte[] buffer;
        public byte[] Value
        {
            get { return buffer; }
            set { buffer = value; }
        }

        private int position = 0;
        public int Position
        {
            get { return position; }
            set { position = value; }
        }

        private int limit = 0;
        public int Limit
        {
            get { return limit; }
        }

        public int Remaining
        {
            get { return buffer.Length - limit; }
        }

        protected ByteBuffer()
        {
        }

        public static ByteBuffer allocate(int size)
        {
            ByteBuffer result = new ByteBuffer();
            result.Value = new byte[size];
            return result;
        }

        public ByteBuffer slice()
        {
            ByteBuffer result = new ByteBuffer();
            result.Value = new byte[limit];
            result.put(buffer, 0, limit);            
            return result;
        }


        public void put(byte bt)
        {
            buffer[position++] = bt;
            limit++;
        }

        public void put(byte[] value)
        {
            put(value, 0, value.Length);
        }

        public void put(byte[] value, int offset, int len)
        {
            Buffer.BlockCopy(value, offset, buffer, position, len);
            position += len - offset;
            limit += len - offset;
        }


        public void putShort(short value)
        {
            byte[] valAsBytes = BitConverter.GetBytes(value);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(valAsBytes);
            }
            put(valAsBytes);
        }

        public void putInt(int value)
        {
            byte[] valAsBytes = BitConverter.GetBytes(value);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(valAsBytes);
            }
            put(valAsBytes);
        }

        public byte get()
        {
            return buffer[position++];
        }

        public void get(byte[] buf)
        {
            Buffer.BlockCopy(buffer, position, buf, 0, buf.Length);
            position += buf.Length;
        }

        public short getShort()
        {
            byte[] valAsBytes = new byte[2];
            get(valAsBytes);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(valAsBytes);
            }

            short result = BitConverter.ToInt16(valAsBytes, 0);
            return result;
        }

        public int getInt()
        {
            byte[] valAsBytes = new byte[4];
            get(valAsBytes);
            if (BitConverter.IsLittleEndian)
            {
                Array.Reverse(valAsBytes);
            }
            int result = BitConverter.ToInt32(valAsBytes, 0);
            return result;
        }



        internal void clear()
        {
            position = 0;
            limit = 0;
        }

        internal void flip()
        {
            limit = position;
            position = 0;            
        }

        internal static ByteBuffer wrap(byte[] buffer)
        {
            ByteBuffer result = new ByteBuffer();
            result.Value = buffer;
            return result;
        }
    }
}
