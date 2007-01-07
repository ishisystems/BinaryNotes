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
	
	public class ServerTransport:Transport
	{
		virtual public String ServerSocket
		{
			get
			{
				return serverChannel;
			}
			
			set
			{
				this.serverChannel = value;
			}
			
		}
        private String serverChannel = null;
		protected List < ServerClientTransport > clients = new List < ServerClientTransport >();
		protected internal AcceptorFactory acceptorFactory;
		
		public ServerTransport(Uri addr, AcceptorFactory acceptorFactory):base(addr, null)
		{
			setSocket(null);
			this.acceptorFactory = acceptorFactory;
			this.socketFactory = acceptorFactory;
		}
		
		public virtual void  startListener()
		{
			lock (this)
			{
				close();
				/*serverChannel = ServerSocketChannel.open();
				serverChannel.configureBlocking(false);
				serverChannel.socket().setReuseAddress(true);
				serverChannel.socket().bind(new InetSocketAddress(getAddr().getHost(), getAddr().getPort()));
                 */
			}
		}
		
		public override void  close()
		{
			if (isAvailable())
			{
				try
				{
					//ServerSocket.close();
				}
				catch (System.IO.IOException e)
				{
					// TODO
				}
			}
			
			lock (clients)
			{
				foreach(ServerClientTransport transport in clients)
				{
					transport.close();
				}
				clients.Clear();
			}
		}
		
				
		public override bool isAvailable()
		{
			lock (addr)
			{
				//return ServerSocket != null && ServerSocket.isOpen();
                return false;
			}
		}
		
		public virtual bool acceptClient()
		{
			/*SocketChannel client = ServerSocket.accept();
			if (client != null)
			{
				System.Net.IPAddress addr = client.socket().getInetAddress();
				System.Console.Out.WriteLine("Client connected from " + addr);
				client.configureBlocking(false);
				ServerClientTransport transport;
				try
				{
					transport = new ServerClientTransport(new URI("bnmq", addr.ToString(), "", ""), this, acceptorFactory);
					transport.setSocket(client);
					lock (clients)
					{
						clients.add(transport);
						fireConnectedEvent(transport);
					}
				}
				catch (URISyntaxException e)
				{
					e = null;
				}
			}
			return client != null;*/
            return false;
		}
		
		public virtual void  removeClient(ServerClientTransport transport)
		{
			lock (clients)
			{
				clients.Remove(transport);
				fireDisconnectedEvent(transport);
			}
		}
		
		protected internal virtual void  fireReceivedData(MessageEnvelope message, ServerClientTransport client)
		{
			doProcessReceivedData(message, client);
		}
		
		protected internal virtual void  fireConnectedEvent(ServerClientTransport client)
		{
            lock(listeners)
            {
				foreach(ITransportConnectionListener listener in listeners)
				{
					listener.onConnected(client);
				}
			}		
        }
		
		protected internal virtual void  fireDisconnectedEvent(ServerClientTransport client)
		{
            lock(listeners)
            {
                foreach (ITransportConnectionListener listener in listeners)
                {
					listener.onDisconnected(client);
				}
            }
		}
		
		
		~ServerTransport()
		{
			close();
		}
		
		protected internal override void  onTransportClosed()
		{
			// Do nothing. 
			//fireDisconnectedEvent();
		}
	}
}