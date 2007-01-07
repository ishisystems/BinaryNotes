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
using System.Threading;
using System.Collections.Generic;
using org.bn.mq.protocol;
using org.bn.mq.net;
namespace org.bn.mq.net.tcp
{
	
	
	public class Reader 
	{
		protected internal ReaderStorage storage;
		protected internal bool finish = false;
		
		public Reader(ReaderStorage storage)
		{
			this.storage = storage;
		}
		
		public virtual void  stop()
		{
			finish = true;
		}
		
		public virtual void  Run()
		{
			IList<Transport> transports = null;
			do 
			{
				try
				{
					transports = storage.waitTransports();
					if (transports != null)
					{						
						foreach(Transport transport in transports)
						{
							ByteBuffer buffer;
							try
							{
								buffer = transport.receiveAsync();
								transport.fireReceivedData(buffer);
							}
							catch (System.Exception e)
							{
								//System.err.println(e);
								//e.printStackTrace();
							}
						}
					}
				}
				catch (System.Exception e)
				{
                    Console.WriteLine(e.ToString());
				}
			}
			while (!finish);
		}
		
		~Reader()
		{
			stop();
		}
	}
}