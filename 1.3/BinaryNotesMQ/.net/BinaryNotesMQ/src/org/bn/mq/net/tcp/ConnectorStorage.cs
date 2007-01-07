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
	
	public class ConnectorStorage
	{
		public class ConnectorStorageEvent
		{
			public ConnectorStorageEvent()
			{
			}

			virtual public ConnectorTransport TransportToConnect
			{
				get
				{
					return this.transportConnect;
				}
				
				set
				{
					this.transportConnect = value;
				}
				
			}
			virtual public ConnectorTransport DisconnectedTransport
			{
				get
				{
					return this.disconnectedTransport;
				}
				
				set
				{
					this.disconnectedTransport = value;
				}
				
			}

            private ConnectorTransport transportConnect = null;
			private ConnectorTransport disconnectedTransport = null;
		}


		
		private LinkedList < ConnectorStorageEvent > awaitingEvents = new LinkedList < ConnectorStorageEvent >();
		protected internal AutoResetEvent awaitEvent;
		private bool finishThread = false;
		
		public ConnectorStorage()
		{
			
		}
		
		public virtual void  addAwaitingTransport(ConnectorTransport transport)
		{
			lock (awaitingEvents)
			{
				ConnectorStorageEvent ev = new ConnectorStorageEvent();
				ev.TransportToConnect = transport;
                awaitingEvents.AddLast(ev);
			}
			awaitEvent.Set();
		}
		
		public virtual void  addDisconnectedTransport(ConnectorTransport transport)
		{
			lock (awaitingEvents)
			{
                ConnectorStorageEvent ev = new ConnectorStorageEvent();
				ev.DisconnectedTransport = transport;
                awaitingEvents.AddLast(ev);
			}
            awaitEvent.Set();
		}
		
		
		public virtual void  removeAwaitingTransport(ConnectorTransport transport)
		{
			lock (awaitingEvents)
			{
				foreach(ConnectorStorageEvent ev in awaitingEvents)
				{
                    if (ev.TransportToConnect != null && ev.TransportToConnect.Equals(transport))
					{
						awaitingEvents.Remove(ev);
						break;
					}
				}
			}
		}

		virtual public ConnectorStorageEvent getAwaitingEvent()
		{
			ConnectorStorageEvent result = null;
			if (finishThread)
				return result;
			
			do 
			{
				lock (awaitingEvents)
				{
					if (awaitingEvents.Count > 0)
					{
                        result = awaitingEvents.First.Value;
						awaitingEvents.RemoveFirst();
					}
				}
				
				if (result == null)
				{
					try
					{
                        awaitEvent.WaitOne();
					}
					catch (System.Exception ex)
					{
						ex = null;
					}
				}
			}
			while (result == null && !finishThread);
			
			return result;
		}

		
		public virtual void  clear()
		{
			lock (awaitingEvents)
			{
				awaitingEvents.Clear();
			}
		}
		
		~ConnectorStorage()
		{
			lock (awaitingEvents)
			{
				finishThread = true;
				awaitingEvents.Clear();
			}
			awaitEvent.Set();
		}
	}
}