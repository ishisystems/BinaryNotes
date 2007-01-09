/*
* Copyright 2006 Abdulla G. Abdurakhmanov (abdulla.abdurakhmanov@gmail.com).
* 
* Licensed under the LGPL, Version 2 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*      http://www.gnu.org/copyleft/lgpl.html
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* 
* With any your questions welcome to my e-mail 
* or blog at http://abdulla-a.blogspot.com.
*/
using System;
using org.bn.coders;

namespace org.bn
{
	
	public class CoderFactory
	{
        private static CoderFactory instance = new CoderFactory();

        public static CoderFactory getInstance() {
            return instance;
        }

        public IEncoder newEncoder() {
            return newEncoder("BER");
        }

        public IEncoder newEncoder(String encodingSchema) {
            if(encodingSchema.Equals("BER",StringComparison.CurrentCultureIgnoreCase)) {
                return new BEREncoder();
            }
            else
            if (encodingSchema.Equals("PER", StringComparison.CurrentCultureIgnoreCase) || 
                encodingSchema.Equals("PER/Aligned", StringComparison.CurrentCultureIgnoreCase) ||
                encodingSchema.Equals("PER/A", StringComparison.CurrentCultureIgnoreCase))
            {
                return new PERAlignedEncoder();
            }
            else
            if (encodingSchema.Equals("PER/Unaligned", StringComparison.CurrentCultureIgnoreCase)||
                encodingSchema.Equals("PER/U", StringComparison.CurrentCultureIgnoreCase))
            {
                return new PERUnalignedEncoder();
            }
            else
            if (encodingSchema.Equals("DER", StringComparison.CurrentCultureIgnoreCase))
            {
                return new DEREncoder();
            }
            else
                return null;
        }

        public IDecoder newDecoder() {
            return newDecoder("BER");
        }

        public IDecoder newDecoder(String encodingSchema) {
            if(encodingSchema.Equals("BER", StringComparison.CurrentCultureIgnoreCase)) {
                return new BERDecoder();
            }
            else
            if (encodingSchema.Equals("PER", StringComparison.CurrentCultureIgnoreCase) || 
                encodingSchema.Equals("PER/Aligned", StringComparison.CurrentCultureIgnoreCase)||
                encodingSchema.Equals("PER/A", StringComparison.CurrentCultureIgnoreCase))
            {
                return new PERAlignedDecoder();
            }
            else
            if (encodingSchema.Equals("PER/Unaligned", StringComparison.CurrentCultureIgnoreCase)||
                encodingSchema.Equals("PER/U", StringComparison.CurrentCultureIgnoreCase))
            {
                return new PERUnalignedDecoder();
            }
            else
            if (encodingSchema.Equals("DER", StringComparison.CurrentCultureIgnoreCase))
            {
                return new DERDecoder();
            }
            else
                return null;
        }
	}
}