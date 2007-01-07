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
	
	public class ConnectorTransport:Transport
	{
		protected internal AutoResetEvent awaitConnectEvent;
		protected internal ConnectorFactory factory;
		
		public ConnectorTransport(Uri addr, ConnectorFactory factory):base(addr, factory)
		{
			this.factory = factory;
		}
		
		public virtual bool connect()
		{
            /*SocketChannel cSocket = SocketChannel.open(new InetSocketAddress(getAddr().getHost(), getAddr().getPort()));
            cSocket.configureBlocking(false);
            if (cSocket.finishConnect())
            {
                setSocket(cSocket);
                return true;
            }
            else*/
            return false;             
		}
		
		protected internal virtual void  onConnected()
		{
			awaitConnectEvent.Set();
			fireConnectedEvent();
		}
		
		protected internal virtual void  onNotConnected()
		{
            awaitConnectEvent.Set();
			factory.reconnect(this);
		}
		
		protected internal virtual void  onDisconnect()
		{
            lock (addr)
            {
                fireDisconnectedEvent();
                if (getSocket() != null)
                {
                    setSocket(null);
                    factory.reconnect(this);
                }
            }
		}
		
		protected internal override void onTransportClosed()
		{
			factory.ConnectorStorage.addDisconnectedTransport(this);
		}
		
		public virtual bool finishConnect()
		{
			try
			{
				awaitConnectEvent.WaitOne();
			}
			catch (System.Threading.ThreadInterruptedException e)
			{
				// TODO
			}
			return isAvailable();
		}
	}
}