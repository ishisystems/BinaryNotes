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

namespace org.bn.mq.net.tcp
{
	
	public abstract class Transport : ITransport
	{
		protected Uri addr;
		private String socket = null;
		//protected internal ReadWriteLock socketLock = new ReentrantReadWriteLock();
		protected internal SocketFactory socketFactory;
		
		//protected internal ReadWriteLock listenersLock = new ReentrantReadWriteLock();
		protected IList < ITransportConnectionListener > listeners = new List < ITransportConnectionListener >();
		
        //protected internal ReadWriteLock readersLock = new ReentrantReadWriteLock();
		protected IList < ITransportReader > readers = new List < ITransportReader >();
		
		protected internal ITransportReader unhanledReader = null;
		
		protected internal ByteBuffer tempReceiveBuffer = ByteBuffer.allocate(64 * 1024);
		protected internal ITransportMessageCoder messageCoder;
		
		protected internal AutoResetEvent callLockEvent = new AutoResetEvent(false);
		protected string currentCallMessageId = "-none-";
		protected internal MessageEnvelope currentCallMessage = null;
		
		public Transport(Uri addr, SocketFactory factory)
		{
			setAddr(addr);
			this.socketFactory = factory;
		}
		
		protected internal virtual void  onTransportClosed()
		{
			close();
		}

		virtual public void setUnhandledMessagesReader(ITransportReader value)
		{
			//readersLock.writeLock().lock_Renamed();
            lock(readers)
            {
				this.unhanledReader = value;
            }
			//readersLock.writeLock().unlock();			
		}

        public String getSocket() 
        {
            return null;
        }

        public void setSocket(String socket)
        {
			/*set
			{
				socketLock.writeLock().lock_Renamed();
				this.socket = value;
				if (this.socketFactory != null)
				{
					if (this.socket != null)
					{
						this.socketFactory.ReaderStorage.addTransport(this);
						this.socketFactory.WriterStorage.addAliveReqInspection(this);
						messageCoder = socketFactory.getTransportFactory().TransportMessageCoderFactory.newCoder(this);
					}
					else
					{
						this.socketFactory.ReaderStorage.removeTransport(this);
						this.socketFactory.WriterStorage.delAliveReqInspection(this);
					}
				}
				socketLock.writeLock().unlock();
			}*/
        }


		
		public virtual void  close()
		{
            lock(addr) // Compact Framework doesn't supported RW-Mutex & Semaphores :(
            {
			    if (isAvailable())
			    {
				    try
				    {
					    //Socket.close();
					    setSocket(null);
				    }
				    catch (System.IO.IOException e)
				    {
					    // TODO
				    }
			    }
            }
		}
		
		
		public virtual ByteBuffer receiveAsync()
		{
			ByteBuffer result = null;
			if (isAvailable())
			{
				result = ByteBuffer.allocate(4096);
				int readedBytes = - 1;
				do 
				{
                    lock(addr) // Compact Framework doesn't supported RW-Mutex & Semaphores :(
                    {
					    tempReceiveBuffer.clear();
					    /*SocketChannel channel = Socket;
					    if (channel != null)
						    readedBytes = channel.read(tempReceiveBuffer);
                         */
					    if (readedBytes > 0)
					    {
						    if (result.Remaining < readedBytes)
						    {
							    byte[] data = result.Value;
							    result = ByteBuffer.allocate(data.Length + 4096);
							    result.put(data);
						    }
						    result.put(tempReceiveBuffer.Value, result.Position, tempReceiveBuffer.Position);
					    }
					    if (readedBytes == - 1)
					    {
						    onTransportClosed();
					    }
                    }   
			    }
			    while (readedBytes > 0);
			    result.flip();
			}
			else
			{
				throw new System.IO.IOException("Not connected");
			}
			return result;
		}
		
		public virtual void  sendAsync(ByteBuffer buffer)
		{
			if (socketFactory.WriterStorage != null)
			{
				if (isAvailable())
				{
					socketFactory.WriterStorage.pushPacket(this, buffer);
				}
				else
					throw new System.IO.IOException("Transport is not connected!");
			}
			else
				throw new System.IO.IOException("Unable to write readonly transport!");
		}
		
		public virtual void  sendAsync(byte[] buffer)
		{
			sendAsync(ByteBuffer.wrap(buffer));
		}
		
		public virtual void  send(MessageEnvelope message)
		{
			if (isAvailable())
			{
				ByteBuffer buffer = messageCoder.encode(message);
				send(buffer);
			}
			else
				throw new System.IO.IOException("Transport is not connected!");
		}
		
		public virtual void  sendAsync(MessageEnvelope message)
		{
			if (isAvailable())
			{
				ByteBuffer buffer = messageCoder.encode(message);
				sendAsync(buffer);
			}
			else
				throw new System.IO.IOException("Transport is not connected!");
		}
		
