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
using System.Collections.Generic;
using org.bn.mq.protocol;
namespace org.bn.mq.net.tcp
{
	
	public class Writer 
	{
		protected internal WriterStorage storage;
		
		public Writer(WriterStorage storage)
		{
			this.storage = storage;
		}
		
		public virtual void  Run()
		{
			TransportPacket packet = null;
			do 
			{				
				try
				{
					packet = storage.waitPacket();
                    if (packet != null)
                    {
                        packet.Transport.send(packet.Data);
                    }
				}
				catch (System.IO.IOException ex)
				{					
					System.Console.Error.WriteLine("Unable to write packet for transport " + packet.Transport);
                    Console.WriteLine(ex.ToString());
				}
			}
			while (packet != null);
		}
		
		internal virtual void  stop()
		{
		}
	}
}