		public virtual void  send(ByteBuffer buffer)
		{
			try
			{
				lock(addr) 
                {
				    if (isAvailable())
				    {
					    /*SocketChannel channel = Socket;
					    if (channel != null)
						    channel.write(buffer);
                         */
				    }
				    else
				    {
					    throw new System.IO.IOException("Not connected");
				    }
                }
			}
			catch (System.Exception ex)
			{
				this.onTransportClosed();
			}
		}
		
		public virtual void  send(byte[] buffer)
		{
			send(ByteBuffer.wrap(buffer));
		}
		
		public virtual void  addConnectionListener(ITransportConnectionListener listener)
		{
			//synchronized(listeners) {
			//listenersLock.writeLock().lock_Renamed();
            lock(listeners)
            {
			    listeners.Add(listener);
            }
			//listenersLock.writeLock().unlock();
		}
		
		public virtual void  delConnectionListener(ITransportConnectionListener listener)
		{
            lock(listeners)
            {
			    listeners.Remove(listener);
            }
		}
		
		public virtual void  fireConnectedEvent()
		{
			lock(listeners) 
            {
				foreach(ITransportConnectionListener listener in listeners)
				{
					listener.onConnected(this);
				}
			}
		}
		
		public virtual void  fireDisconnectedEvent()
		{
			lock(listeners) 
            {
				foreach(ITransportConnectionListener listener in listeners)
				{
					listener.onDisconnected(this);
				}
			}
		}
		
		protected internal virtual bool processReceivedCallMessage(MessageEnvelope message)
		{
			bool result = false;
		    if (message != null)
		    {
			    if (currentCallMessageId.Equals(message.Id))
			    {
				    currentCallMessage = message;					    
				    result = true;
			    }
		    }
            if(result) 
            {
                callLockEvent.Set();
            }
			
			if (!result)
			{
				AsyncCallManager mgr = socketFactory.getTransportFactory().AsyncCallManager;
				AsyncCallItem callAsyncResult = mgr.getAsyncCall(message);
				if (callAsyncResult != null)
				{
					result = true;
					if (callAsyncResult.Listener != null)
					{
						callAsyncResult.Listener.onCallResult(callAsyncResult.Request, message);
					}
				}
			}
			
			return result;
		}
		
		protected internal virtual void  doProcessReceivedData(MessageEnvelope message, Transport forTransport)
		{
			if (message.Body.isAliveRequestSelected())
				return ;
			bool doProcessListeners = !forTransport.processReceivedCallMessage(message);
			
			if (doProcessListeners)
			{
                lock(readers)
                {
				    bool handled = false;
				    foreach(ITransportReader reader in readers)
				    {
					    handled = reader.onReceive(message, forTransport);
				    }
				    if (!handled && this.unhanledReader != null)
					    this.unhanledReader.onReceive(message, forTransport);
                }
			}
		}
		
		protected internal virtual void  doProcessReceivedData(ByteBuffer packet, Transport forTransport)
		{
			MessageEnvelope message = messageCoder.decode(packet);
			doProcessReceivedData(message, forTransport);
		}
		
		protected internal virtual void  fireReceivedData(ByteBuffer packet)
		{
			doProcessReceivedData(packet, this);
		}
		
		public virtual MessageEnvelope call(MessageEnvelope message, int timeout)
		{
			MessageEnvelope result = null;
			lock (this)
			{
				currentCallMessage = null;
				currentCallMessageId = message.Id;
			    sendAsync(message);
		        callLockEvent.WaitOne(timeout*1000,false);
				result = currentCallMessage;
				currentCallMessageId = " -none- ";
			}
			if (result == null)
			{
				throw new TimeoutException("Call by RPC-style message timeout in " + this + "!");
			}
		    return result;
		}
		
		public virtual MessageEnvelope call(MessageEnvelope message)
		{
			return this.call(message, 120); // By default timeout is 2 min
		}
		
		public virtual void  callAsync(MessageEnvelope message, ITransportCallListener listener, int timeout)
		{
			AsyncCallManager mgr = socketFactory.getTransportFactory().AsyncCallManager;
			try
			{
				mgr.storeRequest(message, listener, timeout);
				sendAsync(message);
			}
			catch (System.Exception ex)
			{
				mgr.getAsyncCall(message.Id);
				throw ex;
			}
		}
		
		public virtual void  callAsync(MessageEnvelope message, ITransportCallListener listener)
		{
			this.callAsync(message, listener, 120); // By default timeout is 2 min
		}
		
		public virtual Uri getAddr()
		{
			return addr;
		}
		
		protected internal virtual void  setAddr(Uri addr)
		{
			this.addr = addr;
		}
		
		public virtual bool isAvailable()
		{
            lock(addr)
            {
			    //bool result = Socket != null && Socket.isOpen() && Socket.isConnected();
			    //return result;
                return false;
            }
		}
		
		~Transport()
		{
			close();
		}
		
		public virtual void  addReader(ITransportReader reader)
		{
			//readersLock.writeLock().lock_Renamed();
            lock(readers)
            {
			    readers.Add(reader);
            }
		}
		
		public virtual void  delReader(ITransportReader reader)
		{
            lock(readers)
            {
			    readers.Remove(reader);
			}
		}
	}
}